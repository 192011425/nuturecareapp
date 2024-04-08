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
import androidx.appcompat.app.AppCompatActivity;

import com.example.nurturecareandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class choosestaffActivity extends AppCompatActivity  {

    LinearLayout coversContainer;
    String shopName;
    String username;
    String serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosestaff);

        coversContainer = findViewById(R.id.coversContainer);

        Intent intent = getIntent();
        if (intent != null) {
            // Check if the key "serviceName" exists in the Intent extras
            if (intent.hasExtra("SERVICE_NAME")) {
                // Retrieve the value associated with the key "serviceName"
                serviceName = intent.getStringExtra("SERVICE_NAME");
            }
        }

        addStaffData();

    }

    private void addStaffData() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Staff").child(serviceName);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("Covers", "Querying covers for shop: " + shopName);
//
//                if (!snapshot.exists()) {
//                    // No data found for the specific shop
//                    Log.e("Covers", "No data found for covers in shop: " + shopName);
//                    return;
//                }
//
//                Log.d("Covers", "Number of entries found for covers in shop " + shopName + ": " + snapshot.getChildrenCount());

                // Clear the coversContainer before adding views
                coversContainer.removeAllViews();

                for (DataSnapshot coversSnapshot : snapshot.getChildren()) {
                    Log.d("Covers", "Processing entry for covers");
                    View coverItemView = getLayoutInflater().inflate(R.layout.item_staff, coversContainer, false);
                    Button bookingButton = coverItemView.findViewById(R.id.bookingButton);

                    String name = coversSnapshot.child("name").getValue(String.class);
                    String specification = coversSnapshot.child("specification").getValue(String.class);
                    String contactno = coversSnapshot.child("contactno").getValue(String.class);
                    String experience = coversSnapshot.child("experience").getValue(String.class); // Retrieve the nextpage value
                    String address = coversSnapshot.child("address").getValue(String.class);
                    String id = coversSnapshot.child("id").getValue(String.class);
                    String fees = coversSnapshot.child("fees").getValue(String.class);

                    Log.d("Covers", "serviceName: " + serviceName + ", specification: " + specification);

                        // Inflate the item_cover.xml layout
                        // Find views in the inflated layout

                        TextView names = coverItemView.findViewById(R.id.name);
                        TextView tretmentname = coverItemView.findViewById(R.id.tretmentname);
                        TextView contactnoo = coverItemView.findViewById(R.id.contactno);
                        TextView experiencee = coverItemView.findViewById(R.id.experience);
                        TextView addresss = coverItemView.findViewById(R.id.address);
                        TextView ids = coverItemView.findViewById(R.id.id);
                        TextView feess = coverItemView.findViewById(R.id.fees);


                        // Set data for each view
                    if (specification != null && specification.equals(serviceName)) {

                        names.setText("Name: " + name);
                        tretmentname.setText("Treatment: " + specification);
                        contactnoo.setText("mobile : " + contactno);
                        experiencee.setText("experience: " + experience);
                        addresss.setText("address: " + address);
                        ids.setText("staffid: " + id);
                        feess.setText("fees: " + fees);

                        // Add the inflated layout to the coversContainer
                        coversContainer.addView(coverItemView);

                        bookingButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Create an Intent to start the new activity
                                Intent a = new Intent(choosestaffActivity.this,userentrydActivity.class);
                                a.putExtra("staffId", id);
                                a.putExtra("specification", specification);
                                a.putExtra("fees", fees);
                                startActivity(a);
                                finish();
                                // You can also pass data to the new activity if needed
                                // intent.putExtra("key", "value");
                            }
                        });
                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Covers", "Database error: " + error.getMessage());
            }
        });
    }
}