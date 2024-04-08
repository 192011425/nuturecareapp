package com.example.nurturecareandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectloginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlogin);

        Button loginButton = findViewById(R.id.admin);
        Button staff = findViewById(R.id.staff);
        Button customer = findViewById(R.id.customer);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selectloginActivity.this, adminloginActivity.class);
                startActivity(intent);
            }
        });

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selectloginActivity.this, staffloginActivity.class);
                startActivity(intent);
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selectloginActivity.this, customerloginActivity.class);
                startActivity(intent);
            }
        });
    }
}