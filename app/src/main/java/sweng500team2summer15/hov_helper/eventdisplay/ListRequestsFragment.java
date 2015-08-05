package sweng500team2summer15.hov_helper.eventdisplay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.UserInEvent;

/**
 * Created by Steve on 7/31/2015.
 */
public class ListRequestsFragment extends Fragment{
    private ArrayList<UserInEvent> listOfUsersInEvent = new ArrayList<UserInEvent>();

    public void setListOfUsersInEvent(ArrayList<UserInEvent> arrayListOfUsersInEvents)
    {
        this.listOfUsersInEvent = arrayListOfUsersInEvents;
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
        final EventRequestAdapter adapter = new EventRequestAdapter(this.getActivity(), this.listOfUsersInEvent);

        // create the list view
        ListView listView = (ListView)v.findViewById(R.id.listView);
        // Attach header to listview
        listView.addHeaderView(this.getActivity().getLayoutInflater().inflate(R.layout.list_requests_header, null, false));
        // Attach the adapter to a listview
        listView.setAdapter(adapter);

        // todo: add listener to selection

        return v;
    }
}
