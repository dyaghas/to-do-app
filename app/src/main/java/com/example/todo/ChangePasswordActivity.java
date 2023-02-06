package com.example.todo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private Button btnConfirmPasswordChange;
    private EditText editTextNewPassword;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmNewPassword;

    private String newPassword;
    private String currentEmail;
    private String currentPassword;
    private String confirmNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextEmail = findViewById(R.id.editTextCurrentEmail2);
        editTextPassword = findViewById(R.id.editTextCurrentPassword2);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmNewPassword = findViewById(R.id.editTextConfirmNewPassword);
        btnConfirmPasswordChange = findViewById(R.id.btnConfirmPasswordChange);

        btnConfirmPasswordChange.setOnClickListener(view -> {
            passResetViaEmail();
        });
    }

    private void passResetViaEmail(){
        currentEmail = editTextEmail.getText().toString();
        currentPassword = editTextPassword.getText().toString();
        newPassword = editTextNewPassword.getText().toString();
        confirmNewPassword = editTextConfirmNewPassword.getText().toString();

        if(TextUtils.isEmpty(currentEmail) || TextUtils.isEmpty(currentPassword) ||
                TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmNewPassword)) {
            String toastMessage = ChangePasswordActivity.this.getResources().
                    getString(R.string.empty_fields);
            Toast.makeText(ChangePasswordActivity.this, toastMessage,
                    Toast.LENGTH_SHORT).show();
        } else {
            if(confirmNewPassword.equals(newPassword)) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                AuthCredential credential = EmailAuthProvider
                        .getCredential(currentEmail, currentPassword);

                //prompt user authentication as changing password address is a sensitive operation
                assert user != null;
                user.reauthenticate(credential).addOnCompleteListener(task -> {
                    Log.d(TAG, "User re-authenticated.");
                    setPassword();
                    finish();
                });
            } else {
                String toastMessage = ChangePasswordActivity.this.getResources().
                        getString(R.string.password_match_false);
                Toast.makeText(ChangePasswordActivity.this, toastMessage,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //setters
    public void setPassword() {
        //Change user password
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        assert user1 != null;
        user1.updatePassword(newPassword).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                Log.d(TAG, "User password updated.");
                String toastMessage = ChangePasswordActivity.this.getResources().
                        getString(R.string.password_change_success);
                Toast.makeText(ChangePasswordActivity.this, toastMessage,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}