package com.example.todo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextView emailTextView;
    private Button sendPasswordResetBtn;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailTextView = findViewById(R.id.editTextForgotPasswordEmail);
        sendPasswordResetBtn = findViewById(R.id.sendPasswordResetBtn);

        sendPasswordResetBtn.setOnClickListener(view -> {
            setEmail(emailTextView.getText().toString());

            //Verifies if a textview is empty or not
            if(TextUtils.isEmpty(getEmail())) {
                String toastMessage = ResetPasswordActivity.this.getResources().
                        getString(R.string.empty_fields);
                Toast.makeText(ResetPasswordActivity.this, toastMessage,
                        Toast.LENGTH_SHORT).show();
            } else {
                resetPassword();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (height*.4));
    }

    public void resetPassword() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "resetPassword:sent");
                    String toastMessage = ResetPasswordActivity.this.getResources()
                            .getString(R.string.email_sent);
                    Toast.makeText(ResetPasswordActivity.this, toastMessage,
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.w(TAG, "resetPassword:failed to send", task.getException());
                    String toastMessage = ResetPasswordActivity.this.getResources()
                            .getString(R.string.error);
                    Toast.makeText(ResetPasswordActivity.this, toastMessage,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}