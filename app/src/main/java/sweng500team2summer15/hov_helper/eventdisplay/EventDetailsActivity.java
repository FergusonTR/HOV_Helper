package sweng500team2summer15.hov_helper.eventdisplay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.AsyncTask;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ViewProfileActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.Event;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;
import sweng500team2summer15.hov_helper.event.management.SearchEventActivity;
import sweng500team2summer15.hov_helper.event.management.UserInEvent;
import sweng500team2summer15.hov_helper.map.MapController;
import sweng500team2summer15.hov_helper.map.MapsActivity;

/**
 * Created by Steve on 7/30/2015.
 */
public class EventDetailsActivity extends AppCompatActivity {
    public static final String TAG = EventDetailsActivity.class.getSimpleName();
    //Progress Dialog
    private ProgressDialog pDialog;
    private Event event;
    public static Map<Integer, Boolean> rideRequested = new HashMap<Integer, Boolean>();
    String loginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // get the event passed into this activity
        event = (Event)getIntent().getParcelableExtra("eventForDetails");

        final Button offerButton = (Button) findViewById(R.id.btnRequestRide);

        if (event.eventType.equals("Ride"))
        {
            offerButton.setText("Offer Ride");
        }
        else
        {
            offerButton.setText("Request Ride");
        }

        // setup widget labels depending on if ride or share event
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(event.eventType + " Event Details");
        final Button mapButton = (Button) findViewById(R.id.btnMapRoute);

        // populate text fields

        // Convert Date
        // date
        String date = event.start_Time;
        date = date.substring(0,16);
        String year = date.substring(0,4);
        String monthDay = date.substring(6,10);

        TextView departTime = (TextView) findViewById(R.id.txtDepartTime);
        departTime.setText(date);

        TextView departAddress = (TextView) findViewById(R.id.txtDepartAddress);
        Address startAddress = MapController.getStreetAddressFromLatLon(this, event.startLatitude, event.startLongitude);
        if (startAddress != null)
        {
            departAddress.setText(startAddress.getAddressLine(1) + " " + startAddress.getAddressLine(2));
        }

        String arriveTime = event.start_Time;
        arriveTime = arriveTime.substring(0,16);
        String arriveUear = arriveTime.substring(0,4);
        String arriveMonthDay = arriveTime.substring(6, 10);

        TextView arrivalTime = (TextView) findViewById(R.id.txtArrivalTime);
        arrivalTime.setText(arriveTime);

        TextView arrivalAddress = (TextView) findViewById(R.id.txtArrivalAddress);
        Address arriveAddress = MapController.getStreetAddressFromLatLon(this, event.endLatitude, event.endLongitude);
        if (arrivalAddress != null)
        {
            arrivalAddress.setText(arriveAddress.getAddressLine(1) + " " + arriveAddress.getAddressLine(2));
        }

        // Distance
        TextView distance = (TextView) findViewById(R.id.textViewDistance);
        // convert to two decimals
        Double inDistance = event.endSearchDistance;
        if (inDistance > 0.0)
        {
            DecimalFormat df = new DecimalFormat("#.00");
            String formattedDistance = df.format(inDistance);
            String distanceString = String.valueOf(formattedDistance);
            distance.setText("Distance: " + distanceString);
        }
        else
        {
            distance.setText("");
        }

        // Number of seats
        TextView numberOfSeats = (TextView) findViewById(R.id.txtBoxAvailableSeats);
        numberOfSeats.setText(String.valueOf(event.numberAvailable));

        // populate picture
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        if (event.eventType.equals("Ride"))
        {
            imageView.setImageResource(R.drawable.hitchhiking_ride);
        }
        else
        {
            imageView.setImageResource(R.drawable.hov_helper_logo);
        }



        // handle the map button click
        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double startLat = event.startLatitude;
                double startLon = event.startLongitude;
                double endLat = event.endLatitude;
                double endLon = event.endLongitude;
                showRouteOnMapActivity(startLat, startLon, endLat, endLon);
            }
        });

        Boolean isRideRequested = this.rideRequested.get(event.eventId);


        if ((isRideRequested != null) && (isRideRequested.equals(true)))
        {
            Log.i(TAG, "Ride has been requested on Event: " + event.eventId + " DISABLE BUTTON");
            disableOfferButton();
        }

        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the login Id
                SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE); // specify SharedPreferences for a private file named "hovhelper"
                String login = pref.getString("LOGIN", "");                                       // key/value, get value for key "LOGIN"
                Log.i(TAG, "MY LOGIN ID: " + login);
                //password = pref.getString("PASSWORD", "");                                      // key/value, get value for key "PASSWORD" (currently encrypted)
                //Encryption decryption = Encryption.getDefault("Key", "Salt", new byte[16]);     // class to encrypt/decrypt strings, see NOTE
                //String decryptPw = decryption.decryptOrNull(password);
                UserInEvent newRideRequest = new UserInEvent();
                newRideRequest.eventId = event.eventId;
                newRideRequest.requestedParticipantLoginId = login;
                newRideRequest.requestStatus = "requested";
                new RequestOrOfferRide().execute(newRideRequest);
                disableOfferButton();
            }
        });
    }

    private void disableOfferButton()
    {
        final Button offerButton = (Button) findViewById(R.id.btnRequestRide);
        String buttonType = offerButton.getText().toString();
        if (buttonType.equals("Offer Ride"))
        {
            offerButton.setText("Offered");
        }
        else
        {
            offerButton.setText("Requested");
        }

        offerButton.setEnabled(false);
        rideRequested.put(event.eventId, true);
    }

    private void showRouteOnMapActivity(double startLat, double startLon, double endLat, double endLon)
    {
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);

        intent.putExtra("pickupLat", startLat);
        intent.putExtra("pickupLon", startLon);
        intent.putExtra("dropOffLat", endLat);
        intent.putExtra("dropOffLon", endLon);
        startActivity(intent);
    }

    /**
     * Background Async Task to Create new event
     * */
    class RequestOrOfferRide extends AsyncTask<UserInEvent, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EventDetailsActivity.this);
            pDialog.setMessage("Requesting or Offering Ride..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating event
         */
        protected String doInBackground(UserInEvent... args) {

            UserInEvent input = args[0];

            if (input != null)
            {
                String requestedParticipantLoginId = input.requestedParticipantLoginId;
                String requestStatus = input.requestStatus;
                int eventId = input.eventId;
                Log.i(TAG, "loginId: " + requestedParticipantLoginId + ", status: " + requestStatus + ", eventId: " + eventId);

                int success = input.create(requestedParticipantLoginId,eventId, requestStatus);
                Log.i(TAG, "SUCCESS: " + success);

            }
            else
            {
                Log.i(TAG, "DO IN BACKGROUND, INPUT IS NULL!!!");
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            //ToDo Open a new screen showing the Event Data with a button to view the event
            //   Intent i = new Intent(getApplicationContext(), MainEventActivity.class);
            //   startActivity(i);

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
