package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2signin extends AppCompatActivity {
 private TextView goToSignUp;
 private EditText etMailsignin,etPasswordsignin;
 private  Button btnSignin;
 private FirebaseAuth firebaseAuth;
 private ProgressDialog progressDialog;
    private String etPasswordS,etMailS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2signin);
        goToSignUp=findViewById(R.id.goTosignUp);
        etMailsignin = findViewById(R.id.etMailsignin);
        etPasswordsignin=findViewById(R.id.etPasswordsignin);
        btnSignin=findViewById(R.id.btnSignin);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        goToSignUp.setOnClickListener(v->{
            startActivity(new Intent(MainActivity2signin.this,MainActivity.class));
        });
        btnSignin.setOnClickListener(v->{
            if (validate()) {
              login(etMailsignin.getText().toString(),etPasswordsignin.getText().toString());


            }

        });

    }

    private void login(String toString, String toString1) {
        progressDialog.setMessage("please wait ..!");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(etMailS,etPasswordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful());
                {
                    checkEmailverification();
                }

            }
        });
    }

    private void checkEmailverification() {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user.isEmailVerified())
        {
            finish();
        startActivity(new Intent(MainActivity2signin.this,HomeActivity.class));

    }else{
            Toast.makeText(this, "please check your email!", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }


    private boolean validate() {
        boolean result=false;

        etPasswordS=etPasswordsignin.getText().toString();

        etMailS=etMailsignin.getText().toString();

       if (etMailS.isEmpty() ) {
           etMailsignin.setError("EMail is invalid");

        } else if (etPasswordS.isEmpty()|| etPasswordS.length()<5) {
           etPasswordsignin.setError("password is invalid");
        } else {
            result=true;
        }

        return result;
    }

}
