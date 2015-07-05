package sweng500team2summer15.hov_helper.event.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sweng500team2summer15.hov_helper.R;

//ToDo change the deprecated ActionBarActivity to something accepted.
public class MainEventActivity extends Activity {

    Button btnNewEvent;
    Button btnReadEvent;
    Button btnDeleteEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        //Buttons
        btnNewEvent = (Button) findViewById(R.id.btnCreateEventScrn);
        btnReadEvent = (Button) findViewById(R.id.btnReadEventScrn);
        btnDeleteEvent = (Button) findViewById(R.id.btnDeleteEventScrn);

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

        btnDeleteEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //Launching read event activity
                Intent i = new Intent(getApplicationContext(), DeleteEventActivity.class);
                startActivity(i);
            }
        });


            }
        }



