package sweng500team2summer15.hov_helper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Steve on 6/6/2015.
 */
public class EventDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
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

    public void buttonOnClick(View v)
    {
        EditText pickupLat = (EditText) findViewById(R.id.pickupLat);
        EditText pickupLon = (EditText) findViewById(R.id.pickupLon);
        EditText dropOffLat = (EditText) findViewById(R.id.dropOffLat);
        EditText dropOffLon = (EditText) findViewById(R.id.dropOffLon);
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        // TODO: pass textbox data to map activity instead of hard code.
        intent.putExtra("pickupLat", Double.parseDouble(pickupLat.getText().toString()));
        intent.putExtra("pickupLon", Double.parseDouble(pickupLon.getText().toString()));
        intent.putExtra("dropOffLat", Double.parseDouble(dropOffLat.getText().toString()));
        intent.putExtra("dropOffLon", Double.parseDouble(dropOffLon.getText().toString()));

        startActivity(intent);
    }
}
