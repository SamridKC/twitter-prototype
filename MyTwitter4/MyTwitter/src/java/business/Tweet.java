/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
/**
 *
 * @author samridkc
 */
public class Tweet implements Serializable{
    
    private String tweetID;
    private String tweet;
    private String emailAddress;
    
    public Tweet(String tweet, String emailAddress, String id) {
        this.tweetID = id;
        this.tweet = tweet;
        this.emailAddress = emailAddress;
    }
   
    
    
//    public int getTweetID() {
//        return this.tweetID;
//    }
//    
//    public void setTweetID(int tweetID) {
//        this.tweetID = tweetID;
//    }
    
    public String getTweet() {
        return this.tweet;
    }
    
    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
    
    public String getEmail() {
        return this.emailAddress;
    }

    public void setEmail(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public String getID() {
        return this.tweetID;
    }

    public void setID(String id) {
        this.emailAddress = id;
    }
    
}
