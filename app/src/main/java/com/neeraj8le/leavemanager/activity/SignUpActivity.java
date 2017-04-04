package com.neeraj8le.leavemanager.activity;

import android.graphics.PorterDuff;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.model.Employee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private Button mSubmit;
    TextInputLayout idTextInputLayout,nameTextInputLayout,deptTextInputLayout,designationTextInputLayout,phoneTextInputLayout,emailTextInputLayout
            ,passwordTextInputLayout,confirmPasswordTextInputLayout;
    Spinner s1;
    String supervisors[] = {"Select supervisors", "Arnav", "Neeraj", "Priyanshu", "Yolo"};
    String selectedSupervisor;
    ArrayAdapter<String> adapter;

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

        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

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
                String email = emailTextInputLayout.getEditText().getText().toString();
                String password = passwordTextInputLayout.getEditText().getText().toString();
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
                     Employee employee = new Employee(emp_id,name,dept_name,desig,contact,email,con_pass,supervisor);
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
}


