package sweng500team2summer15.hov_helper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends ActionBarActivity implements View.OnClickListener {

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

        bSignUp.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSignUp:
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();

                Login log = new Login(login, password);
                registerLogin(log);
                break;
            case R.id.tvCancel:
                startActivity(new Intent(this, Start.class));
                break;
        }
    }

    private void registerLogin(Login login)
    {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeLoginDataInBackground(login, new GetUserCallback() {
            @Override
            public void done(Login returnedLogin) {
                startActivity(new Intent(SignUp.this, SignIn.class));
            }
        });
    }
}