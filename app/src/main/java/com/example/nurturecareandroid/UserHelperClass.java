package com.example.nurturecareandroid;

import android.widget.Spinner;

public class UserHelperClass {
     private String name;
     private String treatmentName;
     private String email;
     private String contactNo;
     private String gender;
     private String patientId;
     private String address;
     private String time;
     private String date;
     private String staffId;
     private String fees;

     // Empty constructor needed for Firebase
     public UserHelperClass() {
     }

     public UserHelperClass(String name, String treatmentName, String email, String contactNo, String gender, String patientId, String address, String time, String date, String staffId,String fees) {
          this.name = name;
          this.treatmentName = treatmentName;
          this.email = email;
          this.contactNo = contactNo;
          this.gender = gender;
          this.patientId = patientId;
          this.address = address;
          this.time = time;
          this.date = date;
          this.staffId = staffId;
          this.fees = fees;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getTreatmentName() {
          return treatmentName;
     }

     public void setTreatmentName(String treatmentName) {
          this.treatmentName = treatmentName;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public String getContactNo() {
          return contactNo;
     }

     public void setContactNo(String contactNo) {
          this.contactNo = contactNo;
     }

     public String getGender() {
          return gender;
     }

     public void setGender(String gender) {
          this.gender = gender;
     }

     public String getPatientId() {
          return patientId;
     }

     public void setPatientId(String patientId) {
          this.patientId = patientId;
     }

     public String getAddress() {
          return address;
     }

     public void setAddress(String address) {
          this.address = address;
     }

     public String getTime() {
          return time;
     }

     public void setTime(String time) {
          this.time = time;
     }

     public String getDate() {
          return date;
     }

     public void setDate(String date) {
          this.date = date;
     }

     public String getStaffId() {
          return staffId;
     }

     public void setStaffId(String staffId) {
          this.staffId = staffId;
     }
     public String getFees() {
          return fees;
     }

     public void setFees(String fees) {
          this.fees = fees;
     }

     // Add getters and setters if needed
}

