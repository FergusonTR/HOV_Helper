package sweng500team2summer15.hov_helper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import sweng500team2summer15.hov_helper.Account.ChangePasswordActivity;
import sweng500team2summer15.hov_helper.Account.SignInActivity;
import sweng500team2summer15.hov_helper.Account.SignUpActivity;
import sweng500team2summer15.hov_helper.event.management.MainEventActivity;

public class Start extends Activity implements View.OnClickListener {

    Button bSignIn, bSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        bSignIn = (Button) findViewById(R.id.bSignIn);
        bSignUp = (Button) findViewById(R.id.bSignUp);

        bSignIn.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bSignIn:
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case R.id.bSignUp:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }
}