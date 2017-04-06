package com.neeraj8le.leavemanager.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.adapter.PendingLeaveRequestRecyclerAdapter;
import com.neeraj8le.leavemanager.model.Employee;
import com.neeraj8le.leavemanager.model.Leave;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingLeaveRequestFragment extends Fragment {

    RecyclerView pendingLeaveRequestRecyclerView;
    PendingLeaveRequestRecyclerAdapter pendingLeaveRequestRecyclerAdapter;
    ArrayList<Leave> leaves;
    Employee employee;


    public PendingLeaveRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_pending_leave_request, container, false);

        employee = getArguments().getParcelable("employee");

        leaves = new ArrayList<>();

        leaves.add(new Leave("1", "1", "Maternity Leave", "Had a baby", "1 Apr 17", "1 May 17", 0, "30 Mar 17"));
        leaves.add(new Leave("1", "1", "Study Leave", "studying for mba", "10 Apr 17", "1 May 17", 1, "30 Mar 17"));
        leaves.add(new Leave("1", "1", "Casual Leave", "just for fun", "27 Apr 17", "14 May 17", 2, "30 Mar 17"));

        pendingLeaveRequestRecyclerAdapter = new PendingLeaveRequestRecyclerAdapter(getContext(), leaves);

        pendingLeaveRequestRecyclerView = (RecyclerView) v.findViewById(R.id.pendingLeaveRequestRecyclerView);
        pendingLeaveRequestRecyclerView.setAdapter(pendingLeaveRequestRecyclerAdapter);
        pendingLeaveRequestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return v;
    }

}
