package com.neeraj8le.leavemanager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neeraj8le.leavemanager.R;

/**
 * Created by neera on 08-Apr-17.
 */

public class RequestsViewHolder extends RecyclerView.ViewHolder {

    public TextView leaveDatesTextView, leaveTypeTextView, subordinateNameTextView;
    public ImageView leaveStatusImageView;
    public RelativeLayout container;

    public RequestsViewHolder(View itemView) {
        super(itemView);

        leaveDatesTextView = (TextView) itemView.findViewById(R.id.leaveDatesTextView);
        leaveTypeTextView = (TextView) itemView.findViewById(R.id.leaveTypeTextView);
        subordinateNameTextView = (TextView) itemView.findViewById(R.id.subordinateNameTextView);
        leaveStatusImageView = (ImageView) itemView.findViewById(R.id.leaveStatusImageView);
        container = (RelativeLayout) itemView.findViewById(R.id.container);
    }
}
