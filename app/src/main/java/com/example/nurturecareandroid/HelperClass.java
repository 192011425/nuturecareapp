package com.example.nurturecareandroid;

public class HelperClass {
    private String name;
    private String phoneNo;
    private String email;
    private String password;
    private String gender;

    // Default constructor required for calls to DataSnapshot.getValue(HelperClass.class)
    public HelperClass() {
    }

    public HelperClass(String name, String phoneNo, String email, String password, String gender) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
