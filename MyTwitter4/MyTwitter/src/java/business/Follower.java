/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;

/**
 *
 * @author Bhuwan
 */
public class Follower implements Serializable{
    private int FollowerID;
    private String emailOfFollowed;
    private String emailOfFollower;
    private String followTime;
    
    public Follower(int FollowerID, String emailOfFollowed, String emailOfFollower, String followTime) {
        this.FollowerID = FollowerID;
        this.emailOfFollowed = emailOfFollowed;
        this.emailOfFollower = emailOfFollower;
        this.followTime = followTime;
    }
       
    public void setFollowerID(int FollowerID) {
        this.FollowerID = FollowerID;
    }
    
    public int getFollowerID() {
        return this.FollowerID;
    }

    public void setEmailOfFollowed(String emailOfFollowed) {
        this.emailOfFollowed = emailOfFollowed;
    }
    
    public String getEmailOfFollowed() {
        return this.emailOfFollowed;
    }
    
    public void setEmailOfFollower(String emailOfFollower) {
        this.emailOfFollower = emailOfFollower;
    }
    
    public String getEmailOfFollower() {
        return this.emailOfFollower;
    }
    
    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }
    
    public String getFollowTime() {
        return this.followTime;
    }


}
