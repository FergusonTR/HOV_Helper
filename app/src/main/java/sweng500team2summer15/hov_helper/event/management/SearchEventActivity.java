package sweng500team2summer15.hov_helper.event.management;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import sweng500team2summer15.hov_helper.JSONParser;
import java.util.Calendar;
import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ViewProfileActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.eventdisplay.RequestedEventsActivity;
import sweng500team2summer15.hov_helper.eventdisplay.SearchResultActivity;
import sweng500team2summer15.hov_helper.map.MapController;
import sweng500team2summer15.hov_helper.resource.CheckNetwork;
import sweng500team2summer15.hov_helper.resource.Encryption;
import sweng500team2summer15.hov_helper.resource.dialog.DatePickerFragment;

public class SearchEventActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    SeekBar startSeekBar;
    SeekBar endSeekBar;

    TextView startProgressText;
    TextView endProgressText;
    TextView startTrackingText;
    TextView endTrackingText;

    EditText inputStartSearchAddress;
    EditText inputStartSearchCity;
    EditText inputStartSearchState;
    EditText inputStartSearchZipCode;
    EditText inputStartSearchDistance;
    EditText inputStartSearchDate;

    EditText inputEndSearchAddress;
    EditText inputEndSearchCity;
    EditText inputEndSearchState;
    EditText inputEndSearchZipCode;
    EditText inputEndSearchDistance;

    ImageButton RideSearchBtn;
    ImageButton DriveSearchBtn;

    String login;
    String password;
    String startSearchAddress, startSearchCity, startSearchState, startSearchZipCode;
    String endSearchAddress, endSearchCity, endSearchState, endSearchZipCode;
    Double startSearchDistance, endSearchDistance;
    String searchDate;

    boolean DateOk, startStreetOk, startCityOk,startStateOk, startzipcodeOK;
    boolean endStreetOk, endCityOk,endStateOk, endzipcodeOK;
    String inputEventType="";
    int SearchResult;
    Toast toast;

    final JSONParser jsonParser = new JSONParser();
    private int start_year, start_month, start_day;
    EventList myEventList = new EventList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);
        //Binding boxes
        inputStartSearchAddress =(EditText) findViewById(R.id.Start_Search_Street_Box);
        inputStartSearchCity =(EditText)findViewById(R.id.Start_Search_City_Box);
        inputStartSearchState = (EditText) findViewById(R.id.Start_Search_State_Box);
        inputStartSearchZipCode = (EditText) findViewById(R.id.Start_Search_ZipCode_Box);
        //inputStartSearchDistance = (EditText) findViewById(R.id.StartSearchDistance);
        inputStartSearchDate = (EditText) findViewById(R.id.Start_Search_Date_Box);
        inputEndSearchAddress = (EditText)findViewById(R.id.End_Search_Street_Box);
        inputEndSearchCity = (EditText) findViewById (R.id.End_Search_City_Box);
        inputEndSearchState = (EditText) findViewById(R.id.End_Search_State_Box);
        inputEndSearchZipCode = (EditText) findViewById (R.id.End_Search_ZipCode_Box);
        //inputEndSearchDistance = (EditText) findViewById(R.id.EndSearchDistance);

        //Setup buttons
        RideSearchBtn = (ImageButton) findViewById(R.id.Ride_search_btn);
        DriveSearchBtn = (ImageButton) findViewById(R.id.Drive_Search_btn);

        //Start and End Distance Seekbars
        startSeekBar = (SeekBar)findViewById(R.id.StartSearchDistanceBar);
        endSeekBar =(SeekBar)findViewById(R.id.EndSearchDistanceBar);
        startProgressText = (TextView)findViewById(R.id.startProgressText);
        endProgressText =(TextView) findViewById(R.id.EndProgressText);

        startSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                startProgressText.setText(progress + " " + getString(R.string.miles_from_start));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        endSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                endProgressText.setText(progress + " " + getString(R.string.miles_from_end));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Assign a click listener for the start date entry box
        findViewById(R.id.Start_Search_Date_Box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }

        });




        RideSearchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Capturing status
                startSearchAddress = inputStartSearchAddress.getText().toString();
                startSearchCity = inputStartSearchCity.getText().toString();
                startSearchState = inputStartSearchState.getText().toString();
                startSearchZipCode = inputStartSearchZipCode.getText().toString();
                searchDate = inputStartSearchState.getText().toString();
                endSearchAddress = inputEndSearchAddress.getText().toString();
                endSearchCity = inputEndSearchCity.getText().toString();
                endSearchState = inputEndSearchState.getText().toString();
                endSearchZipCode = inputEndSearchZipCode.getText().toString();
                Double SDistance = (double) startSeekBar.getProgress();
                Double EDistance = (double) endSeekBar.getProgress();
                startSearchDistance = Double.parseDouble(SDistance.toString());
                endSearchDistance = Double.parseDouble(EDistance.toString());

                if (TextUtils.isEmpty(inputStartSearchDate.getText())) {
                    DateOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a start date.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    DateOk = true;
                }

                if (TextUtils.isEmpty((inputStartSearchAddress.getText()))) {
                    startStreetOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a start address.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startStreetOk = true;
                }
                if (TextUtils.isEmpty(inputStartSearchCity.getText())) {
                    startCityOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a start city.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startCityOk = true;
                }

                if (TextUtils.isEmpty(inputStartSearchState.getText())) {
                    startStateOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a start state.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startStateOk = true;
                }

                if (TextUtils.isEmpty(inputStartSearchZipCode.getText())) {
                    startzipcodeOK = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a start zipcode.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startzipcodeOK = true;
                }

                if (TextUtils.isEmpty((inputEndSearchAddress.getText()))) {
                    endStreetOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide an end address.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    endStreetOk = true;
                }

                if (TextUtils.isEmpty(inputEndSearchCity.getText())) {
                    endCityOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide an end city.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    endCityOk = true;
                }

                if (TextUtils.isEmpty(inputEndSearchState.getText())) {
                    endStateOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide an end state.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    endStateOk = true;
                }

                if (TextUtils.isEmpty(inputEndSearchZipCode.getText())) {
                    endzipcodeOK = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a end zipcode.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    endzipcodeOK = true;
                }

                if (startStreetOk && startCityOk && startStateOk && startzipcodeOK && endStreetOk && endStateOk && endzipcodeOK && DateOk) {

                    //Check network status
                    if (CheckNetwork.isNetworkAvailable(SearchEventActivity.this)) {
                        inputEventType = "Drive";
                        //Check that fields are complete
                        new searchEvent().execute();
                        //grab results
                        //forward as an arrayList to the next screen
                    } else
                        toast = Toast.makeText(getApplicationContext(), "Network unavailable.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    toast = Toast.makeText(getApplicationContext(), "Ensure all search fields are complete.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        DriveSearchBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //Capturing status
                startSearchAddress = inputStartSearchAddress.getText().toString();
                startSearchCity = inputStartSearchCity.getText().toString();
                startSearchState = inputStartSearchState.getText().toString();
                startSearchZipCode = inputStartSearchZipCode.getText().toString();
                searchDate = inputStartSearchDate.getText().toString();
                endSearchAddress = inputEndSearchAddress.getText().toString();
                endSearchCity = inputEndSearchCity.getText().toString();
                endSearchState = inputEndSearchState.getText().toString();
                endSearchZipCode = inputEndSearchZipCode.getText().toString();
                Double SDistance = (double)startSeekBar.getProgress();
                Double EDistance = (double)endSeekBar.getProgress();
                startSearchDistance = Double.parseDouble(SDistance.toString());
                endSearchDistance = Double.parseDouble(EDistance.toString());


                if (TextUtils.isEmpty(inputStartSearchDate.getText())) {
                    DateOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a start date.", Toast.LENGTH_SHORT);
                    toast.show();
                } else { DateOk = true; }

                if (TextUtils.isEmpty((inputStartSearchAddress.getText()))) {
                    startStreetOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a start address.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startStreetOk = true;
                }
                if (TextUtils.isEmpty(inputStartSearchCity.getText())) {
                    startCityOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a start city.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startCityOk = true;
                }

                if (TextUtils.isEmpty(inputStartSearchState.getText())) {
                    startStateOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a start state.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startStateOk = true;
                }

                if (TextUtils.isEmpty(inputStartSearchZipCode.getText())) {
                    startzipcodeOK = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a start zipcode.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startzipcodeOK = true;
                }

                if (TextUtils.isEmpty((inputEndSearchAddress.getText()))) {
                    endStreetOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide an end address.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    endStreetOk = true;
                }

                if (TextUtils.isEmpty(inputEndSearchCity.getText())) {
                    endCityOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide an end city.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    endCityOk = true;
                }

                if (TextUtils.isEmpty(inputEndSearchState.getText())) {
                    endStateOk = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide an end state.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    endStateOk = true;
                }

                if (TextUtils.isEmpty(inputEndSearchZipCode.getText())) {
                    endzipcodeOK = false;
                    toast = Toast.makeText(getApplicationContext(), "Please provide a end zipcode.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    endzipcodeOK = true;
                }

                if (startStreetOk && startCityOk && startStateOk && startzipcodeOK && endStreetOk && endStateOk && endzipcodeOK && DateOk) {

                    //Check network status
                    if (CheckNetwork.isNetworkAvailable(SearchEventActivity.this)) {
                        inputEventType = "Ride";
                        //Check that fields are complete
                        new searchEvent().execute();
                        //grab results
                        //forward as an arrayList to the next screen
                    } else{
                    toast = Toast.makeText(getApplicationContext(), "Network unavailable.", Toast.LENGTH_SHORT);
                    toast.show();}
                } else {
                    toast = Toast.makeText(getApplicationContext(), "Ensure all search fields are complete.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });}
    private void showDatePicker(){
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into Dialog
         */

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
    private final DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            start_year = year;
            start_month = (monthOfYear + 1);
            start_day = dayOfMonth;

            inputStartSearchDate = (EditText)(findViewById(R.id.Start_Search_Date_Box));
            if((start_month < 10)&&(start_day < 10))  { inputStartSearchDate.setText(start_year +"-0"+ start_month + "-0" + start_day);}
            if((start_month < 10) &&(start_day >= 10)){ inputStartSearchDate.setText(start_year +"-0"+ start_month + "-" + start_day);}
            if((start_month >= 10)&&(start_day < 10)) { inputStartSearchDate.setText(start_year +"-"+ start_month + "-0" + start_day);}
            if((start_month >= 10)&&(start_day >= 10)){ inputStartSearchDate.setText(start_year +"-"+ start_month + "-" + start_day);}
        }
    };
class searchEvent extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(SearchEventActivity.this);
        pDialog.setMessage("Searching for Events..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE); // specify SharedPreferences for a private file named "hovhelper"
        login = pref.getString("LOGIN", "");                                              // key/value, get value for key "LOGIN"
        password = pref.getString("PASSWORD", "");                                        // key/value, get value for key "PASSWORD" (currently encrypted)
        //Encryption decryption = Encryption.getDefault("Key", "Salt", new byte[16]);       // class to encrypt/decrypt strings, see NOTE
        //String decryptPw = decryption.decryptOrNull(password);                            // get password after decrypting

        String startLocation;
        String endLocation;
        //Address loading of geo coordinates
        startLocation = startSearchAddress + " " + startSearchCity + ", " + startSearchState + " " + startSearchZipCode;
        LatLng startResults = MapController.getGeoCoordinateFromAddress(getApplicationContext(), startLocation);

        //Address loading of geo coordinates
        endLocation = endSearchAddress + " " + endSearchCity + ", " + endSearchState + " " + endSearchZipCode;
        LatLng endResults = MapController.getGeoCoordinateFromAddress(getApplicationContext(), endLocation);

        Double startLatitude = startResults.latitude;
        Double startLongitude = startResults.longitude;

        Double endLatitude = endResults.latitude;
        Double endLongitude = endResults.longitude;

         myEventList.arrayListOfEvents = myEventList.searchByDistance(searchDate, startLatitude,
                startLongitude,endLatitude,endLongitude,startSearchDistance,endSearchDistance,inputEventType);

        SearchResult = myEventList.arrayListOfEvents.size();

        return null;
        }

    protected void onPostExecute(String file_url){
        pDialog.dismiss();
        if (SearchResult > 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Results Found.", Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
            i.putParcelableArrayListExtra("eventList",myEventList.arrayListOfEvents);
            startActivity(i);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No Events found, please try searching again", Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(getApplicationContext(), SearchEventActivity.class);
            startActivity(i);
            finish();
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
