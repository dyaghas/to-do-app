package com.example.todo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerEmail;
    private EditText registerPassword;
    private EditText confirmPassword;

    private TextView goToLogin;

    private Button registerBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerEmail = findViewById(R.id.editTextEmailRegister);
        registerPassword = findViewById(R.id.editTextPasswordRegister);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        goToLogin = findViewById(R.id.textViewGoToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();
        String cp = confirmPassword.getText().toString();

        //Empty fields
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            String toastMessage = RegisterActivity.this.getResources().
                    getString(R.string.empty_fields);
            Toast.makeText(RegisterActivity.this, toastMessage,
                    Toast.LENGTH_SHORT).show();
        //Password fields does not match
        } else if(!cp.equals(password)) {
            String toastMessage = RegisterActivity.this.getResources().
                    getString(R.string.password_match_false);
            Toast.makeText(RegisterActivity.this, toastMessage,
                    Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign up success, update UI with the signed-up user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                String toastMessage = RegisterActivity.this.getResources().
                                        getString(R.string.account_creation_success);
                                Toast.makeText(RegisterActivity.this, toastMessage,
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();

                                startActivity(new Intent(
                                        RegisterActivity.this, LoginActivity.class));
                            } else {
                                // If sign up fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                String toastMessage = RegisterActivity.this.getResources().
                                        getString(R.string.failed_acc_creation);
                                Toast.makeText(RegisterActivity.this, toastMessage,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}