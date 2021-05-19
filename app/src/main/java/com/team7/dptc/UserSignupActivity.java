package com.team7.dptc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignupActivity extends AppCompatActivity {
    //Variables
    Button next, login;
    TextView titleText;

    //Data Variables
    EditText fullName, username, email, password;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        //Hooks
        next = findViewById(R.id.signup_next_btn);
        login = findViewById(R.id.signup_login_btn);
        titleText = findViewById(R.id.signup_title_text);

        //Hooks for getting data
        fullName = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        email =  findViewById(R.id.email);
        password = findViewById(R.id.password);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance(); //getInstance will call root node.
                reference = rootNode.getReference("Users");

                //Get all the values
                String fullname = fullName.getText().toString();
                String userName = username.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                UserHelperClass helperClass = new UserHelperClass(fullname, username, email, password);

                reference.child(userName).setValue(helperClass);
            }
        });
    }

    public void callNextSignupScreen(View view){

        if(!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), UserSignup3rdActivity.class);
/*
        //Add Transition
        Pair[] pairs = new Pair[3];


        pairs[0] = new Pair<View, String>(next, "transition_title_text");
        pairs[1] = new Pair<View, String>(login, "transition_next_btn");
        pairs[2] = new Pair<View, String>(titleText, "transition_login_btn");*/


            startActivity(intent);
        }

    public void callNextLogin(View view) {

        Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.login), "transition_Login");

        startActivity(intent);

    }

    /*
    Validate functions
    */

    private boolean validateFullName()  {
        String val = fullName.getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            //fullName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateUsername() {
        String val = username.getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z"; //User van add any alphabet from upper alphabets to lower alphabets and w stands for whitespaces.
        //20 is 20 characters username.
        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username too big!");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No white spaces allowed");
            return false;
        } else {
            username.setError(null);
           // username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getText().toString().trim();
        String checkEmail = "[a-zA-z0-9._]+A[a-z]+\\.+[a-z]]"; //For email validation, this time of expression we have already used in TOC Practical-3.

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email");
            return false;
        } else {
            email.setError(null);
           // email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getText().toString().trim();
        //At least 1 digit
        //At least 1 lower case letter
        //At least 1 upper case letter
        //Any letter
        //At least 1 special character
        //No white spaces
        //At least 4 character
        String checkPassword = String.format("^(?=.*[0-9])(?=.*[0-9])(?=.*[0-9])(?=.*[a-zA-Z](?=.*[@#$%%^&+=])(?=\\S+$).{4,}$");

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain a digit, lowercase letter, uppercase letter, special character, no white spaces");
            return false;
        } else {
            password.setError(null);
           // password.setErrorEnabled(false);
            return true;
        }
    }

}







