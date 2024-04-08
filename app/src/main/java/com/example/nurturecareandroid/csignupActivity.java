package com.example.nurturecareandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class csignupActivity extends AppCompatActivity {

    TextView signUp, cus_log_link_button;
    EditText customerName, customerPhone, customerGender, customerEmail, customerPassword;
    Button customerRegister;

    FirebaseDatabase rootNode;

    DatabaseReference reference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csignup);

        signUp = findViewById(R.id.customerSign);
        cus_log_link_button = findViewById(R.id.cus_log_link_btn);
        customerName = findViewById(R.id.cus_reg_name);
        customerPhone = findViewById(R.id.cus_reg_phoneNo);
        customerEmail = findViewById(R.id.cus_reg_email);
        customerPassword = findViewById(R.id.cus_reg_password);
        customerGender = findViewById(R.id.cus_reg_gender);
        customerRegister = findViewById(R.id.cus_reg_btn);

        cus_log_link_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent( csignupActivity.this, customerloginActivity.class);
                startActivity(v);
            }
        });


        customerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (customerName.getText().toString().isEmpty() || customerPhone.getText().toString().isEmpty() || customerEmail.getText().toString().isEmpty() || customerPassword.getText().toString().isEmpty() || customerGender.getText().toString().isEmpty()) {
                    Toast.makeText(csignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    saveAdminData();
                }
            }
        });


    }

    private void saveAdminData() {

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Customer");

        //get all values

        String name = customerName.getText().toString();
        String phoneNo = customerPhone.getText().toString();
        String email = customerEmail.getText().toString();
        String password = customerPassword.getText().toString();
        String gender = customerGender.getText().toString();

        // Create a HelperClass object with the admin's information
        HelperClass helperClass = new HelperClass(name, phoneNo, email, password, gender);

        // Store the data in the Firebase Realtime Database under the "Admin" node
        reference.child(name).setValue(helperClass);

        // Start a new activity (adminloginActivity in this case)
        Intent i = new Intent(csignupActivity.this, customerloginActivity.class);
        startActivity(i);

    }
}