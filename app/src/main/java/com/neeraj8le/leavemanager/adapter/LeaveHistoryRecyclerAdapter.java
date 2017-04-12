package com.neeraj8le.leavemanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.neeraj8le.leavemanager.R;
import com.neeraj8le.leavemanager.activity.RequestLeaveActivity;
import com.neeraj8le.leavemanager.model.Leave;
import com.neeraj8le.leavemanager.viewholder.LeaveHistoryViewHolder;
import com.neeraj8le.leavemanager.viewholder.LeaveHistoryViewHolder;

import java.util.ArrayList;

/**
 * Created by Arnav on 06-04-2017.
 */

public class LeaveHistoryRecyclerAdapter extends RecyclerView.Adapter<LeaveHistoryViewHolder> {
    Context context;
    ArrayList<Leave> leaves_history;
    public LeaveHistoryRecyclerAdapter(Context context, ArrayList<Leave> leaves_history) {
        this.context = context;
        this.leaves_history = leaves_history;
    }
    @Override
    public LeaveHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leave_history_item, parent,false);
        return new LeaveHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LeaveHistoryViewHolder holder, int position) {

        String leaveDates = leaves_history.get(position).getFromDate() + " - " + leaves_history.get(position).getToDate();
        holder.leave_historyDatesTextView.setText(leaveDates);
        holder.leave_historyTypeTextView.setText(leaves_history.get(position).getLeaveType());
        if(leaves_history.get(position).getLeaveStatus() == 0) // on hold
            holder.leave_historyStatusImageView.setImageResource(R.drawable.ic_remove_circle_yellow_48dp);
        else if(leaves_history.get(position).getLeaveStatus() == 1) // accepted
            holder.leave_historyStatusImageView.setImageResource(R.drawable.ic_check_circle_green_48dp);
        if(leaves_history.get(position).getLeaveStatus() == 2) // rejected
            holder.leave_historyStatusImageView.setImageResource(R.drawable.ic_cancel_red_48dp);

        holder.history_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,RequestLeaveActivity.class);
//                Leave leave=new Leave(leaves.get(position).getEmployee(),leaves.get(position).getSupervisor(),leaves.get(position).getLeaveType()
//                ,leaves.get(position).getLeaveReason(),leaves.get(position).getFromDate(),leaves.get(position).getToDate(),leaves.get(position).getLeaveStatus(),
//                        leaves.get(position).getApplicationDate(), leaves.get(position).getId());
                intent.putExtra("leave",leaves_history.get(holder.getAdapterPosition()));
                intent.putExtra("intent_data_key","history");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return leaves_history.size();
    }
}
