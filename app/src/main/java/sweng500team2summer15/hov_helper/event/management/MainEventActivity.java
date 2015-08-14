package sweng500team2summer15.hov_helper.event.management;


//import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ViewProfileActivity;
import sweng500team2summer15.hov_helper.eventdisplay.RequestedEventsActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.eventdisplay.SearchResultActivity;
import sweng500team2summer15.hov_helper.resource.CheckNetwork;

public class MainEventActivity extends AppCompatActivity {

    private ArrayList<Event> arrayListOfEvents = new ArrayList<Event>();
    private ViewPager viewPager;
    private SwipableTabAdapter tabAdapter;

    Button btnNewEvent;
    private ProgressDialog pDialog;

    String login;
    String password;
    Toast toast;

    int SearchResult = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_event);

        //Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        //set adapter
        tabAdapter = new SwipableTabAdapter(getSupportFragmentManager());
        tabAdapter.setListTabTitle("List My Events");
        tabAdapter.setMapTabTitle("Map My Events");


        //Buttons
        btnNewEvent = (Button) findViewById(R.id.btnCreateEventScrn);
        btnNewEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Launching create new event activity
                Intent i = new Intent(getApplicationContext(), EventTypeSelection.class);
                startActivity(i);
                finish();
            }
        });
        if (CheckNetwork.isNetworkAvailable(MainEventActivity.this)) {
        //Execute a search for items to populate the list and map fragments
        new EventSearch().execute();}
        else {
            toast = Toast.makeText(getApplicationContext(), "No network Connectivity.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void updateMyEventListViews (ArrayList<Event> newList)
    {
        this.arrayListOfEvents.clear();
        this.arrayListOfEvents=newList;

        tabAdapter.setEvents(this.arrayListOfEvents);
        viewPager.setAdapter(tabAdapter);

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

    class EventSearch extends AsyncTask<String, ArrayList<Event>,ArrayList<Event>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainEventActivity.this);
            pDialog.setMessage("Searching for Events..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected ArrayList<Event> doInBackground(String... args) {

            SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE); // specify SharedPreferences for a private file named "hovhelper"
            login = pref.getString("LOGIN", "");                                              // key/value, get value for key "LOGIN"
            password = pref.getString("PASSWORD", "");                                        // key/value, get value for key "PASSWORD" (currently encrypted)
            //Encryption decryption = Encryption.getDefault("Key", "Salt", new byte[16]);     // class to encrypt/decrypt strings, see NOTE
            //String decryptPw = decryption.decryptOrNull(password);                          // get password after decrypting

            EventList newEventList = new EventList();

                // check if the user has a login
                if (login != null) {
                   newEventList.arrayListOfEvents = newEventList.searchByUser(login);
                    SearchResult = newEventList.arrayListOfEvents.size();

                    if (SearchResult > 0) {
                        publishProgress(new ArrayList<Event>(newEventList.arrayListOfEvents));}
                    else{
                        newEventList.arrayListOfEvents = new ArrayList<Event>();
                    }
                }

        return null;

        }

        @Override
        protected void onProgressUpdate(ArrayList<Event>... update) {
            super.onProgressUpdate(update);
            //ArrayList<Event> myEventList = update[0];
            updateMyEventListViews(update[0]);

         }

        @Override
        protected void onPostExecute(ArrayList<Event> result) {

            pDialog.dismiss();
            if (SearchResult > 0){
                toast = Toast.makeText(getApplicationContext(), "Results Found.", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                toast = Toast.makeText(getApplicationContext(), "No Events Found, create some!", Toast.LENGTH_SHORT);
                toast.show();
            }
    }}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_notify:
                Intent notify = new Intent(getApplicationContext(), RequestedEventsActivity.class);
                startActivity(notify);
                return true;
            case R.id.action_profile:
                Intent profile = new Intent(getApplicationContext(), ViewProfileActivity.class);
                startActivity(profile);
                return true;
            case R.id.action_event:
                Intent event = new Intent(getApplicationContext(), MainEventActivity.class);
                startActivity(event);
                return true;
            case R.id.action_search:
                Intent search = new Intent(getApplicationContext(), SearchEventActivity.class);
                startActivity(search);
                finish();
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



