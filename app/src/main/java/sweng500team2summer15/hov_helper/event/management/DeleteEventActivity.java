package sweng500team2summer15.hov_helper.event.management;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import sweng500team2summer15.hov_helper.*;
import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;


public class DeleteEventActivity extends ActionBarActivity {

    private ProgressDialog pDialog;
    EditText inputLoginId;
    EditText inputEventId;
    TextView outputStatus;
    String TAG = "HOV_Helper";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_event);

        //create button
        Button btn_deleteEvent = (Button) findViewById(R.id.btn_deleteEvent);
        //button click event
        btn_deleteEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // creating a new event in background thread
                new deleteNewEvent().execute();


            }
        });
    }

    public void displayStatus (String status){
        TextView textView = (TextView) findViewById(R.id.textView_status);
        textView.setText(status);
    }

    class deleteNewEvent extends AsyncTask<String, String, String> {

                /**
                 * Before starting background thread Show Progress Dialog
                 */
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    pDialog = new ProgressDialog(DeleteEventActivity.this);
                    pDialog.setMessage("Deleting Event..");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();
                }

                /**
                 * deleting event
                 */
                protected String doInBackground(String... args) {

                    inputEventId = (EditText) findViewById(R.id.editText_eventId);
                    inputLoginId = (EditText) findViewById(R.id.editText_loginId);
                    outputStatus =(TextView) findViewById(R.id.textView_status);


                    String temp_password = "Sweng_500";
                    String status;
                    Event newEvent = new Event();

                    newEvent.loginId = inputLoginId.getText().toString();
                    newEvent.eventId = Integer.parseInt(inputEventId.getText().toString());

                    Log.v(TAG,newEvent.loginId);
                    Log.v(TAG,String.valueOf(newEvent.eventId));

                    status = newEvent.delete(newEvent.loginId, temp_password, newEvent.eventId);
                    Log.v(TAG,status);

                    publishProgress(status);

                    return null;
                }

                protected void onProgressUpdate(String...values){
                    super.onProgressUpdate(values);
                    displayStatus(values[0]);
                }
                /**
                 * After completing background task Dismiss the progress dialog
                 * *
                 */
                protected void onPostExecute(String file_url) {
                    // dismiss the dialog once done
                    pDialog.dismiss();


                }


            }

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
                // TODO - link to profile activity
                //Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                //startActivity(profile);
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
                SharedPreferences pref = this.getSharedPreferences("hovhelper",0);
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
