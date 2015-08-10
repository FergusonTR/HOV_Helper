package sweng500team2summer15.hov_helper.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import sweng500team2summer15.hov_helper.R;

/**
 * Created by Mike on 8/9/2015.
 */
public class ViewUser extends AppCompatActivity {

    // Info Fields
    TextView userName;
    TextView phoneNumber;
    TextView tvemail;
    TextView tvsex;
    TextView smokingPref;
    TextView tvcontactMethod;
    TextView ecName;
    TextView ecNumber;
    TextView tvCancel;

    //Progress Dialog
    private ProgressDialog pDialog;

    Profile newProfile = new Profile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);

        Intent thisIntent = getIntent();
        String userID = thisIntent.getStringExtra("UserID");

        tvCancel = (TextView) findViewById(R.id.tvUser_Cancel);

        new ReadProfile().execute(userID);

        //button click event
        tvCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // cancel
                finish();
            }
        });
    }

    // The following are called to update the displayed data during Async - onProgressUpdate
    public void displayNames (String first, String last){
        userName = (TextView) findViewById(R.id.txtUser_ContactName);
        userName.setText(first + " " + last);
    }
    public void displayPhone (String phone){
        phoneNumber = (TextView) findViewById(R.id.txtUser_Phone);
        phoneNumber.setText("Phone: " + phone);
    }
    public void displayEmail (String email){
        tvemail = (TextView) findViewById(R.id.txtUser_Email);
        tvemail.setText("Email: " + email);
    }
    public void displaySex (String sex) {
        tvsex = (TextView) findViewById(R.id.txtUserSex);

        sex = (sex == "MALE") ? "Male" : "Female";

        tvsex.setText("Sex: " + sex);
    }
    public void displaySmokingPref (String smokingPreference) {
        smokingPref = (TextView) findViewById(R.id.txtUserSex);

        if(smokingPreference == "NONSMOKE")
            smokingPref.setText("Smoking Preference: Non-Smoking");
        else if(smokingPreference == "NOPREF")
            smokingPref.setText("Smoking Preference: No Preference");
        else // Smoking
            smokingPref.setText("Smoking Preference: Smoking");
    }
    public void displayContactMethod (String contactMethod){
        tvcontactMethod = (TextView) findViewById(R.id.txtUser_Contact);

        if(contactMethod == "CALL")
            tvcontactMethod.setText("Preferred Contact Method: Call");
        else // TEXT
            tvcontactMethod.setText("Preferred Contact Method: Text");
    }
    public void displayEmergencyContact (String name, String number){
        ecName = (TextView) findViewById(R.id.txtUser_ContactName);
        ecName.setText("Name: " + name);

        ecNumber = (TextView) findViewById(R.id.txtUser_ContactNumber);
        ecNumber.setText("Phone: " + number);
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
            pDialog = new ProgressDialog(ViewUser.this);
            pDialog.setMessage("Reading User's Profile..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * retrieving event
         */
        protected String doInBackground(String... args) {

            String userID = args[0];

            newProfile = newProfile.GetProfile(userID);

            if (newProfile.LoginID != "") {

                String ecName = newProfile.EmergencyContactInfo.ContactName;
                String ecNumber = newProfile.EmergencyContactInfo.ContactNumber;

                publishProgress(newProfile.UserFirstName,
                        newProfile.UserLastName,
                        newProfile.PhoneNumber,
                        newProfile.EmailAddress,
                        String.valueOf(newProfile.UserSex),
                        String.valueOf(newProfile.UserSmokingPreference),
                        String.valueOf(newProfile.UserPreferredContactMethod),
                        ecName,
                        ecNumber);
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
            pDialog.dismiss();
        }
    }
}
