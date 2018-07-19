package com.example.gagan.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class hello extends AppCompatActivity {

    private EditText ename;
    private EditText emob;
    private EditText eaddress;
    private EditText ecity;
    private EditText eemail;
    private Button esignup;
    private RadioButton emale;
    private CheckBox echeckBox;
    private Spinner dropdown;
    private Spinner stateSpinner;
    private Spinner citySpinner;
    private RadioGroup radioGroup;
    private TextView alreadylogin;
    private ProgressDialog progressdialog;
    private FirebaseAuth mfirebaseauth;
    private String user_donor = null;
    private String states=null;
    private String city=null;
    String userId;
    Userinfo userInfo;
    DatabaseReference reference;
    String[] items;
    private String URL="http://api.androiddeft.com/cities/cities_array.json";

    String user_gender = null, user_bloodgroup = null;

    // private Userinfo userInfo = new Userinfo();

    Boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        progressdialog=new ProgressDialog(this);

        initUi();
        handleBundle();
        // if(edit)  initUiFromPerson();

        //eaddress.addTextChangedListener(new GenericWatcher(R.id.address));

        //  FirebaseUser firebaseUser = mfirebaseauth.getInstance().getCurrentUser();

        echeckBox.setChecked(true);

        emob.setText("1234567890");
//        try {
//            emob.setText(firebaseUser.getPhoneNumber());
//        } catch (NullPointerException e) {
//            Toast.makeText(this, "Error In Phone Number", Toast.LENGTH_SHORT).show();
//        }
        emob.setEnabled(false);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    user_gender = "Male";
                } else if (checkedId == R.id.female) {
                    user_gender = "Female";
                }
            }
        });
        loadStateandCity();

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_bloodgroup = dropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        esignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog.setMessage("SIGNING UP");
                progressdialog.show();
                if (echeckBox.isChecked()) {
                    user_donor = "Donor";
                } else {
                    user_donor = "Not A Donor";
                }
                if (user_bloodgroup == null) {
                    Toast.makeText(hello.this, "Please Select The Blood group", Toast.LENGTH_SHORT).show();
                    progressdialog.dismiss();
                }
                if (user_gender == null) {
                    Toast.makeText(hello.this, "Please Select The Gender", Toast.LENGTH_SHORT).show();
                    progressdialog.dismiss();
                }


                if (TextUtils.isEmpty(ename.getText().toString().trim()) || TextUtils.isEmpty(eemail.getText().toString().trim()) || TextUtils.isEmpty(emob.getText().toString().trim()) || TextUtils.isEmpty(eaddress.getText().toString()) ) {
                    Toast.makeText(hello.this, "Please Fill All The  Details", Toast.LENGTH_SHORT).show();
                    progressdialog.dismiss();
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(eemail.getText().toString()).matches()) {
                    Toast.makeText(hello.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                    progressdialog.dismiss();
                } else {
                    Cities state=(Cities)stateSpinner.getSelectedItem();
                    states=state.getState();
                    city = citySpinner.getSelectedItem().toString();


                    addData(ename.getText().toString().trim(), eemail.getText().toString().trim(), eaddress.getText().toString().trim(),states,city, emob.getText().toString().trim(), user_bloodgroup, user_gender, user_donor);

                    Intent intent = new Intent(hello.this,Profile.class);
                    intent.putExtra("ID",userId);
                    Log.d("AVNI","ID " + userId);
                    startActivity(intent);

                    // startActivity(new Intent(hello.this, ProfileInfo.class));
                }
                progressdialog.dismiss();

            }
        });

    }

    private void initUiFromPerson() {
        Log.d("AVNI","userinfo "+userInfo);
        ename.setText(userInfo.getName());
        eemail.setText(userInfo.getEmail());
        eaddress.setText(userInfo.getAddress());
        emob.setText(userInfo.getMob());
        String state = userInfo.getState();
        String city = userInfo.getCity();
        String bg = userInfo.getBloodgroup();

        String donor = userInfo.getDonor();
        if(donor.equals("Donor"))
            echeckBox.setChecked(true);
        else echeckBox.setChecked(false);

        String gender = userInfo.getGender();
        if(gender.equals("Male"))
            radioGroup.check(R.id.male);
        else radioGroup.check(R.id.female);

    }

    private void initUi() {
        dropdown = findViewById(R.id.spinner);
        items = new String[]{"A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O/+"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        progressdialog = new ProgressDialog(this);
        mfirebaseauth = FirebaseAuth.getInstance();
        ename = findViewById(R.id.name);
        eemail = findViewById(R.id.email);
        eaddress = findViewById(R.id.address);
        stateSpinner=findViewById(R.id.statespinner);
        citySpinner=findViewById(R.id.cityspinner);
        emob = findViewById(R.id.mob);
        echeckBox = findViewById(R.id.check);
        esignup = findViewById(R.id.signup);
        radioGroup = findViewById(R.id.radiogroup);
    }

    private void handleBundle(){
        // Bundle bundle = getIntent().getExtras();
        // Log.d("AVNI","Bundle "+ bundle);
        //if(bundle != null)
        //{
        edit = getIntent().getBooleanExtra("edit",false);
        userId = getIntent().getStringExtra("id");
        //edit = bundle.getBoolean("edit");
        Log.d("AVNI","edit  "+edit);
        Log.d("AVNI","id" + userId);
//            if(edit){
//                userInfo = Parcels.unwrap(bundle.getParcelable("abc"));
//         5   }
//        }
    }

    public void addData(String name, String email, String address, String state ,String city, String mobile, String bloodgroup, String gender, String donor) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("AVNI","current user uid " + uid);

        reference = FirebaseDatabase.getInstance().getReference();

        if(edit) {
            Userinfo info = new Userinfo( name, email, address, state, city, gender, bloodgroup, mobile, donor,uid);
            reference.child("User").child(uid).setValue(info);

        }
        else {
            userId = reference.push().getKey();
            Userinfo info = new Userinfo(name, email, address, state, city, gender, bloodgroup, mobile, donor,uid);
            reference.child("User").child(uid).setValue(info);

        }
        progressdialog.dismiss();

    }


    public void loadStateandCity()
    {
        final List<Cities> stateList=new ArrayList<>();
        final List<String> states=new ArrayList<>();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String state = jsonObject.getString("state");
                        JSONArray cities = jsonObject.getJSONArray("cities");
                        List<String> citiesList = new ArrayList<>();
                        for (int j = 0; j < cities.length(); j++) {
                            citiesList.add(cities.getString(j));
                        }
                        stateList.add(new Cities(state, citiesList));
                        states.add(state);


                    }
                    final CityAdapter cityAdapter = new CityAdapter(hello.this,
                            R.layout.state_list, R.id.spinnerText, stateList);
                    stateSpinner.setAdapter(cityAdapter);
                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Cities cityDetails = cityAdapter.getItem(position);
                            List<String> cityList = cityDetails.getCities();
                            ArrayAdapter citiesAdapter = new ArrayAdapter<>(hello.this,
                                    R.layout.city_list, R.id.cityspinnerText, cityList);
                            citySpinner.setAdapter(citiesAdapter);
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        Singleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }



}

   /* class GenericWatcher implements TextWatcher {
        int id;
        public GenericWatcher(int id) {
            this.id = id;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            switch (id) {
                case R.id.address:
                    if (TextUtils.isEmpty(s) && s.length() == 6) {
                    }
                    break;
            }
        }
    }
}*/