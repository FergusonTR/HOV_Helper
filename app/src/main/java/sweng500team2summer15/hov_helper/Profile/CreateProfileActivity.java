package sweng500team2summer15.hov_helper.Profile;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.event.management.SearchEventActivity;
import sweng500team2summer15.hov_helper.eventdisplay.RequestedEventsActivity;
import sweng500team2summer15.hov_helper.map.MapsActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;
import sweng500team2summer15.hov_helper.resource.Encryption;

/**
 * Created by Mike on 6/30/2015.
 */ // Event Listeners to move the page to the following pages
public class CreateProfileActivity extends AppCompatActivity{

    private ProgressDialog pDialog;

    EditText inputFirstName;
    EditText inputLastName;
    EditText inputPhoneNumber;
    TextView inputEmailAddress;
    RadioButton inputSexMale;
    RadioButton inputContactCall;
    RadioGroup inputSmokingPref;
    EditText inputEmergencyName;
    EditText inputEmergencyPhone;

    private String login;
    private String password;

    Profile newProfile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile_activity);

        inputFirstName = (EditText) findViewById(R.id.txtFirstName);
        inputLastName = (EditText) findViewById(R.id.txtLastName);
        inputPhoneNumber = (EditText) findViewById(R.id.txtPhone);
        inputEmailAddress = (TextView) findViewById(R.id.txtEmailAddress);
        inputEmergencyName = (EditText) findViewById(R.id.txtEmergencyName);
        inputEmergencyPhone = (EditText) findViewById(R.id.txtEmergencyNumber);
        inputSmokingPref = (RadioGroup)findViewById(R.id.rgSMOKE);
        inputSexMale = (RadioButton) findViewById(R.id.rbtnsexMale);
        inputContactCall = (RadioButton) findViewById(R.id.rbtnCALL);

        SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE); // specify SharedPreferences for a private file named "hovhelper"
        login = pref.getString("LOGIN", "");
        inputEmailAddress.setText("Email: " + login);

        //Create button
        Button btnConfirmProfile = (Button) findViewById(R.id.btnProfileConfirm);

        //button click event
        btnConfirmProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                newProfile.UserFirstName = inputFirstName.getText().toString();
                newProfile.UserLastName = inputLastName.getText().toString();
                newProfile.PhoneNumber = inputPhoneNumber.getText().toString();
                newProfile.EmailAddress = inputEmailAddress.getText().toString(); // This will be overwritten in the SubmitProfile call
                newProfile.UserSex = inputSexMale.isChecked() ? Profile.Sex.MALE : Profile.Sex.FEMALE;
                newProfile.UserPreferredContactMethod = inputContactCall.isChecked() ? Profile.PreferredContactMethod.CALL : Profile.PreferredContactMethod.TEXT;

                int selectedSmokingPref = inputSmokingPref.getCheckedRadioButtonId();
                switch (selectedSmokingPref)
                {
                    case R.id.rbtnSMOKE:
                        newProfile.UserSmokingPreference = Profile.SmokingPreference.SMOKE;
                        break;
                    case R.id.rbtnNONSMOKE:
                        newProfile.UserSmokingPreference = Profile.SmokingPreference.NONSMOKE;
                        break;
                    case R.id.rbtnNOPREF:
                        newProfile.UserSmokingPreference = Profile.SmokingPreference.NOPREF;
                        break;
                }

                String emergencyContactNumber = inputEmergencyPhone.getText().toString();
                String emergencyContactName = inputEmergencyName.getText().toString();
                newProfile.EmergencyContactInfo = new EmergencyContactInfo(emergencyContactName, emergencyContactNumber);

                // creating a new event in background thread
                new CreateNewProfile().execute();
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
            //Intent thisIntent = getIntent();
            //String LoginID = thisIntent.getStringExtra("LoginID");
            SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE); // specify SharedPreferences for a private file named "hovhelper"
            login = pref.getString("LOGIN", "");                                              // key/value, get value for key "LOGIN"
            password = pref.getString("PASSWORD", "");                                        // key/value, get value for key "PASSWORD" (currently encrypted)
            Encryption decryption = Encryption.getDefault("Key", "Salt", new byte[16]);       // class to encrypt/decrypt strings, see NOTE
            String decryptPw = decryption.decryptOrNull(password);                            // get password after decrypting

            // Handles submission of the Profile to the database
            newProfile.SubmitProfile(login,password);

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            Intent i = new Intent(getApplicationContext(), MainEventActivity.class);
            startActivity(i);
            finish();
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
