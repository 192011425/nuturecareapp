package com.example.nurturecareandroid;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class raiseissueActivity extends AppCompatActivity {


    EditText Name, Specification, Email, Contactno, Address, StaffIssue;

    TextView Staffid;

    Button submitBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String selectedItemName;
    String sID;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raiseissue);

        Intent intent = getIntent();
        if (intent != null) {
            // Check if the key "serviceName" exists in the Intent extras
            if (intent.hasExtra("STAFF_ID")) {
                // Retrieve the value associated with the key "serviceName"
                sID = intent.getStringExtra("STAFF_ID");
            }
        }

        Name = findViewById(R.id.name);
        Specification = findViewById(R.id.specification);
        Email = findViewById(R.id.email);
        Contactno = findViewById(R.id.contactno);
        Staffid = findViewById(R.id.staffid);
        Address = findViewById(R.id.address);
        StaffIssue = findViewById(R.id.staffissue);
        submitBtn = findViewById(R.id.submitBtn);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_bar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

// Set your custom title
        TextView customTitleTextView = findViewById(R.id.raiseIssueTV);
        customTitleTextView.setText("Raise Issue");

        Staffid.setText(sID);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set onClickListener for submitBtn
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("StaffIssues");

                // Get all values
                String name = Name.getText().toString();
                String specification = Specification.getText().toString();
                String email = Email.getText().toString();
                String contactno = Contactno.getText().toString();
                String staffid = Staffid.getText().toString();
                String address = Address.getText().toString();
                String staffissue = StaffIssue.getText().toString();


                // Create CartHelperClass object
                RaiseHelperClass helperClass = new RaiseHelperClass(name, specification, email, contactno, staffid, address, staffissue);

                // Use push() to generate unique key
                reference.child(staffid).setValue(helperClass);

                // Move to the next activity
                Intent b = new Intent(com.example.nurturecareandroid.raiseissueActivity.this, staffissuesActivity.class);
                startActivity(b);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation item clicks here
            int id = item.getItemId();

            if (id == R.id.staff_issues) {
                Intent i = new Intent(this, raiseissueActivity.class);
                startActivity(i);
            } else if (id == R.id.user_request) {
                Intent i = new Intent(this, UserRequestActivity.class);
                i.putExtra("STAFF_ID", sID);
                startActivity(i);
            } else if (id == R.id.staff_home_logout) {
                Intent i = new Intent(this, selectloginActivity.class);
                startActivity(i);
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

