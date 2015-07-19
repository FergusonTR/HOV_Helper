package sweng500team2summer15.hov_helper.Account;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sweng500team2summer15.hov_helper.R;

public class ResendVerificationCodeActivity extends ActionBarActivity {

    private ProgressDialog pDialog;

    Button bResend;
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resendverificationcode);

        etEmail = (EditText) findViewById(R.id.etEmail);
        bResend = (Button) findViewById(R.id.bResend);

        // sign up button click event
        bResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sign up a new user in background thread
                new ResendVerificationCode().execute();
            }
        });
    }

    // Background Async Task to sign up a new user
    class ResendVerificationCode extends AsyncTask<String, String, String> {

        // Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ResendVerificationCodeActivity.this);
            pDialog.setMessage("Resending Verification Code...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // verify a new user
        protected String doInBackground(String... args) {

            etEmail = (EditText) findViewById(R.id.etEmail);
            bResend = (Button) findViewById(R.id.bResend);

            AccountManagement resendUserVCode = new AccountManagement();
            String result = resendUserVCode.resendVerificationCode(etEmail.getText().toString());

            return result;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(String result) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if (result.equals("Success")) {
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Verification Code Resent", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent i = new Intent(getApplicationContext(), VerificationCodeActivity.class);
                    startActivity(i);
                }
            }
            else {
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResendVerificationCodeActivity.this);
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
