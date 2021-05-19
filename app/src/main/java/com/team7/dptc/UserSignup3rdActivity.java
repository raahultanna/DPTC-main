package com.team7.dptc;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

public class UserSignup3rdActivity extends AppCompatActivity {

    //Variables
    ScrollView scrollView;
    EditText phoneNumber;
    CountryCodePicker countryCodePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup3rd);

        //Hooks
        scrollView = findViewById(R.id.scroll_view);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.phone);
    }



    public void callVerifyOTPScreen(View view) {
        //Validate fields
        if(!validatePhoneNumber()){
            return;
    }//Validation succeeded and now move to next screen to verify phone number and save data.

        //Get all values passed from previous screens using Intent
        String _fullname = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");
        String _date = getIntent().getStringExtra("date");
        String _gender = getIntent().getStringExtra("gender");

        String _getUserEnteredPhoneNumber = phoneNumber.getText().toString().trim(); //We will get phone number
        String _phoneNo = "+"+countryCodePicker.getFullNumber()+_getUserEnteredPhoneNumber; // To get complete country code


        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        //Pass all fields to the next activity
        intent.putExtra("fullName", _fullname);
        intent.putExtra("email",_email);
        intent.putExtra("username",_username);
        intent.putExtra("password",_password);
        intent.putExtra("date",_date);
        intent.putExtra("gender",_gender);
        intent.putExtra("phoneNo",_phoneNo);

       //Now we will add some Transitions
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_Screen");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserSignup3rdActivity.this, pairs);
        }
        else{
            startActivity(intent);
        }
    }

    public void callNextLogin(View view) {

        Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);

        android.util.Pair[] pairs = new android.util.Pair[1];
        pairs[0] = new android.util.Pair<View, String>(findViewById(R.id.login), "transition_Login");

        startActivity(intent);

    }
    private boolean validatePhoneNumber() {
        String val = phoneNumber.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumber.setError("No white spaces allowed");
            return false;
        }else {
            phoneNumber.setError(null);
            return true;
        }

    }
}