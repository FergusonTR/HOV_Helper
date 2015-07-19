package sweng500team2summer15.hov_helper.Account;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sweng500team2summer15.hov_helper.R;

public class ResetPasswordActivity extends ActionBarActivity {

    private ProgressDialog pDialog;

    Button bReset;
    EditText etLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        etLogin = (EditText) findViewById(R.id.etLogin);
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
            pDialog.setMessage("Resetting password...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // verify a new user
        protected String doInBackground(String... args) {

            etLogin = (EditText) findViewById(R.id.etLogin);
            bReset = (Button) findViewById(R.id.bReset);

            AccountManagement resetUserPw = new AccountManagement();
            String result = resetUserPw.resetPassword(etLogin.getText().toString());

            return result;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(String result) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if (result.equals("Success")) {
                {
                    // write credentials to file
                    SharedPreferences pref = getSharedPreferences("prehovhelper", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("LOGIN", etLogin.getText().toString());
                    editor.commit();

                    Toast toast = Toast.makeText(getApplicationContext(), "Password Reset Successfully", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent i = new Intent(getApplicationContext(), VerificationCodeActivity.class);
                    startActivity(i);
                }
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
