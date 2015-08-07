package sweng500team2summer15.hov_helper.event.management;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import sweng500team2summer15.hov_helper.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ProfileManagement;
import sweng500team2summer15.hov_helper.Profile.ViewProfileActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.eventdisplay.RequestedEventsActivity;
import sweng500team2summer15.hov_helper.eventdisplay.SearchResultActivity;
import sweng500team2summer15.hov_helper.map.MapController;
import sweng500team2summer15.hov_helper.map.MapsActivity;
import sweng500team2summer15.hov_helper.resource.CheckNetwork;
import sweng500team2summer15.hov_helper.resource.Encryption;
import sweng500team2summer15.hov_helper.resource.dialog.DatePickerFragment;

public class SearchEventActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

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

    String inputEventType="";
    int SearchResult;
    final JSONParser jsonParser = new JSONParser();

    private int start_year, start_month, start_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);
        //Binding boxes
        inputStartSearchAddress =(EditText) findViewById(R.id.Start_Search_Street_Box);
        inputStartSearchCity =(EditText)findViewById(R.id.Start_Search_City_Box);
        inputStartSearchState = (EditText) findViewById(R.id.Start_Search_State_Box);
        inputStartSearchZipCode = (EditText) findViewById(R.id.Start_Search_ZipCode_Box);
        inputStartSearchDistance = (EditText) findViewById(R.id.Start_Search_Date_Box);
        inputStartSearchDate = (EditText) findViewById(R.id.Start_Search_Date_Box);
        inputEndSearchAddress = (EditText)findViewById(R.id.End_Search_Street_Box);
        inputEndSearchCity = (EditText) findViewById (R.id.End_Search_City_Box);
        inputEndSearchState = (EditText) findViewById(R.id.End_Search_State_Box);
        inputEndSearchZipCode = (EditText) findViewById (R.id.End_Search_ZipCode_Box);
        inputEndSearchDistance = (EditText) findViewById(R.id.EndSearchDistance);

        //Setup buttons
        RideSearchBtn = (ImageButton) findViewById(R.id.Ride_search_btn);
        DriveSearchBtn = (ImageButton) findViewById(R.id.Drive_Search_btn);

        // Assign a click listener for the start date entry box
        findViewById(R.id.Start_Search_Date_Box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }

        });


        RideSearchBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
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
                startSearchDistance = Double.parseDouble(inputStartSearchDistance.getText().toString());
                endSearchDistance = Double.parseDouble(inputEndSearchDistance.getText().toString());


                //Check network status
                if (CheckNetwork.isNetworkAvailable(SearchEventActivity.this)){
                    inputEventType = "Ride";
                    //Check that fields are complete
                    new searchEvent().execute();
                    //grab results
                    //forward as an arrayList to the next screen
                }

            }});

        DriveSearchBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
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
                startSearchDistance = Double.parseDouble(inputStartSearchDistance.getText().toString());
                endSearchDistance = Double.parseDouble(inputEndSearchDistance.getText().toString());

                //Check network status
                if (CheckNetwork.isNetworkAvailable(SearchEventActivity.this)) {
                    inputEventType = "Drive";
                    //Check that fields are complete
                    new searchEvent().execute();
                    //grab results
                    //forward as an arrayList to the next screen
                }
            }});
    }
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
        Encryption decryption = Encryption.getDefault("Key", "Salt", new byte[16]);       // class to encrypt/decrypt strings, see NOTE
        String decryptPw = decryption.decryptOrNull(password);                            // get password after decrypting

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

        String start_time = searchDate + " 00:00:00";
        String end_time = searchDate + " 23:59:59";


        // url to search events
        String url_create_event = "http://www.hovhelper.com/search_event.php";

        // JSON Node names
        String TAG_SUCCESS = "success";

        // Building Parameters
        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> JSONparams = new ArrayList<NameValuePair>();
        JSONparams.add(new BasicNameValuePair("startTime", start_time));
        JSONparams.add(new BasicNameValuePair("endTime", end_time));
        JSONparams.add(new BasicNameValuePair("startLatitude", startLatitude.toString()));
        JSONparams.add(new BasicNameValuePair("startLongitude", startLongitude.toString()));
        JSONparams.add(new BasicNameValuePair("endLatitude", endLatitude.toString()));
        JSONparams.add(new BasicNameValuePair("endLongitude", endLongitude.toString()));
        JSONparams.add(new BasicNameValuePair("startDistance", startSearchDistance.toString()));
        JSONparams.add(new BasicNameValuePair("endDistance", endSearchDistance.toString()));
        JSONparams.add(new BasicNameValuePair("eventType", inputEventType));

        // getting JSON Object
        // Note that create event url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_event, "POST", JSONparams);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully found events
                //this.eventId = json.getInt("event");
                SearchResult = 1;


            } else {
                // failed to create event
                SearchResult = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    return null;}

    protected void onPostExecute(String file_url){
        pDialog.dismiss();
        if (SearchResult != 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Results Found.", Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
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
