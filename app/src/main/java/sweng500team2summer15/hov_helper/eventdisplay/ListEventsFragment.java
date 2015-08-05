package sweng500team2summer15.hov_helper.eventdisplay;

import android.app.Activity;
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
import sweng500team2summer15.hov_helper.map.MapsActivity;

/**
 * Fragment for list of events
 * Created by Steve on 6/6/2015.
 */
public class ListEventsFragment extends Fragment {
    public static final String TAG = ListEventsFragment.class.getSimpleName();
    private ArrayList<Event> arrayListOfEvents = new ArrayList<Event>();

    public void setEvents(ArrayList<Event> arrayListOfEvents)
    {
        this.arrayListOfEvents = arrayListOfEvents;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_list_events, container, false);

        // Adapter for event list
        final EventArrayAdapter adapter = new EventArrayAdapter(this.getActivity(), this.arrayListOfEvents);

        // create the list view
        ListView listView = (ListView)v.findViewById(R.id.listView);
        // Attach header to listview
        listView.addHeaderView(this.getActivity().getLayoutInflater().inflate(R.layout.list_events_header, null, false));
        // Attach the adapter to a listview
        listView.setAdapter(adapter);

        // setup listener to listen to click events
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
                        displayEventDetailsActivity(selectedEvent);
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void showRouteOnMapActivity(double startLat, double startLon, double endLat, double endLon)
    {
        Intent intent = new Intent(this.getActivity().getApplicationContext(),MapsActivity.class);

        intent.putExtra("pickupLat", startLat);
        intent.putExtra("pickupLon", startLon);
        intent.putExtra("dropOffLat", endLat);
        intent.putExtra("dropOffLon", endLon);
        startActivity(intent);
    }

    private void displayEventDetailsActivity(Event event)
    {
        Intent intent = new Intent(this.getActivity().getApplicationContext(),EventDetailsActivity.class);
        intent.putExtra("eventForDetails", event);
        startActivity(intent);
    }
}
