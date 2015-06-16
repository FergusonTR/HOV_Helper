package sweng500team2summer15.hov_helper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

//ToDo change the deprecated ActionBarActivity to something accepted.
public class MainActivity extends ActionBarActivity {

    Button btnNewEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons
        btnNewEvent = (Button) findViewById(R.id.btnCreateEvent);

        btnNewEvent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //Launching create new event activity
                Intent i = new Intent(getApplicationContext(), CreateEventActivity.class);
                startActivity(i);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
