package com.neeraj8le.leavemanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.model.Leave;

public class RequestLeaveActivity extends AppCompatActivity {
    TextView leave_type,leave_reason,from_date,to_date,current_date;
    Button accept,reject;
    Leave leave;

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
//        String leavetype=intent.getStringExtra("Leave Type:");
//        String leavereason=intent.getStringExtra("Leave Reason:");
//        String leaveduration=intent.getStringExtra("Leave Duration:");
        leave_type.setText("Leave Type:"+leave.leaveType);
        leave_reason.setText("Leave Reason:"+leave.leaveReason);
        from_date.setText("From:"+leave.fromDate);
        to_date.setText("To:"+leave.toDate);
        current_date.setText("Application Date:"+leave.applicationDate);


    }
}
