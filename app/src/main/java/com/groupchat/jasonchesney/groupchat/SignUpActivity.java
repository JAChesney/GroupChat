package com.groupchat.jasonchesney.groupchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    AutoCompleteTextView signemail;
    EditText pnumber, fname, lname;
    Button signbutton, ahaa;
    ProgressBar progressbar;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        pnumber = (EditText) findViewById(R.id.phoneno);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        signemail = (AutoCompleteTextView) findViewById(R.id.signemail);
        signbutton = (Button) findViewById(R.id.signbutton);
        ahaa = (Button) findViewById(R.id.haveacbutton);
        progressbar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        signbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        ahaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

        private void registerUser() {

            String email = signemail.getText().toString().trim();
            String phonenumber = pnumber.getText().toString().trim();
            String firstname = fname.getText().toString().trim();

            if (phonenumber.isEmpty()) {
                pnumber.setError("Phone number is required");
                pnumber.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                signemail.setError("Email is required");
                signemail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                signemail.setError("Please enter a valid email");
                signemail.requestFocus();
                return;
            }

            if (phonenumber.length() < 10) {
                pnumber.setError("Please enter valid phone number");
                pnumber.requestFocus();
                return;
            }

            progressbar.setVisibility(View.VISIBLE);



            String phonen = "+91" + phonenumber;

            Intent intent = new Intent(SignUpActivity.this, VerifyActivity.class);
            intent.putExtra("phonenum", phonen);
            intent.putExtra("fname", firstname);
            intent.putExtra("memberid", "mem1");
            progressbar.setVisibility(View.GONE);
            startActivity(intent);
        }

}