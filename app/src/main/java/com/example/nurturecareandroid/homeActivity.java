package com.example.nurturecareandroid;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class homeActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button serviceBtn = findViewById(R.id.service_btn);
        Button StatusBtn = findViewById(R.id.status_btn);
        Button complaintsBtn = findViewById(R.id.complaint_btn);
        Button emergencyBtn = findViewById(R.id.emer_btn);

        navigationView = findViewById(R.id.nav);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

// Set your custom title
        TextView customTitleTextView = findViewById(R.id.homeTitle);
        customTitleTextView.setText("HOME");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
                    Intent intent = new Intent(homeActivity.this, homeActivity.class);
                    startActivity(intent);
                } else if (id == R.id.profile) {
                    Intent intent = new Intent(homeActivity.this, profileActivity.class);
                    startActivity(intent);
                    finish();
                } else if (id == R.id.logout) {
                    Intent intent = new Intent(homeActivity.this, selectloginActivity.class);
                    startActivity(intent);
                    finish();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homeActivity.this, serviceActivity.class);
                startActivity(i);
            }
        });

        StatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homeActivity.this, BstatusActivity.class);
                startActivity(i);
            }
        });

        complaintsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToComplaintsActivity();
            }
        });

        emergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homeActivity.this, emergencyActivity.class);
                startActivity(intent);
            }
        });
    }

//    private void navigateToServiceActivity(){
//        Intent intent = new Intent(this, choosestaffActivity.class);
//        startActivity(intent);
//    }

    private void navigateToComplaintsActivity(){
        Intent intent = new Intent(this, complaintsActivity.class);
        startActivity(intent);
    }

}
