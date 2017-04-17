package com.neeraj8le.leavemanager.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.neeraj8le.leavemanager.activity.AddLeaveActivity;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
         Calendar c=Calendar.getInstance();
         int year=c.get(Calendar.YEAR);
         int month=c.get(Calendar.MONTH);
         int day=c.get(Calendar.DAY_OF_MONTH);

        long minDate = getArguments().getLong("minDate", 0);
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),(AddLeaveActivity) getActivity(),year,month,day);

        if(minDate == 0)
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        else
            datePickerDialog.getDatePicker().setMinDate(minDate);
        return datePickerDialog;
    }

    //    public DatePickerFragment() {
//        // Required empty public constructor
//    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
//    }

}
