package com.example.nurturecareandroid;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BstatusActivity extends AppCompatActivity implements PaymentResultListener {

    LinearLayout coversContainer;
    String shopName;
    String username;
    TextView rejectMessagee;
    Button paymentbtn;
    String id;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bstatus);

        coversContainer = findViewById(R.id.coversContainer);

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
                    id = coversSnapshot.getKey();

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
                    String status = coversSnapshot.child("status").getValue(String.class);
                    String feesString = coversSnapshot.child("fees").getValue(String.class);

                    // Inflate the item_cover.xml layout
                    View coverItemView = getLayoutInflater().inflate(R.layout.status, coversContainer, false);

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
                    TextView payFees = coverItemView.findViewById(R.id.pay_fees);
                    rejectMessagee = coverItemView.findViewById(R.id.rejectTV);
                    paymentbtn = coverItemView.findViewById(R.id.paymentbtn);


                    SharedPreferences sPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                    String customerName = sPreferences.getString("customerName", "");
//                    String customerContact = sPreferences.getString("customerContact", "");
//                    String customerEmail = sPreferences.getString("customerEmail", "");

                    Checkout.preload(getApplicationContext());

                    if (name.equals(customerName)){
                        // Set data for each view
                    namee.setText("Name: " + name);
                    treatmentnames.setText("Treatmentname: " + treatmentname);
                    emails.setText("email : " + email);
                    contactnoo.setText("mobile : " + contactno);
                    genders.setText("gender: " + gender);
                    patientids.setText("patientid: " + patientid);
                    addresss.setText("address: " + address);
                    dates.setText("date: " + date);
                    times.setText("time: " + time);
                    payFees.setText("fees: " + feesString);
                    staffids.setText("staffid: " + staffid);

                        int fees = Integer.parseInt(feesString);

                    final String profileKey = "profileId_" + id;
                    SharedPreferences preferences = getSharedPreferences("buttonVisibility", MODE_PRIVATE);
                    final SharedPreferences.Editor editor = preferences.edit();

// Check the visibility state on app start
                    boolean areButtonsVisible = preferences.getBoolean(profileKey, true);

                    if (status != null) {
                        if (status.equals("accepted")) {
                            // Show payment button
                            paymentbtn.setVisibility(View.VISIBLE);
                            rejectMessagee.setVisibility(View.GONE);
                        } else if (status.equals("rejected")) {
                            rejectMessagee.setVisibility(View.VISIBLE);
                            paymentbtn.setVisibility(View.GONE);
                            rejectMessagee.setText("Thanks for Booking, Unfortunately your Request is Rejected");
                        }
                    }

                    paymentbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPayment(fees,id);
                        }
                    });

                    // Add the inflated layout to the coversContainer
                    coversContainer.addView(coverItemView);

                }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Covers", "Database error: " + error.getMessage());
            }
        });
    }

    private void startPayment(int fees, String id) {
        // Initialize Razorpay Checkout
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_U2XWpODmhRkL0l"); // Replace with your Razorpay key ID

        try {
            // Create payment options
            JSONObject options = new JSONObject();
            options.put("name", "Nurture Care");
            options.put("description", "Payment for Booking");
            options.put("currency", "INR");
            options.put("amount", fees*100); // Replace with the actual amount in paise
            options.put("prefill.email", "customer@Nurturecare.com");
            options.put("prefill.contact", "030-050-040");

            // Open Razorpay Checkout activity
            checkout.open(BstatusActivity.this, options);
        } catch (Exception e) {
            Log.e("RazorpayPaymentActivity", "Error in starting Razorpay Checkout", e);
            Toast.makeText(this, "Error in payment", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String paymentId) {
        Log.d("PaymentSuccess", "Payment success: " + paymentId);
        Toast.makeText(this, "Payment success", Toast.LENGTH_SHORT).show();
        updatePaymentDetails(id,paymentId, "Payment Success");
    }

    private void updatePaymentDetails(String id,String paymentId, String paymentStatus) {
        // Get the unique ID for the current entry
        /* Get the ID of the current entry, you might need to modify this */;

        DatabaseReference paymentReference = FirebaseDatabase.getInstance().getReference("userRequest").child("Date").child(id);

        // Create a map to update the payment details
        Map<String, Object> paymentDetails = new HashMap<>();
        paymentDetails.put("paymentId", paymentId);
        paymentDetails.put("paymentStatus", paymentStatus);

        // Update the Firebase entry with payment details
        paymentReference.updateChildren(paymentDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("PaymentDetails", "Payment details updated successfully");
                        rejectMessagee.setVisibility(View.GONE);
                        paymentbtn.setVisibility(View.VISIBLE);
                        paymentbtn.setText("Paid");
                        paymentbtn.setEnabled(false);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("PaymentDetails", "Error updating payment details: " + e.getMessage());
                    }
                });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("PaymentError", "Payment failed: " + s);
        Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
        updatePaymentDetails(id,"", "Payment Failed");
    }
}