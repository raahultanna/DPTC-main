package com.team7.dptc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Users extends AppCompatActivity {
    private Button mUser, mAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mUser = (Button) findViewById(R.id.user);
        mAdmin = (Button) findViewById(R.id.admin);

        mUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Users.this, UserLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Users.this, AdminLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}