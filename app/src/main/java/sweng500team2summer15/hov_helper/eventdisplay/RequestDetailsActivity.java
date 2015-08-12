package sweng500team2summer15.hov_helper.eventdisplay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Profile.ViewProfileActivity;
import sweng500team2summer15.hov_helper.Profile.ViewUser;
import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;
import sweng500team2summer15.hov_helper.event.management.SearchEventActivity;
import sweng500team2summer15.hov_helper.event.management.UserInEvent;

public class RequestDetailsActivity extends AppCompatActivity {
    private UserInEvent userInEvent;
    public static final String TAG = RequestDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        // get the event passed into this activity
        userInEvent = (UserInEvent)getIntent().getParcelableExtra("eventRequestForDetails");

        if (userInEvent != null)
        {
            // get my loginId
            // get the login Id
            SharedPreferences pref = getSharedPreferences("hovhelper", Context.MODE_PRIVATE); // specify SharedPreferences for a private file named "hovhelper"
            String login = pref.getString("LOGIN", "");                                       // key/value, get value for key "LOGIN"
            Log.i(TAG, "MY LOGIN ID: " + login);

            final String eventLoginId = userInEvent.loginId;
            String eventName = userInEvent.eventName;
            String eventId = String.valueOf(userInEvent.eventId);
            String status = userInEvent.requestStatus;

            Log.i(TAG, eventLoginId + eventName + eventId + status);
            TextView requestFromPrompt = (TextView) findViewById(R.id.textView12);
            requestFromPrompt.setText("Request To:");

            TextView requestFromField = (TextView) findViewById(R.id.txtRequestFrom);
            requestFromField.setText(eventLoginId);
            // make link
            requestFromField.setMovementMethod(LinkMovementMethod.getInstance());
            Spannable spans1 = (Spannable)requestFromField.getText();
            ClickableSpan clickSpan1 = new ClickableSpan() {

                @Override
                public void onClick(View widget)
                {
                    Intent intent = new Intent(RequestDetailsActivity.this.getApplicationContext(),ViewUser.class);
                    intent.putExtra("UserID", eventLoginId);
                    startActivity(intent);
                }
            };
            spans1.setSpan(clickSpan1, 0, spans1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);



            TextView profileIdPrompt = (TextView) findViewById(R.id.textView13);
            //profileIdPrompt.setText("");
            TextView profileId = (TextView) findViewById(R.id.txtProfileId);
            profileId.setText("None");

            // if no event name, do not show it

            TextView eventNameField = (TextView) findViewById(R.id.txtEventName);
            if (eventName.isEmpty())
            {
                eventNameField.setText("None");
            }
            else
            {
                eventNameField.setText(eventName);
            }


            // Clickable Event ID link
            TextView eventIdField = (TextView) findViewById(R.id.txtEventId);
            eventIdField.setText(eventId);
            eventIdField.setMovementMethod(LinkMovementMethod.getInstance());
            Spannable spans = (Spannable)eventIdField.getText();
            ClickableSpan clickSpan = new ClickableSpan() {

                @Override
                public void onClick(View widget)
                {
                    Intent intent = new Intent(RequestDetailsActivity.this.getApplicationContext(),EventDetailsActivity.class);
                    intent.putExtra("eventForDetails", userInEvent);
                    intent.putExtra("IsReadOnly", "True");
                    startActivity(intent);
                }
            };
            spans.setSpan(clickSpan, 0, spans.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


            TextView statusField = (TextView) findViewById(R.id.txtEventStatus);
            statusField.setText(status);

            final Button declineButton = (Button) findViewById(R.id.btnDecline);
            declineButton.setText("Cancel");

            final Button acceptButton = (Button) findViewById(R.id.btnRequestRide);
            acceptButton.setText("Sent Request");
            acceptButton.setEnabled(false);




        }
        else
        {
            Log.i(TAG, "USER IN EVENT IS NULL!!!");
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
