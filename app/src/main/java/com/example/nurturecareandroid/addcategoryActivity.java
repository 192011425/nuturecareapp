package com.example.nurturecareandroid;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addcategoryActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private EditText ServiceNameEditText;

    private EditText InformationEditText;
    private ImageView imageView;
    private Button submitButton;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private static final int PICK_IMAGE_REQUEST = 1;


    private FirebaseDatabase rootnode;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategory);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        ServiceNameEditText= findViewById(R.id.ser_name);
        InformationEditText = findViewById(R.id.add_information);
        imageView = findViewById(R.id.pro_img);
        submitButton = findViewById(R.id.submit);

        drawerLayout = findViewById(R.id.drawerLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView customTitleTextView = findViewById(R.id.addCategory);
        customTitleTextView.setText("Add Category");

        navigationView = findViewById(R.id.nav_bar);
        navigationView.setNavigationItemSelectedListener(addcategoryActivity.this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                // Open gallery to choose image
//                Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
//                galleryIntent.setType("image/*");
//                galleryIntent.setType("image/*");
//                startActivityForResult(galleryIntent, 1);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootnode = FirebaseDatabase.getInstance();
                mDatabase = rootnode.getReference("category");


                // Get all values
                String servicename = ServiceNameEditText.getText().toString();
                String information = InformationEditText.getText().toString();
                String imageView  = imageUri.toString();


                // Create CartHelperClass object
                categoryHelperClass helperClass = new categoryHelperClass(servicename, information, imageView);

                // Use push() to generate unique key
                mDatabase.child(servicename).setValue(helperClass);

                uploadData();

                // Move to the next activity
                Intent b = new Intent(addcategoryActivity.this,addstaffActivity.class);
                startActivity(b);
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadData() {
        String serviceName = ServiceNameEditText.getText().toString().trim();
        String information = InformationEditText.getText().toString().trim();

        if (serviceName.isEmpty() || information.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference filePath = mStorage.child("category").child("images").child("images/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));
        filePath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    // Image uploaded successfully, get the download URL
                    filePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();

                                // Save service details to database using correct user ID
                                DatabaseReference userServicesRef = mDatabase.child("users").child("services").child("serviceName");
                                userServicesRef.child("serviceName").setValue(serviceName);
                                userServicesRef.child("information").setValue(information);
                                userServicesRef.child("imageUrl").setValue(downloadUri.toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(addcategoryActivity.this, "Service uploaded successfully.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(addcategoryActivity.this, "Failed to upload service details.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(addcategoryActivity.this, "Failed to get image download URL.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(addcategoryActivity.this, "Failed to upload image.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            imageUri = data.getData();
//            imageView.setImageURI(imageUri);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
             imageUri = data.getData();
        }
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