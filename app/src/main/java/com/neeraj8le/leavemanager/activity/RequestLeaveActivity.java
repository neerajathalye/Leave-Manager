package com.neeraj8le.leavemanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.model.Leave;

public class RequestLeaveActivity extends AppCompatActivity implements View.OnClickListener {
    TextView leave_type,leave_reason,from_date,to_date,current_date;
    Button accept,reject;
    Leave leave;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_leave);
        leave_type=(TextView)findViewById(R.id.leave_type);
        leave_reason=(TextView)findViewById(R.id.leave_reason);
        from_date=(TextView)findViewById(R.id.from_date);
        to_date=(TextView)findViewById(R.id.to_date);
        current_date=(TextView)findViewById(R.id.current_date);
        accept=(Button)findViewById(R.id.accept);
        reject=(Button)findViewById(R.id.reject);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        leave=getIntent().getParcelableExtra("leave");
        leave_type.setText(leave.getLeaveType());
        leave_reason.setText(leave.getLeaveReason());
        from_date.setText(leave.getFromDate());
        to_date.setText(leave.getToDate());
        current_date.setText(leave.getApplicationDate());

        accept.setOnClickListener(this);
        reject.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("leave");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.accept:
                leave.setLeaveStatus(1);
                mDatabase.child(String.valueOf(leave.getId())).setValue(leave);
                finish();
                break;
            case R.id.reject:
                leave.setLeaveStatus(2);
                mDatabase.child(String.valueOf(leave.getId())).setValue(leave);
                finish();
                break;
        }
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
