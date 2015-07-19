package sweng500team2summer15.hov_helper.eventdisplay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.Event;
import sweng500team2summer15.hov_helper.map.MapsActivity;


public class EventDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // get the event passed into this activity
        final Event event = (Event)getIntent().getSerializableExtra("eventForDetails");


        // setup widget labels depending on if ride or share event
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(event.eventType + " Event Details");
        final Button mapButton = (Button) findViewById(R.id.btnMapRoute);
        final Button offerButton = (Button) findViewById(R.id.btnRequestRide);
        if (event.eventType == "Ride")
        {
            offerButton.setText("Request Ride");
        }
        else
        {
            offerButton.setText("Offer Ride");
        }


        // handle the map button click
        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double startLat = event.startLatitude;
                double startLon = event.startLongitude;
                double endLat = event.endLatitude;
                double endLon = event.endLongitude;
                showRouteOnMapActivity(startLat, startLon, endLat, endLon);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
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

    private void showRouteOnMapActivity(double startLat, double startLon, double endLat, double endLon)
    {
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);

        intent.putExtra("pickupLat", startLat);
        intent.putExtra("pickupLon", startLon);
        intent.putExtra("dropOffLat", endLat);
        intent.putExtra("dropOffLon", endLon);
        startActivity(intent);
    }
}
