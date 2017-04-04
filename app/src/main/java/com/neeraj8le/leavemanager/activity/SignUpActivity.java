package com.neeraj8le.leavemanager.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.model.Employee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "========";
    private Button mSubmit;
    TextInputLayout idTextInputLayout,nameTextInputLayout,deptTextInputLayout,designationTextInputLayout,phoneTextInputLayout,emailTextInputLayout
            ,passwordTextInputLayout,confirmPasswordTextInputLayout;
    Spinner s1;
    String supervisors[] = {"Select supervisors", "Arnav", "Neeraj", "Priyanshu", "Yolo"};
    String selectedSupervisor;
    ArrayAdapter<String> adapter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabase;
    boolean userExists = false;
    ProgressDialog progressDialog;

    void showToast(String msg)

    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        s1 = (Spinner) findViewById(R.id.spinner1);
        mSubmit = (Button) findViewById(R.id.submit);
        idTextInputLayout = (TextInputLayout) findViewById(R.id.idTextInputLayout);
        nameTextInputLayout= (TextInputLayout) findViewById(R.id.nameTextInputLayout);
        deptTextInputLayout = (TextInputLayout) findViewById(R.id.deptTextInputLayout);
        designationTextInputLayout = (TextInputLayout) findViewById(R.id.designationTextInputLayout);
        phoneTextInputLayout = (TextInputLayout) findViewById(R.id.phoneTextInputLayout);
        emailTextInputLayout= (TextInputLayout) findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.passwordTextInputLayout);
        confirmPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.confirmPasswordTextInputLayout);

        adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, supervisors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s1.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("employee");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Signing Up...");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(SignUpActivity.this, "SIGN IN", Toast.LENGTH_SHORT).show();
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                progressDialog.show();

                idTextInputLayout.setErrorEnabled(false);
                nameTextInputLayout.setErrorEnabled(false);
                deptTextInputLayout.setErrorEnabled(false);
                designationTextInputLayout.setErrorEnabled(false);
                phoneTextInputLayout.setErrorEnabled(false);
                emailTextInputLayout.setErrorEnabled(false);
                passwordTextInputLayout.setErrorEnabled(false);
                confirmPasswordTextInputLayout.setErrorEnabled(false);


                String emp_id = idTextInputLayout.getEditText().getText().toString();
                String name = nameTextInputLayout.getEditText().getText().toString();
                String dept_name = deptTextInputLayout.getEditText().getText().toString();
                String desig = designationTextInputLayout.getEditText().getText().toString();
                String contact = phoneTextInputLayout.getEditText().getText().toString();
                final String email = emailTextInputLayout.getEditText().getText().toString();
                final String password = passwordTextInputLayout.getEditText().getText().toString();
                String con_pass=confirmPasswordTextInputLayout.getEditText().getText().toString();
                String supervisor = selectedSupervisor;



                 if (TextUtils.isEmpty(name)) {
                    nameTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                } else if (TextUtils.isEmpty(emp_id)) {
                    idTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                } else if (TextUtils.isEmpty(dept_name)) {
                    deptTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                } else if (TextUtils.isEmpty(desig)) {
                    designationTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                } else if (TextUtils.isEmpty(contact)) {
                    phoneTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                }
                 else if (contact.length() < 10) {
                     phoneTextInputLayout.setError(getString(R.string.invalid_number));
                 }else if (TextUtils.isEmpty(email)) {
                    emailTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                } else if (!isValidEmail(email)) {
                     emailTextInputLayout.setError(getString(R.string.invalid_email_id));
                 }else if (TextUtils.isEmpty(password)) {
                    passwordTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                }
                else if(TextUtils.isEmpty(con_pass))
                {
                    confirmPasswordTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                }
                else if(!(password.equals(con_pass)))
                {
                    confirmPasswordTextInputLayout.setError(getString(R.string.password_error));
                }
                else if(supervisor.equals(supervisors[0]))
                 {
                     showToast("Please select your supervisors");
                 }
                 else
                 {
                     final Employee employee = new Employee(emp_id,name,dept_name,desig,contact,email,supervisor);


                     mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                 Log.d(TAG, ds.getValue().toString());
                                 if (ds.child("email").getValue().toString().equals(email)) {
                                     Toast.makeText(SignUpActivity.this, "There is already an account registered with this Email Id.", Toast.LENGTH_SHORT).show();
                                     progressDialog.dismiss();
                                     userExists = true;
                                 }
                             }

                             if(!userExists)
                             {
                                 mAuth.createUserWithEmailAndPassword(employee.getEmail(), password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                     @Override
                                     public void onComplete(@NonNull Task<AuthResult> task) {

                                         if (!task.isSuccessful()) {
                                             Toast.makeText(SignUpActivity.this, "This Email Id is already linked to an account! Please Sign In.", Toast.LENGTH_SHORT).show();
                                             progressDialog.dismiss();
                                         }
                                         else
                                         {
                                             final String user_id = mAuth.getCurrentUser().getUid();
//                                             DatabaseReference current_user_db = mDatabase.child(user_id);
                                             mDatabase.child(user_id).setValue(employee);
//                                             current_user_db.child("name").setValue(name);
//                                             current_user_db.child("phoneNumber").setValue(phoneNumber);
//                                             current_user_db.child("email").setValue(email);
                                             progressDialog.dismiss();
                                             finish();
                                         }

                                     }
                                 });
                             }
                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError) {
                             Toast.makeText(SignUpActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                         }
                     });

                 }
            }

        });

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSupervisor = s1.getSelectedItem().toString();
                showToast(selectedSupervisor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showToast("Not Selected");
            }
        });

}
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}


