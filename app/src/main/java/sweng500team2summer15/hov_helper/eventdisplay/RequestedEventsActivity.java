package sweng500team2summer15.hov_helper.eventdisplay;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.UserInEvent;

/**
 * Created by Steve on 7/30/2015.
 */
public class RequestedEventsActivity extends ActionBarActivity implements ActionBar.TabListener {
    public static final String TAG = RequestedEventsActivity.class.getSimpleName();
    //Progress Dialog
    private ProgressDialog pDialog;

    private ArrayList<UserInEvent> arrayListOfEventRequests = new ArrayList<UserInEvent>();
    private ViewPager viewPager;
    private EventRequestsTabAdapter mAdapter;
    private ActionBar actionBar;

    // Tab titles
    private String[] tabs = {"Requested Rides", "Offered Rides"};

    public void updateListViews(ArrayList<UserInEvent> newList)
    {
        UserInEvent userInEvent = newList.get(0);
        Log.i(TAG, "LOGIN ID: " + userInEvent.requestedParticipantLoginId);
        Log.i(TAG, "EVENT ID: " + userInEvent.eventId);
        Log.i(TAG, "STATUS: " + userInEvent.requestStatus);
        arrayListOfEventRequests.clear();
        arrayListOfEventRequests.addAll(newList);
        //viewPager.invalidate();
        //arrayListOfEventRequests.addAll(newList);
        //mAdapter.setOfferedRidesFromEventArray(arrayListOfEventRequests);
        //mAdapter.setRequestedRidesFromEventArray(arrayListOfEventRequests);
        mAdapter.notifyDataSetChanged();
        //viewPager.refreshDrawableState();
    }

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
        arrayListOfEventRequests = myList;
        // ************* TEST ABOVE

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        mAdapter = new EventRequestsTabAdapter(getSupportFragmentManager());
        mAdapter.setOfferedRidesFromEventArray(arrayListOfEventRequests);
        mAdapter.setRequestedRidesFromEventArray(arrayListOfEventRequests);

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs)
        {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        new ReadRideRequests().execute("booby tester"); // TODO: Update. The parameter should be the loginId
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_requested_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public void setUsersInEvents(ArrayList<UserInEvent> arrayListOfEventRequests)
    {
        this.arrayListOfEventRequests = arrayListOfEventRequests;
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


            // get the list of rides
            UserInEvent userInEvent = new UserInEvent();
            String input = "\"booby tester\""; //TODO: Update. the parameter should be args[0]
            Log.i(TAG, "INPUT: " + input);
            List<UserInEvent> userInEventList = userInEvent.getRequestedRides(input);
            Log.i(TAG, "UserInEventList Size: " + userInEventList.size());

            if (!userInEventList.isEmpty())
            {
                publishProgress(new ArrayList(userInEventList));
            }
            else{
                Log.i(TAG, "User Event List is NULL!!!");
            }

            return null;
        }


        @Override
        protected void onProgressUpdate( ArrayList<UserInEvent>... update){
            super.onProgressUpdate(update);
            ArrayList<UserInEvent> listOfEvents = update[0];
            if (listOfEvents != null)
            {
                Log.i(TAG, "ON PROGRESS UPDATE. LIST SIZE: " + listOfEvents.size());
                updateListViews(update[0]);
                UserInEvent userInEvent = listOfEvents.get(0);
                Log.i(TAG, "LOGIN ID: " + userInEvent.requestedParticipantLoginId);
                Log.i(TAG, "EVENT ID: " + userInEvent.eventId);
                Log.i(TAG, "STATUS: " + userInEvent.requestStatus);
            }
            else
            {
                Log.i(TAG, "update[0] IS N U L L");
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
}
