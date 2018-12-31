///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package business;
//
//import java.io.Serializable;
//
///**
// *
// * @author samridkc
// */
//public class userMention implements Serializable {
//
//    private int iduserMention;
//    private String email_ID;
//    private int tweetID;
//
//    public userMention() {
//        email_ID = "";
//    }
//
//    public userMention(String fromString) {
//        String[] data = fromString.replace("[", "").split(",");
//        this.setUM_ID(Integer.parseInt(data[0]));
//        this.setEmailID(data[1]);
//        this.setTweetID(data[2]);
//    }
//
//    public int getiduserMention() {
//        return this.iduserMention;
//    }
//
//    public void setiduserMention(int userMentionID) {
//        this.iduserMention = userMentionID;
//    }
//
//    public String getemailID() {
//        return this.email_ID;
//    }
//
//    public void setEmail(String emailAddress) {
//        this.email_ID = emailAddress;
//    }
//
//    public int getTweetID() {
//        return this.tweetID;
//    }
//
//    public void setTweetID(int tweetID) {
//        this.tweetID = tweetID;
//    }
//}
