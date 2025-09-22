package com.example.youbike.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String username;
    private String email;
    private String phone;
    private String easycard;
    private double balance;
    private String password;
    public LoggedInUser(String username, String email, String phone, String easycard, double balance
            , String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.phone = phone;
        this.easycard = easycard;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEasycard() {
        return easycard;
    }



}
