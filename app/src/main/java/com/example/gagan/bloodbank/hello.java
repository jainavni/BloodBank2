package com.example.gagan.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private RadioGroup radioGroup;
    private TextView alreadylogin;
    private ProgressDialog progressdialog;
    private FirebaseAuth mfirebaseauth;
    private String user_donor=null;

    String user_gender=null,user_bloodgroup=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        progressdialog = new ProgressDialog(this);
        mfirebaseauth = FirebaseAuth.getInstance();
        ename=(EditText) findViewById(R.id.name);
        eemail=(EditText)findViewById(R.id.email); 
        eaddress=(EditText)findViewById(R.id.address);
        ecity=(EditText)findViewById(R.id.city);
        emob=(EditText)findViewById(R.id.mob);
        echeckBox=(CheckBox) findViewById(R.id.check);
        esignup = (Button) findViewById(R.id.signup);
        radioGroup=findViewById(R.id.radiogroup);

        FirebaseUser firebaseUser=mfirebaseauth.getInstance().getCurrentUser();
        try{
            emob.setText(firebaseUser.getPhoneNumber());}
        catch (NullPointerException e) {
            Toast.makeText(this, "Error In Phone Number", Toast.LENGTH_SHORT).show();
        }
        emob.setEnabled(false);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.male)
                {
                    user_gender="Male";
                }
                else if(checkedId==R.id.female)
                {
                    user_gender="Female";
                }
            }
        });

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_bloodgroup=dropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(echeckBox.isChecked())
        {
            user_donor="Donor";
        }
        else {
            user_donor="Not A Donor";
        }

        esignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog.setMessage("SIGNING UP");
                progressdialog.show();
                if (user_bloodgroup == null) {
                    Toast.makeText(hello.this, "Please Select The Blood group", Toast.LENGTH_SHORT).show();
                    progressdialog.dismiss();
                }
                if (user_gender == null) {
                    Toast.makeText(hello.this, "Please Select The Gender", Toast.LENGTH_SHORT).show();
                    progressdialog.dismiss();
                }


                if(TextUtils.isEmpty(ename.getText().toString().trim())||TextUtils.isEmpty(eemail.getText().toString().trim())||TextUtils.isEmpty(emob.getText().toString().trim())||TextUtils.isEmpty(eaddress.getText().toString())||TextUtils.isEmpty(ecity.getText().toString().trim()))
                {
                    Toast.makeText(hello.this, "Please Fill All The  Details", Toast.LENGTH_SHORT).show();
                    progressdialog.dismiss();
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(eemail.getText().toString()).matches())
                {
                    Toast.makeText(hello.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                    progressdialog.dismiss();
                }
                else
                {
                    addData(ename.getText().toString().trim(), eemail.getText().toString().trim(),eaddress.getText().toString().trim(),ecity.getText().toString().trim(),emob.getText().toString().trim(),user_bloodgroup,user_gender,user_donor);
                    startActivity(new Intent(hello.this,Profile.class));
                }
                progressdialog.dismiss();

            }
        });
    }
    public void addData(String name,String email,String address, String city,String mobile,String bloodgroup,String gender,String donor)
    {
        DatabaseReference refrerence= FirebaseDatabase.getInstance().getReference("User");
        UserInfo info=new UserInfo(name,email,address,city,gender,bloodgroup,mobile,donor);
        refrerence.child(refrerence.push().getKey()).setValue(info);
        progressdialog.dismiss();

    }
    }

