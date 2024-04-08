package com.example.nurturecareandroid;

public class StaffHelperClass {
    private String name, specification, email, password, contactno, experience, address, id, fees;

    public StaffHelperClass() {
        // Default constructor required for Firebase
    }

    // Constructor
    public StaffHelperClass(String name, String specification, String email, String password, String contactno, String experience, String address, String id, String fees) {
        this.name = name;
        this.specification = specification;
        this.email = email;
        this.password = password;
        this.contactno = contactno;
        this.experience = experience;
        this.address = address;
        this.id = id;
        this.fees = fees;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getSpecification() {
        return specification;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getContactno() {
        return contactno;
    }

    public String getExperience() {
        return experience;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public String getFees() {
        return fees;
    }
}
