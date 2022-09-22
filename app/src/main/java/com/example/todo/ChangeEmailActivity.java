package com.example.todo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailActivity extends AppCompatActivity {

     private Button btnConfirm;
     private EditText editTextNewEmail;
     private EditText editTextEmail;
     private EditText editTextPassword;

     private String newEmail;
     private String currentEmail;
     private String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        btnConfirm = findViewById(R.id.btnConfirm);
        editTextNewEmail = findViewById(R.id.editTextChangeEmail);
        editTextEmail = findViewById(R.id.editTextCurrentEmail);
        editTextPassword = findViewById(R.id.editTextCurrentPassword);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (height*.6));

        btnConfirm.setOnClickListener(view -> {
            changeEmail();
        });
    }

    private void changeEmail() {
        newEmail = editTextNewEmail.getText().toString();
        currentEmail = editTextEmail.getText().toString();
        currentPassword = editTextPassword.getText().toString();

        if(TextUtils.isEmpty(newEmail) || TextUtils.isEmpty(currentEmail) ||
                TextUtils.isEmpty(currentPassword)) {
            String toastMessage = ChangeEmailActivity.this.getResources().
                    getString(R.string.empty_fields);
            Toast.makeText(ChangeEmailActivity.this, toastMessage,
                    Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider
                    .getCredential(currentEmail, currentPassword);

            //prompt user authentication as changing email address is a sensitive operation
            assert user != null;
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                Log.d(TAG, "User re-authenticated.");
                setEmail();
                finish();
            });
        }
    }

    //setters
    public void setEmail() {
        //Change email address
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        assert user1 != null;
        user1.updateEmail(newEmail).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                Log.d(TAG, "User email address updated.");
                String toastMessage = ChangeEmailActivity.this.getResources().
                        getString(R.string.email_change_success);
                Toast.makeText(ChangeEmailActivity.this, toastMessage,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}