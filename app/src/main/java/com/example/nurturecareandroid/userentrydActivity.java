package com.example.nurturecareandroid;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class userentrydActivity extends AppCompatActivity {

    private EditText addressEditText, patientIdEditText;

    private TextView nameTextView, emailTextView, contactNoTextView;

    private TextView treatmentNameTextView,staffIdTextView,dateTextView,timeTextView, feesTextView;

    private Spinner spinnerGender;
    String staffId;
    String treatmentName;
    String fees;

    private Button SendRequestButton;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userentryd);

        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String customerName = preferences.getString("customerName", "");
        String customerContact = preferences.getString("customerContact", "");
        String customerEmail = preferences.getString("customerEmail", "");


        Intent intent = getIntent();
        if (intent != null) {
            // Check if the key "serviceName" exists in the Intent extras
            if (intent.hasExtra("staffId")) {
                // Retrieve the value associated with the key "serviceName"
                staffId = intent.getStringExtra("staffId");
            }
            if (intent.hasExtra("specification")) {
                // Retrieve the value associated with the key "serviceName"
                treatmentName = intent.getStringExtra("specification");
            }
            if (intent.hasExtra("fees")) {
                // Retrieve the value associated with the key "serviceName"
                fees = intent.getStringExtra("fees");
            }
        }

        // Get the root reference of the database
        mDatabase = FirebaseDatabase.getInstance().getReference("userRequest");

        // Initialize EditText fields
        nameTextView = findViewById(R.id.name);
        treatmentNameTextView = findViewById(R.id.treatmentname);
        emailTextView = findViewById(R.id.email);
        contactNoTextView = findViewById(R.id.contactno);
        spinnerGender = findViewById(R.id.spinnerGender);
        addressEditText = findViewById(R.id.address);
        patientIdEditText = findViewById(R.id.patientid);
        dateTextView = findViewById(R.id.current_date);
        timeTextView = findViewById(R.id.time);
        staffIdTextView = findViewById(R.id.staffid);
        feesTextView = findViewById(R.id.staff_fees);

        nameTextView.setText(customerName);
        emailTextView.setText(customerEmail);
        contactNoTextView.setText(customerContact);
        treatmentNameTextView.setText(treatmentName);
        staffIdTextView.setText(staffId);
        feesTextView.setText(fees);

        SendRequestButton = findViewById(R.id.book_home_slot);
        SendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToFirebase();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerGender.setAdapter(adapter);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    private void saveDataToFirebase() {
        String name = nameTextView.getText().toString();
        String treatmentName = treatmentNameTextView.getText().toString();
        String email = emailTextView.getText().toString();
        String contactNo = contactNoTextView.getText().toString();
        String selectedGender = spinnerGender.getSelectedItem().toString();

        String address = addressEditText.getText().toString();
        String patientId = patientIdEditText.getText().toString();

        String date = dateTextView.getText().toString();
        String time = timeTextView.getText().toString();

        String staffId = staffIdTextView.getText().toString();
        String fees = feesTextView.getText().toString();

        if (name.isEmpty() || treatmentName.isEmpty() || email.isEmpty() ||
                contactNo.isEmpty() || address.isEmpty() ||
                patientId.isEmpty() || date.isEmpty() || time.isEmpty() ||
                staffId.isEmpty() || fees.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String entryKey = mDatabase.child("Date").push().getKey();

        UserHelperClass helperClass = new UserHelperClass(
                name, treatmentName, email, contactNo, selectedGender, patientId, address, time, date, staffId, fees
        );

        mDatabase.child("Date").child(entryKey).setValue(helperClass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(userentrydActivity.this, homeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Error saving data", task.getException()); // Add this line
                    }
                });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String date = String.format("%02d/%02d/%04d", dayOfMonth,month + 1, year);
                    dateTextView.setText(date);
                },
                // Pass the current date as the default values
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    String time = String.format("%02d:%02d", hourOfDay, minute);
                    timeTextView.setText(time);
                },
                // Pass the current time as the default values
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false // Use 24-hour format
        );

        timePickerDialog.show();
    }

}
