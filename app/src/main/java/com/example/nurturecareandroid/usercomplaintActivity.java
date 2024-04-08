package com.example.nurturecareandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nurturecareandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class usercomplaintActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    LinearLayout coversContainer;
    String shopName;
    String username;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercomplaint);

        coversContainer = findViewById(R.id.coversContainer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

// Set your custom title
        TextView customTitleTextView = findViewById(R.id.userComplaintTV);
        customTitleTextView.setText("User Complaints");

        drawerLayout = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.nav_bar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        showCoversForShop();

    }

    private void showCoversForShop() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserComplaints");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Covers", "Querying covers for shop: " + shopName);

                if (!snapshot.exists()) {
                    // No data found for the specific shop
                    Log.e("Covers", "No data found for covers in shop: " + shopName);
                    return;
                }

                Log.d("Covers", "Number of entries found for covers in shop " + shopName + ": " + snapshot.getChildrenCount());

                for (DataSnapshot coversSnapshot : snapshot.getChildren()) {
                    Log.d("Covers", "Processing entry for covers");

                    String name = coversSnapshot.child("name").getValue(String.class);
                    String treatmentname = coversSnapshot.child("treatmentname").getValue(String.class);
                    String email = coversSnapshot.child("email").getValue(String.class);// Retrieve the nextpage value
                    String contactno = coversSnapshot.child("contactno").getValue(String.class);
                    String address = coversSnapshot.child("address").getValue(String.class);
                    String issue = coversSnapshot.child("issue").getValue(String.class);



                    // Inflate the item_cover.xml layout
                    View coverItemView = getLayoutInflater().inflate(R.layout.complaints, coversContainer, false);

                    // Find views in the inflated layout

                    TextView namee = coverItemView.findViewById(R.id.name);
                    TextView treatmentnames = coverItemView.findViewById(R.id.treatmentname);
                    TextView emails = coverItemView.findViewById(R.id.email);
                    TextView contactnoo = coverItemView.findViewById(R.id.contactno);
                    TextView addresss = coverItemView.findViewById(R.id.address);
                    TextView issues = coverItemView.findViewById(R.id.issue);


                    // Set data for each view

                    namee.setText("Name: " + name);
                    treatmentnames.setText("Treatmentname: " + treatmentname);
                    emails.setText("email : " + email);
                    contactnoo.setText("mobile : " + contactno);
                    addresss.setText("address: " + address);
                    issues.setText("issue: " + issue);

                    // Add the inflated layout to the coversContainer
                    coversContainer.addView(coverItemView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Covers", "Database error: " + error.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
}