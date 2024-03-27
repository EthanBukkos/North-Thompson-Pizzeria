package com.example.norththompsonpizzarialandingactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyCode extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText codeInput;
    private Button verifyBtn, resendCodeBtn;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        mAuth = FirebaseAuth.getInstance();
        codeInput = findViewById(R.id.codeInputAccountCreation);
        verifyBtn = findViewById(R.id.verifyBtn);
        resendCodeBtn = findViewById(R.id.resendCodeBtn);

        // Extract data from intent
        final String phoneNumber = getIntent().getStringExtra("phoneNumber");
        final String email = getIntent().getStringExtra("email");
        final String password = getIntent().getStringExtra("password");

        // Send the verification code
        sendVerificationCode(phoneNumber);

        // Click listener for 'Verify Code' button
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("ActivityLifeCycle","VerifyCode: onCreate");
                String code = codeInput.getText().toString().trim();

                if (code.isEmpty()) {
                    Toast.makeText(VerifyCode.this,"Please input the verification code.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                    signInWithAuthCredential(credential, email, password);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(VerifyCode.this,"Verification failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        resendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resendVerificationCode(phoneNumber, mResendToken);
            }
        });

    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(VerifyCode.this, "Invalid.",
                                Toast.LENGTH_SHORT).show();
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        Toast.makeText(VerifyCode.this, "Daily SMS request limit reached.",
                                Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(VerifyCode.this, "Verification failed.",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider
                        .ForceResendingToken token) {

                    super.onCodeSent(verificationId, token);

                    mVerificationId  = verificationId;
                    mResendToken = token;

                }
            };

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(token)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithAuthCredential(PhoneAuthCredential credential, String email, String password) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // If verification is successful, create the user using email and password
                    createUserWithEmailPassword(email, password);
                } else {
                    Toast.makeText(VerifyCode.this,"Verification failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUserWithEmailPassword(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("VerifyCode", "Account creation successful");
                            Intent signInIntent = new Intent(VerifyCode.this,
                                    SignInActivity.class);
                            // Clear the activity stack;
                            signInIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Log.d("VerifyCode", "Redirecting to SignInActivity");
                            startActivity(signInIntent);
                        } else {
                            Toast.makeText(VerifyCode.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
