package com.team7.dptc;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;

public class Report extends AppCompatActivity {

    EditText rname, remail, rmess;
    Button btn_send, btn_details;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        rname= findViewById(R.id.rname);
        remail = findViewById(R.id.remail);
        rmess = findViewById(R.id.rmess);

        btn_send = findViewById(R.id.btn_send);
        btn_details = findViewById(R.id.btn_details);
        Firebase.setAndroidContext(this);

        String UniqueID =
        Settings.Secure.getString(getApplicationContext().getContentResolver(),
        Settings.Secure.ANDROID_ID);

        firebase = new Firebase("https://dptc-4002f-default-rtdb.firebaseio.com/Users" + UniqueID);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_details.setEnabled(true);
                final String name = rname.getText().toString();
                final String email = remail.getText().toString();
                final String mess = rmess.getText().toString();

                Firebase child_name = firebase.child("Name");
                child_name.setValue(name);
                if (name.isEmpty()){
                    rname.setError("This is a required field");
                    btn_send.setEnabled(false);
                }
                else{
                    rname.setError(null);
                    btn_send.setEnabled(true);
                }

                Firebase child_email = firebase.child("Email");
                child_email.setValue(email);
                if(email.isEmpty())
                {
                    remail.setError("This is an required field");
                    btn_send.setEnabled(false);
                } else{
                    remail.setError(null);
                    btn_send.setEnabled(true);
                }

                Firebase child_mess = firebase.child("Message");
                child_mess.setValue(mess);
                if(mess.isEmpty()){
                    rmess.setError("This is a required field");
                    btn_send.setEnabled(false);
                } else{
                    rmess.setError(null);
                    btn_send.setEnabled(true);
                }

                btn_details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(Report.this)
                                .setTitle("Sended Details: ")
                                .setMessage("Name - "+ rname + "\n\nEmail - "+ remail + "\n\nMessage - "+rmess)
                                .show();
                    }
                });
            }
        });

    }
}