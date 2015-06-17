package sweng500team2summer15.hov_helper;

        import java.util.ArrayList;
        import java.util.List;

        import org.apache.http.NameValuePair;
        import org.apache.http.message.BasicNameValuePair;
        import org.json.JSONException;
        import org.json.JSONObject;

        import android.content.Context;
        import android.content.ContextWrapper;
        import android.content.Intent;

        import java.text.DateFormat;
        import java.util.Date;
        import java.util.Calendar;

        import java.text.SimpleDateFormat;


/**
 * Created by Terry on 6/4/2015.
 */
public class Event {
    //Portions code was borrowed from http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/

    JSONParser jsonParser = new JSONParser();

    String loginId = "testLoginId";
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


   public int create(String loginId, String password) {
        //This would add the event to the mySQL database.
        //Test Value
       this.eventId = 0;

       //ToDo obtaining the eventID assigned after SQL Update.
       //ToDo develop a way of performing the create over an SSL.
       //ToDo have user data verified prior to creation of event.
       //ToDo Consider creating a counter to limit the number of entries created by one user over a period of time.
       //ToDo Timestamps

       // url to create new product
       //ToDo Change this to point to the Hovhelper website
       String url_create_event = "http://192.168.1.16/create_event.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_EVENT_ID = "eventId";

       // Building Parameters
      //ToDo remove deprecated approach and use URLBuilder instead
       List<NameValuePair> params = new ArrayList<NameValuePair>();
       params.add(new BasicNameValuePair("loginId", loginId));
       params.add(new BasicNameValuePair("password", password));
       params.add(new BasicNameValuePair("numberSeats", Integer.toString(this.numberSeats)));
      //params.add(new BasicNameValuePair("locationId", Integer.toString(this.locationId)));
      //params.add(new BasicNameValuePair("frequencyId", Integer.toString(this.frequencyId)));
      //params.add(new BasicNameValuePair("start_Time", this.start_Time));
      //params.add(new BasicNameValuePair("end_Time", this.end_Time));
      //params.add(new BasicNameValuePair("eventType", this.eventType));
      //params.add(new BasicNameValuePair("occurrence", this.occurrence));

       // getting JSON Object
       // Note that create event url accepts POST method
       JSONObject json = jsonParser.makeHttpRequest(url_create_event,"POST", params);

       // check log cat for response
       //Log.d("Create Response", json.toString());

       // check for success tag
       try {
           int success = json.getInt(TAG_SUCCESS);

           if (success == 1) {
               // successfully created event
               this.eventId = json.getInt(TAG_EVENT_ID);
               //Intent i = new Intent(getApplicationContext(), MainActivity.class);
               //startActivity(i);
               // closing this screen
               //finish();
           } else {
               // failed to create event
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
        return this.eventId;
    }

   public Event read(int eventID){
       //This would retrieve  the event from the mySQL database.

        Event retrieveEvent = new Event();

       //ToDo SQL based JSON/PHP script work

        return retrieveEvent;
    }

   public boolean update(String loginId, String password, int eventID, Event updateEvent){
      //This would update an event with information changed from the screen and report success
       boolean success = false;
      //ToDo SQL based JSON/PHP script work

       return success;
   }

   public boolean delete (String loginId, String password, int eventId) {
       //This would delete an report success
       boolean success = false;
      //ToDo SQL based JSON/PHP script work
       return success;
   }

}

