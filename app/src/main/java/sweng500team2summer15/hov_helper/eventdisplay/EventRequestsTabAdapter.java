package sweng500team2summer15.hov_helper.eventdisplay;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.event.management.UserInEvent;

/**
 * Created by steve on 7/18/2015.
 */
public class EventRequestsTabAdapter extends FragmentPagerAdapter {
    private static final int NUM_TABS = 2;

    ListRequestsFragment requestedRidesFragment = new ListRequestsFragment();
    ListRequestsFragment offeredRidesFragment = new ListRequestsFragment();

    public EventRequestsTabAdapter(FragmentManager fm) {super(fm);}

    public void setRequestedRidesFromEventArray(ArrayList<UserInEvent> arrayList)
    {
        requestedRidesFragment.setListOfUsersInEvent(arrayList);
        notifyDataSetChanged();
    }

    public void setOfferedRidesFromEventArray(ArrayList<UserInEvent> arrayList)
    {
        offeredRidesFragment.setListOfUsersInEvent(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return requestedRidesFragment;
            case 1:
                return offeredRidesFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}
