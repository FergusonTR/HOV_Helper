package sweng500team2summer15.hov_helper.resource;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ViewProfileActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;
import sweng500team2summer15.hov_helper.event.management.SearchEventActivity;
import sweng500team2summer15.hov_helper.eventdisplay.RequestedEventsActivity;

/**
 * Created by Terry on 8/8/2015.
 */
public class SharedActionBar {    // ACTION BAR ITEMS



    public boolean onOptionsItemSelected(Context context, MenuItem item) {

        context.getApplicationContext();

        /*
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_notify:
                Intent notify = new Intent(getApplicationContext(), RequestedEventsActivity.class);
                startActivity(notify);
                finish();
                return true;
            case R.id.action_profile:
                Intent profile = new Intent(getApplicationContext(), ViewProfileActivity.class);
                startActivity(profile);
                finish();
                return true;
            case R.id.action_event:
                Intent event = new Intent(getApplicationContext(), MainEventActivity.class);
                startActivity(event);
                finish();
                return true;
            case R.id.action_search:
                Intent search = new Intent(getApplicationContext(), SearchEventActivity.class);
                startActivity(search);
                finish();
                return true;
            case R.id.action_change_password:
                Intent changePassword = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(changePassword);
                finish();
                return true;
            case R.id.action_sign_out:
                // delete credentials file
                SharedPreferences pref = this.getSharedPreferences("hovhelper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                Intent signOut = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signOut);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);*/
        return true;
    }
}
