package com.neeraj8le.leavemanager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neeraj8le.leavemanager.R;

public class PendingLeaveRequestViewHolder extends RecyclerView.ViewHolder {

    public TextView leaveDatesTextView, leaveTypeTextView;
    public ImageView leaveStatusImageView;
    public RelativeLayout container;


    public PendingLeaveRequestViewHolder(View itemView) {
        super(itemView);

        leaveDatesTextView = (TextView) itemView.findViewById(R.id.leaveDatesTextView);
        leaveTypeTextView = (TextView) itemView.findViewById(R.id.leaveTypeTextView);
        leaveStatusImageView = (ImageView) itemView.findViewById(R.id.leaveStatusImageView);
        container = (RelativeLayout) itemView.findViewById(R.id.container);
    }
}
