package sweng500team2summer15.hov_helper;

        import java.util.ArrayList;
        import java.util.List;

        import org.apache.http.NameValuePair;
        import org.apache.http.message.BasicNameValuePair;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import android.content.Context;
        import android.content.ContextWrapper;
        import android.content.Intent;
        import android.widget.EditText;

        import java.text.DateFormat;
        import java.util.Date;
        import java.util.Calendar;

        import java.text.SimpleDateFormat;


/**
 * Created by Terry on 6/4/2015.
 */
public class Event {
    //Portions code was borrowed from http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/

    final JSONParser jsonParser = new JSONParser();

    String loginId = "testLoginId";
    int eventId = 0;
    int numberSeats = 0;
    int numberAvailable = 0;
    Double startLatitude = 44.033300;
    Double startLongitude = -71.134474;
    Double endLatitude = 41.584987;
    Double endLongitude = -71.264558;
    String eventType = "Ride";
    String event_interval = "weekly";
    String daysofweek ="Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday";

    DateFormat dateformat =  new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");


    Date start_today = Calendar.getInstance().getTime();
    String start_Time = dateformat.format(start_today);

    Date end_today = Calendar.getInstance().getTime();
    String end_Time = dateformat.format(end_today);

    String createTimeStamp ="";


   public int create(String loginId, String password) {
        //This would add the event to the mySQL database.
        //Test Value
       this.eventId = 0;

       //ToDo develop a way of performing the create over an SSL.
       //ToDo have user data verified prior to creation of event.
       //ToDo Consider creating a counter to limit the number of entries created by one user over a period of time.

       // url to create new product
       String url_create_event = "http://www.hovhelper.com/create_event.php";

        // JSON Node names
        String TAG_SUCCESS = "success";

       // Building Parameters
      //ToDo remove deprecated approach and use URLBuilder instead
       List<NameValuePair> params = new ArrayList<NameValuePair>();
       params.add(new BasicNameValuePair("loginId", loginId));
       params.add(new BasicNameValuePair("password", password));
       params.add(new BasicNameValuePair("numberSeats", Integer.toString(this.numberSeats)));
       params.add(new BasicNameValuePair("numberAvailable", Integer.toString(this.numberSeats)));
       params.add(new BasicNameValuePair("startTime", this.start_Time));
       params.add(new BasicNameValuePair("endTime", this.end_Time));
       params.add(new BasicNameValuePair("eventType", this.eventType));
       params.add(new BasicNameValuePair("event_interval", this.event_interval));
       params.add(new BasicNameValuePair("startLatitude", this.startLatitude.toString()));
       params.add(new BasicNameValuePair("startLongitude", this.startLongitude.toString()));
       params.add(new BasicNameValuePair("endLatitude", this.endLatitude.toString()));
       params.add(new BasicNameValuePair("endLongitude", this.endLongitude.toString()));
       params.add(new BasicNameValuePair("daysofweek", this.daysofweek));

       // getting JSON Object
       // Note that create event url accepts POST method
       JSONObject json = jsonParser.makeHttpRequest(url_create_event,"POST", params);

       // check for success tag
       try {
           int success = json.getInt(TAG_SUCCESS);

           if (success == 1) {
               // successfully created event
               this.eventId = json.getInt("event");

           } else {
               // failed to create event
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
        return this.eventId;
    }

   public Event read(int eventId){
       //This would retrieve  the event from the mySQL database.

        //Event retrieveEvent = new Event();

       // url to create new product
       //ToDo Change this to point to the Hovhelper website
       String url_read_event = "http://www.hovhelper.com/read_event.php";

       // JSON Node names
       String TAG_SUCCESS = "success";
       String TAG_EVENT = "event";
       String TAG_EVENTID = "eventId";
       String TAG_LOGINID = "loginId";
       String TAG_NUMBERSEATS = "numberSeats";
       String TAG_NUMBERAVAILABLE = "numberAvailable";
       String TAG_STARTTIME = "startTime";
       String TAG_ENDTIME = "endTime";
       String TAG_DAYSOFWEEK = "daysofweek";
       String TAG_EVENT_INTERVAL = "event_interval";
       String TAG_STARTLATITUDE = "startLatitude";
       String TAG_ENDLATITUDE = "endLatitude";
       String TAG_ENDLONGITUDE = "endLongitude";
       String TAG_EVENTTYPE = "eventType";
       String TAG_CREATEDTIMESTAMP = "createdTimeStamp";


       // Building Parameters
       //ToDo remove deprecated approach and use URLBuilder instead
       List<NameValuePair> params = new ArrayList<NameValuePair>();
       params.add(new BasicNameValuePair("eventId", Integer.toString(eventId)));

        JSONObject json = jsonParser.makeHttpRequest(url_read_event,"GET", params);

        // check for success tag
       try {
           int success = json.getInt(TAG_SUCCESS);

           if (success == 1) {
               // successfully read event
               JSONArray eventObj = json.getJSONArray(TAG_EVENT); // JSON Array

               // get event object from JSON Array
               JSONObject event = eventObj.getJSONObject(0);

               //load the results of the JSON Array into the current object
               this.eventId = (event.getInt(TAG_EVENTID));
               this.loginId = (event.getString(TAG_LOGINID));
               this.numberSeats = (event.getInt(TAG_NUMBERSEATS));
               this.numberAvailable = (event.getInt(TAG_NUMBERAVAILABLE));
               this.start_Time = (event.getString(TAG_STARTTIME));
               this.end_Time = (event.getString(TAG_ENDTIME));
               this.daysofweek = (event.getString(TAG_DAYSOFWEEK));
               this.event_interval = (event.getString(TAG_EVENT_INTERVAL));
               this.startLatitude = (event.getDouble(TAG_STARTLATITUDE));
               this.startLongitude = (event.getDouble(TAG_ENDLATITUDE));
               this.endLatitude = (event.getDouble(TAG_ENDLATITUDE));
               this.endLongitude = (event.getDouble(TAG_ENDLONGITUDE));
               this.eventType = (event.getString(TAG_EVENTTYPE));
               this.createTimeStamp = (event.getString(TAG_CREATEDTIMESTAMP));


           } else {
               // Event with EventId not found
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
       return null;
    }

   public boolean update(String loginId, String password, int eventID, Event updateEvent){
      //This would update an event with information changed from the screen and report success
       boolean success = false;
      //ToDo SQL based JSON/PHP script work

       return false;
   }

   public boolean delete (String loginId, String password, int eventId) {
       //This would delete an report success
       //boolean success = false;
      //ToDo SQL based JSON/PHP script work
       return false;
   }

}

