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
public class CreateProfileActivity extends Activity {

    private ProgressDialog pDialog;

    EditText inputFirstName;
    EditText inputLastName;
    EditText inputPhoneNumber;
    EditText inputEmailAddress;
    RadioButton inputSexMale;
    RadioButton inputContactCall;
    RadioGroup inputSmokingPref;
    EditText inputEmergencyName;
    EditText inputEmergencyPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile_activity);

        //Create button
        Button btnConfirmProfile = (Button) findViewById(R.id.btnProfileConfirm);
        Button btnCancelProfile = (Button) findViewById(R.id.btnProfileCancel);

        //button click event
        btnConfirmProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // creating a new event in background thread
                new CreateNewProfile().execute();
                // TODO - mike - navigate away from this page after creating a valid profile
            }
        });

        //button click event
        btnCancelProfile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                // Launcing profile mgmt activity
                Intent i = new Intent(getApplicationContext(), ProfileManagement.class);
                startActivity(i);
            }
        });
    }

    private class CreateNewProfile extends AsyncTask<String, String, String>
    {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateProfileActivity.this);
            pDialog.setMessage("Creating Profile..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating event
         */
        protected String doInBackground(String... args)
        {
            inputFirstName = (EditText) findViewById(R.id.txtFirstName);
            inputLastName = (EditText) findViewById(R.id.txtLastName);
            inputPhoneNumber = (EditText) findViewById(R.id.txtPhone);
            inputEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);
            inputEmergencyName = (EditText) findViewById(R.id.txtEmergencyName);
            inputEmergencyPhone = (EditText) findViewById(R.id.txtEmergencyNumber);
            inputSmokingPref = (RadioGroup)findViewById(R.id.rgSMOKE);
            inputSexMale = (RadioButton) findViewById(R.id.rbtnsexMale);
            inputContactCall = (RadioButton) findViewById(R.id.rbtnCALL);

            Profile newProfile = new Profile();
            newProfile.UserFirstName = inputFirstName.getText().toString();
            newProfile.UserLastName = inputLastName.getText().toString();
            newProfile.PhoneNumber = Integer.parseInt(inputPhoneNumber.getText().toString());
            newProfile.EmailAddress = inputEmailAddress.getText().toString();
            newProfile.UserSex = inputSexMale.isChecked() ? Profile.Sex.MALE : Profile.Sex.FEMALE;
            newProfile.UserPreferredContactMethod = inputContactCall.isChecked() ? Profile.PreferredContactMethod.CALL : Profile.PreferredContactMethod.TEXT;
            //TODO - determine how to pass in smoking preference
            //TODO - error handling for phone numbers when they arent integers

            int emergencyContactNumber = Integer.parseInt(inputEmergencyPhone.getText().toString());
            String emergencyContactName = inputEmergencyName.getText().toString();
            newProfile.EmergencyContactInfo = new EmergencyContactInfo(emergencyContactName, emergencyContactNumber);

            // Handles submission of the Profile to the database
            newProfile.SubmitProfile();

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            //ToDo Open a new screen showing the Profile Data with a button to update the profile
            //   Intent i = new Intent(getApplicationContext(), MainActivity.class);
            //   startActivity(i);

        }
    }
}
