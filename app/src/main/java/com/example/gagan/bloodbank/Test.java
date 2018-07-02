package com.example.gagan.bloodbank;

import android.location.Location;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.TimerTask;

public class Test extends AppCompatActivity {
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello ");
            }
        }, 2000);


        Location location = null;
        Location location1 = null;

        location.distanceTo(location1);

        final TextView t1 = (TextView) findViewById(R.id.name);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.getValue(Map.class);

                String value = map.get("name");
                t1.setText(value);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
