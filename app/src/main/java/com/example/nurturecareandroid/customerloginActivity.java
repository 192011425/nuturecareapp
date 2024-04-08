package com.example.nurturecareandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class customerloginActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerlogin);

        loginUsername = findViewById(R.id.customer_email);
        loginPassword = findViewById(R.id.customer_password);
        signupRedirectText = findViewById(R.id.signUpcustomer);
        loginButton = findViewById(R.id.customer_Home_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!validateUsername() || !validatePassword()) {
//                    return; // Stop execution if validation fails
//                }

                String userUsername = loginUsername.getText().toString().trim();
                String userPassword = loginPassword.getText().toString().trim();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        boolean userFound = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String customeEmailFromDB = snapshot.child("email").getValue(String.class);
                            String customerPasswordFromDB = snapshot.child("password").getValue(String.class);
                            String customerNameFromDB = snapshot.child("name").getValue(String.class);
                            String customerContactFromDB = snapshot.child("phoneNo").getValue(String.class);
                            String customerGenderFromDB = snapshot.child("gender").getValue(String.class);

                            if (userUsername.equals(customeEmailFromDB) && userPassword.equals(customerPasswordFromDB)) {

                                // Save data to SharedPreferences
                                SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("customerName", customerNameFromDB);
                                editor.apply();

                                Toast.makeText(customerloginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(customerloginActivity.this, homeActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(customerloginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("DatabaseError", "Error: " + error.getMessage());
                        Toast.makeText(customerloginActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customerloginActivity.this, csignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
