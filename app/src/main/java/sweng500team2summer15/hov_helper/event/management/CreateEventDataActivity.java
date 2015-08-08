package sweng500team2summer15.hov_helper.event.management;

import com.google.android.gms.maps.model.LatLng;
import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ViewProfileActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.eventdisplay.RequestedEventsActivity;
import sweng500team2summer15.hov_helper.map.MapController;
import sweng500team2summer15.hov_helper.resource.dialog.DatePickerFragment;
import sweng500team2summer15.hov_helper.resource.dialog.TimePickerFragment;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;

public class CreateEventDataActivity extends AppCompatActivity {

    EditText inputEvent_name_Box;
    EditText inputStart_date_box;
    EditText inputStart_time_box;
    EditText inputStart_street_address_box;
    EditText inputStart_city_box;
    EditText inputStart_state_box;
    EditText inputStart_zipcode_box;
    EditText inputNumberOfSeats_box;
    public Event tempEvent;
    private int start_year, start_month, start_day;
    private int start_hour, start_minute;
    private String login;
    private String password;
    String searchAddress;
    private boolean startDateOk,startTimeOk,startLocationOk,numberOfSeatsOk,eventNameOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempEvent = getIntent().getParcelableExtra("tempEvent");
        // Get the view from the create event data activity
        setContentView(R.layout.activity_create_event_data);

        //Text Boxes assigned
        inputEvent_name_Box = (EditText) findViewById(R.id.event_name_box);
        inputStart_date_box = (EditText) findViewById(R.id.start_date_box);
        inputStart_time_box = (EditText) findViewById(R.id.start_time_box);
        inputStart_street_address_box = (EditText) findViewById(R.id.start_street_address_box);
        inputStart_city_box = (EditText) findViewById(R.id.start_city_box);
        inputStart_state_box = (EditText) findViewById(R.id.start_state_box);
        inputStart_zipcode_box = (EditText) findViewById(R.id.start_zipcode_box);
        inputNumberOfSeats_box = (EditText) findViewById(R.id.numberSeatsAvailable);

        //restrict entry to the pickers
        inputStart_date_box.setKeyListener(null);
        inputStart_time_box.setKeyListener(null);

