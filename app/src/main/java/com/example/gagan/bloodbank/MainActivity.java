package com.example.gagan.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText memail;
    private EditText mpass;
    private  Button mlogin;
    private Button msignup;
    private TextView forgetpassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth mfirebase;
    private  FirebaseAuth.AuthStateListener mstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        memail=(EditText)findViewById(R.id.email);
        mpass=(EditText)findViewById(R.id.password);
        mlogin=(Button)findViewById(R.id.login);
        msignup=(Button)findViewById(R.id.signup);
        progressDialog=new ProgressDialog(this);
        mfirebase=FirebaseAuth.getInstance();
        forgetpassword=(TextView)findViewById(R.id.forgetpass);


        mstate= new FirebaseAuth.AuthStateListener() {
           @Override

           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               if (mfirebase.getCurrentUser()!=null){// to check whether user is already logged in or not
                   startActivity(new Intent(MainActivity.this,Profile.class));
                   //ok
               }
           }
       };
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            signIn();
            }
        });
        msignup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this,hello.class));
    }
});
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,hello.class));
            }
        });

        }


    private void signIn() {
        progressDialog.setMessage("Signing In User...");
        progressDialog.show();
        String email = memail.getText().toString();
        String pass = mpass.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(MainActivity.this, "SignIn Failed,Field Empty", Toast.LENGTH_LONG).show();

        }
        else
        {

            mfirebase.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        emailVerify();
                       // startActivity(new Intent(MainActivity.this, Profile.class));
                    } else {
                        Toast.makeText(MainActivity.this, "SignIn Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });


    }}
   private void emailVerify() {
        FirebaseUser firebaseUser = mfirebase.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if (emailflag) {
            finish();
          //  startActivity(new Intent(MainActivity.this, Profile.class));
        } else {
            Toast.makeText(this, "Please Verfy Your Email", Toast.LENGTH_SHORT).show();
            mfirebase.signOut();
        }
    }


        @Override
        protected void onStart () {
            super.onStart();
            mfirebase.addAuthStateListener(mstate);
        }

    }