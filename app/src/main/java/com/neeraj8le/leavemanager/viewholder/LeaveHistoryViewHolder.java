package com.neeraj8le.leavemanager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neeraj8le.leavemanager.R;

public class LeaveHistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView leave_historyDatesTextView, leave_historyTypeTextView;
    public ImageView leave_historyStatusImageView;
    public RelativeLayout history_container;

    public LeaveHistoryViewHolder(View itemView) {
        super(itemView);

        leave_historyDatesTextView = (TextView) itemView.findViewById(R.id.leave_historyDatesTextView);
        leave_historyTypeTextView = (TextView) itemView.findViewById(R.id.leave_historyTypeTextView);
        leave_historyStatusImageView = (ImageView) itemView.findViewById(R.id.leave_historyStatusImageView);
        history_container = (RelativeLayout) itemView.findViewById(R.id.history_container);
    }
}

