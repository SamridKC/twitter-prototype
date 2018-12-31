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
public class Hashtag implements Serializable{
    private String hashtagID;
    private String hashtagText;
    private int hashtagCount;
    
    public Hashtag(String hashtagID, String hashtagText, int hashtagCount) {
        this.hashtagID = hashtagID;
        this.hashtagText = hashtagText;
        this.hashtagCount = hashtagCount;
    }
       
    public void setHashtag(String hashtagText) {
        this.hashtagText = hashtagText;
    }
    
    public String getHashtag() {
        return this.hashtagText;
    }

    public void sethashtagCount(int hashtagCount) {
        this.hashtagCount = hashtagCount;
    }
    
    public int gethashtagCount() {
        return this.hashtagCount;
    }
    
        public void sethashtagID(String hashtagID) {
        this.hashtagID = hashtagID;
    }
    
    public String gethashtagID() {
        return this.hashtagID;
    }


}
