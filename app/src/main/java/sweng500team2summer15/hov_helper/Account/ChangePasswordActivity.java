package sweng500team2summer15.hov_helper.Account;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.resource.Encryption;

public class ChangePasswordActivity extends ActionBarActivity {

    private ProgressDialog pDialog;

    Button bChange;
    EditText etCurrentPassword, etNewPassword, etReenterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        etCurrentPassword = (EditText) findViewById(R.id.etCurrentPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etReenterPassword = (EditText) findViewById(R.id.etReenterPassword);
        bChange = (Button) findViewById(R.id.bChange);

        // sign up button click event
        bChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sign up a new user in background thread
                new ChangePassword().execute();
            }
        });
    }

    // Background Async Task to sign up a new user
    class ChangePassword extends AsyncTask<String, String, String> {

        // Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChangePasswordActivity.this);
            pDialog.setMessage("Changing password...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // verify a new user
        protected String doInBackground(String... args) {

            etCurrentPassword = (EditText) findViewById(R.id.etCurrentPassword);
            etNewPassword = (EditText) findViewById(R.id.etNewPassword);
            etReenterPassword = (EditText) findViewById(R.id.etReenterPassword);
            bChange = (Button) findViewById(R.id.bChange);

            // get current user's login
            SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE);
            String login = pref.getString("LOGIN", "");

            // test to decrypt password
            String password = pref.getString("PASSWORD", "");
            Encryption decryption = Encryption.getDefault("Key", "Salt", new byte[16]);
            String decryptPw = decryption.decryptOrNull(password);

            AccountManagement changeUserPw = new AccountManagement();
            String result = changeUserPw.changePassword(login, etCurrentPassword.getText().toString(), etNewPassword.getText().toString(), etReenterPassword.getText().toString());

            return result;
        }


        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(String result) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if (result.equals("Success")) {
                Toast toast = Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_SHORT);
                toast.show();

                // for encrypting password
                Encryption encryption = Encryption.getDefault("Key", "Salt", new byte[16]);
                String encryptPw = encryption.encryptOrNull(etNewPassword.getText().toString());

                // write new credentials to file
                SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("PASSWORD", encryptPw);
                editor.commit();

                finish();
            }
            else {
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
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
}
