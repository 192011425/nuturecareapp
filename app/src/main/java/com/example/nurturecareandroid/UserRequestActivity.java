package com.example.nurturecareandroid;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nurturecareandroid.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class UserRequestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout coversContainer;
    String shopName;
    String username;
    String staffId;
    DrawerLayout drawerLayout;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request);

        coversContainer = findViewById(R.id.coversContainer);

        Intent intent = getIntent();
            if (intent.hasExtra("STAFF_ID")) {
                // Retrieve the value associated with the key "serviceName"
                staffId = intent.getStringExtra("STAFF_ID");
            }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

// Set your custom title
        TextView customTitleTextView = findViewById(R.id.userRequestTV);
        customTitleTextView.setText("User Request");

        drawerLayout = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.nav_bar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        showCustomerData();

    }

    private void showCustomerData() {

        reference = FirebaseDatabase.getInstance().getReference("userRequest").child("Date");

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

                    String id = coversSnapshot.getKey();

                    String name = coversSnapshot.child("name").getValue(String.class);
                    String treatmentname = coversSnapshot.child("treatmentName").getValue(String.class);
                    String email = coversSnapshot.child("email").getValue(String.class);// Retrieve the nextpage value
                    String contactno = coversSnapshot.child("contactNo").getValue(String.class);
                    String gender = coversSnapshot.child("gender").getValue(String.class);
                    String patientid = coversSnapshot.child("patientId").getValue(String.class);
                    String address = coversSnapshot.child("address").getValue(String.class);
                    String date = coversSnapshot.child("date").getValue(String.class);
                    String time = coversSnapshot.child("time").getValue(String.class);
                    String staffid = coversSnapshot.child("staffId").getValue(String.class);

                    // Inflate the item_cover.xml layout
                    View coverItemView = getLayoutInflater().inflate(R.layout.userdata, coversContainer, false);

                    // Find views in the inflated layout

                    TextView namee = coverItemView.findViewById(R.id.name);
                    TextView treatmentnames = coverItemView.findViewById(R.id.treatmentname);
                    TextView emails = coverItemView.findViewById(R.id.email);
                    TextView contactnoo = coverItemView.findViewById(R.id.contactno);
                    TextView genders = coverItemView.findViewById(R.id.gender);
                    TextView patientids = coverItemView.findViewById(R.id.patientid);
                    TextView addresss = coverItemView.findViewById(R.id.address);
                    TextView dates = coverItemView.findViewById(R.id.date);
                    TextView times = coverItemView.findViewById(R.id.time);
                    TextView staffids = coverItemView.findViewById(R.id.staffid);
                    Button acceptbtn = coverItemView.findViewById(R.id.acceptbtn);
                    Button rejectbtn = coverItemView.findViewById(R.id.rejectbtn);


                    // Set data for each view

                    if (staffid != null && staffid.equals(staffId)) {
                        namee.setText("Name: " + name);
                        treatmentnames.setText("Treatmentname: " + treatmentname);
                        emails.setText("email : " + email);
                        contactnoo.setText("mobile : " + contactno);
                        genders.setText("gender: " + gender);
                        patientids.setText("patientid: " + patientid);
                        addresss.setText("address: " + address);
                        dates.setText("date: " + date);
                        times.setText("time: " + time);
                        staffids.setText("staffid: " + staffid);

                        coversContainer.addView(coverItemView);
                    }

                    final String profileKey = "profileId_" + id;
                    SharedPreferences preferences = getSharedPreferences("buttonVisibility", MODE_PRIVATE);
                    final SharedPreferences.Editor editor = preferences.edit();

                    // Check the visibility state on app start
                    boolean areButtonsVisible = preferences.getBoolean(profileKey, true);

                    acceptbtn.setVisibility(areButtonsVisible ? View.VISIBLE : View.GONE);
                    rejectbtn.setVisibility(areButtonsVisible ? View.VISIBLE : View.GONE);

                    acceptbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateStatus(id, "accepted");
                            acceptbtn.setVisibility(View.GONE);
                            rejectbtn.setVisibility(View.GONE);
                            editor.putBoolean(profileKey, false);
                            editor.apply();
                        }
                    });

                    rejectbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateStatus(id, "rejected");
                            acceptbtn.setVisibility(View.GONE);
                            rejectbtn.setVisibility(View.GONE);
                            editor.putBoolean(profileKey, false);
                            editor.apply();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Covers", "Database error: " + error.getMessage());
            }
        });
    }

    private void updateStatus(String id, String status) {
        DatabaseReference dateRef = reference.child(id);
        Map<String, Object> statusUpdate = new HashMap<>();
        statusUpdate.put("status", status);

        dateRef.updateChildren(statusUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Status updated successfully
                        Log.e("status :" ,"success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Log.e("status :" ,"failure");
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.staff_issues) {
            Intent i = new Intent(this, raiseissueActivity.class);
            startActivity(i);
        } else if (id == R.id.user_request) {
            Intent i = new Intent(this, UserRequestActivity.class);
            i.putExtra("STAFF_ID", staffId);
            startActivity(i);
        } else if (id == R.id.staff_home_logout) {
            Intent i = new Intent(this, selectloginActivity.class);
            startActivity(i);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}