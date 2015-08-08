package sweng500team2summer15.hov_helper.event.management;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 7/30/2015.
 */
public class UserInEvent extends Event {
    public static final String TAG = UserInEvent.class.getSimpleName();
    public int uniqueId;
    public String requestedParticipantLoginId;
    public String requestStatus;

    public int create(String requestedParticipantLoginId, int eventId, String requestStatus) {
        //This would add the event to the mySQL database.
        //Test Value
        this.eventId = 0;

        // url to create new user in event entry
        String url_create_event = "http://www.hovhelper.com/create_users_in_event.php";

        // JSON Node names
        String TAG_SUCCESS = "success";

        // Building Parameters
        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("loginId", requestedParticipantLoginId));
        params.add(new BasicNameValuePair("eventId", Integer.toString(eventId)));
        params.add(new BasicNameValuePair("requestStatus", requestStatus));

        // getting JSON Object
        // Note that create event url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_event,"POST", params);
        if (json == null)
        {
            Log.i(TAG, "JSON IS NULL");
        }

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                Log.i(TAG, "Successfully created event");
                // successfully created event
                this.uniqueId = json.getInt("event");
                createResult = this.uniqueId;


            } else {
                Log.i(TAG, "Failed to create event");
                // failed to create event
                createResult = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createResult;
    }


    // Get a list of 'Request for Ride Events' from other people for your event as the event owner
    public List<UserInEvent> getRequestedRides(String eventOwnerLoginId)
    {
        List<UserInEvent> requestedRides = new ArrayList<UserInEvent>();
        UserInEvent participant = null;

        String url_read_event = "http://www.hovhelper.com/read_users_in_event.php";


        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_EVENT= "event";
        String TAG_UNIQUE_ID = "uniqueId";
        String TAG_REQUESTED_PARTICIPANT_LOGIN_ID = "userInEventLoginId";
        String TAG_REQUEST_STATUS = "requestStatus";
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
        params.add(new BasicNameValuePair("loginId", "\"+eventOwnerLoginId+\"")); // Note /"quotes needed in case of space

        JSONObject json = jsonParser.makeHttpRequest(url_read_event,"GET", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                Log.i(TAG,"Json Success! found record(s)");

                // successfully read event
                JSONArray eventObj = json.getJSONArray(TAG_EVENT); // JSON Array
                for (int i = 0; i < eventObj.length(); i++)
                {
                    UserInEvent userInEventInstance = new UserInEvent();
                    // get event object from JSON Array
                    JSONObject event = eventObj.getJSONObject(0);

                    //load the results of the JSON Array into the current object
                    userInEventInstance.uniqueId = (event.getInt(TAG_UNIQUE_ID));
                    userInEventInstance.requestedParticipantLoginId = (event.getString(TAG_REQUESTED_PARTICIPANT_LOGIN_ID));
                    userInEventInstance.requestStatus = (event.getString(TAG_REQUEST_STATUS));
                    userInEventInstance.eventId = (event.getInt(TAG_EVENTID));
                    userInEventInstance.loginId = (event.getString(TAG_LOGINID));
                    userInEventInstance.numberSeats = (event.getInt(TAG_NUMBERSEATS));
                    userInEventInstance.numberAvailable = (event.getInt(TAG_NUMBERAVAILABLE));
                    userInEventInstance.start_Time = (event.getString(TAG_STARTTIME));
                    userInEventInstance.end_Time = (event.getString(TAG_ENDTIME));
                    userInEventInstance.daysofweek = (event.getString(TAG_DAYSOFWEEK));
                    userInEventInstance.event_interval = (event.getString(TAG_EVENT_INTERVAL));
                    userInEventInstance.startLatitude = (event.getDouble(TAG_STARTLATITUDE));
                    userInEventInstance.startLongitude = (event.getDouble(TAG_ENDLATITUDE));
                    userInEventInstance.endLatitude = (event.getDouble(TAG_ENDLATITUDE));
                    userInEventInstance.endLongitude = (event.getDouble(TAG_ENDLONGITUDE));
                    userInEventInstance.eventType = (event.getString(TAG_EVENTTYPE));
                    userInEventInstance.createTimeStamp = (event.getString(TAG_CREATEDTIMESTAMP));
                    requestedRides.add(userInEventInstance);
                }
            } else {
                Log.i(TAG, "NO RIDE REQUESTS FOUND!. SUCCESS: " + success);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestedRides;
    }

    // TODO: delete below, maybe same result as offeredRide?
    List<UserInEvent> getOfferedRides()
    {
        List<UserInEvent> offeredRides = new ArrayList<UserInEvent>();

        return offeredRides;
    }
}