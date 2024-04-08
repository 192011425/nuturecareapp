package com.example.nurturecareandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;

public class profileActivity extends AppCompatActivity {

    EditText nameTextView, emailTextView, passwordTextView, contactnumberView, genderTextView;
    FirebaseDatabase rootNode;

    DatabaseReference reference;
    String customerName;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        navigationView = findViewById(R.id.nav);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);

        // Display user information in TextViews
        nameTextView = findViewById(R.id.fetch_ProfileName);
        emailTextView = findViewById(R.id.fetch_ProfileEmail);
        passwordTextView = findViewById(R.id.fetch_ProfilePassword);
        contactnumberView = findViewById(R.id.fetch_ProfileContact);
        genderTextView = findViewById(R.id.fetch_ProfileGender);
        Button saveUserProfile = findViewById(R.id.saveUserProfile);

        SharedPreferences sPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        customerName = sPreferences.getString("customerName", "");

        String name = customerName.toString();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

// Set your custom title
        TextView customTitleTextView = findViewById(R.id.profileTitle);
        customTitleTextView.setText("My Profile");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String customeEmailFromDB = snapshot.child("email").getValue(String.class);
                    String customerPasswordFromDB = snapshot.child("password").getValue(String.class);
                    String customerNameFromDB = snapshot.child("name").getValue(String.class);
                    String customerContactFromDB = snapshot.child("phoneNo").getValue(String.class);
                    String customerGenderFromDB = snapshot.child("gender").getValue(String.class);

                    if (name.equals(customerNameFromDB)) {
                        Toast.makeText(profileActivity.this, "successfully fetched", Toast.LENGTH_SHORT).show();
                        nameTextView.setText(customerNameFromDB);
                        emailTextView.setText(customeEmailFromDB);
                        passwordTextView.setText(customerPasswordFromDB);
                        contactnumberView.setText(customerContactFromDB);
                        genderTextView.setText(customerGenderFromDB);
                    } else {
                        Toast.makeText(profileActivity.this, "data not fetched", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Error: " + error.getMessage());
                Toast.makeText(profileActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });


        saveUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAdminData();
                Intent loginIntent = new Intent(profileActivity.this, customerloginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
                    Intent intent = new Intent(profileActivity.this, homeActivity.class);
                    startActivity(intent);
                } else if (id == R.id.profile) {
                    Intent intent = new Intent(profileActivity.this, profileActivity.class);
                    startActivity(intent);
                    finish();
                } else if (id == R.id.logout) {
                    Intent intent = new Intent(profileActivity.this, selectloginActivity.class);
                    startActivity(intent);
                    finish();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void saveAdminData() {

//        String editedCustomerName = nameTextView.getText().toString().trim();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Customer");

        //get all values

        String name = nameTextView.getText().toString();
        String phoneNo = contactnumberView.getText().toString();
        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        String gender = genderTextView.getText().toString();

        // Create a HelperClass object with the admin's information
        HelperClass helperClass = new HelperClass(name, phoneNo, email, password, gender);

        // Store the data in the Firebase Realtime Database under the "Admin" node
        reference.child(customerName).setValue(helperClass);
    }
}
