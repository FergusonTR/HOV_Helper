package sweng500team2summer15.hov_helper;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CreateEventActivity extends ActionBarActivity {

    EditText inputNumberSeats;
    EditText inputEventType;
    EditText inputLoginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        //Edit Text
        inputNumberSeats = (EditText) findViewById(R.id.editText_numberSeats);
        inputEventType = (EditText) findViewById(R.id.editText_eventType);
        inputLoginId = (EditText) findViewById(R.id.editText_loginId);

        //Create button
        Button btnAddEvent = (Button) findViewById(R.id.btnAddEvent);

        //button click event
        btnAddEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Event newEvent = new Event();

                //newEvent.numberSeats = Integer.parseInt(inputNumberSeats.toString());
                //newEvent.eventType = inputEventType.getText().toString();
                //newEvent.loginId =  Integer.parseInt(inputLoginId.toString());

                newEvent.create(newEvent.loginId,"Sweng_500");



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
