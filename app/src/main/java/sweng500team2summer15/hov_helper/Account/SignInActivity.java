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

import sweng500team2summer15.hov_helper.MainActivity;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.Start;

public class SignInActivity extends Activity {

    private ProgressDialog pDialog;
    private int _success = 0;

    Button bSignIn;
    EditText etLogin, etPassword;
    TextView tvCancel, tvForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bSignIn = (Button) findViewById(R.id.bSignIn);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvForgot = (TextView) findViewById(R.id.tvForgot);

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
                        startActivity(new Intent(SignInActivity.this, Start.class));
                        break;
                }
            }
        });
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
            bSignIn = (Button) findViewById(R.id.bSignUp);
            tvCancel = (TextView) findViewById(R.id.tvCancel);
            tvForgot = (TextView) findViewById(R.id.tvForgot);

            AccountManagement user = new AccountManagement();
            _success = user.signIn(etLogin.getText().toString(), etPassword.getText().toString());

            return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            // TODO - placeholder code
            if (_success == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setMessage("Success")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else {
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    builder.setMessage("Failure")
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
            }
            _success = 0;
        }
    }
}