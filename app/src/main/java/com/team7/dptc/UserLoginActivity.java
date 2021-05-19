package com.team7.dptc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserLoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button login;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_login);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
        email= (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString();
                final String Password = password.getText().toString();
                mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(UserLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(UserLoginActivity.this, "sign in error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
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

    public void loginUser(View view){
        //Validate login
        if(!validateEmail() | !validatePassword()){
            return;
        }
        else {
            isUser();
        }

    }

    private void isUser() {

        String userEnteredEmail = email.getText().toString().trim();
        String userEnteredPassword = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredEmail);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    email.setError(null);

                    String passwordFromDB = snapshot.child(userEnteredEmail).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userEnteredPassword)){
                        email.setError(null);


                        String nameFromDB = snapshot.child(userEnteredEmail).child("name").getValue(String.class);
                        String usernameFromDB = snapshot.child(userEnteredEmail).child("username").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredEmail).child("email").getValue(String.class);

                       /* Intent intent = new Intent(getApplicationContext(),UserProfile.class);

                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("username",usernameFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("password",passwordFromDB);

                        startActivity(intent);
                        */
                    }
                    else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else{
                    email.setError("No such email exists");
                    email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

}