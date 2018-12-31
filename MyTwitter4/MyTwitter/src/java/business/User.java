/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @javabean for User Entity
 */
public class User implements Serializable {
    //define attributes fullname, ...

    //define set/get methods for all attributes.
    private String fullName;
    private String userName;
    private String emailAddress;
    private String password;
    private String salt;
    private String birthdate;
    private String questionNo;
    private String answer;
    private String last_login_time;

    public User() {
        fullName = "";
        userName = "";
        emailAddress = "";
        password = "";
        salt = "";
        birthdate = "";
        questionNo = "";
        answer = "";
        last_login_time = "";
        
    }
    
    public User(ArrayList l) {
        this.fullName = (String) l.get(0);
        this.userName = (String) l.get(1);
        this.emailAddress = (String) l.get(2);
        this.password = (String) l.get(3);
        this.salt = (String) l.get(4);
        this.birthdate = (String) l.get(5);
        this.questionNo = (String) l.get(6);
        this.answer = (String) l.get(7);
        this.last_login_time = (String) l.get(8);
    }

    public User(String fromString) {
        String[] data = fromString.replace("[", "").split(",");   
        this.setFullName(data[1]);
        this.setUserName(data[2]);
        this.setEmail(data[3]);
        this.setPassword(data[4]);
        this.setSalt(data[5]);
        this.setBirthDate(data[6]);
        this.setQuestionNo(data[7]);
        this.setAnswer(data[8]);
        this.setLastLoginTime(data[9]);
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.emailAddress;
    }

    public void setEmail(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getBirthDate() {
        return this.birthdate;
    }

    public void setBirthDate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getQuestionNo() {
        return this.questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getLastLoginTime() {
        return this.last_login_time;
    }

    public void setLastLoginTime(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s,%s,%s,%s,%s,%s,%s]", this.getFullName(), this.getUserName(), this.getEmail(), this.getPassword(),this.getSalt(), this.getBirthDate(), this.getQuestionNo(), this.getAnswer(),this.getLastLoginTime()));
        return sb.toString();
    }

}
