package com.example.nurturecareandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminloginActivity extends AppCompatActivity {

    EditText adminEmail, adminPassword;
    Button adminLogin;
    TextView signUpAdmin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        adminEmail = findViewById(R.id.admin_email);
        adminPassword = findViewById(R.id.admin_password);
        adminLogin = findViewById(R.id.admin_Home_login);
        signUpAdmin = findViewById(R.id.signUpadmin);



        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = adminEmail.getText().toString().trim();
                String password = adminPassword.getText().toString().trim();

                // Assuming your database structure has a "admins" node containing admin information
                DatabaseReference adminsRef = FirebaseDatabase.getInstance().getReference("Admin");

                adminsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Assuming each child node represents an admin with 'email' and 'password' fields
                            String adminEmailFromDB = snapshot.child("email").getValue(String.class);
                            String adminPasswordFromDB = snapshot.child("password").getValue(String.class);
                            // Check if the provided email and password match any admin's credentials
                            if (email.equals(adminEmailFromDB) && password.equals(adminPasswordFromDB)) {
                                Toast.makeText(adminloginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(adminloginActivity.this, addstaffActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle potential errors
                        Log.e("AdminLoginActivity", "Error retrieving data", databaseError.toException());
                    }
                });

            }
        });

        signUpAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to the admin signup activity
                Intent intent = new Intent(adminloginActivity.this, asignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
