/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Tweet;
import dataaccess.TweetDB;
import business.User;
import static dataaccess.HashtagDB.matchFinder;
import static dataaccess.UserDB.getJSON;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import static java.time.Instant.now;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author samridkc
 */
public class TweetDB {

    public static int insert(String tweet, String email) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "Insert into Tweets(Tweet, postingTime, email_Address) VALUES ('" + tweet + "', now() , '" + email + "')";

            //add values to the above SQL statement and execute it.
//            int result = 
            statement.executeUpdate(preparedSQL, Statement.RETURN_GENERATED_KEYS);

            preparedSQL = "SELECT LAST_INSERT_ID();";
            ResultSet rs = statement.executeQuery(preparedSQL);
            rs.next();
            int result = rs.getInt(1);

            rs.close();
            statement.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }

    public static ArrayList search(String emailAddress, ArrayList<User> followed) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String SQLforFollowedTweets = "";

            for (User object : followed) {
                SQLforFollowedTweets += "UNION (SELECT * from Tweets where email_Address = '" + object.getEmail() + "')";
            }

            User u = UserDB.search(emailAddress);
            String preparedSQL = "(Select * from Tweets where email_Address = '" + emailAddress + "')UNION"
                    + "(SELECT * FROM Tweets WHERE Tweet Like '%@" + u.getUserName() + "%')"
                    + SQLforFollowedTweets + "ORDER BY postingTime DESC;";

            ResultSet rs = statement.executeQuery(preparedSQL);

            ArrayList hi = getTweets(rs);

            rs.close();
            statement.close();
            connection.close();
            return hi;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return null;
    }

    public static int numOfTweets(String email) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "SELECT COUNT(*) AS total FROM twitterdb.Tweets WHERE email_Address = '" + email + "';";

            //add values to the above SQL statement and execute it.
            ResultSet rs = statement.executeQuery(preparedSQL);

            int result = 0;
            if (rs.next()) {
                result = rs.getInt(1);
            }

            rs.close();
            statement.close();
            connection.close();
            return result;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }

    public static ArrayList getTweets(ResultSet results)
            throws SQLException {

        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        ArrayList arr = new ArrayList();

        while (results.next()) {
            Tweet temp = new Tweet(colorHashtag(colorMention(results.getString(2))), results.getString(4), results.getString(1));
            arr.add(temp);
        }
        return arr;
    }

    public static String colorMention(String tweet) {
        String message = tweet;
        String newMessage = "";

        ArrayList<String> hashtags = matchFinder("@(\\w*[0-9a-zA-Z]+\\w*[0-9a-zA-Z])", tweet);  // hashtag regex

        for (String var : hashtags) {
            String replaceString = "<a class='blueX'>" + var + "</a>";
            newMessage = message.replace(var, replaceString);
            message = newMessage;
        }
        if (newMessage.isEmpty()) {
            newMessage = message;
        }
        return newMessage;
    }

    public static String colorHashtag(String tweet) {
        String message = tweet;
        String newMessage = "";

        ArrayList<String> hashtags = matchFinder("#(\\w*[0-9a-zA-Z]+\\w*[0-9a-zA-Z])", tweet);  // hashtag regex

        for (String var : hashtags) {
            String replaceString = "<a class='blueX' href ='membership?action=gotoHashtag&hashtag=%23"
                    + var.substring(1) + "'>" + var + "</a>";
            newMessage = message.replace(var, replaceString);
            message = newMessage;
        }
        if (newMessage.isEmpty()) {
            newMessage = message;
        }
        return newMessage;
    }

    public static int deleteTweet(String id) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "DELETE FROM tweetHashtag WHERE (tweetID = '" + id + "');";
            statement.executeUpdate(preparedSQL);

            preparedSQL = "DELETE FROM Tweets WHERE (idTweets = '" + id + "');";

            //add values to the above SQL statement and execute it.
            int result = statement.executeUpdate(preparedSQL);

            statement.close();
            connection.close();
            return result;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }

    public static ArrayList<String> allNotification(User u, String lastLoginTime) throws IOException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "SELECT * FROM \n"
                    + "(\n"
                    + "(SELECT NULL AS Tweet, result.emailOfFollower,  result.followTime, result.userName FROM \n"
                    + "(SELECT  Follower.emailOfFollower,  user.userName , Follower.followTime\n"
                    + "FROM Follower\n"
                    + "INNER JOIN user\n"
                    + "ON user.emailAddress = Follower.emailOfFollower\n"
                    + "where  Follower.emailOfFollowed = '" + u.getEmail() + "' )  result\n"
                    + ")\n"
                    + "UNION ALL\n"
                    + "(SELECT Tweets.Tweet, NULL AS emailOfFollower,  Tweets.postingTime, NULL AS userName FROM Tweets \n"
                    + "WHERE Tweet LIKE '%@" + u.getUserName() + "%') \n"
                    + ")\n"
                    + "rs\n"
                    + "WHERE followTime >  '" + lastLoginTime + "'\n"
                    + "ORDER BY followTime DESC";

            ResultSet results = statement.executeQuery(preparedSQL);

            ArrayList<String> notification = new ArrayList<String>();

            while (results.next()) {
                String temp = results.getString(1);
                if (temp != null) {
                    temp = colorHashtag(colorMention(temp));
                } else {
                    temp = "@" + results.getString(4) + " started following you";
                    temp = colorMention(temp);
                }
                notification.add(temp);
            }

            results.close();
            statement.close();
            connection.close();
            return notification;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return null;

    }

    public static ArrayList<String> allSearch(String search) throws IOException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "(SELECT Tweets.Tweet, NULL AS fullname,  NULL AS username, NULL AS hashtagText FROM Tweets WHERE Tweet LIKE '%"
                    + search + "%') UNION ALL (SELECT NULL AS Tweet, user.fullname,  user.username, NULL AS hashtagText FROM user WHERE fullname LIKE '%"
                    + search + "%' OR  username LIKE '%" + search + "%') UNION ALL (SELECT NULL AS Tweet, NULL AS fullname, NULL AS username, "
                    + " hashtag.hashtagText FROM hashtag WHERE hashtagText LIKE '%"
                    + search + "%')";

            ResultSet results = statement.executeQuery(preparedSQL);

            ArrayList<String> notification = new ArrayList<String>();

            while (results.next()) {
                String temp = results.getString(1);
                if (temp != null) {
                    temp = colorHashtag(colorMention(temp));
                } else {
                    temp = results.getString(2);
                    if (temp != null) {
                        temp = "People: " + temp;
                        String temp1 = results.getString(3);
                        if(temp1!= null ){
                            notification.add(temp);
                            temp = "Username found: " + colorMention(temp1);
                        }
                    }                   
                    else {
                        temp = results.getString(3);
                        if (temp != null) {
                            temp = "Username found: " + colorMention(temp);
                        } else {
                            temp = results.getString(4);
                            if (temp != null) {
                                temp = colorHashtag(colorMention(temp));
                            }
                        }
                    }
                }
                notification.add(temp);
            }

            results.close();
            statement.close();
            connection.close();
            return notification;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return null;

    }

}
