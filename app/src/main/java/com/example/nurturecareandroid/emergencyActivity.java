package com.example.nurturecareandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class emergencyActivity extends AppCompatActivity {



        EditText Name, Specification, Email, Password, Contactnumber, Experience, Address,StaffId ,Fees;

        Button saveBtn;
        FirebaseDatabase rootNode;
        DatabaseReference reference;
        String selectedItemName;
        Calendar myCalendar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_emergency);




            // Set onClickListener for submitBtn

        }
    }

