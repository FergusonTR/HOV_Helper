package sweng500team2summer15.hov_helper.Account;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.Start;

public class SignUpActivity extends Activity {

    private ProgressDialog pDialog;
    private int _success = 0;

    Button bSignUp;
    EditText etLogin, etPassword;
    TextView tvCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        tvCancel = (TextView) findViewById(R.id.tvCancel);

        // sign up button click event
        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sign up a new user in background thread
                new SignUpUser().execute();
            }
        });

        // cancel text click event
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.tvCancel:
                        startActivity(new Intent(SignUpActivity.this, Start.class));
                        break;
                }
            }
        });
    }

    // Background Async Task to sign up a new user
    class SignUpUser extends AsyncTask<String, String, String> {

        // Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Creating Sign Up...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // sign up a new user
        protected String doInBackground(String... args) {

            etLogin = (EditText) findViewById(R.id.etLogin);
            etPassword = (EditText) findViewById(R.id.etPassword);
            bSignUp = (Button) findViewById(R.id.bSignUp);
            tvCancel = (TextView) findViewById(R.id.tvCancel);

            AccountManagement newUser = new AccountManagement();
            _success = newUser.signUp(etLogin.getText().toString(), etPassword.getText().toString());

            return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            // TODO - placeholder code
            if (_success == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                builder.setMessage("Success")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else {
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage("Failure")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                                    startActivity(i);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
            _success = 0;
        }
    }
}