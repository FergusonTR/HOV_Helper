package sweng500team2summer15.hov_helper.Profile;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;
import sweng500team2summer15.hov_helper.map.MapsActivity;

/**
 * Created by Mike on 6/30/2015.
 */
public class ViewProfileActivity extends ActionBarActivity {

    String readSuccess = "false";

    //Progress Dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Button btnConfirmUpdate = (Button) findViewById(R.id.btnProfileUpdate);
        Button btnCancelProfile = (Button) findViewById(R.id.btnProfileUpdateCancel);

        // Attempt to get the profile
        new ReadProfile().execute();

        // Listen for the cancel or update buttons
        //button click event
        btnConfirmUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                // update profile
                new UpdateProfile().execute();
            }
        });

        //button click event
        btnCancelProfile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                // cancel
                Intent i = new Intent(getApplicationContext(), ProfileManagement.class);
                startActivity(i);
            }
        });
    }

    // The following are called to update the displayed data during Async - onProgressUpdate
    public void displayNames (String first, String last){
        EditText txtfirst = (EditText) findViewById(R.id.txtProfile_FirstName);
        txtfirst.setText(first);

        EditText txtlast = (EditText) findViewById(R.id.txtProfile_LastName);
        txtlast.setText(last);
    }
    public void displayPhone (String phone){
        EditText editText = (EditText) findViewById(R.id.txtProfile_Phone);
        editText.setText(phone);
    }
    public void displayEmail (String email){
        EditText editText = (EditText) findViewById(R.id.txtProfile_Email);
        editText.setText(email);
    }
    public void displaySex (String sex) {
        RadioGroup rgSex = (RadioGroup) findViewById(R.id.profileUpdate_sex);

        if(sex == "FEMALE")
            rgSex.check(R.id.profileUpdate_female);
        else // Male
            rgSex.check(R.id.profileUpdate_male);
    }
    public void displaySmokingPref (String smokingPreference) {
        RadioGroup smoking = (RadioGroup)findViewById(R.id.txtProfileUpdate_Smoking);

        if(smokingPreference == "NONSMOKE")
            smoking.check(R.id.rbtnUpdate_NONSMOKE);
        else if(smokingPreference == "NOPREF")
            smoking.check(R.id.rbtnUpdate_NOPREF);
        else // Smoking
            smoking.check(R.id.rbtnUpdate_SMOKE);
    }
    public void displayContactMethod (String contactMethod){
        RadioGroup contact = (RadioGroup) findViewById(R.id.rgUpdate_CONTACT);

        if(contactMethod == "CALL")
            contact.check(R.id.rbtnUpdate_CALL);
        else // TEXT
            contact.check(R.id.rbtnUpdate_TEXT);
    }
    public void displayEmergencyContact (String name, String number){
        EditText ecName = (EditText) findViewById(R.id.txtProfileUpdate_ContactName);
        ecName.setText(name);

        EditText ecNumber = (EditText) findViewById(R.id.txtProfileUpdate_ContactNumber);
        ecNumber.setText(number);
    }

    /**
     * Background Async Task to Create new event
     * */
    class ReadProfile extends AsyncTask<String, String, String> {

        String readsuccess = "false";

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewProfileActivity.this);
            pDialog.setMessage("Reading Profile..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * retrieving event
         */
        protected String doInBackground(String... args) {

            Intent thisIntent = getIntent();
            String loginID = thisIntent.getStringExtra("LoginID");

            Profile newProfile = new Profile();
            newProfile = newProfile.GetProfile(loginID);

            if (newProfile.LoginID != "") {

                String ecName = newProfile.EmergencyContactInfo.ContactName;
                int ecNumber = newProfile.EmergencyContactInfo.ContactNumber;

                publishProgress(newProfile.UserFirstName,
                        newProfile.UserLastName,
                        String.valueOf(newProfile.PhoneNumber),
                        newProfile.EmailAddress,
                        String.valueOf(newProfile.UserSex),
                        String.valueOf(newProfile.UserSmokingPreference),
                        String.valueOf(newProfile.UserPreferredContactMethod),
                        ecName,
                        String.valueOf(ecNumber));
                readsuccess = "true";

            } else {
                // display error message and return to profile screen

                readsuccess = "false";
            }

            return readsuccess;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            // Update the view with the user's profile info
            displayNames(values[0], values[1]);
            displayPhone(values[2]);
            displayEmail(values[3]);
            displaySex(values[4]);
            displaySmokingPref(values[5]);
            displayContactMethod(values[6]);
            displayEmergencyContact(values[7],values[8]);
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            Button btnUpdateProfile = (Button) findViewById(R.id.btnProfileUpdate);
            pDialog.dismiss();
            if (readsuccess.equals("true")){btnUpdateProfile.setEnabled(true);}
            else{btnUpdateProfile.setEnabled(false);}
        }
    }

    class UpdateProfile extends AsyncTask<String, String, String> {

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

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewProfileActivity.this);
            pDialog.setMessage("Updating Profile..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating event
         */
        protected String doInBackground(String... args)
        {
            Intent thisIntent = getIntent();
            String LoginID = thisIntent.getStringExtra("LoginID");

            inputFirstName = (EditText) findViewById(R.id.txtProfile_FirstName);
            inputLastName = (EditText) findViewById(R.id.txtProfile_LastName);
            inputPhoneNumber = (EditText) findViewById(R.id.txtProfile_Phone);
            inputEmailAddress = (EditText) findViewById(R.id.txtProfile_Email);
            inputEmergencyName = (EditText) findViewById(R.id.txtProfileUpdate_ContactName);
            inputEmergencyPhone = (EditText) findViewById(R.id.txtProfileUpdate_ContactNumber);
            inputSmokingPref = (RadioGroup)findViewById(R.id.txtProfileUpdate_Smoking);
            inputSexMale = (RadioButton) findViewById(R.id.profileUpdate_male);
            inputContactCall = (RadioButton) findViewById(R.id.rbtnUpdate_CALL);

            Profile newProfile = new Profile();
            newProfile.UserFirstName = inputFirstName.getText().toString();
            newProfile.UserLastName = inputLastName.getText().toString();
            newProfile.PhoneNumber = Integer.parseInt(inputPhoneNumber.getText().toString());
            newProfile.EmailAddress = inputEmailAddress.getText().toString();
            newProfile.UserSex = inputSexMale.isChecked() ? Profile.Sex.MALE : Profile.Sex.FEMALE;
            newProfile.UserPreferredContactMethod = inputContactCall.isChecked() ? Profile.PreferredContactMethod.CALL : Profile.PreferredContactMethod.TEXT;

            int selectedSmokingPref = inputSmokingPref.getCheckedRadioButtonId();
            switch (selectedSmokingPref)
            {
                case R.id.rbtnUpdate_SMOKE:
                    newProfile.UserSmokingPreference = Profile.SmokingPreference.SMOKE;
                    break;
                case R.id.rbtnUpdate_NONSMOKE:
                    newProfile.UserSmokingPreference = Profile.SmokingPreference.NONSMOKE;
                    break;
                case R.id.rbtnUpdate_NOPREF:
                    newProfile.UserSmokingPreference = Profile.SmokingPreference.NOPREF;
                    break;
            }

            //TODO - error handling for phone numbers when they arent integers

            int emergencyContactNumber = Integer.parseInt(inputEmergencyPhone.getText().toString());
            String emergencyContactName = inputEmergencyName.getText().toString();
            newProfile.EmergencyContactInfo = new EmergencyContactInfo(emergencyContactName, emergencyContactNumber);

            // Handles submission of the Profile to the database
            newProfile.UpdateProfile(LoginID);

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            Intent i = new Intent(getApplicationContext(), ProfileManagement.class);
            startActivity(i);
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
