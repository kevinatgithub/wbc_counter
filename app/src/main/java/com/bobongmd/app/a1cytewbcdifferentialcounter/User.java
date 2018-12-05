package com.bobongmd.app.a1cytewbcdifferentialcounter;

public class User {

    private String user_id;
    private String password;
    private String full_name;
    private String designation;

    public User(String user_id, String password, String full_name, String designation) {
        this.user_id = user_id;
        this.password = password;
        this.full_name = full_name;
        this.designation = designation;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPassword() {
        return password;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getDesignation() {
        return designation;
    }
}
