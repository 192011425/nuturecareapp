package com.example.nurturecareandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class frontpageActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);
//        loginButton = findViewById(R.id.button);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(frontpageActivity.this, selectloginActivity.class);
//                startActivity(intent);
//            }
//        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start your main activity
                Intent intent = new Intent(frontpageActivity.this, selectloginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}