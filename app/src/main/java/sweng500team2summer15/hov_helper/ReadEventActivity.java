package sweng500team2summer15.hov_helper;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;


public class ReadEventActivity extends Activity{

    //Progress Dialog
    private ProgressDialog pDialog;

    TextView outputNumberSeats;
    TextView outputLoginId;
    EditText inputEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_event);

        //Create button
        Button btnGetEvent = (Button) findViewById(R.id.btnGetEvent);

        //button click event
        btnGetEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){

                // creating a new event ina background thread
                new ReadEvent().execute();

            }


        });


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
            outputLoginId = (EditText) findViewById(R.id.editText_loginId);
            outputNumberSeats = (TextView)findViewById(R.id.text_numberSeats);


            int eventId = Integer.parseInt(inputEventId.getText().toString());

            Event newEvent = new Event();

            newEvent.read(eventId);

            Intent i = new Intent(getApplicationContext(), ReadEventActivity.class);
            startActivity(i);



            outputLoginId.setText(newEvent.loginId);
            outputNumberSeats.setText(String.valueOf(newEvent.numberSeats));


            //ToDo Open a new screen showing the Event Data with a button to view the event





            // Context context = getApplicationContext();
            // CharSequence text = "Event Created: "+ Integer.toString(newEvent.eventId);
            // int duration = Toast.LENGTH_SHORT;

            //Toast toast = Toast.makeText(context, text, duration);

            //toast.show();


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
;

        }
    }}
