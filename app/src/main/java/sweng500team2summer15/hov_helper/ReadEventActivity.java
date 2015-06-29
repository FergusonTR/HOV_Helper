package sweng500team2summer15.hov_helper;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;


public class ReadEventActivity extends Activity{

    //Progress Dialog
    private ProgressDialog pDialog;

    EditText inputEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_event);

        //Create button
        Button btnGetEvent = (Button) findViewById(R.id.btnGetEvent);

        //button click event
        btnGetEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // creating a new event ina background thread
                new ReadEvent().execute();


            }


        });


    }

    public void displayLoginId (String loginId){
        TextView textView = (TextView) findViewById(R.id.textView_LoginID);
        textView.setText(loginId);
        }
    public void displayEventType (String eventType){
        TextView textView = (TextView) findViewById(R.id.textView_eventType);
        textView.setText(eventType);
        }
    public void displayNumberSeats (String numberSeats){
        TextView textView = (TextView) findViewById(R.id.textView_numberSeats);
        textView.setText(numberSeats);
        }
    public void displayNumberAvailable (String numberAvailable) {
        TextView textView = (TextView) findViewById(R.id.textView_numberAvailable);
        textView.setText(numberAvailable);
        }
    public void displayStartTime (String startTime) {
        TextView textView = (TextView) findViewById(R.id.textView_startTime);
        textView.setText(startTime);
        }
    public void displayEndTime (String endTime){
        TextView textView = (TextView) findViewById(R.id.textView_endTime);
        textView.setText(endTime);
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
            newEvent.read(eventId);
            if (newEvent.eventId != 0) {

                publishProgress(newEvent.loginId,
                        newEvent.eventType,
                        String.valueOf(newEvent.numberSeats),
                        String.valueOf(newEvent.numberAvailable),
                        newEvent.start_Time,
                        newEvent.end_Time);
            }
            else{
                publishProgress("not found",
                        "not found",
                        "not found",
                        "not found",
                        "not found",
                        "not found");
            }



            //ToDo Open a new screen showing the Event Data with a button to view the event


            return null;
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
           pDialog.dismiss();


        }

    }}

