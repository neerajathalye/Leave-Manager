package com.neeraj8le.leavemanager.activity;


import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.fragment.DatePickerFragment;

public class AddLeaveActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
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

        adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, leave_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s1.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        selectedLeave = s1.getSelectedItem().toString();
        myCalendar=Calendar.getInstance();

//        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener(){
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                myCalendar.set(Calendar.YEAR,year);
//                myCalendar.set(Calendar.MONTH,month);
//                myCalendar.set(Calendar.DAY_OF_MONTH,day);
//                updateLabel();
//            }
//        };



        mfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new DatePickerDialog(AddLeaveActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                selectedEditText = mfrom;
                DialogFragment dialogFragment=new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(),"datePicker");
                updateDate(mfrom);
            }
        });
        mto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedEditText = mto;
                DialogFragment dialogFragment=new DatePickerFragment();
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
                String reason=leave_reason.getEditText().getText().toString();
                String from=from_date.getEditText().getText().toString();
                String to=to_date.getEditText().getText().toString();
            }
        });
    }
    private void updateLabel()
    {
        String myFormat="MM/dd/yy";
        SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);
        mfrom.setText(sdf.format(myCalendar.getTime()));
        mto.setText(sdf.format(myCalendar.getTime()));
    }

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

        String myFormat="MM/dd/yy";
        SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);
        v.setText(sdf.format(myCalendar.getTime()));
    }
}
