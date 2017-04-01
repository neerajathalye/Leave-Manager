package com.neeraj8le.leavemanager.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.neeraj8le.leavemanager.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signInButton;
    private Button signUpButton;
    TextView forgotPasswordTextView;
    TextInputLayout emailTextInputLayout,passwordTextInputLayout;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailTextInputLayout=(TextInputLayout)findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout=(TextInputLayout)findViewById(R.id.passwordTextInputLayout);

        forgotPasswordTextView=(TextView)findViewById(R.id.forgotPasswordTextView);

        signUpButton=(Button)findViewById(R.id.signUpButton);
        signInButton = (Button) findViewById(R.id.signInButton);

        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        forgotPasswordTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signInButton:

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
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
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


}
