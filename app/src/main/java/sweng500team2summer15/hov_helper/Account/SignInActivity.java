package sweng500team2summer15.hov_helper.Account;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sweng500team2summer15.hov_helper.Profile.CreateProfileActivity;
import sweng500team2summer15.hov_helper.Profile.Profile;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.Start;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;
import sweng500team2summer15.hov_helper.resource.Encryption;

public class SignInActivity extends Activity {

    private ProgressDialog pDialog;

    Button bSignIn;
    EditText etLogin, etPassword;
    TextView tvCancel, tvForgot, tvResendVerificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bSignIn = (Button) findViewById(R.id.bSignIn);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvForgot = (TextView) findViewById(R.id.tvForgot);
        tvResendVerificationCode = (TextView) findViewById(R.id.tvResendVerificationCode);

        // sign up button click event
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sign up a new user in background thread
                new SignInUser().execute();
            }
        });

        // cancel text click event
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tvCancel:
                        startActivity(new Intent(SignInActivity.this, Start.class));
                        finish();
                        break;
                }
            }
        });

        // forgot password text click event
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tvForgot:
                        startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
                        break;
                }
            }
        });

        tvResendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.tvResendVerificationCode:
                        startActivity(new Intent(SignInActivity.this, ResendVerificationCodeActivity.class));
                        break;
                }
            }
        });

        SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE);
        String login = pref.getString("LOGIN", "");
        String password = pref.getString("PASSWORD", "");

        // if private file already contains a user's login and password,
        // s/he is still logged in! redirect to main event screen
        if (!login.isEmpty() && !password.isEmpty()) {
            Intent i = new Intent(getApplicationContext(), MainEventActivity.class);
            startActivity(i);
            finish();
        }
    }

    // Background Async Task to sign in the user
    class SignInUser extends AsyncTask<String, String, String> {

        // Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignInActivity.this);
            pDialog.setMessage("Signing In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // sign in the user
        protected String doInBackground(String... args) {

            etLogin = (EditText) findViewById(R.id.etLogin);
            etPassword = (EditText) findViewById(R.id.etPassword);
            bSignIn = (Button) findViewById(R.id.bSignIn);
            tvCancel = (TextView) findViewById(R.id.tvCancel);
            tvForgot = (TextView) findViewById(R.id.tvForgot);

            AccountManagement user = new AccountManagement();
            user.login = etLogin.getText().toString();
            user.password = etPassword.getText().toString();
            String result = user.signIn(user.login, user.password);

            return result;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(String result) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if (result.equals("Success")) {
                // for encrypting password
                Encryption encryption = Encryption.getDefault("Key", "Salt", new byte[16]);
                String encryptPw = encryption.encryptOrNull(etPassword.getText().toString());

                // write credentials to file
                SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("LOGIN", etLogin.getText().toString());
                editor.putString("PASSWORD", encryptPw);
                editor.commit();

                // We're logged in, check to see if a profile already exists...
                new ReadProfile().execute(etLogin.getText().toString());
            }
            else {
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    builder.setMessage(result)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        }
    }

    /**
     * Background Async Task to read a profile
     * */
    class ReadProfile extends AsyncTask<String, String, String> {

        String readsuccess = "false";
        String loginID = "";

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignInActivity.this);
            pDialog.setMessage("Reading Profile..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * retrieving profile
         */
        protected String doInBackground(String... args) {

            loginID = args[0];

            Profile newProfile = new Profile();
            newProfile = newProfile.GetProfile(loginID);

            if (newProfile.LoginID != "")
                readsuccess = "true";
            else
                readsuccess = "false";

            return readsuccess;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if(readsuccess == "true")
            {
                // profile exists. redirect to event mgmt screen
                Intent i = new Intent(getApplicationContext(), MainEventActivity.class);
                startActivity(i);
                finish();
            }
            else if(readsuccess == "false")
            {
                // profile is not created, redirect to profile creation screen
                Intent i = new Intent(getApplicationContext(), CreateProfileActivity.class);
                i.putExtra("LoginID", loginID);
                startActivity(i);
                finish();
            }
        }
    }
}