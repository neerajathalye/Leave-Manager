package com.neeraj8le.leavemanager.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.adapter.LeaveHistoryRecyclerAdapter;
import com.neeraj8le.leavemanager.model.Leave;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaveHistoryFragment extends Fragment {
    RecyclerView leaveHistoryRecyclerView;
    LeaveHistoryRecyclerAdapter leaveHistoryRecyclerAdapter;
    ArrayList<Leave> leaves_history;


    public LeaveHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_leave_history, container, false);
        leaves_history = new ArrayList<>();

        leaves_history.add(new Leave("1", "1", "Maternity Leave", "Had a baby", "10 Apr 17", "1 May 17", 0, "30 Mar 17"));
        leaves_history.add(new Leave("1", "1", "Study Leave", "studying for mba", "10 Apr 17", "1 May 17", 1, "30 Mar 17"));
        leaves_history.add(new Leave("1", "1", "Casual Leave", "just for fun", "27 Apr 17", "14 May 17", 2, "30 Mar 17"));

        leaveHistoryRecyclerAdapter = new LeaveHistoryRecyclerAdapter(getContext(), leaves_history);

        leaveHistoryRecyclerView = (RecyclerView) v.findViewById(R.id.leaveHistoryRecyclerView);
        leaveHistoryRecyclerView.setAdapter(leaveHistoryRecyclerAdapter);
        leaveHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

}
