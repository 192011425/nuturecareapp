package com.example.nurturecareandroid;

public class ComplaintsHelperClass {
    String name, treatmentname, email, contactno, address, issue;

    public ComplaintsHelperClass(String name, String treatmentname, String email, String contactno, String address, String issue) {
        this.name = name;
        this.treatmentname = treatmentname;
        this.email = email;
        this.contactno = contactno;
        this.address = address;
        this.issue = issue;
    }

    public String getName() {
        return name;
    }

    public String getTreatmentname() {
        return treatmentname;
    }

    public String getEmail() {
        return email;
    }

    public String getContactno() {
        return contactno;
    }

    public String getAddress() {
        return address;
    }

    public String getIssue() {
        return issue;
    }
}

