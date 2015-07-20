package sweng500team2summer15.hov_helper.eventdisplay;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.Event;
import sweng500team2summer15.hov_helper.map.MapController;

/**
 * Created by Steve on 7/5/2015.
 */
public class EventArrayAdapter extends ArrayAdapter<Event> {
    private final Context context;
    private final ArrayList<Event> events;

    public EventArrayAdapter(Context context, ArrayList<Event> events)
    {
        super(context, R.layout.list_event, events);
        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_event, parent, false);
        }

        TextView typeView = (TextView) convertView.findViewById(R.id.eventType);
        TextView startLocationView = (TextView) convertView.findViewById(R.id.startLocation);
        TextView endLocationView = (TextView) convertView.findViewById(R.id.endLocation);
        TextView timeView = (TextView) convertView.findViewById(R.id.eventTime);

        typeView.setText(event.eventType);

        // get the start address from the lat/lon
        Address startAddress = MapController.getStreetAddressFromLatLon(this.context, event.startLatitude, event.startLongitude);
        Address endAddress = MapController.getStreetAddressFromLatLon(this.context, event.endLatitude, event.endLongitude);
        startLocationView.setText(startAddress.getAddressLine(1) + " " + startAddress.getAddressLine(2));
        endLocationView.setText(endAddress.getAddressLine(0) + " " + endAddress.getAddressLine(1) + " " + endAddress.getAddressLine(2));

        // date
        String date = event.start_Time;
        timeView.setText(date);

        return convertView;
    }
}
