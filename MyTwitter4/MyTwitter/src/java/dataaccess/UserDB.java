/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Tweet;
import business.User;
import static dataaccess.TweetDB.colorHashtag;
import static dataaccess.TweetDB.colorMention;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    public static long insert(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "Insert into User(fullName, username, emailAddress, password, salt, birthdate, "
                    + "questionNo, answer,last_login_time) Values ('" + user.getFullName() + "', '" + user.getUserName() + "', '" + user.getEmail() + "', '"
                    + user.getPassword() + "', '" + user.getSalt() + "', '" + user.getBirthDate() + "', '" + user.getQuestionNo() + "', '"
                    + user.getAnswer() +  "', now())";

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

    public static User search(String emailAddress) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "Select * from user where emailAddress = '" + emailAddress + "';";

            ResultSet rs = statement.executeQuery(preparedSQL);

            String result = getJSON(rs);

            if ("".equals(result)) {
                return null;
            }

            User userObj = new User(result);

            rs.close();
            statement.close();
            connection.close();
            return userObj;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return null;
    }

    public static String getJSON(ResultSet results)
            throws SQLException {
        StringBuilder JSONString = new StringBuilder();
        JSONString.append("[");
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (results.next()) {
            JSONString.append("{");
            for (int i = 1; i <= columnCount; i++) {
//                JSONString.append("\"" + metaData.getColumnName(i) + "\"");
//                JSONString.append(":");
                JSONString.append(results.getString(i));
                if (i != columnCount) {
                    JSONString.append(",");
                }
            }
            JSONString.append("}");

            JSONString.append(",");

        }
        String JSONreturn = JSONString.toString();
        JSONreturn = JSONreturn.substring(0, JSONreturn.length() - 1);
        if (!JSONreturn.isEmpty()) {
            JSONreturn = JSONreturn + "]";
        }
        return JSONreturn;
    }

    public static ArrayList searchAll() throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "Select * from user";

            ResultSet rs = statement.executeQuery(preparedSQL);

            ArrayList hi = getAllUsers(rs);

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

    public static ArrayList getAllUsers(ResultSet results)
            throws SQLException {

        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        ArrayList arr = new ArrayList();

        ArrayList userString = new ArrayList();

        while (results.next()) {
            userString.add(results.getString(2));
            userString.add(results.getString(3));
            userString.add(results.getString(4));
            userString.add(results.getString(5));
            userString.add(results.getString(6));
            userString.add(results.getString(7));
            userString.add(results.getString(8));
            userString.add(results.getString(9));
            userString.add(results.getString(10));
            

            User temp = new User(userString);
            arr.add(temp);
            userString.clear();
        }
        return arr;
    }

    public static long Update(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();
            String preparedSQL = "UPDATE user SET "
                    + "fullname = '" + user.getFullName() + "', birthdate = '" + user.getBirthDate()
                    + "', password = '" + user.getPassword() + "', salt = '" + user.getSalt() + "',questionNo = '" + user.getQuestionNo()
                    + "', answer = '" + user.getAnswer() + "' WHERE emailAddress = '" + user.getEmail() + "';";

            int result = statement.executeUpdate(preparedSQL);
            /*statement.executeUpdate(preparedSQL);
            connection.close();
            return true;*/

            statement.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
            return 0;
        }
    }

    public static long newLogin(String email) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();
            
            
            String preparedSQL = "UPDATE user SET last_login_time = now() WHERE emailAddress = '" 
                    + email + "';";

            int result = statement.executeUpdate(preparedSQL);
            /*statement.executeUpdate(preparedSQL);
            connection.close();
            return true;*/

            statement.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
            return 0;
        }
    }

}
