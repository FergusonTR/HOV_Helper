package sweng500team2summer15.hov_helper;

import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
//import java.util.TimeZone;
import java.text.SimpleDateFormat;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


/**
 * Created by Terry on 6/4/2015.
 */
public class Event {
    int loginId = 0;
    int eventId = 0;
    int numberSeats = 0;
    int locationId = 0;
    int frequencyId = 0;
    String eventType = "";
    String occurrence = "";

    DateFormat st =  new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    DateFormat et =  new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    Date today = Calendar.getInstance().getTime();
    String start_Time = st.format(today);
    String end_Time = et.format(today);

    //SimpleDateFormat endTime = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

    /*Location Object needs to be defined*/
    //String startLocation;
    //String endLocation;
    //Date startTime;
    //SimpleDateFormat format = new SimpleDateFormat("");

   public int create(int loginId, String password) {
        //This would add the event to the mySQL database.
        //Test Value
        int eventId = 0;
        int ownerId = loginId;

       //ToDo obtaining the eventID assigned after SQL Update.
       //ToDo develop a way of performing the create over an SSL.
       //ToDo have user data verified prior to creation of event.
       //ToDo Consider creating a counter to limit the number of entries created by one user over a period of time.
       //ToDo Timestamps

       //This code was borrowed from http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
       JSONParser jsonParser = new JSONParser();

       // url to create new product
       //ToDo Change this to point to the Hovhelper website
       String url_create_event = "http://192.168.1.6/create_event.php";

        // JSON Node names
        String TAG_SUCCESS = "success";

       // Building Parameters
      //ToDo remove deprecated approach and use URLBuilder instead
       List<NameValuePair> params = new ArrayList<NameValuePair>();
       params.add(new BasicNameValuePair("ownerId", Integer.toString(ownerId)));
       params.add(new BasicNameValuePair("numberSeats", Integer.toString(this.numberSeats)));
       params.add(new BasicNameValuePair("locationId", Integer.toString(this.locationId)));
       params.add(new BasicNameValuePair("frequencyId", Integer.toString(this.frequencyId)));
       params.add(new BasicNameValuePair("start_Time", this.start_Time));
       params.add(new BasicNameValuePair("end_Time", this.end_Time));
       params.add(new BasicNameValuePair("eventType", this.eventType));
       params.add(new BasicNameValuePair("occurrence", this.occurrence));

       // getting JSON Object
       // Note that create product url accepts POST method
       JSONObject json = jsonParser.makeHttpRequest(url_create_event,"POST", params);

       // check log cat for response
       Log.d("Create Response", json.toString());

       // check for success tag
       try {
           int success = json.getInt(TAG_SUCCESS);

           if (success == 1) {
               // successfully created product
               //Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
               //startActivity(i);

               // closing this screen
               //finish();
           } else {
               // failed to create product
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }

        return eventId;
    }

   public Event read(int eventID){
       //This would retrieve  the event from the mySQL database.

        Event retrieveEvent = new Event();

       //ToDo SQL based JSON/PHP script work

        return retrieveEvent;
    }

   public boolean update(int loginId, String password, int eventID, Event updateEvent){
      //This would update an event with information changed from the screen and report success
       boolean success = false;
      //ToDo SQL based JSON/PHP script work

       return success;
   }

   public boolean delete (int loginId, String password, int eventId) {
       //This would delete an report success
       boolean success = false;
      //ToDo SQL based JSON/PHP script work
       return success;
   }
}
