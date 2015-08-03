package sweng500team2summer15.hov_helper.resource.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

/**
 * Created by terry.ferguson on 8/1/2015.
 */
public class TimePickerFragment extends DialogFragment {
    TimePickerDialog.OnTimeSetListener ontimeSet;

    public TimePickerFragment() {

    }
    public void setCallBack(TimePickerDialog.OnTimeSetListener ontime) {
        ontimeSet = ontime;

    }
    private int hour, minute;

    @Override
    public void setArguments (Bundle args){
        super.setArguments(args);
        hour = args.getInt("hour");
        minute = args.getInt("minute");
    }
    @Override
    public Dialog onCreateDialog (Bundle savedInstance){
        //ToDo Limit the Range of timepicker to the present would require you to examine the existing date and determine if it was today
        return new TimePickerDialog(getActivity(), ontimeSet,hour, minute, DateFormat.is24HourFormat(getActivity()));

    }
}
