package com.example.tushar.pgi.model;


public class LoginModel {

    private String uid;
    private String password;
    private String type;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginModel() {
    }

    /**
     *
     * @param uid
     * @param type
     * @param password
     */
    public LoginModel(String uid, String password, String type) {
        super();
        this.uid = uid;
        this.password = password;
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}