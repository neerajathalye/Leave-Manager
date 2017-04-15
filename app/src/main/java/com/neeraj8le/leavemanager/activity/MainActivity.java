package com.neeraj8le.leavemanager.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.SharedPrefManager;
import com.neeraj8le.leavemanager.adapter.ViewPagerAdapter;
import com.neeraj8le.leavemanager.firebase.MyFirebaseInstanceIDService;
import com.neeraj8le.leavemanager.fragment.LeaveHistoryFragment;
import com.neeraj8le.leavemanager.fragment.PendingLeaveRequestFragment;
import com.neeraj8le.leavemanager.fragment.SubordinateLeaveRequestFragment;
import com.neeraj8le.leavemanager.model.Employee;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton addLeaveFAB;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Employee employee;
    private BroadcastReceiver broadcastReceiver;
    TextView confirmation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
//        getSupportActionBar().setTitle(SharedPrefManager.getInstance(MainActivity.this).getToken());

        SharedPreferences sharedPreferences = getSharedPreferences("EMPLOYEE_FILE_KEY", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("employee", "");
        employee = gson.fromJson(json, Employee.class);


//        employee = getIntent().getParcelableExtra("employee");
//        Toast.makeText(this, "main : " + employee.getName(), Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                Toast.makeText(context, SharedPrefManager.getInstance(MainActivity.this).getToken(), Toast.LENGTH_SHORT).show();
//                mDatabase.child("employee").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").setValue(SharedPrefManager.getInstance(MainActivity.this).getToken());
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIDService.TOKEN_BROADCAST));


        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                } else
                    {
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                    // User is signed out
                }
                // ...
            }
        };

        addLeaveFAB = (FloatingActionButton) findViewById(R.id.addLeaveFAB);

        addLeaveFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddLeaveActivity.class);
//                intent.putExtra("employee", employee);
                startActivity(intent);
            }
        });

        viewPager  = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PendingLeaveRequestFragment(), "Pending");
        adapter.addFragment(new SubordinateLeaveRequestFragment(), "Requests");
        adapter.addFragment(new LeaveHistoryFragment(), "History");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = inflater.inflate(R.layout.logout_dialog, null);
                confirmation=(TextView)findViewById(R.id.confirmation);
                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Logout");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(true);


                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(alert.getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
                return true;
            case R.id.editProfile:
                Intent intent=new Intent(this,EditProfileActivity.class);
                startActivity(intent);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
