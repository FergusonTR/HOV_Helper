package sweng500team2summer15.hov_helper.event.management;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ProfileManagement;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.eventdisplay.RequestedEventsActivity;
import sweng500team2summer15.hov_helper.map.MapController;
import sweng500team2summer15.hov_helper.map.MapsActivity;
import sweng500team2summer15.hov_helper.resource.Encryption;
import sweng500team2summer15.hov_helper.resource.dialog.DatePickerFragment;
import sweng500team2summer15.hov_helper.resource.dialog.TimePickerFragment;

public class CreateEventDataEndActivity extends AppCompatActivity {

    //Progress Dialog
    private ProgressDialog pDialog;

    EditText inputEnd_date_box;
    EditText inputEnd_time_box;
    EditText inputEnd_street_address_box;
    EditText inputEnd_city_box;
    EditText inputEnd_state_box;
    EditText inputEnd_zipcode_box;
    CheckBox inputEnd_roundTrip_checbox;

    private int end_year, end_month, end_day;
    private int end_hour, end_minute;
    private boolean endDateOk,endTimeOk,endLocationOk;
    private String login;
    private String password;
    public int EventResult = 0;
    public Event tempEvent;
    String searchAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         // Get the view from the create event data activity

        setContentView(R.layout.activity_create_event_data_end);
        tempEvent = getIntent().getParcelableExtra("tempEvent");
        //Assign Text Boxes
        inputEnd_date_box = (EditText) findViewById(R.id.end_date_box);
        inputEnd_time_box = (EditText) findViewById(R.id.end_time_box);
        inputEnd_street_address_box = (EditText) findViewById(R.id.end_street_address_box);
        inputEnd_city_box = (EditText) findViewById(R.id.end_city_box);
        inputEnd_state_box = (EditText) findViewById(R.id.end_state_box);
        inputEnd_zipcode_box = (EditText) findViewById(R.id.end_zipcode_box);
        inputEnd_roundTrip_checbox = (CheckBox) findViewById(R.id.roundTrip);
;
        //restrict entry to the pickers
        inputEnd_date_box.setKeyListener(null);
        inputEnd_time_box.setKeyListener(null);

        // Assign a click listener for the start time entry box
        findViewById(R.id.end_time_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        // Assign a click listener for the start date entry box
        findViewById(R.id.end_date_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }

        });

        // Cancel Button action
        cancelOnButtonClick();
        // Create button action
        createOnButtonClick();
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
            end_year = year;
            end_month = (monthOfYear + 1);
            end_day = dayOfMonth;

            inputEnd_date_box = (EditText)(findViewById(R.id.end_date_box));
            if((end_month < 10)&&(end_day < 10))  { inputEnd_date_box.setText(end_year +"-0"+ end_month + "-0" + end_day);}
            if((end_month < 10) &&(end_day >= 10)){ inputEnd_date_box.setText(end_year +"-0"+ end_month + "-" + end_day);}
            if((end_month >= 10)&&(end_day < 10)) { inputEnd_date_box.setText(end_year +"-"+ end_month + "-0" + end_day);}
            if((end_month >= 10)&&(end_day >= 10)){ inputEnd_date_box.setText(end_year +"-"+ end_month + "-" + end_day);}
        }
    };
    TimePickerDialog.OnTimeSetListener ontime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            end_hour = hour;
            end_minute = minute;
            inputEnd_time_box = (EditText) (findViewById(R.id.end_time_box));
            if (end_minute < 10) {
                inputEnd_time_box.setText(end_hour + ":0" + end_minute);
            }
            else  {inputEnd_time_box.setText(end_hour + ":" + end_minute);}
        }

    };
    private void createOnButtonClick(){
        Button create_btn = (Button) findViewById(R.id.Create_btn);
        create_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Check End Date
                        if (TextUtils.isEmpty(inputEnd_date_box.getText()))
                        {
                            endDateOk = false;
                            Toast toast = Toast.makeText(getApplicationContext(), "Please provide a start date.", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            endDateOk = true;
                        }

                        //Check Start Time
                        if (TextUtils.isEmpty(inputEnd_time_box.getText())) {
                            endTimeOk = false;
                            Toast toast = Toast.makeText(getApplicationContext(), "Please provide a start time.", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            endTimeOk = true;
                        }

                        //ToDo add for checking to ensure that Start Time is Before End Time.

                        //Address loading of geo coordinates
                        searchAddress = inputEnd_street_address_box.getText().toString() + " " + inputEnd_city_box.getText().toString() + ", " + inputEnd_state_box.getText().toString() + " " + inputEnd_zipcode_box.getText().toString();
                        LatLng startResults = MapController.getGeoCoordinateFromAddress(getApplicationContext(), searchAddress);

                        if (startResults.latitude != 0.0) {
                            endLocationOk = true;
                        } else {
                            inputEnd_street_address_box.setText("");
                            inputEnd_city_box.setText("");
                            inputEnd_state_box.setText("");
                            inputEnd_zipcode_box.setText("");
                            endLocationOk = false;

                            Toast toast = Toast.makeText(getApplicationContext(), "Address not found, try again.", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        if (endDateOk && endTimeOk && endLocationOk) {

                            tempEvent.end_Time = inputEnd_date_box.getText().toString() + " " + inputEnd_time_box.getText().toString() + ":00";
                            tempEvent.endLatitude = startResults.latitude;
                            tempEvent.endLongitude = startResults.longitude;
                            if(inputEnd_roundTrip_checbox.isChecked()){
                            tempEvent.returnTrip = "yes";
                            }
                            else{
                                tempEvent.returnTrip ="no";
                            }

                            //Launch the background task that sends data to the database
                            new CreateNewEvent().execute();
                        }  else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Ensure that data is entered in all fields.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
        );
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
                    }
                }
        );
    }
    class CreateNewEvent extends AsyncTask<String, String, String> {
        /**
         * Before Starting background thread show progress dialog on screen
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateEventDataEndActivity.this);
            pDialog.setMessage("Creating Event..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE); // specify SharedPreferences for a private file named "hovhelper"
            login = pref.getString("LOGIN", "");                                              // key/value, get value for key "LOGIN"
            password = pref.getString("PASSWORD", "");                                        // key/value, get value for key "PASSWORD" (currently encrypted)
            Encryption decryption = Encryption.getDefault("Key", "Salt", new byte[16]);     // class to encrypt/decrypt strings, see NOTE
            String decryptPw = decryption.decryptOrNull(password);                          // get password after decrypting
            tempEvent.loginId = login;
            EventResult = tempEvent.create(login, password);
        return null;
        }

        protected void onPostExecute(String file_url){
            pDialog.dismiss();
            if (EventResult != 0) {
                Toast toast = Toast.makeText(getApplicationContext(), "Event Created.", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(getApplicationContext(), MainEventActivity.class);
                startActivity(i);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Failed to Create Event, Start Over", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(getApplicationContext(), CreateEventDataActivity.class);
                startActivity(i);
            }

        }

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
                Intent profile = new Intent(getApplicationContext(), ProfileManagement.class);
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