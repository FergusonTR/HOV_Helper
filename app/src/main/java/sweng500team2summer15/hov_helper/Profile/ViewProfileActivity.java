package sweng500team2summer15.hov_helper.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import sweng500team2summer15.hov_helper.R;

/**
 * Created by Mike on 6/30/2015.
 */ // Event Listeners to move the page to the following pages
public class ViewProfileActivity extends Activity {

    //Progress Dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        //Create button
        //Button btnGetEvent = (Button) findViewById(R.id.btnGetEvent);

        //button click event
        //btnGetEvent.setOnClickListener(new View.OnClickListener() {

            //@Override
            //public void onClick(View view) {

                // creating a new event ina background thread
                //new ReadEvent().execute();


            //}


        //});
    }



}
