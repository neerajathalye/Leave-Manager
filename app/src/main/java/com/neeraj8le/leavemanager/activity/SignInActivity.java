package com.neeraj8le.leavemanager.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neeraj8le.leavemanager.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signInButton;
    private Button signUpButton;
    EditText met1,met2;
    TextView mtv1;
    TextInputLayout til1,til2;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        til1=(TextInputLayout)findViewById(R.id.textInputLayout);
        til2=(TextInputLayout)findViewById(R.id.textInputLayout);
        met1=(EditText)findViewById(R.id.et1);
        met2=(EditText)findViewById(R.id.et2);
        mtv1=(TextView)findViewById(R.id.tv1);
        signUpButton=(Button)findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);
        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);
        email=til1.getEditText().getText().toString();
        password=til2.getEditText().getText().toString();

        mtv1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View alertLayout = inflater.inflate(R.layout.layout_custom_dialog,null);
                final EditText etEmail = (EditText) alertLayout.findViewById(R.id.et_email);
                final AlertDialog.Builder alert = new AlertDialog.Builder(SignInActivity.this);
                alert.setTitle("Password Recovery");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(true);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(alert.getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        String email = etEmail.getText().toString();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

                return false;
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signInButton:
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.signUpButton:
                Intent intent1=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent1);
                break;
        }
    }


}
