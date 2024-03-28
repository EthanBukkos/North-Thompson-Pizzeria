package com.example.norththompsonpizzarialandingactivity;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class AccountCreation extends AppCompatActivity {

    Button backBtn, createAccount;
    EditText phoneEditText, emailEditText, pwdEditText, retypePwdEditText;
    FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        mAuth = FirebaseAuth.getInstance();
        backBtn = findViewById(R.id.createAccBackBtn);
        phoneEditText = findViewById(R.id.phoneInput);
        emailEditText = findViewById(R.id.creatAcctEmailInput);
        pwdEditText = findViewById(R.id.createPwdInput);
        retypePwdEditText = findViewById(R.id.retypePwdInput);
        createAccount = findViewById(R.id.AccCreateBtn);

        // Listener for 'Back' button in AccountCreation
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(AccountCreation.this,
                        SignInActivity.class);
                startActivity(backIntent);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString().trim(); // trim ensures no whitespaces
                String password = pwdEditText.getText().toString().trim();
                String retypePassword = retypePwdEditText.getText().toString().trim();
                String phoneNumber = phoneEditText.getText().toString().trim();
                String phonePattern = "^\\+1\\d{10}$";

                if (!phoneNumber.matches(phonePattern)) {
                    Toast.makeText(AccountCreation.this, "Please user correct phone number format.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the passwords match
                if (!password.equals(retypePassword)) {
                    Toast.makeText(AccountCreation.this,"Passwords must match.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getApplicationContext(),
                            "Please input all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(),
                            "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If all validations are passed, proceed with SMS code verification
                Log.d(TAG, "Attempting to start VerifyCode Activity with phoneNumber: " + phoneNumber);
                Intent verifyCodeIntent = new Intent(AccountCreation.this, VerifyCode.class);
                verifyCodeIntent.putExtra("phoneNumber", phoneNumber);
                verifyCodeIntent.putExtra("email", email);
                verifyCodeIntent.putExtra("password", password);
                startActivity(verifyCodeIntent);

            }
        });
    }

    private void createUserProfile(String email, String password, String phoneNumber) {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userID = user.getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference usersRef = database.getReference("users");

            Map<String, Object> userData = new HashMap<>();
            userData.put("email", email);
            userData.put("phoneNumber", phoneNumber);

            usersRef.child(userID).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(AccountCreation.this,
                            "User profile created successfully", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(AccountCreation.this, "User profile creation failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}