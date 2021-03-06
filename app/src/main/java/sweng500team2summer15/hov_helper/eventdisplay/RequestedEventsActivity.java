package sweng500team2summer15.hov_helper.eventdisplay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ProfileManagement;
import sweng500team2summer15.hov_helper.Profile.ViewProfileActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;
import sweng500team2summer15.hov_helper.event.management.SearchEventActivity;
import sweng500team2summer15.hov_helper.event.management.UserInEvent;
import sweng500team2summer15.hov_helper.map.MapsActivity;

/**
 * Created by Steve on 7/30/2015.
 */
public class RequestedEventsActivity extends AppCompatActivity {
    public static final String TAG = RequestedEventsActivity.class.getSimpleName();
    //Progress Dialog
    private ProgressDialog pDialog;
    private EventRequestsTabAdapter mAdapter;
    private ViewPager viewPager;

    private ArrayList<UserInEvent> arrayListOfRequestedRides = new ArrayList<UserInEvent>();
    private ArrayList<UserInEvent> arrayListOfOfferedRides = new ArrayList<UserInEvent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_events);

        // ************** TEST BELOW
        ArrayList<UserInEvent> myList = new ArrayList<UserInEvent>();
        // populate with dummy data
        UserInEvent e1 = new UserInEvent();
        e1.requestStatus = "requested";
        e1.requestedParticipantLoginId = "mickey mouse";
        e1.eventId = 100;
        e1.eventType = "Ride";
        e1.startLatitude = 40.82;
        e1.startLongitude = -77.8561126;
        e1.endLatitude = 40.8122837;
        e1.endLongitude = -77.8561126;
        myList.add(e1);
        UserInEvent e2 = new UserInEvent();
        e2.requestStatus = "requested";
        e2.requestedParticipantLoginId = "mickey mouse";
        e2.eventId = 100;
        e2.eventType = "Drive";
        e2.startLatitude = 40.83;
        e2.startLongitude = -77.8561126;
        e2.endLatitude = 40.8122837;
        e2.endLongitude = -77.8561126;
        myList.add(e2);
        //arrayListOfEventRequests = myList;
        // ************* TEST ABOVE

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        // set adapter
        mAdapter = new EventRequestsTabAdapter(getSupportFragmentManager());
        mAdapter.setOfferedRidesFromEventArray(arrayListOfOfferedRides);
        mAdapter.setRequestedRidesFromEventArray(arrayListOfRequestedRides);
        viewPager.setAdapter(mAdapter);
        new ReadRideRequests().execute("booby tester"); // TODO: Update. The parameter should be the loginId
    }

    public void updateRequestedRidesListViews(ArrayList<UserInEvent> newList)
    {
        // Ride Requests
        if (!newList.isEmpty())
        {
            UserInEvent userInEvent = newList.get(0);
            Log.i(TAG, "LOGIN ID: " + userInEvent.requestedParticipantLoginId);
            Log.i(TAG, "EVENT ID: " + userInEvent.eventId);
            Log.i(TAG, "STATUS: " + userInEvent.requestStatus);
        }

        arrayListOfRequestedRides.clear();
        arrayListOfRequestedRides.addAll(newList);
        mAdapter.setRequestedRidesFromEventArray(arrayListOfRequestedRides);
    }

    public void updateOfferedRidesListViews(ArrayList<UserInEvent> newList)
    {
        // Drive Requests
        if (!newList.isEmpty())
        {
            UserInEvent userInEvent = newList.get(0);
            Log.i(TAG, "LOGIN ID: " + userInEvent.requestedParticipantLoginId);
            Log.i(TAG, "EVENT ID: " + userInEvent.eventId);
            Log.i(TAG, "STATUS: " + userInEvent.requestStatus);
        }
        arrayListOfOfferedRides.clear();
        arrayListOfOfferedRides.addAll(newList);
        mAdapter.setOfferedRidesFromEventArray(arrayListOfOfferedRides);
    }

    /**
     * Background Async Task to Create new event
     * */
    class ReadRideRequests extends AsyncTask<String, ArrayList<UserInEvent>, ArrayList<UserInEvent>> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RequestedEventsActivity.this);
            pDialog.setMessage("Reading Ride Requests..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * retrieving event
         */
        protected ArrayList<UserInEvent> doInBackground(String... args) {
            // get the login Id
            SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE); // specify SharedPreferences for a private file named "hovhelper"
            String login = pref.getString("LOGIN", "");                                       // key/value, get value for key "LOGIN"
            Log.i(TAG, "MY LOGIN ID: " + login);
            //password = pref.getString("PASSWORD", "");                                      // key/value, get value for key "PASSWORD" (currently encrypted)
            //Encryption decryption = Encryption.getDefault("Key", "Salt", new byte[16]);     // class to encrypt/decrypt strings, see NOTE
            //String decryptPw = decryption.decryptOrNull(password);                          // get password after decrypting

            // get the list of rides
            UserInEvent userInEvent = new UserInEvent();
            String input = login;//"\"booby tester\""; //TODO: Update. the parameter should be args[0]
            Log.i(TAG, "INPUT: " + input);
            int i = 3;
            // Get the list of requests I submitted
            List<UserInEvent> myUserInEventList = userInEvent.getMyRequestedRides("\""+input+"\"");
            // Determine if the request I made was for a ride or drive event and put in appropriate list
            List<UserInEvent> rideEventRequestList = new ArrayList<UserInEvent>();
            List<UserInEvent> driveEventRequestList = new ArrayList<UserInEvent>();
            for (UserInEvent event:myUserInEventList)
            {
                // The type of event is the event you were responding to
                if (event.eventType.equals("Ride"))
                {
                    // if you were responding to a ride event, you are offering to drive
                    driveEventRequestList.add(event);
                }
                else
                {
                    // if you were responding to a drive event, you were requesting to ride
                    rideEventRequestList.add(event);
                }
            }

            // Get the list of requests from other people requesting my event
            List<UserInEvent> userInEventList = userInEvent.getRequestedRides(input);
            List<UserInEvent> filterMeOutUserInEventList = userInEvent.getRequestedRides(input);

            for (UserInEvent event:userInEventList)
            {
                if (event.loginId.equals(login))
                {
                    filterMeOutUserInEventList.add(event);
                }
            }

            for (UserInEvent event:userInEventList)
            {
                // The type of event is the event they were responding to
                if (event.eventType.equals("Ride"))
                {
                    // if they were responding to your ride event, they were requesting a ride
                    rideEventRequestList.add(event);
                }
                else
                {
                    // if they were responding to a drive event, they were requesting to drive
                    driveEventRequestList.add(event);
                }
            }

            Log.i(TAG, "MyUsersInEventList Size: " + myUserInEventList.size());
            Log.i(TAG, "UserInEventList Size: " + userInEventList.size());

                // TODO: determine if we want to go with different tab view below for showing 'Ride Requests (ours and theirs) and Drive Requests (ours and theirs)
                // instead of current implentation of 'My Ride Requests' and 'Offered Ride Requests'
                // publishProgress(new ArrayList<UserInEvent>(rideEventRequestList), new ArrayList<UserInEvent>(driveEventRequestList));
                publishProgress(new ArrayList<UserInEvent>(myUserInEventList), new ArrayList<UserInEvent>(filterMeOutUserInEventList));


            return null;
        }

        @Override
        protected void onProgressUpdate( ArrayList<UserInEvent>... update){
            super.onProgressUpdate(update);
            ArrayList<UserInEvent> listOfMyRideRequests = update[0];
            ArrayList<UserInEvent> listOfRidesOffered = update[1];
            if (listOfMyRideRequests != null)
            {
                Log.i(TAG, "ON PROGRESS UPDATE. LIST SIZE: " + listOfMyRideRequests.size());
                updateRequestedRidesListViews(update[0]);
            }
            else
            {
                Log.i(TAG, "update[0] IS N U L L");
            }

            if (listOfRidesOffered != null)
            {
                Log.i(TAG, "ON PROGRESS UPDATE. LIST SIZE: " + listOfRidesOffered.size());
                updateOfferedRidesListViews(update[1]);
            }
            else
            {
                Log.i(TAG, "update[1] IS N U L L");
            }


        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        @Override
        protected void onPostExecute(ArrayList<UserInEvent> result) {
            // dismiss the dialog once done
            pDialog.dismiss();
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
