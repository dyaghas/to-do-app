package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigActivity extends AppCompatActivity {

    private LinearLayout logoutLayout;
    private LinearLayout changeEmailLayout;
    private LinearLayout changePasswordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        logoutLayout = findViewById(R.id.logoutLayout);
        changeEmailLayout = findViewById(R.id.changeEmailLayout);

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ConfigActivity.this, LoginActivity.class));
            }
        });

        changeEmailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfigActivity.this, ChangeEmailActivity.class));
            }
        });

    }
}