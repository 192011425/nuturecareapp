package com.example.nurturecareandroid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class serviceActivity extends AppCompatActivity {

    LinearLayout coversContainer;
    String shopName;

    private List<String> serviceNames = new ArrayList<>();

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoryservice);

        coversContainer = findViewById(R.id.coversContainer);

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        showCoversForShop();
    }
    private void showCoversForShop() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("category");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Your existing code...
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String serviceName = userSnapshot.child("serviceName").getValue(String.class);
                    serviceNames.add(serviceName);
                    String information = userSnapshot.child("information").getValue(String.class);
                    String imageUrl = userSnapshot.child("imageView").getValue(String.class);
                    String userId = userSnapshot.getKey(); // Unique ID of the store owner

                    // Inflate the CardView layout
                    View cardViewLayout = getLayoutInflater().inflate(R.layout.stafflist, coversContainer, false);

                    // Find Views inside the CardView layout
                    ImageView userImageView = cardViewLayout.findViewById(R.id.coverImageView);
                    Button serviceNameTextView = cardViewLayout.findViewById(R.id.customizeButton);
                    TextView informationTextView = cardViewLayout.findViewById(R.id.informationTextView);

                     //Load the image using Picasso
//                    if (imageUrl != null && imageUrl.startsWith("content://")) {
//                        if (checkSelfPermission(Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION)
//                                != PackageManager.PERMISSION_GRANTED) {
//                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                        } else {
//                            // Permission granted, proceed with loading the image
//                            loadImageFromUri(imageUrl, userImageView);
//                        }
//                    } else {
                        // Load the image using Picasso (assuming imageUrl is a URL)
//                        Picasso.get().load(imageUrl).placeholder(R.drawable.calendar).into(userImageView);
                        // Load the image using Picasso (assuming imageUrl is a URL)
                        Picasso.get().load(imageUrl).placeholder(R.drawable.calendar).into(userImageView);

                    // Set serviceName and information to TextViews
                    serviceNameTextView.setText(serviceName);
                    informationTextView.setText(information);

                    serviceNameTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int index = coversContainer.indexOfChild(cardViewLayout);
                            // Get the index of the clicked card
                            if (index >= 0 && index < serviceNames.size()) {
                                // Retrieve the service name based on the index
                                String serviceName = serviceNames.get(index);
                                // Create an intent to start the next activity
                                Intent intent = new Intent(serviceActivity.this, choosestaffActivity.class);

                                // Pass the serviceName to the next activity
                                intent.putExtra("SERVICE_NAME", serviceName);

                                // Start the next activity
                                startActivity(intent);
                            }
                        }
                    });

                    // Add the inflated layout to the coversContainer
                    coversContainer.addView(cardViewLayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("service", "Database error: " + error.getMessage());
            }
        });
    }

}
