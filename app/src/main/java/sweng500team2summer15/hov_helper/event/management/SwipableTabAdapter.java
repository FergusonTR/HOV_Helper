package sweng500team2summer15.hov_helper.event.management;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.eventdisplay.ListEventsFragment;
import sweng500team2summer15.hov_helper.map.MapFragment;

/**
 * Created by Steve on 8/5/2015.
 * This Class allows us to manage multiple swipe tabs and set a list of events to populate list and map
 */
public class SwipableTabAdapter extends FragmentPagerAdapter {
    ListEventsFragment listFragment = new ListEventsFragment();
    MapFragment mapFragment = new MapFragment();
    public String LIST_TAB_TITLE = "List Events";
    public String MAP_TAB_TITLE = "Map Events";
    public SwipableTabAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int arg0){
        switch (arg0) {
            case 0:
                return mapFragment;
            case 1:
                return listFragment;
            default:
                break;
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) {
            return MAP_TAB_TITLE;
        } else {
            return LIST_TAB_TITLE;
        }
    }
    public int getCount() {
        return 2;
    }
    public void setEvents(ArrayList<Event> arrayListOfEvents)
    {
        listFragment.setEvents(arrayListOfEvents);
        mapFragment.setEvents(arrayListOfEvents);
    }
    public void setListTabTitle(String title)
    {
        this.LIST_TAB_TITLE = title;
    }
    public void setMapTabTitle(String title)
    {
        this.MAP_TAB_TITLE = title;
    }
}
