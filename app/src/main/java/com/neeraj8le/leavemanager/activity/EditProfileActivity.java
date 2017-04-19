package com.neeraj8le.leavemanager.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.model.Employee;
import com.neeraj8le.leavemanager.model.Leave;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.neeraj8le.leavemanager.R.id.parent;

public class EditProfileActivity extends AppCompatActivity {
    private Button mUpdate;
    TextInputLayout idTextInputLayout, nameTextInputLayout, deptTextInputLayout, designationTextInputLayout, phoneTextInputLayout;
//    Spinner s1;
//    ArrayList<String> supervisors;
//    String selectedSupervisor;
//    ArrayAdapter<String> adapter;
    ProgressDialog progressDialog;
    Employee employee;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    void showToast(String msg)

    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
//        s1 = (Spinner) findViewById(R.id.spinner1);
        mUpdate = (Button) findViewById(R.id.update);
        idTextInputLayout = (TextInputLayout) findViewById(R.id.idTextInputLayout);
        nameTextInputLayout = (TextInputLayout) findViewById(R.id.nameTextInputLayout);
        deptTextInputLayout = (TextInputLayout) findViewById(R.id.deptTextInputLayout);
        designationTextInputLayout = (TextInputLayout) findViewById(R.id.designationTextInputLayout);
        phoneTextInputLayout = (TextInputLayout) findViewById(R.id.phoneTextInputLayout);

        SharedPreferences sharedPreferences = getSharedPreferences("EMPLOYEE_FILE_KEY", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("employee", "");
        employee = gson.fromJson(json, Employee.class);

//        supervisors = new ArrayList<>();
//        supervisors.add("Select your supervisor");
//        supervisors.add("admin");
//
//        adapter = new ArrayAdapter<>(EditProfileActivity.this, R.layout.simple_spinner_item, supervisors);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        s1.setAdapter(adapter);
//        s1.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("employee");

//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                supervisors = new ArrayList<>();
//                supervisors.add("Select your supervisor");
//
//                for (DataSnapshot ds : dataSnapshot.getChildren())
//                {
//                    supervisors.add(ds.child("name").getValue().toString());
//                }
//                adapter = new ArrayAdapter<>(EditProfileActivity.this, R.layout.simple_spinner_item, supervisors);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                s1.setAdapter(adapter);
//                s1.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//                s1.setSelection(getDefaultSupervisor());
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Updating your profile...");
//
        idTextInputLayout.getEditText().setText(employee.getId());
        nameTextInputLayout.getEditText().setText(employee.getName());
        deptTextInputLayout.getEditText().setText(employee.getDepartmentName());
        designationTextInputLayout.getEditText().setText(employee.getDesignation());
        phoneTextInputLayout.getEditText().setText(employee.getPhoneNumber());

        idTextInputLayout.setEnabled(false);
        nameTextInputLayout.setEnabled(false);



        mUpdate.setOnClickListener(new View.OnClickListener()

        {
            public void onClick (View view){


                idTextInputLayout.setErrorEnabled(false);
                nameTextInputLayout.setErrorEnabled(false);
                deptTextInputLayout.setErrorEnabled(false);
                designationTextInputLayout.setErrorEnabled(false);
                phoneTextInputLayout.setErrorEnabled(false);

                String emp_id = idTextInputLayout.getEditText().getText().toString();
                String name = nameTextInputLayout.getEditText().getText().toString();
                String dept_name = deptTextInputLayout.getEditText().getText().toString();
                String desig = designationTextInputLayout.getEditText().getText().toString();
                String contact = phoneTextInputLayout.getEditText().getText().toString();
//                String supervisor = selectedSupervisor;


                if (TextUtils.isEmpty(emp_id)) {
                    idTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                } else if (TextUtils.isEmpty(name)) {
                    nameTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                } else if (TextUtils.isEmpty(dept_name)) {
                    deptTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                } else if (TextUtils.isEmpty(desig)) {
                    designationTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                } else if (TextUtils.isEmpty(contact)) {
                    phoneTextInputLayout.setError(getString(R.string.field_cannot_be_empty));
                } else if (contact.length() < 10) {
                    phoneTextInputLayout.setError(getString(R.string.invalid_number));
//                } else if (supervisor.equals(supervisors.get(0))) {
//                    showToast("Please select your supervisor");
                } else {
                    progressDialog.show();

                    final Employee modifiedEmployee = new Employee(emp_id, name, dept_name, desig, contact, employee.getEmail(), employee.getToken(), employee.isHR());

                    mDatabase.child(mAuth.getCurrentUser().getUid()).setValue(modifiedEmployee);

                    progressDialog.dismiss();

                    finish();
                }
            }

        });

//        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//
//        {
//            @Override
//            public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
//                selectedSupervisor = s1.getSelectedItem().toString();
//                showToast(selectedSupervisor);
//            }
//
//            @Override
//            public void onNothingSelected (AdapterView < ? > parent){
//                showToast("Not Selected");
//            }
//        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public int getDefaultSupervisor()
//    {
//        for(int i = 0; i < supervisors.size(); i++)
//            if (supervisors.get(i).equals(employee.getSupervisorName()))
//                return i;
//        return 0;
//    }
    }

