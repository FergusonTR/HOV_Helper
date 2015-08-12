package sweng500team2summer15.hov_helper.event.management;

import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import sweng500team2summer15.hov_helper.JSONParser;

/**
 * Created by Terry on 6/4/2015.
 */
public class EventList extends ArrayList{

    final JSONParser jsonParser = new JSONParser();
    public ArrayList<Event> arrayListOfEvents = new ArrayList<Event>();
    public ArrayList<Event> searchByDistance(String searchDate, Double startLatitude, Double startLongitude,
                                   Double endLatitude, Double endLongitude,
                                   Double startSearchDistance, Double endSearchDistance,
                                   String inputEventType ){
        //create a temporary event class to serve as the transport for each event

        ArrayList<Event> myEventList = new ArrayList<Event>();

        //create a place to hold all of the events returned
        JSONArray events = null;

        String start_time = searchDate + " 00:00:00";
        String end_time = searchDate + " 23:59:59";

        // url to search events
        String url_search_event_distance = "http://www.hovhelper.com/search_event.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_EVENTS = "events";
        String TAG_EVENTID ="eventId";
        String TAG_LOGINID = "loginId";
        String TAG_NUMBERSEATS = "numberSeats";
        String TAG_NUMBERAVAILABLE = "numberAvailable";
        String TAG_STARTTIME = "startTime";
        String TAG_ENDTIME = "endTime";
        String TAG_STARTLATITUDE ="startLatitude";
        String TAG_STARTLONGITUDE ="startLongitude";
        String TAG_STARTDISTANCE ="startDistance";
        String TAG_ENDLATITUDE = "endLatitude";
        String TAG_ENDLONGITUDE = "endLongitude";
        String TAG_ENDDISTANCE = "endDistance";
        String TAG_EVENTTYPE = "eventType";
        String TAG_RETURNTRIP = "returnTrip";

             // Building Parameters
        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> JSONparams = new ArrayList<NameValuePair>();
        JSONparams.add(new BasicNameValuePair("startTime", start_time));
        JSONparams.add(new BasicNameValuePair("endTime", end_time));
        JSONparams.add(new BasicNameValuePair("startLatitude", startLatitude.toString()));
        JSONparams.add(new BasicNameValuePair("startLongitude", startLongitude.toString()));
        JSONparams.add(new BasicNameValuePair("endLatitude", endLatitude.toString()));
        JSONparams.add(new BasicNameValuePair("endLongitude", endLongitude.toString()));
        JSONparams.add(new BasicNameValuePair("startDistance", startSearchDistance.toString()));
        JSONparams.add(new BasicNameValuePair("endDistance", endSearchDistance.toString()));
        JSONparams.add(new BasicNameValuePair("eventType", inputEventType));

        // getting JSON Object
        // Note that create event url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_search_event_distance, "GET", JSONparams);

       //GRAB RESULTS AND COPY TO THIS.ArrayList<Event>

        //Examine JSON Response
        Log.d("EventList",json.toString());

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully found events
                // Getting Array of Events
                events = json.getJSONArray(TAG_EVENTS);

                // loop through all of the events recovered
                for (int i=0; i < events.length();i++){
                    JSONObject c = events.getJSONObject(i);

                     //store each json item in an event
                    //tempEvent = new Event();
                    Event tempEvent = new Event();
                    //store each json item in an event
                    tempEvent.eventId =c.getInt(TAG_EVENTID);
                    tempEvent.loginId =c.getString(TAG_LOGINID);
                    tempEvent.numberSeats = c.getInt(TAG_NUMBERSEATS);
                    tempEvent.numberAvailable = c.getInt(TAG_NUMBERAVAILABLE);
                    tempEvent.start_Time = c.getString(TAG_STARTTIME);
                    tempEvent.end_Time = c.getString(TAG_ENDTIME);
                    tempEvent.startLatitude = c.getDouble(TAG_STARTLATITUDE);
                    tempEvent.startLongitude = c.getDouble(TAG_STARTLONGITUDE);
                    tempEvent.startSearchDistance = c.getDouble(TAG_STARTDISTANCE);
                    tempEvent.endLatitude = c.getDouble(TAG_ENDLATITUDE);
                    tempEvent.endLongitude = c.getDouble(TAG_ENDLONGITUDE);
                    tempEvent.endSearchDistance = c.getDouble(TAG_ENDDISTANCE);
                    tempEvent.eventType = c.getString(TAG_EVENTTYPE);
                    tempEvent.returnTrip = c.getString(TAG_RETURNTRIP);

                    //add to the array
                   myEventList.add(tempEvent);
                }
            } else {
                // failed to create event
                //return this;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Exception thrown empty list returned
            //return this;
        }
        return myEventList;
    }
    public ArrayList<Event> searchByUser(String loginId) {
        //create a temporary event class to serve as the transport for each event

        ArrayList<Event> myEventList = new ArrayList<Event>();
        Event tempEvent;

        //create a place to hold all of the events returned
        JSONArray events = null;

        // url to search events
        String url_search_my_events = "http://www.hovhelper.com/my_events.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_EVENTS = "events";
        String TAG_EVENTID = "eventId";
        String TAG_LOGINID = "loginId";
        String TAG_NUMBERSEATS = "numberSeats";
        String TAG_NUMBERAVAILABLE = "numberAvailable";
        String TAG_STARTTIME = "startTime";
        String TAG_ENDTIME = "endTime";
        String TAG_STARTLATITUDE = "startLatitude";
        String TAG_STARTLONGITUDE = "startLongitude";
        String TAG_ENDLATITUDE = "endLatitude";
        String TAG_ENDLONGITUDE = "endLongitude";
        String TAG_EVENTTYPE = "eventType";
        String TAG_EVENTNAME = "eventName";
        String TAG_RETURNTRIP = "returnTrip";
        String TAG_REQUESTEDSTATUS = "requestedStatus";


        // Building Parameters
        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> JSONparams = new ArrayList<NameValuePair>();
        JSONparams.add(new BasicNameValuePair("loginId", loginId));

        // getting JSON Object
        // Note that create event url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_search_my_events, "GET", JSONparams);

        //GRAB RESULTS AND COPY TO THIS.ArrayList<Event>

        //Examine JSON Response
        Log.d("EventList", json.toString());

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully found events
                // Getting Array of Events
                events = json.getJSONArray(TAG_EVENTS);

                // loop through all of the events recovered
                for (int i = 0; i < events.length(); i++) {
                    JSONObject c = events.getJSONObject(i);

                    tempEvent = new Event();
                    //store each json item in an event
                    tempEvent.eventId = c.getInt(TAG_EVENTID);
                    tempEvent.loginId = c.getString(TAG_LOGINID);
                    tempEvent.numberSeats = c.getInt(TAG_NUMBERSEATS);
                    tempEvent.numberAvailable = c.getInt(TAG_NUMBERAVAILABLE);
                    tempEvent.start_Time = c.getString(TAG_STARTTIME);
                    tempEvent.end_Time = c.getString(TAG_ENDTIME);
                    tempEvent.startLatitude = c.getDouble(TAG_STARTLATITUDE);
                    tempEvent.startLongitude = c.getDouble(TAG_STARTLONGITUDE);
                    tempEvent.endLatitude = c.getDouble(TAG_ENDLATITUDE);
                    tempEvent.endLongitude = c.getDouble(TAG_ENDLONGITUDE);
                    tempEvent.eventType = c.getString(TAG_EVENTTYPE);
                    tempEvent.eventName = c.getString(TAG_EVENTNAME);
                    tempEvent.returnTrip = c.getString(TAG_RETURNTRIP);
                    //tempEvent.requestedStatus = c.getString(TAG_REQUESTEDSTATUS);

                    //add to the array
                    myEventList.add(tempEvent);
                }
            } else {
                // failed to create event
                //return this;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Exception thrown empty list returned
            //return this;
        }
        return myEventList;
    }

 }




