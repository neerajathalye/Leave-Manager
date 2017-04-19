package com.neeraj8le.leavemanager.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.gson.Gson;
import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.SharedPrefManager;
import com.neeraj8le.leavemanager.model.Employee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signInButton;
    private Button signUpButton;
    TextView forgotPasswordTextView;
    TextInputLayout emailTextInputLayout,passwordTextInputLayout;
    String email;
    String password;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog progressDialog;
    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailTextInputLayout=(TextInputLayout)findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout=(TextInputLayout)findViewById(R.id.passwordTextInputLayout);

        forgotPasswordTextView=(TextView)findViewById(R.id.forgotPasswordTextView);

        signUpButton=(Button)findViewById(R.id.signUpButton);
        signInButton = (Button) findViewById(R.id.signInButton);

        Toast.makeText(this, SharedPrefManager.getInstance(this).getToken(), Toast.LENGTH_SHORT).show();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Signing In...");

        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        forgotPasswordTextView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("employee");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    Toast.makeText(SignInActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();

//                    progressDialog.show();
                    if (user.isEmailVerified())
                    {
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds : dataSnapshot.getChildren())
                                {
                                    if (ds.child("email").getValue().equals(mAuth.getCurrentUser().getEmail()))
                                    {

                                        employee = ds.getValue(Employee.class);

                                        String refreshedToken = SharedPrefManager.getInstance(SignInActivity.this).getToken();

                                        employee.setToken(refreshedToken);

                                        mDatabase.child(mAuth.getCurrentUser().getUid()).child("token").setValue(refreshedToken);

                                        SharedPreferences sharedPreferences = getSharedPreferences("EMPLOYEE_FILE_KEY", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        Gson gson = new Gson();
                                        String json = gson.toJson(employee);
                                        editor.putString("employee", json);
                                        editor.apply();

//                                        Toast.makeText(SignInActivity.this, "Sign in " + employee.getName(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//                                        intent.putExtra("employee", employee);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(SignInActivity.this, "Kindly Verify your Email Id first", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signInButton:

                progressDialog.show();

                email = emailTextInputLayout.getEditText().getText().toString();
                password = passwordTextInputLayout.getEditText().getText().toString();

                emailTextInputLayout.setErrorEnabled(false);
                passwordTextInputLayout.setErrorEnabled(false);

                if(TextUtils.isEmpty(email))
                    emailTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                else if (!isValidEmail(email))
                    emailTextInputLayout.setError(getString(R.string.invalid_email_id));
                else if(TextUtils.isEmpty(password))
                    passwordTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                else
                {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                checkUserExists();
                            }
                            else
                            {
                                Toast.makeText(SignInActivity.this, "Wrong email/password", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
                break;

            case R.id.signUpButton:
                Intent intent1=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent1);
                break;
            case R.id.forgotPasswordTextView:
                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View alertLayout = inflater.inflate(R.layout.layout_custom_dialog,null);
                final TextInputLayout emailTextInputLayout = (TextInputLayout) alertLayout.findViewById(R.id.emailTextInputLayout);
                final AlertDialog.Builder alert = new AlertDialog.Builder(SignInActivity.this);
                alert.setTitle("Forgot Password");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(true);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(alert.getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setPositiveButton("Send Email", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        String email = emailTextInputLayout.getEditText().getText().toString();
                        mAuth.sendPasswordResetEmail(email);
                        Toast.makeText(SignInActivity.this, "Password Reset Mail has been sent.", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

                break;
        }
    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    private void checkUserExists()
    {
        progressDialog.show();
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(user_id))
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
//                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                    else {
                        Toast.makeText(SignInActivity.this, "Kindly Verify your Email Id", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }

                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(SignInActivity.this, "You need to sign up first...", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
