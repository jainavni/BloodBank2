package com.example.gagan.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BloodBankInfo extends AppCompatActivity {

    TextView nameView, addressView, districtView,cityView, stateView, pincodeView, contactView, helplineView, emailView, websiteView, serviceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_info);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String district = intent.getStringExtra("district");
        String city = intent.getStringExtra("city");
        String state = intent.getStringExtra("state");
        String address = intent.getStringExtra("address");
        String pincode = intent.getStringExtra("pincode");
        String helpline = intent.getStringExtra("helpline");
        String contact = intent.getStringExtra("contact");
        String email = intent.getStringExtra("email");
        String website = intent.getStringExtra("website");
        String servicetime = intent.getStringExtra("servicetime");

         nameView = findViewById(R.id.mName);
         addressView = findViewById(R.id.mAddress);
         districtView = findViewById(R.id.mDistrict);
         cityView = findViewById(R.id.mCity);
         stateView = findViewById(R.id.mState);
         pincodeView = findViewById(R.id.mPincode);
         contactView = findViewById(R.id.mContact);
         helplineView = findViewById(R.id.mhelpline);
         contactView = findViewById(R.id.mContact);
         websiteView = findViewById(R.id.mWebsite);
         serviceView = findViewById(R.id.mService);

         nameView.setText("Name: " + name);
            addressView.setText("Address:  " + address);
            districtView.setText("District:  " + district);
            cityView.setText("City:  " + city);
            stateView.setText("State:  " + state);
            contactView.setText("Contact No:  " + contact);
            helplineView.setText("Helpline No:  " + helpline);
            emailView.setText("Email:  " + email);
            websiteView.setText("Website:  " + website);
            pincodeView.setText("Pincode:  " + pincode);
            serviceView.setText("Service Time:  " + servicetime);

    }
}
