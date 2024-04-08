package com.example.nurturecareandroid;

public class RaiseHelperClass {

    String name, specification, email, contactno, staffid, address, issue;

    public RaiseHelperClass(String name, String specification, String email, String contactno, String staffid, String address, String issue) {
        this.name = name;
        this.specification = specification;
        this.email = email;
        this.contactno = contactno;
        this.staffid = staffid;
        this.address = address;
        this.issue = issue;
    }

    public String getName() {
        return name;
    }

    public String getSpecification() {
        return specification;
    }

    public String getEmail() {
        return email;
    }

    public String getContactno() {
        return contactno;
    }

    public String getStaffid() {
        return staffid;
    }

    public String getAddress() {
        return address;
    }

    public String getissue() {
        return issue;
    }
}
