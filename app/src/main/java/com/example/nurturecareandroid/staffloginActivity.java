package com.example.nurturecareandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class staffloginActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stafflogin);

        loginUsername = findViewById(R.id.staff_email);
        loginPassword = findViewById(R.id.staff_password);
        loginButton = findViewById(R.id.staff_Home_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userUsername = loginUsername.getText().toString().trim();
                String userPassword = loginPassword.getText().toString().trim();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Staff Availablity");
//        Query checkUserDatabase = reference.orderByChild("name").equalTo(userUsername);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dSnapshot) {

                        for (DataSnapshot snapshot : dSnapshot.getChildren()) {
                            String adminEmailFromDB = snapshot.child("email").getValue(String.class);
                            String adminPasswordFromDB = snapshot.child("password").getValue(String.class);
                            String adminStaffIdFromDB = snapshot.child("id").getValue(String.class);

                            if (userUsername.equals(adminEmailFromDB) && userPassword.equals(adminPasswordFromDB)) {
                                Toast.makeText(staffloginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(staffloginActivity.this, raiseissueActivity.class);
                                intent.putExtra("STAFF_ID",adminStaffIdFromDB);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
}

//    public void checkUser(){
//        String userUsername = loginUsername.getText().toString().trim();
//        String userPassword = loginPassword.getText().toString().trim();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");
////        Query checkUserDatabase = reference.orderByChild("name").equalTo(userUsername);
//
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dSnapshot) {

//                if (snapshot.exists()){
//                    loginUsername.setError(null);
//                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
//
//                    if (passwordFromDB.equals(userPassword)){
//                        loginUsername.setError(null);
//
//                        //Pass the data using intent
//
//                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
//                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
//                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
//
//                        Intent intent = new Intent(customerloginActivity.this, homeActivity.class);
//
//                        intent.putExtra("name", nameFromDB);
//                        intent.putExtra("email", emailFromDB);
//                        intent.putExtra("username", usernameFromDB);
//                        intent.putExtra("password", passwordFromDB);
//
//                        startActivity(intent);
//                    } else {
//                        loginPassword.setError("Invalid Credentials");
//                        loginPassword.requestFocus();
//                    }
//                } else {
//                    loginUsername.setError("User does not exist");
//                    loginUsername.requestFocus();
//                }
//            }


//            public void onClick(View view) {
//                Intent intent = new Intent(customerloginActivity.this, homeActivity.class);
//                startActivity(intent);
//            }
