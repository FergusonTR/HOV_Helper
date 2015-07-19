package sweng500team2summer15.hov_helper.Account;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sweng500team2summer15.hov_helper.R;

public class VerificationCodeActivity extends ActionBarActivity {

    private ProgressDialog pDialog;

    Button bVerify;
    EditText etVerificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationcode);

        etVerificationCode = (EditText) findViewById(R.id.etVerificationCode);
        bVerify = (Button) findViewById(R.id.bVerify);

        // sign up button click event
        bVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sign up a new user in background thread
                new VerifyUser().execute();
            }
        });
    }

    // Background Async Task to sign up a new user
    class VerifyUser extends AsyncTask<String, String, String> {

        // Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(VerificationCodeActivity.this);
            pDialog.setMessage("Verifying...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // verify a new user
        protected String doInBackground(String... args) {

            etVerificationCode = (EditText) findViewById(R.id.etVerificationCode);
            bVerify = (Button) findViewById(R.id.bVerify);

            AccountManagement verifyUser = new AccountManagement();
            String result = verifyUser.verifyAccount(etVerificationCode.getText().toString());

            return result;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(String result) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if (result == null) {
                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(i);
            }
            else {
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VerificationCodeActivity.this);
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
