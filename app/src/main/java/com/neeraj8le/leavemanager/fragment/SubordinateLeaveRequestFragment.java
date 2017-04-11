package com.neeraj8le.leavemanager.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.adapter.PendingLeaveRequestRecyclerAdapter;
import com.neeraj8le.leavemanager.adapter.RequestsRecyclerAdapter;
import com.neeraj8le.leavemanager.model.Employee;
import com.neeraj8le.leavemanager.model.Leave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubordinateLeaveRequestFragment extends Fragment {

    RecyclerView requestsRecyclerView;
    RequestsRecyclerAdapter requestsRecyclerAdapter;
    ArrayList<Leave> leaves;
    Employee employee;
    DatabaseReference mDatabase;

    public SubordinateLeaveRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_subordinate_leave_request, container, false);

//        employee = getArguments().getParcelable("employee");

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("EMPLOYEE_FILE_KEY", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("employee", "");
        employee = gson.fromJson(json, Employee.class);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("leave");

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                leaves = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    if(ds.child("supervisor").getValue() != null)
                        if(ds.child("supervisor").getValue().equals(employee.getName()))
                        {
                            leaves.add(ds.getValue(Leave.class));
                        }
                }

                Collections.sort(leaves, new Comparator<Leave>() {
                    @Override
                    public int compare(Leave o1, Leave o2) {
                        if(o1.getLeaveStatus() == 0 || o2.getLeaveStatus() == 0)
                            return -1;
                        else if(o1.getLeaveStatus() == o2.getLeaveStatus())
                            return  o2.getApplicationDate().compareTo(o1.getApplicationDate());
                        else if(o1.getLeaveStatus() != 0 || o2.getLeaveStatus() != 0)
                            return  o2.getApplicationDate().compareTo(o1.getApplicationDate());
                        else
                            return  1;
                    }
                });

                requestsRecyclerAdapter = new RequestsRecyclerAdapter(getContext(), leaves);
                requestsRecyclerView = (RecyclerView) v.findViewById(R.id.requestsRecyclerView);
                requestsRecyclerView.setAdapter(requestsRecyclerAdapter);
                requestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return v;
    }

}
