package com.marksapplication.app;

public class employees {

    private String name;
    private String phonenumber;
    private String email;
    private String password;

    public employees(){}

    public employees(String name, String phonenumber, String email, String password) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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

}
