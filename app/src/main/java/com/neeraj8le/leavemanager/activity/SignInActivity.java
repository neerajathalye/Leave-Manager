package com.neeraj8le.leavemanager.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.neeraj8le.leavemanager.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInButton = (Button) findViewById(R.id.signInButton);

        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signInButton:
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
