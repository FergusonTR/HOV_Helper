package sweng500team2summer15.hov_helper;

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


public class SignUpActivity extends Activity {

    //Progress Dialog
    private ProgressDialog pDialog;

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

        //button click event
        bSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // sign up a new user in background threa
                new SignUpUser().execute();
            }
        });

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

    /**
     * Background Async Task to sign up a new user
     */
    class SignUpUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Creating Sign Up...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * sign up a new user
         */
        protected String doInBackground(String... args) {

            etLogin = (EditText) findViewById(R.id.etLogin);
            etPassword = (EditText) findViewById(R.id.etPassword);
            bSignUp = (Button) findViewById(R.id.bSignUp);
            tvCancel = (TextView) findViewById(R.id.tvCancel);

            AccountManagement newUser = new AccountManagement();
            newUser.signUp(etLogin.getText().toString(), etPassword.getText().toString());

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            // TODO - placeholder code
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            builder.setMessage("Success")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            Intent i = new Intent(getApplicationContext(), SignIn.class);
            startActivity(i);
        }
    }
}