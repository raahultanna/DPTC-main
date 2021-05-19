package com.team7.dptc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.team7.dptc.R.layout;

public class Screen extends AppCompatActivity {

     Button mLogin, mSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //NO TITLE bar only fullscreen
        setContentView(layout.activity_screen);

    }

    public void callNextLogin(View view) {

        Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.login), "transition_Login");

        startActivity(intent);

    }

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Screen.this, pairs);
            startActivity(intent,options.toBundle());
        } else {
            startActivity(intent);
        }
    }*/

    public void callNextSignup(View view) {

        Intent intent = new Intent(getApplicationContext(), UserSignupActivity.class);

        //Add Transition
        //android.util.Pair[] pairs = new android.util.Pair[1];
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.sign), "transition_Sign_up");

        startActivity(intent);

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Screen.this, pairs);
            startActivity(intent,options.toBundle());
        } else {
            startActivity(intent);
        }*/
    }
}


   /* private Button mUser, mAdmin;

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
    }*/