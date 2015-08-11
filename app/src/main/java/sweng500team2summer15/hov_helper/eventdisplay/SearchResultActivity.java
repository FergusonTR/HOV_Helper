package sweng500team2summer15.hov_helper.eventdisplay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ViewProfileActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.Event;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;
import sweng500team2summer15.hov_helper.event.management.SearchEventActivity;
import sweng500team2summer15.hov_helper.event.management.SwipableTabAdapter;


/**
 * Created by Steve on 6/30/2015.
 */
public class SearchResultActivity extends AppCompatActivity {
    public static final String TAG = SearchResultActivity.class.getSimpleName();
    private ArrayList<Event> arrayListOfEvents = new ArrayList<Event>();
    SwipableTabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // TODO: Remove Events Below after search result implemented. For Testing only
       // ArrayList<Event> myList = new ArrayList<Event>();
        // populate with dummy data
        /*Event e1 = new Event();
        e1.eventId = 1;
        e1.eventType = "Ride";
        e1.startLatitude = 40.82;
        e1.startLongitude = -77.8561126;
        e1.endLatitude = 40.8122837;
        e1.endLongitude = -77.8561126;
        e1.numberAvailable = 4;
        myList.add(e1);
        Event e2 = new Event();
        e2.eventId = 2;
        e2.eventType = "Drive";
        e2.startLatitude = 40.83;
        e2.startLongitude = -77.8561126;
        e2.endLatitude = 40.8122837;
        e2.endLongitude = -77.8561126;
        e2.numberAvailable = 2;
        myList.add(e2);*/

        // TODO: remove below line. This is an example of how another activity would pass in an array of events
        //getIntent().putExtra("eventList", myList);
        this.arrayListOfEvents =  getIntent().getParcelableArrayListExtra("eventList");
        if (this.arrayListOfEvents == null)
        {
            // create empty list
            this.arrayListOfEvents = new ArrayList<Event>();
        }

        Log.i(TAG, "NUMBER OF EVENTS FOUND: " + this.arrayListOfEvents.size());
        for (int i = 0; i < this.arrayListOfEvents.size(); i++)
        {
            Event event = this.arrayListOfEvents.get(i);
            System.out.println("[" + i +"]"+"START LAT: " + event.startLatitude);
            System.out.println("[" + i +"]"+"END LON: " + event.startLatitude);
            System.out.println("[" + i +"]"+"END LAT: " + event.endLatitude);
            System.out.println("[" + i +"]"+"END LON: " + event.endLatitude);
        }

        //Swipe pages
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        if (tabAdapter == null)
        {
            tabAdapter = new SwipableTabAdapter(getSupportFragmentManager());
            tabAdapter.setListTabTitle("List Search Results");
            tabAdapter.setMapTabTitle("Map Search Results");
            tabAdapter.setEvents(this.arrayListOfEvents);
        }
        else
        {
            Log.i(TAG, "USE EXISTING TAB ADAPTER!!!!!");
            updateSearchResults(this.arrayListOfEvents);
        }

        viewPager.setAdapter(tabAdapter);

        // get arraylist of events passed in to populate tab list and tab map
        //this.arrayListOfEvents = (ArrayList<Event>)getIntent().getSerializableExtra("eventList");
    }

    public void updateSearchResults(ArrayList<Event> updatedEventList)
    {
        arrayListOfEvents.clear();
        arrayListOfEvents.addAll(updatedEventList);
        tabAdapter.notifyDataSetChanged();
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