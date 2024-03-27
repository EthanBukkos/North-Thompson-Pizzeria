package com.norththompsonpizzeria.signincreateaccount;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    Button signInBtn, createAccBtn, forgotPwdBtn;
    EditText emailEditText, pwdEditText;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInBtn = findViewById(R.id.signInBtn);
        createAccBtn = findViewById(R.id.createAccBtn);
        forgotPwdBtn = findViewById(R.id.forgotPwdBtn);
        emailEditText = findViewById(R.id.usernameInput);
        pwdEditText = findViewById(R.id.pwdInput);

        // Firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        // Listener for 'Forgot Password' button
        forgotPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPwdIntent = new Intent(
                        SignInActivity.this,ForgotPasswordActivity.class);
                startActivity(forgotPwdIntent);
            }
        });

        // Listener for 'Create Account' button
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccIntent = new Intent(
                        SignInActivity.this, AccountCreation.class);
                startActivity(createAccIntent);
            }
        });

        // Listener for 'Sign In' button
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = emailEditText.getText().toString();
                password = pwdEditText.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                            "Please input email/password.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),
                            "Please input email/password.",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignInActivity.this,
                                            "Sign in successful.",Toast.LENGTH_SHORT).show();
                                    Intent signedInMenuIntent = new Intent(SignInActivity.this,
                                            Menu.class);
                                    startActivity(signedInMenuIntent);
                                    finish();
                                } else {

                                    Toast.makeText(SignInActivity.this,
                                            "Email/Password is incorrect.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }

    // Check if user is signed in
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null){
//            Intent signedInMenuIntent = new Intent(SignInActivity.this,
//                    Menu.class);
//            startActivity(signedInMenuIntent);
//            finish();
//        }
//    }

}