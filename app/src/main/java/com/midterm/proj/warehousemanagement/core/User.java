package com.midterm.proj.warehousemanagement.core;

import com.midterm.proj.warehousemanagement.algorithms.SHA1;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class User {
    String sUsername;
    String sPlainTextPassword;
    String sHashedPassword;
    String sPhoneNumber;

    public User(){}

    public User(String sUsername, String sPassword, String sPhoneNumber) {
        this.sUsername = sUsername;
        this.sPlainTextPassword = sPassword;
        hashPasswordSHA1();
        this.sPhoneNumber = sPhoneNumber;
    }

    public void hashPasswordSHA1(){
        try {
            this.sHashedPassword = SHA1.hashString(this.sPlainTextPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
