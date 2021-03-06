package com.neeraj8le.leavemanager.activity;


import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.fragment.DatePickerFragment;
import com.neeraj8le.leavemanager.model.Employee;
import com.neeraj8le.leavemanager.model.Leave;

public class AddLeaveActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    Employee employee;
    private Button mSubmit;
    Spinner s1;
    String leave_type[] = {"Select Leave type","Earned Leave","Casual Leave","Medical Leave","Maternity Leave","Quarantine Leave",
            "Sabbatical Leave","Paternity Leave","Leave without pay"};
    String selectedLeave;
    String reason,from,to;
    String date="";
    String month="";
    String year="";
    ArrayAdapter<String> adapter;
    Calendar myCalendar;
    private TextInputLayout leave_reason,from_date,to_date;
    EditText mreason,mfrom,mto;
    EditText selectedEditText;
    DatabaseReference mDatabase;
    long size;

    void showToast(String msg)

    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_leave);
        mSubmit = (Button) findViewById(R.id.submit);
        leave_reason=(TextInputLayout)findViewById(R.id.reasonTextInputLayout);
        from_date=(TextInputLayout)findViewById(R.id.fromTextInputLayout);
        to_date=(TextInputLayout)findViewById(R.id.toTextInputLayout);
        mreason=(EditText)findViewById(R.id.leave_reason);
        mfrom=(EditText)findViewById(R.id.from);
        mto=(EditText)findViewById(R.id.to);
        s1 = (Spinner) findViewById(R.id.leave_type_spinner);

        to_date.setEnabled(false);
        mto.setEnabled(false);

        adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, leave_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s1.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        selectedLeave = s1.getSelectedItem().toString();
        myCalendar=Calendar.getInstance();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("leave");


        SharedPreferences sharedPreferences = getSharedPreferences("EMPLOYEE_FILE_KEY", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("employee", "");
        employee = gson.fromJson(json, Employee.class);

        mfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new DatePickerDialog(AddLeaveActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                selectedEditText = mfrom;
                long minDate = 0;
                Bundle bundle = new Bundle();
                bundle.putLong("minDate", minDate);
                DialogFragment dialogFragment=new DatePickerFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(),"datePicker");
                updateDate(mfrom);

                to_date.setEnabled(true);
                mto.setEnabled(true);
            }
        });
        mto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedEditText = mto;

                long minDate = 0;
                SimpleDateFormat f = null;
                Date d = null;
                String dateParts[] = mfrom.getText().toString().split(" ");
                if(dateParts[0].length() == 2)
                    f = new SimpleDateFormat("dd MMM yy");
                else if(dateParts[0].length() == 1)
                    f = new SimpleDateFormat("d MMM yy");
                try {
                    if(dateParts[0].length() == 2)
                        d = f.parse(mfrom.getText().toString().substring(0, 9));
                    else if(dateParts[0].length() == 1)
                        d = f.parse(mfrom.getText().toString().substring(0, 8));
                    if(d != null)
                        minDate = d.getTime();
                    else
                        minDate = 0;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Bundle bundle = new Bundle();
                bundle.putLong("minDate", minDate);

                DialogFragment dialogFragment=new DatePickerFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(),"datePicker");
                updateDate(mto);
//                new DatePickerDialog(AddLeaveActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLeave = s1.getSelectedItem().toString();
                showToast(selectedLeave);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showToast("Not Selected");
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String reason=leave_reason.getEditText().getText().toString();
                final String from=from_date.getEditText().getText().toString();
                final String to=to_date.getEditText().getText().toString();
                final String leaveType = selectedLeave;
                Calendar c = Calendar.getInstance();
                String myFormat="dd MMM yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                final String applicationDate = sdf.format(c.getTime());

                 if(selectedLeave.equals("Select Leave type"))
                {
                    showToast("Please select Leave type");
                }
                else if(TextUtils.isEmpty(reason))
                 {
                     leave_reason.setError("Enter reason for leave now");
                 }
                 else if(TextUtils.isEmpty(from))
                 {
                     from_date.setError("Date cannot be empty");
                 }
                 else if(TextUtils.isEmpty(to))
                {
                    to_date.setError("Date cannot be empty");
                }
                else {

                     mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             size = dataSnapshot.getChildrenCount();
                             final Leave leave = new Leave(employee.getName(), leaveType, reason, from, to, 0, applicationDate, size, employee.getToken());
                             mDatabase.child(String.valueOf(size)).setValue(leave);
                             finish();
                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError) {

                         }
                     });
                 }

            }
        });
    }
//    private void updateLabel()
//    {
//        String myFormat="MM/dd/yy";
//        SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);
//        mfrom.setText(sdf.format(myCalendar.getTime()));
//        mto.setText(sdf.format(myCalendar.getTime()));
//    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//        date = String.valueOf(i2);
//        month = String.valueOf(i1);
//        year = String.valueOf(i);
        myCalendar.set(Calendar.DAY_OF_MONTH, i2);
        myCalendar.set(Calendar.MONTH, i1);
        myCalendar.set(Calendar.YEAR, i);

        updateDate(selectedEditText);

    }

    public void updateDate(EditText v)
    {
        String myFormat="dd MMM yy";
        SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);
        v.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
