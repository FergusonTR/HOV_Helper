package sweng500team2summer15.hov_helper.eventdisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.Event;
import sweng500team2summer15.hov_helper.event.management.UserInEvent;

/**
 * Created by Steve on 7/31/2015.
 */
public class ListRequestsFragment extends Fragment{
    public static final String TAG = ListEventsFragment.class.getSimpleName();
    private ArrayList<UserInEvent> listOfUsersInEvent = new ArrayList<UserInEvent>();
    EventRequestAdapter adapter;

    public void setListOfUsersInEvent(ArrayList<UserInEvent> arrayListOfUsersInEvents)
    {
        this.listOfUsersInEvent = arrayListOfUsersInEvents;
        if (adapter != null)
        {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_requests, container, false);

        // Adapter for request list
        adapter = new EventRequestAdapter(this.getActivity(), this.listOfUsersInEvent);

        // create the list view
        ListView listView = (ListView)v.findViewById(R.id.listView);
        // Attach header to listview
        listView.addHeaderView(this.getActivity().getLayoutInflater().inflate(R.layout.list_requests_header, null, false));
        // Attach the adapter to a listview
        listView.setAdapter(adapter);

        // todo: add listener to selection

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "On Item Clicked. Selection postion: " + position);

                if (position != 0)
                {
                    Event selectedEvent = adapter.getItem(position-1);
                    if (selectedEvent != null)
                    {
                        double startLat = selectedEvent.startLatitude;
                        double startLon = selectedEvent.startLongitude;
                        double endLat = selectedEvent.endLatitude;
                        double endLon = selectedEvent.endLongitude;
                        //showRouteOnMapActivity(startLat, startLon, endLat, endLon);
                        displayRequestDetailsActivity();
                    }
                    else
                    {
                        Log.i(TAG, "SELECTED EVENT IS NULL");
                    }
                }
                else
                {
                    Log.i(TAG, "POSITION IS ZERO. Title Selected. Ignore..");
                }
            }
        });


        return v;
    }

    private void displayRequestDetailsActivity()
    {
        Intent intent = new Intent(this.getActivity().getApplicationContext(),RequestDetailsActivity.class);
        //intent.putExtra("eventForDetails", event);
        startActivity(intent);
    }
}
