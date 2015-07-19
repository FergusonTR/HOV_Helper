package sweng500team2summer15.hov_helper.event.management;

        import android.app.Activity;
        import android.app.ProgressDialog;

        import android.content.Context;
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

        import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
        import sweng500team2summer15.hov_helper.Account.SignInActivity;
        import sweng500team2summer15.hov_helper.MapsActivity;
        import sweng500team2summer15.hov_helper.R;


public class CreateEventActivity extends ActionBarActivity {

    //Progress Dialog
    private ProgressDialog pDialog;

    EditText inputNumberSeats;
    EditText inputEventType;
    EditText inputLoginId;
    EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //Create button
        Button btnAddEvent = (Button) findViewById(R.id.btnAddEvent);

        //button click event
        btnAddEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){

                // creating a new event in background thread
                new CreateNewEvent().execute();



            }
        });
    }
    /**
     * Background Async Task to Create new event
     * */
    class CreateNewEvent extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateEventActivity.this);
            pDialog.setMessage("Creating Event..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating event
         */
        protected String doInBackground(String... args) {

            inputNumberSeats = (EditText) findViewById(R.id.editText_numberSeats);
            inputEventType = (EditText) findViewById(R.id.editText_eventType);
            inputLoginId = (EditText) findViewById(R.id.editText_loginId);
            inputPassword = (EditText) findViewById(R.id.editText_password);
            String password = inputPassword.getText().toString();

            Event newEvent = new Event();

            //ToDo add code to capture all event parameters
            newEvent.numberSeats = Integer.parseInt(inputNumberSeats.getText().toString());
            newEvent.eventType = inputEventType.getText().toString();
            newEvent.loginId = inputLoginId.getText().toString();

            //ToDo add code to call the map function and translate street address to lat long


            newEvent.create(newEvent.loginId, password);

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            //ToDo Open a new screen showing the Event Data with a button to view the event
         //   Intent i = new Intent(getApplicationContext(), MainEventActivity.class);
         //   startActivity(i);

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