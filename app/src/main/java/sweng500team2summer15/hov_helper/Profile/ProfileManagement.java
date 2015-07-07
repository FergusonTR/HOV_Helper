package sweng500team2summer15.hov_helper.Profile;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.Start;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;

// created by Mike
// Aims to handle additional screen for profile management buttons

public class ProfileManagement extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Create button
        Button btnCreateProfile = (Button) findViewById(R.id.btnCreateProfileScrn);
        Button btnUpdateProfile = (Button) findViewById(R.id.btnUpdateProfileScrn);
        Button btnDeleteProfile = (Button) findViewById(R.id.btnDeleteProfileScrn);
        Button btnViewProfile = (Button) findViewById(R.id.btnViewProfileScrn);
        Button btnEventManagement = (Button) findViewById(R.id.btnViewMainEventScrn);
        Button btnSignOut = (Button) findViewById(R.id.btnSignOut);

        //button click event
        btnCreateProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching profile create activity
                Intent i = new Intent(getApplicationContext(), CreateProfileActivity.class);
                startActivity(i);
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching profile update activity - TODO
                //Intent i = new Intent(getApplicationContext(), UpdateProfile.class);
                //startActivity(i);
            }
        });

        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching profile delete activity - TODO
                //Intent i = new Intent(getApplicationContext(), DeleteProfileActivity.class);
                //startActivity(i);
            }
        });

        btnViewProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching profile view activity - TODO
                Intent i = new Intent(getApplicationContext(), ViewProfileActivity.class);
                startActivity(i);
            }
        });

        btnEventManagement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching event management activity
                Intent i = new Intent(getApplicationContext(), MainEventActivity.class);
                startActivity(i);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching sign out
                Intent i = new Intent(getApplicationContext(), Start.class);
                startActivity(i);
            }
        });
    }
}