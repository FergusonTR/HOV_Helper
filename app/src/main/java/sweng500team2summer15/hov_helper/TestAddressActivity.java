package sweng500team2summer15.hov_helper;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import sweng500team2summer15.hov_helper.map.MapController;


public class TestAddressActivity extends ActionBarActivity {

    EditText inputStreet;
    EditText inputCity;
    EditText inputState;
    EditText inputZipcode;

    String resultsSuccess = "false";

    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_address);
        Button btnLatLon = (Button) findViewById(R.id.btnLatLon);
        btnLatLon.setEnabled(true);

        btnLatLon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // creating a new event ina background thread
                new GetLatLon().execute();


            }


        });
    }

    public void displayOutputLat (String Latitude) {
        TextView textView = (TextView) findViewById(R.id.outputLatitude);
        textView.setText(Latitude);
    }
    public void displayOutputLon (String Longitude) {
        TextView textView = (TextView) findViewById(R.id.outputLongitude);
        textView.setText(Longitude);
    }

    class GetLatLon extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(TestAddressActivity.this);
            pDialog.setMessage("Retrieving Address..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected void onProgressUpdate(String...values){
            super.onProgressUpdate(values);
            displayOutputLat(values[0]);
            displayOutputLon(values[1]);
        }

        protected String doInBackground(String... args) {

            inputStreet = (EditText) findViewById(R.id.inputStreet);
            inputCity = (EditText) findViewById(R.id.inputCity);
            inputState = (EditText) findViewById(R.id.inputState);
            inputZipcode = (EditText) findViewById(R.id.inputZipcode);

            String  searchaddress = inputStreet.getText().toString() + " " + inputCity.getText().toString() + ", " +inputState.getText().toString()+" " +inputZipcode.getText().toString();

            LatLng Results = MapController.getGeoCoordinateFromAddress (getApplicationContext(),searchaddress);


            if (Results.latitude != 0.0) {

                String TempLatitude = Double.toString(Results.latitude);
                String TempLongitude = Double.toString(Results.longitude);


                publishProgress(TempLatitude,TempLongitude);
                resultsSuccess = "true";

            }
            else{
                publishProgress("not found",
                        "not found");
                resultsSuccess = "false";
            }

            return resultsSuccess;
        }

        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}