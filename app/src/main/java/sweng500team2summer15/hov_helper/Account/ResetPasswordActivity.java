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

import sweng500team2summer15.hov_helper.R;

public class ResetPasswordActivity extends ActionBarActivity {

    private ProgressDialog pDialog;

    Button bReset;
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        etEmail = (EditText) findViewById(R.id.etEmail);
        bReset = (Button) findViewById(R.id.bReset);

        // sign up button click event
        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sign up a new user in background thread
                new ResetPassword().execute();
            }
        });
    }

    // Background Async Task to sign up a new user
    class ResetPassword extends AsyncTask<String, String, String> {

        // Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ResetPasswordActivity.this);
            pDialog.setMessage("Resetting Password...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // verify a new user
        protected String doInBackground(String... args) {

            etEmail = (EditText) findViewById(R.id.etEmail);
            bReset = (Button) findViewById(R.id.bReset);

            AccountManagement resetUserPw = new AccountManagement();
            String result = resetUserPw.resetPassword(etEmail.getText().toString());

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
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
