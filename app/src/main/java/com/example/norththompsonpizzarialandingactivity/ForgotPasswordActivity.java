package com.example.norththompsonpizzarialandingactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button backBtn, verifyCodeBtn;
    private EditText emailEditText, smsCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        backBtn = findViewById(R.id.forgotPwdBackBtn);
        emailEditText = findViewById(R.id.resetEmailInput);
        smsCodeEditText = findViewById(R.id.codeInput);
        verifyCodeBtn = findViewById(R.id.verifyCodeBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                startActivity(backIntent);
            }
        });
    }

    public void onResetRequest(View view) {
        String userEmail = emailEditText.getText().toString();

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("userProfile").whereEqualTo("email", userEmail).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            String phoneNumber = documentSnapshot.getString("phoneNumber");

                            Map<String, Object> data = new HashMap<>();
                            data.put("phoneNumber", phoneNumber);

                            FirebaseFunctions.getInstance()
                                    .getHttpsCallable("sendPasswordSMS")
                                    .call(data)
                                    .addOnCompleteListener(new OnCompleteListener<HttpsCallableResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<HttpsCallableResult> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ForgotPasswordActivity.this, "SMS sent.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ForgotPasswordActivity.this, "SMS failed to send.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ForgotPasswordActivity.this, "Error sending SMS: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Email is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Toast.makeText(ForgotPasswordActivity.this, "Failed to retrieve user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
