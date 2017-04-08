package com.neeraj8le.leavemanager.fragment;


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
import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.adapter.PendingLeaveRequestRecyclerAdapter;
import com.neeraj8le.leavemanager.adapter.RequestsRecyclerAdapter;
import com.neeraj8le.leavemanager.model.Employee;
import com.neeraj8le.leavemanager.model.Leave;

import java.util.ArrayList;

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

        employee = getArguments().getParcelable("employee");
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
//                          Toast.makeText(getContext(), leaves.get(0).getLeaveReason(), Toast.LENGTH_SHORT).show();

                        }
                }
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
