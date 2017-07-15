package com.example.tarav.ourapp;

/**
 * Created by tarav on 15.07.2017.
 */

public class UserMemo {

    private String username;
    private String email;
    private String password;
    private String password2;

    public UserMemo (String username, String email, String password, String password2){
        this.username = username;
        this.email = email;
        this.password = password;
        this.password2 = password2;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

}
