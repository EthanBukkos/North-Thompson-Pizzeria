package com.example.norththompsonpizzarialandingactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button backBtn, verifyCodeBtn, sendCodeBtn;
    private EditText emailEditText, smsCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        backBtn = findViewById(R.id.forgotPwdBackBtn);
        emailEditText = findViewById(R.id.resetEmailInput);
        smsCodeEditText = findViewById(R.id.codeInput);
        verifyCodeBtn = findViewById(R.id.verifyCodeBtn);
        sendCodeBtn = findViewById(R.id.forgotPwdSendCodeBtn);
    }

    private void setupListeners() {
        backBtn.setOnClickListener(v -> startActivity(new Intent(ForgotPasswordActivity.this, SignInActivity.class)));
        sendCodeBtn.setOnClickListener(this::onResetRequest);
        verifyCodeBtn.setOnClickListener(v -> onVerifyCode());
    }

    public void onResetRequest(View view) {
        String userEmail = emailEditText.getText().toString().trim();

        if (userEmail.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("userProfile").whereEqualTo("email", userEmail).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        String phoneNumber = documentSnapshot.getString("phoneNumber");

                        Map<String, Object> data = new HashMap<>();
                        data.put("phoneNumber", phoneNumber);

                        FirebaseFunctions.getInstance()
                                .getHttpsCallable("sendPasswordSMS")
                                .call(data)
                                .addOnSuccessListener(httpsCallableResult ->
                                        Toast.makeText(ForgotPasswordActivity.this, "SMS sent.", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e ->
                                        Toast.makeText(ForgotPasswordActivity.this, "Error sending SMS: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email is incorrect.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(ForgotPasswordActivity.this, "Failed to retrieve user data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void onVerifyCode() {
        Intent resetPasswordIntent = new Intent(ForgotPasswordActivity.this, ResetPassword.class);
        startActivity(resetPasswordIntent);
    }
}
