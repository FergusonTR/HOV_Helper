package sweng500team2summer15.hov_helper.Profile;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.JSONParser;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.Start;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;
import sweng500team2summer15.hov_helper.map.MapsActivity;

// created by Mike
// Aims to handle additional screen for profile management buttons

public class ProfileManagement extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the current user's login information
        SharedPreferences preferences = getSharedPreferences("hovhelper", Context.MODE_PRIVATE);
        final String login = preferences.getString("LOGIN", "");

        // todo: query the table for this entry...only display the applicable buttons. needs done on a separate thread
        //Profile myProfile = new Profile();
        //myProfile = myProfile.GetProfile(login);

        // Determine if the profile has been created yet
        //if(myProfile.LoginID == "")
        //{

        //}

        //Create button
        Button btnCreateProfile = (Button) findViewById(R.id.btnCreateProfileScrn);
        Button btnDeleteProfile = (Button) findViewById(R.id.btnDeleteProfileScrn);
        Button btnViewProfile = (Button) findViewById(R.id.btnViewProfileScrn);
        Button btnSignOut = (Button) findViewById(R.id.btnSignOut);

        //button click event
        btnCreateProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching profile create activity
                Intent i = new Intent(getApplicationContext(), CreateProfileActivity.class);
                i.putExtra("LoginID", login);
                startActivity(i);
            }
        });

        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Display an alert to ensure the user really wants to delete their profile
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileManagement.this);
                builder.setMessage("Are you sure you want to delete your profile?");
                builder.setCancelable(true);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you wish to delete your profile? Doing so will sign you out of the application.");
                builder.setPositiveButton("Yes, delete my profile", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteProfile().execute(login);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing. dialog is dismissed
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btnViewProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching profile view activity
                Intent i = new Intent(getApplicationContext(), ViewProfileActivity.class);
                i.putExtra("LoginID", login);
                startActivity(i);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching sign out
                Intent i = new Intent(getApplicationContext(), Start.class);
                startActivity(i);
            }
        });
    }

    class DeleteProfile extends AsyncTask<String, String, String> {

        private ProgressDialog pDialog;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileManagement.this);
            pDialog.setMessage("Deleting Profile..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * deleting event
         */
        protected String doInBackground(String... args) {

            String loginID = args[0];

            JSONParser jsonParser = new JSONParser();

            // url to create new product
            String url_delete_profile = "http://www.hovhelper.com/delete_profile.php";

            String TAG_SUCCESS = "success";
            String TAG_MESSAGE = "message";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("loginID", loginID));

            // failedReason captures the feedback from the delete_profile.php
            String failedReason = "something went wrong";

            // deleting event by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(url_delete_profile, "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully deleted profile
                    failedReason = json.getString(TAG_MESSAGE);

                } else {
                    // failed to create profile, capture reason
                    failedReason = json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            publishProgress(failedReason);

            return failedReason;
        }

        protected void onProgressUpdate(String...values){
            super.onProgressUpdate(values);
        }

        /**
         * After completing background task Dismiss the progress dialog
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
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