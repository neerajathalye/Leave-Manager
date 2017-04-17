package com.neeraj8le.leavemanager.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.adapter.PendingLeaveRequestRecyclerAdapter;
import com.neeraj8le.leavemanager.model.Employee;
import com.neeraj8le.leavemanager.model.Leave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;
/**
 * A simple {@link Fragment} subclass.
 */
public class PendingLeaveRequestFragment extends Fragment {

    RecyclerView pendingLeaveRequestRecyclerView;
    PendingLeaveRequestRecyclerAdapter pendingLeaveRequestRecyclerAdapter;
    ArrayList<Leave> leaves;
    Employee employee;
    DatabaseReference mDatabase;
    TextView defaultBackground;


    public PendingLeaveRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_pending_leave_request, container, false);

        defaultBackground = (TextView) v.findViewById(R.id.defaultBackground);

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
                    if(ds.child("employee").getValue().equals(employee.getName()) && (long) ds.child("leaveStatus").getValue() == 0)
                    {
                        leaves.add(ds.getValue(Leave.class));
//                        Toast.makeText(getContext(), leaves.get(0).getLeaveReason(), Toast.LENGTH_SHORT).show();

                    }
                }

                Collections.sort(leaves, new Comparator<Leave>() {
                    @Override
                    public int compare(Leave o1, Leave o2) {
                        return  o2.getApplicationDate().compareTo(o1.getApplicationDate());
                    }
                });

                if(leaves.isEmpty())
                    defaultBackground.setVisibility(View.VISIBLE);
                else
                    defaultBackground.setVisibility(View.GONE);

                pendingLeaveRequestRecyclerAdapter = new PendingLeaveRequestRecyclerAdapter(getContext(), leaves);
                pendingLeaveRequestRecyclerView = (RecyclerView) v.findViewById(R.id.pendingLeaveRequestRecyclerView);
                pendingLeaveRequestRecyclerView.setAdapter(pendingLeaveRequestRecyclerAdapter);
                pendingLeaveRequestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        leaves.add(new Leave("1", "1", "Maternity Leave", "Had a baby", "1 Apr 17", "1 May 17", 0, "30 Mar 17"));
//        leaves.add(new Leave("1", "1", "Study Leave", "studying for mba", "10 Apr 17", "1 May 17", 1, "30 Mar 17"));
//        leaves.add(new Leave("1", "1", "Casual Leave", "just for fun", "27 Apr 17", "14 May 17", 2, "30 Mar 17"));


        return v;
    }

}
