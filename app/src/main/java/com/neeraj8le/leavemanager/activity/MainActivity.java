package com.neeraj8le.leavemanager.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.adapter.ViewPagerAdapter;
import com.neeraj8le.leavemanager.fragment.LeaveHistoryFragment;
import com.neeraj8le.leavemanager.fragment.PendingLeaveRequestFragment;
import com.neeraj8le.leavemanager.fragment.SubordinateLeaveRequestFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

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


}
