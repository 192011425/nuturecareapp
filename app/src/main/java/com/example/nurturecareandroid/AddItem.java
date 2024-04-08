package com.example.nurturecareandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServiceDetailAdapter adapter;
    private List<Service> serviceList;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        databaseReference = FirebaseDatabase.getInstance().getReference("category");

        serviceList = new ArrayList<>();
        adapter = new ServiceDetailAdapter(this, serviceList);
        recyclerView.setAdapter(adapter);
        Button uploadProductButton = findViewById(R.id.uploadProductButton);

        uploadProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to UploadProductActivity
                Intent intent = new Intent(AddItem.this, UploadProductActivity.class);
                startActivity(intent);
            }
        });

        // Retrieve service details from Firebase Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Service service = snapshot.getValue(Service.class);
                    serviceList.add(service);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddItem.this, "Failed to retrieve service details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}