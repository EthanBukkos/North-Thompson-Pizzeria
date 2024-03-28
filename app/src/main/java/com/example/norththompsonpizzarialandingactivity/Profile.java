package com.example.norththompsonpizzarialandingactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

   EditText nameEditText, addressEditText, paymentEditText;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Button saveBtn, backBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameEditText = findViewById(R.id.nameInputEditText);
        addressEditText = findViewById(R.id.addressInputEditText);
        paymentEditText = findViewById(R.id.paymentInputEditText);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        saveBtn = findViewById(R.id.saveProfileBtn);
        backBtn = findViewById(R.id.profileBackBtn);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Profile.this, SignInActivity.class);
                startActivity(backIntent);
            }
        });

        saveBtn.setOnClickListener(v -> saveUserProfile());

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userID = user.getUid();

            // Retrieve user details from Firebase
            databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserProfile userProfile = snapshot.getValue(UserProfile.class);
                    if (userProfile != null) {
                        nameEditText.setText(userProfile.name);
                        addressEditText.setText(userProfile.address);
                        paymentEditText.setText(userProfile.paymentMethod);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Profile.this, "Failed to load user profile.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public static class UserProfile {
        public String name, address, paymentMethod;

        public UserProfile() {

        }

        public UserProfile(String name, String address, String paymentMethod) {
            this.name = name;
            this.address = address;
            this.paymentMethod = paymentMethod;
        }
    }

    public void saveUserProfile() {
        String name = nameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String paymentMethod = paymentEditText.getText().toString().trim();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userID = user.getUid();
            UserProfile updatedProfile = new UserProfile(name, address, paymentMethod);
            databaseReference.child(userID).setValue(updatedProfile)
                    .addOnCompleteListener(unused -> Toast.makeText(Profile.this, "Profile Updated.",
                            Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(Profile.this, "Update Failed..",
                            Toast.LENGTH_SHORT).show());
        }
    }

    }
