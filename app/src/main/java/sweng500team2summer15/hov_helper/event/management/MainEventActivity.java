package sweng500team2summer15.hov_helper.event.management;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ProfileManagement;
import sweng500team2summer15.hov_helper.eventdisplay.ListEventsFragment;
import sweng500team2summer15.hov_helper.map.MapFragment;
import sweng500team2summer15.hov_helper.map.MapsActivity;
import sweng500team2summer15.hov_helper.R;

public class MainEventActivity extends AppCompatActivity {

    Button btnNewEvent;
    Button btnReadEvent;
    Button btnDeleteEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);
        //Swipe pages
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        //Buttons
        btnNewEvent = (Button) findViewById(R.id.btnCreateEventScrn);
        //btnReadEvent = (Button) findViewById(R.id.btnReadEventScrn);
        //btnDeleteEvent = (Button) findViewById(R.id.btnDeleteEventScrn);

        btnNewEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //Launching create new event activity
                Intent i = new Intent(getApplicationContext(), EventTypeSelection.class);
                startActivity(i);
            }
        });

        /*btnReadEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //Launching read event activity
                Intent i = new Intent(getApplicationContext(), ReadEventActivity.class);
                startActivity(i);
            }
        });*/

        /*btnDeleteEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //Launching read event activity
                Intent i = new Intent(getApplicationContext(), DeleteEventActivity.class);
                startActivity(i);
            }
        });*/
    }

    /**
     * This Class allows me to manage multiple swipe pages
     */
    public class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm){
            super(fm);

        }
        @Override
        public Fragment getItem(int arg0){
            switch (arg0) {
                case 0:
                    return new MapFragment();
                case 1:
                    return new ListEventsFragment();
                default:
                    break;
                          }
            return null;
            }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0) {
                return "Map Events";
            } else {
                return "List Events";
            }
        }

        public int getCount() {
            return 2;
        }

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



