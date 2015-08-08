package sweng500team2summer15.hov_helper.event.management;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
        import sweng500team2summer15.hov_helper.Account.SignInActivity;
        import sweng500team2summer15.hov_helper.Profile.ViewProfileActivity;
        import sweng500team2summer15.hov_helper.eventdisplay.RequestedEventsActivity;
        import sweng500team2summer15.hov_helper.R;


public class ReadEventActivity extends AppCompatActivity {

    //Progress Dialog
    private ProgressDialog pDialog;

    EditText inputEventId;
    EditText inputNumberSeats;
    EditText inputNumberAvailable;
    EditText inputEventType;
    EditText inputStartTime;
    EditText inputEndTime;

    String readsuccess = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_event);

        //Create button
        Button btnGetEvent = (Button) findViewById(R.id.btnGetEvent);
        Button btnUpdateEvent = (Button) findViewById(R.id.btnUpdateEvent);
        btnUpdateEvent.setEnabled(false);

        //button click event
        btnGetEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // creating a new event ina background thread
                new ReadEvent().execute();


            }


        });
        btnUpdateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // updating an event in a background thread
                new UpdateEvent().execute();

            }
        });


    }

    public void displayLoginId (String loginId){
        TextView textView = (TextView) findViewById(R.id.textView_LoginID);
        textView.setText(loginId);
        }
    public void displayEventType (String eventType){
        EditText editText = (EditText) findViewById(R.id.textView_eventType);
        editText.setText(eventType);
        }
    public void displayNumberSeats (String numberSeats){
        EditText editText = (EditText) findViewById(R.id.textView_numberSeats);
        editText.setText(numberSeats);
        }
    public void displayNumberAvailable (String numberAvailable) {
        EditText editText = (EditText) findViewById(R.id.textView_numberAvailable);
        editText.setText(numberAvailable);
        }
    public void displayStartTime (String startTime) {
        EditText editText = (EditText) findViewById(R.id.textView_startTime);
        editText.setText(startTime);
        }
    public void displayEndTime (String endTime){
        EditText editText = (EditText) findViewById(R.id.textView_endTime);
        editText.setText(endTime);
        }
    public void displayStatus (String status){
        TextView textView = (TextView) findViewById(R.id.textView_status);
        textView.setText(status);
    }


    /**
     * Background Async Task to Create new event
     * */
    class ReadEvent extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReadEventActivity.this);
            pDialog.setMessage("Reading Event..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * retrieving event
         */
        protected String doInBackground(String... args) {

            inputEventId = (EditText) findViewById(R.id.editText_eventId);

            int eventId = Integer.parseInt(inputEventId.getText().toString());

            Event newEvent = new Event();
            newEvent = newEvent.read(eventId);

            if (newEvent.eventId != 0) {

                publishProgress(newEvent.loginId,
                        newEvent.eventType,
                        String.valueOf(newEvent.numberSeats),
                        String.valueOf(newEvent.numberAvailable),
                        newEvent.start_Time,
                        newEvent.end_Time);
                readsuccess = "true";

            }
            else{
                publishProgress("not found",
                        "not found",
                        "not found",
                        "not found",
                        "not found",
                        "not found");
                readsuccess = "false";
            }



            //ToDo Open a new screen showing the Event Data with a button to view the event


            return readsuccess;
        }

        @Override
        protected void onProgressUpdate(String...values){
            super.onProgressUpdate(values);
            displayLoginId(values[0]);
            displayEventType(values[1]);
            displayNumberSeats(values[2]);
            displayNumberAvailable(values[3]);
            displayStartTime(values[4]);
            displayEndTime(values[5]);
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            Button btnUpdateEvent = (Button) findViewById(R.id.btnUpdateEvent);
            pDialog.dismiss();
            if (readsuccess.equals("true")){btnUpdateEvent.setEnabled(true);}
            else{btnUpdateEvent.setEnabled(false);}
        }

    }

    class UpdateEvent extends AsyncTask<String,String,String>{
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReadEventActivity.this);
            pDialog.setMessage("Updating Event..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * updating event
         */
        protected String doInBackground(String... args) {

            inputEventId = (EditText) findViewById(R.id.editText_eventId);

            String status ="";

            int eventId = Integer.parseInt(inputEventId.getText().toString());

            Event newEvent = new Event();
            newEvent = newEvent.read(eventId);

            inputEventType = (EditText) findViewById(R.id.textView_eventType);
            inputNumberAvailable = (EditText) findViewById(R.id.textView_numberAvailable);
            inputNumberSeats = (EditText) findViewById(R.id.textView_numberSeats);
            inputStartTime =(EditText) findViewById(R.id.textView_startTime);
            inputEndTime = (EditText) findViewById(R.id.textView_endTime);

            newEvent.numberSeats = Integer.parseInt(inputNumberSeats.getText().toString());
            newEvent.numberAvailable = Integer.parseInt(inputNumberAvailable.getText().toString());
            newEvent.eventType = inputEventType.getText().toString();
            newEvent.start_Time = inputStartTime.getText().toString();
            newEvent.end_Time = inputEndTime.getText().toString();

            //ToDo Remove these hard coded values
            newEvent.startLongitude = -71.19;
            newEvent.startLatitude = 41.50;
            newEvent.endLatitude = 41.30;
            newEvent.endLongitude = -71.25;
            newEvent.daysofweek = "Monday";
            newEvent.event_interval = "daily";


            status = newEvent.update(newEvent.loginId,"SWEng_500",newEvent.eventId);

            if (newEvent.eventId != 0) {


                publishProgress(status);

            }
            else{
                publishProgress("not found");

            }



            //ToDo Open a new screen showing the Event Data with a button to view the event


            return null;
        }

        @Override
        protected void onProgressUpdate(String...values){
            super.onProgressUpdate(values);
            displayStatus(values[0]);

        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        @Override
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
                return super.onOptionsItemSelected(item);
        }
    }
}

