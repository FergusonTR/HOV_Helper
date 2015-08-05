package sweng500team2summer15.hov_helper.eventdisplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.UserInEvent;

/**
 * Created by user on 7/31/2015.
 */
public class EventRequestAdapter extends ArrayAdapter<UserInEvent> {
    private final Context context;
    private final ArrayList<UserInEvent> requests;

    public EventRequestAdapter(Context context, ArrayList<UserInEvent> requestList)
    {
        super(context, R.layout.list_requests, requestList);
        this.context = context;
        this.requests = requestList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UserInEvent userInEvent = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_requests, parent, false);
        }

        TextView eventIdView = (TextView) convertView.findViewById(R.id.eventId);
        TextView requesterLoginId = (TextView) convertView.findViewById(R.id.requesterLoginId);
        TextView statusView = (TextView) convertView.findViewById(R.id.status);

        eventIdView.setText(String.valueOf(userInEvent.eventId));
        requesterLoginId.setText(userInEvent.requestedParticipantLoginId);
        statusView.setText(userInEvent.requestStatus);
        return convertView;
    }
}
