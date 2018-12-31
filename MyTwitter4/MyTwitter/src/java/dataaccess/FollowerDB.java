/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.User;
import static dataaccess.HashtagDB.matchFinder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samridkc
 */
public class FollowerDB {

    public static long insert(String emailOfFollowed, String emailOfFollower) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();

            String preparedSQL = "INSERT INTO Follower (emailOfFollowed, emailOfFollower,followTime) VALUES ('"
                    + emailOfFollowed + "', '" + emailOfFollower + "',now());";
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

    public static ArrayList<User> followed(String follower) throws ClassNotFoundException {
        try {
            ArrayList<User> usersFollowed = new ArrayList<User>();

            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "SELECT emailOfFollowed FROM Follower WHERE emailOfFollower = '" + follower + "'";
            ResultSet rs = statement.executeQuery(preparedSQL);

            while (rs.next()) {
                User temp = new User();
                try {
                    temp = UserDB.search(rs.getString(1));
                } catch (IOException ex) {
                    Logger.getLogger(FollowerDB.class.getName()).log(Level.SEVERE, null, ex);
                }
                usersFollowed.add(temp);
            }
            rs.close();
            statement.close();
            connection.close();

            return usersFollowed;
        } catch (SQLException ex) {
            Logger.getLogger(HashtagDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static long delete(String emailOfFollowed, String emailOfFollower) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();

            String preparedSQL = "DELETE   FROM Follower WHERE emailOfFollowed = '"
                    + emailOfFollowed + "' AND emailOfFollower = '" + emailOfFollower + "'";
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

    public static int followerCount(String followed) throws ClassNotFoundException {
        try {
            ArrayList<User> followers = new ArrayList<User>();

            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "SELECT COUNT(emailOfFollower) FROM Follower WHERE emailOfFollowed = '" + followed + "';";
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

}
