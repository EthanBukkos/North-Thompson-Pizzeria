package com.norththompsonpizzeria.signincreateaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {

    EditText newPwdEditText, confirmPwdEditText;
    Button resetBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        newPwdEditText =findViewById(R.id.newPwdInput);
        confirmPwdEditText = findViewById(R.id.retypePwdInput2);
        resetBtn = findViewById(R.id.resetPwdBtn);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPwdEditText.getText().toString().trim();
                String confirmNewPassword = confirmPwdEditText.getText().toString().trim();

                if (!newPassword.equals(confirmNewPassword)) {
                    Toast.makeText(ResetPassword.this,"Passwords must match.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPassword.length() < 6) {
                    Toast.makeText(ResetPassword.this,"Password must be at least 6 characters.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // After checks, proceed with password reset
                updatePassword(newPassword);
            }
        });
    }

    private void updatePassword(String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.updatePassword(newPassword).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    Toast.makeText(ResetPassword.this, "Password changed successfully.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResetPassword.this, "Failed to change password.",
                            Toast.LENGTH_SHORT).show();
                }

            });
        }
    }
}