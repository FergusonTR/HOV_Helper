package sweng500team2summer15.hov_helper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends ActionBarActivity implements View.OnClickListener {

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

        bSignIn.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bSignIn:

                break;
            case R.id.tvCancel:
                startActivity(new Intent(this, Start.class));
                break;
            case R.id.tvForgot:

                break;
        }
    }
}