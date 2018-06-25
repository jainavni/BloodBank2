package com.example.gagan.bloodbank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ForgetPassword extends AppCompatActivity {
    private EditText passwordemail;
    private Button reset;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        passwordemail=(EditText)findViewById(R.id.etForgetpassword);
        reset=(Button)findViewById(R.id.btreset);
        firebaseAuth=FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String useremail=passwordemail.getText().toString().trim();
            if(TextUtils.isEmpty(useremail))
            {
                Toast.makeText(ForgetPassword.this, "Please Enter Your resgistered Email ID", Toast.LENGTH_SHORT).show();
            }
            else
            {
                firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgetPassword.this, "Password Reset Email Sent", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(ForgetPassword.this,MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(ForgetPassword.this, "Entered Email is not registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            }
        });
    }
}
