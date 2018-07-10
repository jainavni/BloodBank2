package com.example.gagan.bloodbank;

import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private EditText e1,e2,e3,e4,e5;
    private Button b;
    private FirebaseAuth firebaseAuth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();



        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");

        e1 = (EditText)findViewById(R.id.edit_name);
        e2 = (EditText)findViewById(R.id.edit_email);
        e3 = (EditText)findViewById(R.id.edit_address);
        e4 = (EditText)findViewById(R.id.edit_state);
        e5 = (EditText)findViewById(R.id.edit_city);
        b = (Button)findViewById(R.id.edit_btn);
        e1.setText(uid);

       DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("User").child(uid);
        Log.d("User",uid);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfo userInfo=dataSnapshot.getValue(UserInfo.class);
                Log.d("User",uid);
                e1.setText(userInfo.getName());
                e2.setText(userInfo.getEmail());
                e3.setText(userInfo.getAddress());
                e4.setText(userInfo.getState());
                e5.setText(userInfo.getCity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
