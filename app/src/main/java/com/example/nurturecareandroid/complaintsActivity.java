package com.example.nurturecareandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class complaintsActivity extends AppCompatActivity {


        EditText Name, TreatmentName, Email, Contactno,Address, Issue;

        Button submitBtn;
        FirebaseDatabase rootNode;
        DatabaseReference reference;
        String selectedItemName;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_complaints);

            Name = findViewById(R.id.add_treatmentName);
            TreatmentName= findViewById(R.id.treatmentname);
            Email = findViewById(R.id.email);
            Contactno = findViewById(R.id.contactno);
            Address = findViewById(R.id.address);
             Issue= findViewById(R.id.issue);
            submitBtn= findViewById(R.id.submitBtn);


            // Set onClickListener for submitBtn
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("UserComplaints");

                    // Get all values
                    String name = Name.getText().toString();
                    String  treatmentName =  TreatmentName.getText().toString();
                    String email = Email.getText().toString();
                    String contactno = Contactno.getText().toString();
                    String address = Address.getText().toString();
                    String issue = Issue.getText().toString();


                    // Create CartHelperClass object
                    ComplaintsHelperClass helperClass = new ComplaintsHelperClass(name, treatmentName, email, contactno, address, issue);

                    // Use push() to generate unique key
                    reference.child(issue).setValue(helperClass);

                    // Move to the next activity
                    Intent b = new Intent(com.example.nurturecareandroid.complaintsActivity.this, homeActivity.class);
                    startActivity(b);
                }
            });
        }
    }

