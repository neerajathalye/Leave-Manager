package com.neeraj8le.leavemanager.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.neeraj8le.leavemanager.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private Button mSubmit;
    Spinner s1;
    String supervisor[] = {"Select supervisor", "Arnav", "Neeraj", "Priyanshu", "Yolo"};

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
        final TextInputLayout til0 = (TextInputLayout) findViewById(R.id.textInputLayout0);
        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.textInputLayout1);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.textInputLayout2);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.textInputLayout3);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.textInputLayout4);
        final TextInputLayout til6 = (TextInputLayout) findViewById(R.id.textInputLayout6);
        final TextInputLayout til7 = (TextInputLayout) findViewById(R.id.textInputLayout7);
        final TextInputLayout til8 = (TextInputLayout) findViewById(R.id.textInputLayout8);
        final String emp_id = til0.getEditText().getText().toString();
        final String name = til1.getEditText().getText().toString();
        final String dept_name = til2.getEditText().getText().toString();
        final String desig = til3.getEditText().getText().toString();
        final String contact = til4.getEditText().getText().toString();
        final String email = til6.getEditText().getText().toString();
        final String password = til7.getEditText().getText().toString();
        final String con_pass=til8.getEditText().getText().toString();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, supervisor);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        final String supervise = s1.getSelectedItem().toString();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!(password.equals(con_pass)))
                {
                    til8.setError("Passwords do not match");
                }
                 if (name.length()==0) {
                    til1.setError("Field cannot be empty");
                } if (emp_id.length()==0) {
                    til0.setError("Field cannot be empty");
                } if (dept_name.length()==0) {
                    til2.setError("Field cannot be empty");
                } if (desig.length()==0) {
                    til3.setError("Field cannot be empty");
                } if (contact.length()==0) {
                    til4.setError("Field cannot be empty");
                } if (email.length()==0) {
                    til6.setError("Field cannot be empty");
                } if (password.length()==0) {
                    til7.setError("Field cannot be empty");
                }
                if(con_pass.length()==0)
                {
                    til8.setError("Field cannot be empty");
                }
                if (contact.length() > 10) {
                    til4.setError("Number cannot be more than 10 digits long");
                } if (!isValidEmail(email)) {
                    til6.setError("Invalid Email");
                }
            }

        });

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showToast(supervisor[position]);
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
}