        // Assign a click listener for the start time entry box
        findViewById(R.id.start_time_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        // Assign a click listener for the start date entry box
        findViewById(R.id.start_date_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }

        });
        // Cancel Button action
        cancelOnButtonClick();
       // Next button action
        nextOnButtonClick();
    }
    // Invokes the DatePicker Fragment
    private void showDatePicker(){
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into Dialog
         */
        //ToDo prevent going backward in time
        Calendar calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "DatePicker");
    }
    // Invokes the TimePicker Fragment
    private void showTimePicker(){
        TimePickerFragment time = new TimePickerFragment();
        /**
         * Set Up Current Time Into Dialog
         */
        //ToDo prevent going backward in date
        final Calendar c = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("hour", c.get(Calendar.HOUR_OF_DAY));
        args.putInt("minute", c.get(Calendar.MINUTE));
        time.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        time.setCallBack(ontime);
        time.show(getSupportFragmentManager(), "TimePicker");
    }

    private final DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            start_year = year;
            start_month = (monthOfYear + 1);
            start_day = dayOfMonth;

            inputStart_date_box = (EditText)(findViewById(R.id.start_date_box));
            if((start_month < 10)&&(start_day < 10))  { inputStart_date_box.setText(start_year +"-0"+ start_month + "-0" + start_day);}
            if((start_month < 10) &&(start_day >= 10)){ inputStart_date_box.setText(start_year +"-0"+ start_month + "-" + start_day);}
            if((start_month >= 10)&&(start_day < 10)) { inputStart_date_box.setText(start_year +"-"+ start_month + "-0" + start_day);}
            if((start_month >= 10)&&(start_day >= 10)){ inputStart_date_box.setText(start_year +"-"+ start_month + "-" + start_day);}
        }
    };
    TimePickerDialog.OnTimeSetListener ontime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            start_hour = hour;
            start_minute = minute;
            inputStart_time_box = (EditText) (findViewById(R.id.start_time_box));
            if (start_minute < 10) {
                inputStart_time_box.setText(start_hour + ":0" + start_minute);
            }
            else  {inputStart_time_box.setText(start_hour + ":" + start_minute);}
        }

    };
    protected String doInBackground(){
        SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE); // specify SharedPreferences for a private file named "hovhelper"
        login = pref.getString("LOGIN", "");                                              // key/value, get value for key "LOGIN"
        password = pref.getString("PASSWORD", "");                                        // key/value, get value for key "PASSWORD" (currently encrypted)
        //Encryption decryption = Encryption.getDefault("Key", "Salt", new byte[16]);     // class to encrypt/decrypt strings, see NOTE
        //String decryptPw = decryption.decryptOrNull(password);                          // get password after decrypting

        return null;
    }
    private void nextOnButtonClick(){
        Button next_btn = (Button) findViewById(R.id.Next_btn);
        next_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Grab user login Id
                        tempEvent.loginId = login;
                        //Check Start Date
                        if (TextUtils.isEmpty(inputStart_date_box.getText())) {
                            startDateOk = false;
                            Toast toast = Toast.makeText(getApplicationContext(), "Please provide a start date.", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            startDateOk = true;
                        }

                        //Check Start Time
                        if (TextUtils.isEmpty(inputStart_time_box.getText())) {
                            startTimeOk = false;
                            Toast toast = Toast.makeText(getApplicationContext(), "Please provide a start time.", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            startTimeOk = true;
                        }

                        //Check Event name
                        if (TextUtils.isEmpty(inputEvent_name_Box.getText())) {
                            eventNameOk = false;
                            Toast toast = Toast.makeText(getApplicationContext(), "Please provide an event name.", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            eventNameOk = true;
                        }

                        //Address loading of geo coordinates
                        searchAddress = inputStart_street_address_box.getText().toString() + " " + inputStart_city_box.getText().toString() + ", " + inputStart_state_box.getText().toString() + " " + inputStart_zipcode_box.getText().toString();
                        LatLng startResults = MapController.getGeoCoordinateFromAddress(getApplicationContext(), searchAddress);

                        if (startResults.latitude != 0.0) {
                            startLocationOk = true;
                        } else {
                            inputStart_street_address_box.setText("");
                            inputStart_city_box.setText("");
                            inputStart_state_box.setText("");
                            inputStart_zipcode_box.setText("");
                            startLocationOk = false;
                            Toast toast = Toast.makeText(getApplicationContext(), "Address not found, try again.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        int tempNumberOfSeats=0;
                        // Check Number of Seats (Available or Requested)
                        if(TextUtils.isEmpty(inputNumberOfSeats_box.getText())){
                            numberOfSeatsOk = false;
                            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid number of available (or requested) seats.", Toast.LENGTH_SHORT);
                               toast.show();
                        } else {
                            tempNumberOfSeats = Integer.parseInt(inputNumberOfSeats_box.getText().toString()) ;
                            if(tempNumberOfSeats <=0) {numberOfSeatsOk = false;}
                            else {numberOfSeatsOk=true;}}

                        //This tests to see if all of the entries are completed
                        if (eventNameOk && startDateOk && startTimeOk && startLocationOk && numberOfSeatsOk) {

                            tempEvent.eventName = inputEvent_name_Box.getText().toString();
                            tempEvent.start_Time = inputStart_date_box.getText().toString() + " " + inputStart_time_box.getText().toString() + ":00";
                            tempEvent.startLatitude = startResults.latitude;
                            tempEvent.startLongitude = startResults.longitude;
                            tempEvent.numberSeats = tempNumberOfSeats;
                            //sets number available equal to the number of seats
                            tempEvent.numberAvailable = tempNumberOfSeats;

                            Intent i = new Intent(getApplicationContext(), CreateEventDataEndActivity.class);
                            i.putExtra("tempEvent", tempEvent);
                            startActivity(i);

                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Ensure that data is entered in all fields.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
    }
    private void cancelOnButtonClick() {
        Button cancel_btn = (Button) findViewById(R.id.Cancel_btn);
        cancel_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ToDo destroy the Event object created
                        Intent i = new Intent(getApplicationContext(), MainEventActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
        );
    }
    // ACTION BAR ITEMS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_notify:
                Intent notify = new Intent(getApplicationContext(), RequestedEventsActivity.class);
                startActivity(notify);
                finish();
                return true;
            case R.id.action_profile:
                Intent profile = new Intent(getApplicationContext(), ViewProfileActivity.class);
                startActivity(profile);
                finish();
                return true;
            case R.id.action_event:
                Intent event = new Intent(getApplicationContext(), MainEventActivity.class);
                startActivity(event);
                finish();
                return true;
            case R.id.action_search:
                Intent search = new Intent(getApplicationContext(), SearchEventActivity.class);
                startActivity(search);
                finish();
                return true;
            case R.id.action_change_password:
                Intent changePassword = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(changePassword);
                finish();
                return true;
            case R.id.action_sign_out:
                // delete credentials file
                SharedPreferences pref = this.getSharedPreferences("hovhelper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                Intent signOut = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signOut);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
