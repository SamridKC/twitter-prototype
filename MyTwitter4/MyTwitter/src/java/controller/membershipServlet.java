/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import dataaccess.FollowerDB;
import dataaccess.HashtagDB;
import dataaccess.UserDB;
import dataaccess.TweetDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.MailUtilGmail;
import util.PasswordUtil;
//import util.PasswordUtil;

@WebServlet(name = "membershipServlet", urlPatterns = {"/membership"})
public class membershipServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int numberOfTweets = 0;
        int numberOfFollowers = 0;
        String action = request.getParameter("action");
        String url = "";
        String message = "";

        HttpSession session = request.getSession();
//        Cookie[] cookies = request.getCookies();     // request is an instance of type 
//        boolean foundCookie = false;
//        for (int i = 0; i < cookies.length; i++) {
//            Cookie c = cookies[i];
//            if (c.getName().equals("emailRemember")) {
//                String emailRemember = c.getValue();
//                foundCookie = true;
//                session.setAttribute("emailRemember", emailRemember);
//            }
//        }
//        

        if (action.equals("signup")) {
            url = "/signup.jsp";

            //HttpSession session = request.getSession();
//            User user = (User) session.getAttribute("user");
            // get parameters from the request
            String fullName = request.getParameter("fullname");
            String userName = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmpassword");
            String DOB = request.getParameter("dateofbirth");
            String securityQuestion = request.getParameter("question");
            String answer = request.getParameter("securityanswer");

            String mySalt = PasswordUtil.getSalt();

            User u = new User();
            u.setFullName(fullName);
            u.setUserName(userName);
            u.setEmail(email);
            try {
                u.setPassword(PasswordUtil.hashPassword(password + mySalt));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            u.setSalt(mySalt);
            u.setBirthDate(DOB);
            u.setQuestionNo(securityQuestion);
            u.setAnswer(answer);

            User returned = null;

            try {
                returned = UserDB.search(email);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            int length = password.length();
            if ((password.length() > 7) && password.equals(confirmPassword) && !fullName.isEmpty() && !userName.isEmpty()
                    && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !DOB.isEmpty()
                    && !securityQuestion.isEmpty() && !answer.isEmpty() && returned == null) {
                try {
                    UserDB.insert(u);
                    request.setAttribute("user", u);
                } catch (ClassNotFoundException e) {
                    Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, e);
                }

                request.setAttribute("user", u);
                //url = "/home.jsp";
                url = "/membership?action=update";
            } else if (returned != null) {
                //              request.setAttribute("user", u);
                request.setAttribute("emailRemember", email);
                url = "/login.jsp";
                message = "Email already exists!";
                request.setAttribute("errorMessage", message);
            } else {
                if (password.length() < 8) {
                    message = "Password must be at least 8 characters long.";
                    request.setAttribute("passwordError", message);
                    url = "/signup.jsp";
                }

                if (!password.equals(confirmPassword)) {
                    message = "Password and Confirm Password do not match";
                    request.setAttribute("confirmPasswordError", message);
                    url = "/signup.jsp";
                }

                if (fullName.isEmpty()) {
                    message = "Enter Full Name";
                    request.setAttribute("fullNameError", message);
                    url = "/signup.jsp";
                }
                if (userName.isEmpty()) {
                    message = "Enter User Name";
                    request.setAttribute("userNameError", message);
                    url = "/signup.jsp";
                }
                if (email.isEmpty()) {
                    message = "Enter Email";
                    request.setAttribute("emailError", message);
                    url = "/signup.jsp";
                }

                if (password.isEmpty()) {
                    message = "Enter Password";
                    request.setAttribute("passwordError", message);
                    url = "/signup.jsp";
                    if (confirmPassword.isEmpty()) {
                        message = "Enter Confirm Password";
                        request.setAttribute("confirmpasswordError", message);
                        url = "/signup.jsp";
                    }
                }

                if (DOB.isEmpty()) {
                    message = "Enter DOB";
                    request.setAttribute("dateofbirthError", message);
                    url = "/signup.jsp";
                }

                if (answer.isEmpty()) {
                    message = "Enter Security Answer";
                    request.setAttribute("answerError", message);
                    url = "/signup.jsp";
                }
       //         request.setAttribute("user", u);
                url = "/signup.jsp";
            }

        } //***********************************************************************
        // LOGIN
        //***********************************************************************
        else if (action.equals("login")) {

            HttpSession Session = request.getSession();
            User u = (User) Session.getAttribute("user");

            if (u == null) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String hashedPass = null;
                try {
                    u = UserDB.search(email);
                    Session.setAttribute("user", u);
                    if (u != null) {
                        hashedPass = PasswordUtil.hashPassword(password + u.getSalt());
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (u == null) {
                    url = "/login.jsp";
                    message = "* Email or Password does not match.";
                    request.setAttribute("errorMessage", message);

                } else if (u.getPassword().equals(hashedPass)) {
                    String str = (String) request.getParameter("remember");
                    if (str != null && str.equals("Remember")) {
                        Cookie c = new Cookie("emailRemember", request.getParameter("email"));
                        c.setPath("/");                      // allow entire app to access it
                        c.setMaxAge(24 * 60 * 60);
                        response.addCookie(c);
                    }
                    message = "";
                    request.setAttribute("errorMessage", message);
                    try {
                        String previousLogin = u.getLastLoginTime();
                        Session.setAttribute("previousLogin", previousLogin);
                        UserDB.newLogin(u.getEmail());
                        ArrayList followed = FollowerDB.followed(u.getEmail());
                        numberOfTweets = TweetDB.numOfTweets(u.getEmail());
                        numberOfFollowers = FollowerDB.followerCount(u.getEmail());
                        ArrayList alltweets = TweetDB.search(u.getEmail(), followed);
                        ArrayList allUsers = UserDB.searchAll();
                        ArrayList topTenTrends = HashtagDB.trends();
                        request.setAttribute("numberT", numberOfTweets);
                        request.setAttribute("allTweets", alltweets);
                        request.setAttribute("allUsers", allUsers);
                        request.setAttribute("followed", followed);
//                        session.setAttribute("allUsers", allUsers);
                        request.setAttribute("topTen", topTenTrends);
                        request.setAttribute("followersCount", numberOfFollowers);

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    url = "/home.jsp";
                } else {
                    Session.removeAttribute("user");
                    url = "/login.jsp";
                    message = "*Login Failed! Email or Password does not match.";
                    request.setAttribute("errorMessage", message);
                }
            } else {
                Session.setAttribute("user", u);
                message = "";
                request.setAttribute("errorMessage", message);
                try {
                    ArrayList followed = FollowerDB.followed(u.getEmail());

                    numberOfFollowers = FollowerDB.followerCount(u.getEmail());
                    numberOfTweets = TweetDB.numOfTweets(u.getEmail());
                    ArrayList alltweets = TweetDB.search(u.getEmail(), followed);
                    ArrayList allUsers = UserDB.searchAll();
                    request.setAttribute("numberT", numberOfTweets);
                    request.setAttribute("allTweets", alltweets);
                    request.setAttribute("allUsers", allUsers);
//                    session.setAttribute("allUsers", allUsers);
                    request.setAttribute("followed", followed);
                    ArrayList topTenTrends = HashtagDB.trends();
                    request.setAttribute("topTen", topTenTrends);
                    request.setAttribute("followersCount", numberOfFollowers);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                url = "/home.jsp";

            }
        } else if (action.equals("signout")) {
            url = "/login.jsp";
            // HttpSession session = request.getSession();
            session.removeAttribute("u");
            request.setAttribute("errorMessage", "");
            session.invalidate();
        } // *****************
        // TWEET POST 
        // *****************
        else if (action.equals("tweetPost")) {
            //HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            // get parameters from the request
            String tweet = request.getParameter("tweet");
            String email = user.getEmail();

            if (!tweet.isEmpty()) {
                try {
                    int tweetID = TweetDB.insert(tweet, email);
                    HashtagDB.insert(tweet, tweetID);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            url = "/membership?action=login";

        } else if (action.equals("profile")) {

            HttpSession Session = request.getSession();

            User u = (User) Session.getAttribute("user");

            User user = null;
            try {
                user = UserDB.search(u.getEmail());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (user != null) {
                Session.setAttribute("buttonVal", "Update");
            }
            // else just return null?

            url = "/signup.jsp";
        } else if (action.equals("update")) {

            // get parameters from the request
            String fullName = request.getParameter("fullname");
            String userName = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmpassword");
            String DOB = request.getParameter("dateofbirth");
            String securityQuestion = request.getParameter("question");
            String answer = request.getParameter("securityanswer");

            String mySalt = PasswordUtil.getSalt();

            User u = new User();
            u.setFullName(fullName);
            u.setUserName(userName);
            u.setEmail(email);

            try {
                u.setPassword(PasswordUtil.hashPassword(password + mySalt));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            u.setSalt(mySalt);
            u.setBirthDate(DOB);
            u.setQuestionNo(securityQuestion);
            u.setAnswer(answer);
            HttpSession Session = request.getSession();

            //User u = (User) Session.getAttribute("user");
            User user = null;
            try {
                user = UserDB.search(u.getEmail());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (user != null) {
                try {
                    UserDB.Update(u);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                Session.setAttribute("user", u);
                Session.setAttribute("buttonVal", "Login");
            }

            url = "/membership?action=login";
        } // ****************
        // Forgot Password 
        // ****************
        else if (action.equals("forgotpassword")) {
            try {
                String email = request.getParameter("email");
                String questionNo = request.getParameter("question");
                String answer = request.getParameter("securityanswer");

                User temp = UserDB.search(email);
                if (temp != null) {
                    char[] tempPassword = new char[8];
                    // display email already exists
                    if (temp.getQuestionNo().equals(questionNo) && temp.getAnswer().equals(answer)) {
                        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                        SecureRandom rnd = new SecureRandom();
                        for (int i = 0; i < 8; i++) {
                            tempPassword[i] = characters.charAt(rnd.nextInt(characters.length()));
                        }
                        String testEmail = temp.getEmail();
                        String test = temp.getFullName();
                        String newPassword = new String(tempPassword);
                        temp.setPassword(PasswordUtil.hashPassword(newPassword + temp.getSalt()));
                        UserDB.Update(temp);

                        String to = email;
                        String from = "samwebapi@gmail.com";
                        Boolean isBodyHtml = false;

                        String subject = "Password Reset from MiniTwitter";
                        String body = "Hello " + temp.getFullName() + "!\n"
                                + "This is your temporary password. Please login and change it to your password!\n"
                                + "Password: " + newPassword + "\nThank you.\n";
                        try {
                            MailUtilGmail.sendMail(to, from, subject, body, true);
                            //MailUtilGmail.sendMail(to, from, subject, body, isBodyHtml);
                        } catch (MessagingException e) {
                            System.out.println(e);
                        }
                        message = "Temporary password sent to " + email;
                        request.setAttribute("message", message);
                        url = "/forgotpassword.jsp";
                    } else {
                        message = "User info is not correct";
                        request.setAttribute("message", message);
                        url = "/forgotpassword.jsp";
                    }
                } else {
                    message = "User is not found";
                    request.setAttribute("message", message);
                    url = "/signup.jsp";
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } // ****************
        // Delete Tweet
        // ****************
        else if (action.equals("deleteTweet")) {
            String id = request.getParameter("tweet");

            try {
                TweetDB.deleteTweet(id);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            url = "/membership?action=login";
        } // ****************
        // gotoHashtag Tweet
        // ****************
        else if (action.equals("gotoHashtag")) {
            String hashtag = request.getParameter("hashtag");
            url = "/hashtag.jsp";

            try {
                ArrayList hashTweets = HashtagDB.hashTweets(hashtag);
                request.setAttribute("hashTweets", hashTweets);
                request.setAttribute("hashtag", hashtag);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } //******************************************************************
        // Follow
        //******************************************************************
        else if (action.equals("follow")) {
            User u = (User) session.getAttribute("user");

            String followUser = request.getParameter("followUser");

            try {
                // get parameters from the request
                FollowerDB.insert(followUser, u.getEmail());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            url = "/membership?action=login";
        } //******************************************************************
        // Unfollow
        //******************************************************************
        else if (action.equals("unfollow")) {
            User u = (User) session.getAttribute("user");
            // get parameters from the request
            String unfollowUser = request.getParameter("unfollowUser");

            try {
                // get parameters from the request
                FollowerDB.delete(unfollowUser, u.getEmail());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            url = "/membership?action=login";
        } else if (action.equals("notification")) {
            try {
                HttpSession Session = request.getSession();
                User u = (User) Session.getAttribute("user");
                String previous_Login = (String) Session.getAttribute("previousLogin");
                ArrayList notifications = TweetDB.allNotification(u, previous_Login);
                session.setAttribute("notifications", notifications);
                url = "/notification.jsp";
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if (action.equals("search")) {
            try {
                String query = request.getParameter("search");
                ArrayList<String> allSearch = TweetDB.allSearch(query);
                session.setAttribute("searched", query);
                session.setAttribute("allSearch", allSearch);
                url = "/search.jsp";
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
