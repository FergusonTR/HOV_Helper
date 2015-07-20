package sweng500team2summer15.hov_helper.eventdisplay;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.event.management.Event;
import sweng500team2summer15.hov_helper.eventdisplay.ListEventsFragment;
import sweng500team2summer15.hov_helper.map.MapFragment;

/**
 * Created by steve on 7/18/2015.
 */
public class SearchResultsTabAdapter extends FragmentPagerAdapter {
    private static final int NUM_TABS = 2;

    ListEventsFragment listFragment = new ListEventsFragment();
    MapFragment mapFragment = new MapFragment();

    public SearchResultsTabAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public void setEvents(ArrayList<Event> arrayListOfEvents)
    {
        listFragment.setEvents(arrayListOfEvents);
        mapFragment.setEvents(arrayListOfEvents);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // List fragment
                return listFragment;
            case 1:
                // Map fragment
                return mapFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return NUM_TABS;
    }
}
