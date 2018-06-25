package com.example.gagan.bloodbank;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity{
    private EditText ename;
    private EditText eemail;
    private EditText emob;
    private EditText epass;
    private EditText erepass;
    private EditText eaddress;
    private EditText ecity;
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
        setContentView(R.layout.activity_sign_up);
        dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        progressdialog = new ProgressDialog(this);
        mfirebaseauth = FirebaseAuth.getInstance();
        eemail = (EditText) findViewById(R.id.email);
        ename=(EditText) findViewById(R.id.name);
        eaddress=(EditText)findViewById(R.id.address);
        ecity=(EditText)findViewById(R.id.city);
        emob=(EditText)findViewById(R.id.mob);
        echeckBox=(CheckBox) findViewById(R.id.check);
        esignup = (Button) findViewById(R.id.signup);
        radioGroup=findViewById(R.id.radiogroup);


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
        alreadylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,MainActivity.class));
            }
        });
       esignup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressdialog.show();
               if (user_bloodgroup == null) {
                   Toast.makeText(SignUp.this, "Please Select The Blood group", Toast.LENGTH_SHORT).show();
               }
                   if (user_gender == null) {
                       Toast.makeText(SignUp.this, "Please Select The Gender", Toast.LENGTH_SHORT).show();
                   }


               if(TextUtils.isEmpty(ename.getText().toString().trim())||TextUtils.isEmpty(eemail.getText().toString().trim())|| TextUtils.isEmpty(epass.getText().toString().trim())||TextUtils.isEmpty(erepass.getText().toString().trim())||TextUtils.isEmpty(emob.getText().toString().trim())||TextUtils.isEmpty(eaddress.getText().toString())||TextUtils.isEmpty(ecity.getText().toString().trim()))
               {
                   Toast.makeText(SignUp.this, "Please Fill All The  Details", Toast.LENGTH_SHORT).show();
               }
               if(!Patterns.EMAIL_ADDRESS.matcher(eemail.getText().toString()).matches())
               {
                   Toast.makeText(SignUp.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   addData(ename.getText().toString().trim(), eemail.getText().toString().trim(),eaddress.getText().toString().trim(),ecity.getText().toString().trim(),emob.getText().toString().trim(),user_bloodgroup,user_gender,user_donor);

                   progressdialog.dismiss();
                   //registerUser(eemail.getText().toString().trim(),epass.getText().toString(),user_bloodgroup,user_gender);
               }

           }
       });
    }

/*
    public void registerUser(final String email, String password, final String bloodgroup, final String gender) {
        progressdialog.setMessage("Regsitering User...");
        progressdialog.show();
        mfirebaseauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        verifyEmail();
                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignUp.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                progressdialog.dismiss();}
                                else{
                            Toast.makeText(SignUp.this, "Please check the internet connection", Toast.LENGTH_SHORT).show();
                                progressdialog.dismiss();
                            }
                        }
                   // startActivity(new Intent(SignUp.this,Profile.class));
                }

                   // Toast.makeText(SignUp.this, "Please check the internet connection", Toast.LENGTH_SHORT).show();
                    //progressdialog.dismiss();

            });}

    private void verifyEmail()
    {
        FirebaseUser firebaseUser=mfirebaseauth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        addData(ename.getText().toString().trim(), eemail.getText().toString().trim(),eaddress.getText().toString().trim(),ecity.getText().toString().trim(),emob.getText().toString().trim(),user_bloodgroup,user_gender);
                        Toast.makeText(SignUp.this, "Successfully Registered,Email Verification mail has been sent", Toast.LENGTH_SHORT).show();
                        mfirebaseauth.signOut();
                        finish();
                        startActivity(new Intent(SignUp.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(SignUp.this, "Email Verification not sent,Try Again after sometime", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
   /* public void checkEmail()
    {
        mfirebaseauth.fetchProvidersForEmail(eemail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
               boolean check=task.getResult().getProviders().isEmpty();
               if(!check)
               {
                 //  verifyEmail();
                 //  registerUser(eemail.getText().toString().trim(),epass.getText().toString(),user_bloodgroup,user_gender);
               }
               else
               {
                   Toast.makeText(SignUp.this, "Email Already Registered", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }*/

    public void addData(String name,String email,String address, String city,String mobile,String bloodgroup,String gender,String donor)
    {
       /* AlertDialog.Builder di=new AlertDialog.Builder(this).setTitle("Important Message").setMessage("Please verify you email").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        DatabaseReference refrerence=FirebaseDatabase.getInstance().getReference("User");
        UserInfo info=new UserInfo(name,email,address,city,gender,bloodgroup,mobile,donor);
        refrerence.child(refrerence.push().getKey()).setValue(info);
        progressdialog.dismiss();

    }

}



