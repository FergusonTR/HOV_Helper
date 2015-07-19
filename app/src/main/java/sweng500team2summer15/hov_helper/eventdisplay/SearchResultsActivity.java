package sweng500team2summer15.hov_helper.eventdisplay;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.SearchResultsTabAdapter;
import sweng500team2summer15.hov_helper.event.management.Event;
import sweng500team2summer15.hov_helper.map.MapController;


public class SearchResultsActivity extends ActionBarActivity implements
        ActionBar.TabListener {
    private ArrayList<Event> arrayListOfEvents = new ArrayList<Event>();
    private ViewPager viewPager;
    private SearchResultsTabAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"List View", "Map View"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        LatLng found = MapController.getGeoCoordinateFromAddress(this, "20425 sw imperial pl. aloha, OR 97006");
        System.out.println("FOUND LAT LON: " + found.latitude + ", " + found.longitude);


        // TODO: populate event list for testing only *****
        ArrayList<Event> myList = new ArrayList<Event>();
        // populate with dummy data
        Event e1 = new Event();
        e1.eventType = "Ride";
        e1.startLatitude = 40.82;
        e1.startLongitude = -77.8561126;
        e1.endLatitude = 40.8122837;
        e1.endLongitude = -77.8561126;
        myList.add(e1);
        Event e2 = new Event();
        e2.eventType = "Share";
        e2.startLatitude = 40.83;
        e2.startLongitude = -77.8561126;
        e2.endLatitude = 40.8122837;
        e2.endLongitude = -77.8561126;
        myList.add(e2);

        getIntent().putExtra("eventList", myList);
        // TODO: remove above *****


        // get arraylist of events passed in
        this.arrayListOfEvents = (ArrayList<Event>)getIntent().getSerializableExtra("eventList");
        if (this.arrayListOfEvents == null)
        {
            // create empty list
            this.arrayListOfEvents = new ArrayList<Event>();
        }

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        mAdapter = new SearchResultsTabAdapter(getSupportFragmentManager());
        mAdapter.setEvents(arrayListOfEvents);

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    public void setEvents(ArrayList<Event> arrayListOfEvents)
    {
        this.arrayListOfEvents = arrayListOfEvents;
    }
}