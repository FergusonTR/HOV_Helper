package sweng500team2summer15.hov_helper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//ToDo change the deprecated ActionBarActivity to something accepted.
public class MainActivity extends Activity {

    Button btnNewEvent;
    Button btnReadEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons
        btnNewEvent = (Button) findViewById(R.id.btnCreateEventScrn);
        btnReadEvent = (Button) findViewById(R.id.btnReadEventScrn);

        btnNewEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //Launching create new event activity
                Intent i = new Intent(getApplicationContext(), CreateEventActivity.class);
                startActivity(i);
            }
        });

        btnReadEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //Launching read event activity
                Intent i = new Intent(getApplicationContext(), ReadEventActivity.class);
                startActivity(i);
            }
        });
            }
        }



