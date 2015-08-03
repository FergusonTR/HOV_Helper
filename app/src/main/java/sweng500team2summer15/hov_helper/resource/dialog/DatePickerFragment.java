package sweng500team2summer15.hov_helper.resource.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by terry.ferguson on 8/1/2015.
 */
public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateSet;

    public DatePickerFragment() {
    }
    public void setCallBack(DatePickerDialog.OnDateSetListener ondate){
        ondateSet = ondate;
    }
    private int year, month, day;

    @Override
    public void setArguments(Bundle args){
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        DatePickerDialog dpicker = new DatePickerDialog(getActivity(), ondateSet, year, month, day);
        //limits this to the current date
        dpicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        return dpicker ;
    }
}

