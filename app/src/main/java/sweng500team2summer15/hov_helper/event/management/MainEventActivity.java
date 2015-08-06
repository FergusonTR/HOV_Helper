package sweng500team2summer15.hov_helper.event.management;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ProfileManagement;
import sweng500team2summer15.hov_helper.map.MapsActivity;
import sweng500team2summer15.hov_helper.R;

public class MainEventActivity extends AppCompatActivity {
    private ArrayList<Event> arrayListOfEvents = new ArrayList<Event>();

    Button btnNewEvent;

    String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        // TODO: Remove Events Below after list of events query implemented. For Testing only
        ArrayList<Event> myList = new ArrayList<Event>();

        // populate with dummy data
        Event e1 = new Event();
        e1.eventId = 1;
        e1.eventType = "Ride";
        e1.startLatitude = 40.82;
        e1.startLongitude = -77.8561126;
        e1.endLatitude = 40.8122837;
        e1.endLongitude = -77.8561126;
        e1.numberAvailable = 4;

        myList.add(e1);
        Event e2 = new Event();
        e2.eventId = 2;
        e2.eventType = "Drive";
        e2.startLatitude = 40.83;
        e2.startLongitude = -77.8561126;
        e2.endLatitude = 40.8122837;
        e2.endLongitude = -77.8561126;
        e2.numberAvailable = 2;
        myList.add(e2);

        // TODO: remove below line. This is an example of how another activity would pass in an array of events
        getIntent().putExtra("eventList", myList);

        //Swipe pages
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        SwipableTabAdapter tabAdapter = new SwipableTabAdapter(getSupportFragmentManager());
        tabAdapter.setListTabTitle("List My Events");
        tabAdapter.setMapTabTitle("Map My Events");
        viewPager.setAdapter(tabAdapter);

        // get arraylist of events passed in to populate tab list and tab map (Note: If populating list in this activity, remove below line)
        this.arrayListOfEvents = (ArrayList<Event>)getIntent().getSerializableExtra("eventList");
        if (this.arrayListOfEvents == null)
        {
            // create empty list
            this.arrayListOfEvents = new ArrayList<Event>();
        }
        tabAdapter.setEvents(this.arrayListOfEvents);

        //Buttons
        btnNewEvent = (Button) findViewById(R.id.btnCreateEventScrn);

        btnNewEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //Launching create new event activity
                Intent i = new Intent(getApplicationContext(), EventTypeSelection.class);
                startActivity(i);
                finish();
            }
        });

    }

    protected String doInBackground(){
        SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE); // specify SharedPreferences for a private file named "hovhelper"
        login = pref.getString("LOGIN", "");                                              // key/value, get value for key "LOGIN"
        password = pref.getString("PASSWORD", "");                                        // key/value, get value for key "PASSWORD" (currently encrypted)
        //Encryption decryption = Encryption.getDefault("Key", "Salt", new byte[16]);     // class to encrypt/decrypt strings, see NOTE
        //String decryptPw = decryption.decryptOrNull(password);                          // get password after decrypting

        return null;
    }
    // ACTION BAR ITEMS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent profile = new Intent(getApplicationContext(), ProfileManagement.class);
                startActivity(profile);
                return true;
            case R.id.action_event:
                Intent event = new Intent(getApplicationContext(), MainEventActivity.class);
                startActivity(event);
                return true;
            case R.id.action_map:
                Intent map = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(map);
                return true;
            case R.id.action_change_password:
                Intent changePassword = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(changePassword);
                return true;
            case R.id.action_sign_out:
                // delete credentials file
                SharedPreferences pref = this.getSharedPreferences("hovhelper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                Intent signOut = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signOut);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



