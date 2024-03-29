package com.example.todo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;

    private TextView goToRegister;
    private TextView forgotPassword;

    private Button loginBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.editTextEmail);
        loginPassword = findViewById(R.id.editTextPassword);
        goToRegister = findViewById(R.id.textViewGoToRegister);
        forgotPassword = findViewById(R.id.textViewForgotPassword);

        //ClickListeners
        goToRegister.setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
        forgotPassword.setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class)));

        loginBtn = findViewById(R.id.loginBtn);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        if(TextUtils.isEmpty(email)) {
            loginEmail.setError((getString(R.string.empty_fields)));
            loginEmail.requestFocus();
        } else if(TextUtils.isEmpty(password)) {
            loginPassword.setError((getString(R.string.empty_fields)));
            loginPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Log.d(TAG, "sign in with email: success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Log.w(TAG, "sign in with email: failure", task.getException());
                        String toastMessage = LoginActivity.this.getResources().
                                getString(R.string.failed_authentication);
                        Toast.makeText(LoginActivity.this, toastMessage,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}