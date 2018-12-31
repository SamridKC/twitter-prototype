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
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bhuwan
 */
public class HashtagDB {

    public static long insert(String tweet, int tweetID) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            ArrayList<String> hashtags = matchFinder("#(\\w*[0-9a-zA-Z]+\\w*[0-9a-zA-Z])", tweet);  // hashtag regex

            for (String var : hashtags) {
                String preparedSQL = "Insert into hashtag(hashtagText, hashtagCount) VALUES ('" + var + "','"
                        + 1 + "') ON DUPLICATE KEY UPDATE hashtagCount = hashtagCount + 1 ;";
                statement.executeUpdate(preparedSQL);

                preparedSQL = "SELECT hashtagID FROM hashtag WHERE hashtagText = '" + var + "';";

                //add values to the above SQL statement and execute it.
                ResultSet rs = statement.executeQuery(preparedSQL);

                int hashtagID = 0;
                if (rs.next()) {
                    hashtagID = rs.getInt(1);
                }

                preparedSQL = "Insert into tweetHashtag(tweetID, hashtagID) VALUES ('" + tweetID + "','" + hashtagID + "');";
                statement.executeUpdate(preparedSQL);

                rs.close();
            }

            statement.close();
            connection.close();
            return 1;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }

        return 0;
    }

    public static ArrayList<String> matchFinder(String regex, String tweet) {
        ArrayList<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(regex)
                .matcher(tweet);
        while (m.find()) {
            allMatches.add(m.group());
        }

        return allMatches;
    }

    public static ArrayList<String> trends() throws ClassNotFoundException {
        try {
            ArrayList<String> tenTrends = new ArrayList<String>();

            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "select hashtagText from hashtag ORDER BY hashtagCount desc limit 10";
            ResultSet rs = statement.executeQuery(preparedSQL);

            while (rs.next()) {
                String temp = rs.getString(1);
                tenTrends.add(temp);
            }

            rs.close();
            statement.close();
            connection.close();

            return tenTrends;
        } catch (SQLException ex) {
            Logger.getLogger(HashtagDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ArrayList<Tweet> hashTweets(String hash) throws ClassNotFoundException {
        try {
            ArrayList<Tweet> tweets = new ArrayList<Tweet>();

            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "<yourMYSqlServerName>";
            String password = "<yourPasswordForMYSql>";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            Statement statement = connection.createStatement();

            String preparedSQL = "SELECT * FROM TWEETS WHERE Tweets.idTweets IN\n"
                    + "(SELECT tweetHashtag.tweetID FROM tweetHashtag WHERE tweetHashtag.hashtagID =\n"
                    + "(SELECT hashtag.hashtagID FROM hashtag WHERE hashtag.hashtagText = '" + hash + "'))\n"
                    + "ORDER BY Tweets.postingTime DESC";
            ResultSet rs = statement.executeQuery(preparedSQL);

            while (rs.next()) {
                Tweet temp = new Tweet(colorHashtag(colorMention(rs.getString(2))), rs.getString(4), rs.getString(1));
                //             String temp = rs.getString(1);
                tweets.add(temp);
            }
            rs.close();
            statement.close();
            connection.close();

            return tweets;
        } catch (SQLException ex) {
            Logger.getLogger(HashtagDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
