package com.neeraj8le.leavemanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.activity.RequestLeaveActivity;
import com.neeraj8le.leavemanager.model.Leave;
import com.neeraj8le.leavemanager.viewholder.RequestsViewHolder;

import java.util.ArrayList;

/**
 * Created by Neeraj Athalye on 08-Apr-17.
 */

public class RequestsRecyclerAdapter extends RecyclerView.Adapter<RequestsViewHolder> {

    Context context;
    ArrayList<Leave> leaves;
    Leave leave;

    public RequestsRecyclerAdapter(Context context, ArrayList<Leave> leaves) {
        this.context = context;
        this.leaves = leaves;
    }

    @Override
    public RequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.requests_item, parent,false);
        return new RequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RequestsViewHolder holder, int position) {

        final String leaveDates = leaves.get(position).getFromDate() + " - " + leaves.get(position).getToDate();
        holder.leaveDatesTextView.setText(leaveDates);
        holder.leaveTypeTextView.setText(leaves.get(position).getLeaveType());
        holder.subordinateNameTextView.setText(leaves.get(position).getEmployee());
        if(leaves.get(position).getLeaveStatus() == 0) // on hold
            holder.leaveStatusImageView.setImageResource(R.drawable.ic_remove_circle_yellow_48dp);
        else if(leaves.get(position).getLeaveStatus() == 1) // accepted
            holder.leaveStatusImageView.setImageResource(R.drawable.ic_check_circle_green_48dp);
        if(leaves.get(position).getLeaveStatus() == 2) // rejected
            holder.leaveStatusImageView.setImageResource(R.drawable.ic_cancel_red_48dp);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,RequestLeaveActivity.class);
//                Leave leave=new Leave(leaves.get(position).getEmployee(),leaves.get(position).getSupervisor(),leaves.get(position).getLeaveType()
//                ,leaves.get(position).getLeaveReason(),leaves.get(position).getFromDate(),leaves.get(position).getToDate(),leaves.get(position).getLeaveStatus(),
//                        leaves.get(position).getApplicationDate(), leaves.get(position).getId());
                intent.putExtra("leave",leaves.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return leaves.size();
    }
}
