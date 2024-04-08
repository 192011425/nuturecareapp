package com.example.nurturecareandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class addstaffActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText Name, Specification, Email, Password, Contactno, Experience, Address, Id, Fees;

    Button saveBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    DatabaseReference staffReference;
    String selectedItemName;
//    int numberOfStaffMembers = 0;
    Calendar myCalendar;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstaff);

//        numberOfStaffMembers = getSharedPreferences("YourPreferences", Context.MODE_PRIVATE).getInt("number_of_staffs", 0);

        Name = findViewById(R.id.name);
        Specification = findViewById(R.id.specification);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Contactno = findViewById(R.id.contactno);
        Experience = findViewById(R.id.experience);
        Address = findViewById(R.id.address);
        Id = findViewById(R.id.id);
        Fees = findViewById(R.id.fees);
        saveBtn = findViewById(R.id.save_btn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

// Set your custom title
        TextView customTitleTextView = findViewById(R.id.addStaff);
        customTitleTextView.setText("STAFF DATA");

        drawerLayout = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.nav_bar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // Set onClickListener for submitBtn
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Staff");
                staffReference = rootNode.getReference("Staff Availablity");

                // Get all values
                String name = Name.getText().toString();
                String specification = Specification.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String contactno = Contactno.getText().toString();
                String experience = Experience.getText().toString();
                String address = Address.getText().toString();
                String id = Id.getText().toString();
                String fees = Fees.getText().toString();

//                numberOfStaffMembers++;
//
//                getSharedPreferences("YourPreferences", Context.MODE_PRIVATE)
//                        .edit()
//                        .putInt("number_of_staffs", numberOfStaffMembers)
//                        .apply();
//
//                String staffnumber = String.valueOf(numberOfStaffMembers);

                // Create CartHelperClass object
                StaffHelperClass helperClass = new StaffHelperClass(name, specification, email, password, contactno, experience, address, id, fees);

                // Set the value in the database under the specification and staff name
                reference.child(specification).push().setValue(helperClass);
                staffReference.push().setValue(helperClass);

//                reference.child(staffIdentifier).setValue(helperClass);

                // Use push() to generate unique key
//                reference.child(specification).setValue(helperClass);

//                reference.push().setValue(helperClass);

                // Move to the next activity
                Intent b = new Intent(addstaffActivity.this, addstaffActivity.class);
                startActivity(b);
            }
        });
    }
        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item){
            int id = item.getItemId();

            if (id == R.id.admin_staff) {
                Intent intent = new Intent(this, addstaffActivity.class);
                startActivity(intent);
            } else if (id == R.id.admin_category) {
                Intent intent = new Intent(this, addcategoryActivity.class);
                startActivity(intent);
            } else if (id == R.id.admin_staff_issues) {
                Intent intent = new Intent(this, staffissuesActivity.class);
                startActivity(intent);
            } else if (id == R.id.adminavailable_staff) {
                Intent intent = new Intent(this, staffavailableActivity.class);
                startActivity(intent);
            } else if (id == R.id.user_complaint) {
                Intent intent = new Intent(this, usercomplaintActivity.class);
                startActivity(intent);
            } else if (id == R.id.admin_home_logout) {
                Intent intent = new Intent(this, frontpageActivity.class);
                startActivity(intent);
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

//        @Override
//        public void onBackPressed () {
//            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                drawerLayout.closeDrawer(GravityCompat.START);
//            } else {
//                super.onBackPressed();
//            }
//        }
    }



