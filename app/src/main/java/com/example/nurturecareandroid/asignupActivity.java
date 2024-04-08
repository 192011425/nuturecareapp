package com.example.nurturecareandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class asignupActivity extends AppCompatActivity {

    TextView signUp, admin_member;
    EditText adminName, adminPhone, adminGender, adminEmail, adminPassword;
    Button adminRegister;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignup);

        signUp = findViewById(R.id.admin_sign);
        admin_member = findViewById(R.id.admin_log_link_btn);
        adminName = findViewById(R.id.admin_reg_name);
        adminPhone = findViewById(R.id.admin_reg_phoneNo);
        adminEmail = findViewById(R.id.admin_reg_email);
        adminPassword = findViewById(R.id.admin_reg_password);
        adminGender = findViewById(R.id.admin_reg_gender);
        adminRegister = findViewById(R.id.admin_reg_btn);

        adminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adminName.getText().toString().isEmpty() || adminPhone.getText().toString().isEmpty() || adminEmail.getText().toString().isEmpty() || adminPassword.getText().toString().isEmpty() || adminGender.getText().toString().isEmpty()) {
                    Toast.makeText(asignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    saveAdminData();
                }
            }
        });

        admin_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(asignupActivity.this, adminloginActivity.class);
                startActivity(v);
            }
        });
    }

    private void saveAdminData() {

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Admin");

        // Get all values
        String name = adminName.getText().toString();
        String phoneNo = adminPhone.getText().toString();
        String email = adminEmail.getText().toString();
        String password = adminPassword.getText().toString();
        String gender = adminGender.getText().toString();

        // Create a HelperClass object with the admin's information
        HelperClass helperClass = new HelperClass(name, phoneNo, email, password, gender);

        // Store the data in the Firebase Realtime Database under the "Admin" node
        reference.child(name).setValue(helperClass);

        // Start a new activity (adminloginActivity in this case)
        Intent i = new Intent(asignupActivity.this, adminloginActivity.class);
        startActivity(i);

    }


}