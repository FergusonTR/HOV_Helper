package sweng500team2summer15.hov_helper.eventdisplay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.Event;
import sweng500team2summer15.hov_helper.eventdisplay.EventArrayAdapter;
import sweng500team2summer15.hov_helper.map.MapsActivity;

public class SearchResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        // Construct the data source
        ArrayList<Event> arrayListOfEvents = new ArrayList<Event>();
        // populate with dummy data
        Event e1 = new Event();
        e1.eventType = "Ride";
        e1.startLatitude = 40.82;
        e1.startLongitude = -77.8561126;
        e1.endLatitude = 40.8122837;
        e1.endLongitude = -77.8561126;
        arrayListOfEvents.add(e1);

        // Adapter for event list
        final EventArrayAdapter adapter = new EventArrayAdapter(this, arrayListOfEvents);

        // create the list view
        ListView listView = (ListView) findViewById(R.id.listView);
        // Attach header to listview
        listView.addHeaderView(getLayoutInflater().inflate(R.layout.list_header, null, false));
        // Attach the adapter to a listview
        listView.setAdapter(adapter);





        // setup listener to listen to click events
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("SELECTION!!!!!!!!!!!!!!!!!!" + position);
                Event selectedEvent = adapter.getItem(position-1);

                double startLat = selectedEvent.startLatitude;
                double startLon = selectedEvent.startLongitude;
                double endLat = selectedEvent.endLatitude;
                double endLon = selectedEvent.endLongitude;
                showRouteOnMapActivity(startLat, startLon, endLat, endLon);
            }
        });
    }

    private void showRouteOnMapActivity(double startLat, double startLon, double endLat, double endLon)
    {
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        intent.putExtra("pickupLat", startLat);
        intent.putExtra("pickupLon", startLon);
        intent.putExtra("dropOffLat", endLat);
        intent.putExtra("dropOffLon", endLon);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
