package sweng500team2summer15.hov_helper.event.management;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import sweng500team2summer15.hov_helper.JSONParser;


/**
 * Created by Terry on 6/4/2015.
 */
public class Event implements Parcelable{
    //Portions code was borrowed from http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
    final JSONParser jsonParser = new JSONParser();
    int createResult = 0;
    int updateResult = 0;
    public String loginId = "testLoginId";
    public int eventId = 0;
    public int numberSeats = 0;
    public int numberAvailable = 0;
    public Double startLatitude = 0.0;
    public Double startLongitude = 0.0;
    public Double endLatitude = 0.0;
    public Double endLongitude = 0.0;
    public String eventType = "Ride";
    public String event_interval = "weekly";
    public String returnTrip = "no";
    public String daysofweek ="Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday";

    DateFormat dateformat =  new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

    Date start_today = Calendar.getInstance().getTime();
    public String start_Time = dateformat.format(start_today);

    Date end_today = Calendar.getInstance().getTime();
    public String end_Time = dateformat.format(end_today);

    public String createTimeStamp ="";
    public String eventName ="";
    public Event(){
    }
    public Event(Parcel in) {
        setCreateResult(in.readInt());
        setUpdateResult(in.readInt());
        setLoginId(in.readString());
        setEventId(in.readInt());
        setNumberSeats(in.readInt());
        setNumberAvailable(in.readInt());
        setStartLatitude(in.readDouble());
        setStartLongitude(in.readDouble());
        setEndLatitude(in.readDouble());
        setEndLongitude(in.readDouble());
        setEventType(in.readString());
        setEventInterval(in.readString());
        setDaysOfWeek(in.readString());
        setReturnTrip(in.readString());
        setStart_Time(in.readString());
        setEnd_Time(in.readString());
        setCreateTimeStamp(in.readString());
        setEventName(in.readString());
    }
    public void setCreateResult(int createResult){
        this.createResult = createResult;
    }
    public void setUpdateResult(int updateResult){
        this.updateResult = updateResult;
    }
    public void setLoginId(String loginId){
        this.loginId = loginId;
    }
    public void setEventId(int eventId){
        this.eventId = eventId;
    }
    public void setNumberSeats(int numberSeats){
        this.numberSeats = numberSeats;
    }
    public void setNumberAvailable(int numberAvailable){
        this.numberAvailable = numberAvailable;
    }
    public void setStartLatitude(double startLatitude){
        this.startLatitude = startLatitude;
    }
    public void setStartLongitude(double startLongitude){
        this.startLongitude = startLongitude;
    }
    public void setEndLatitude(double endLatitude){
        this.endLatitude = endLatitude;
    }
    public void setEndLongitude(double endLongitude){
        this.endLongitude = endLongitude;
    }
    public void setEventType (String eventType){
        this.eventType = eventType;
    }
    public void setEventInterval (String event_interval) {
        this.event_interval = event_interval;
    }
    public void setDaysOfWeek (String daysofweek){
        this.daysofweek = daysofweek;
    }
    public void setReturnTrip (String returnTrip) {
        this.returnTrip = returnTrip;
    }
    public void setStart_Time (String start_Time) {
        this.start_Time = start_Time;
    }
    public void setEnd_Time (String end_Time){
        this.end_Time = end_Time;
    }
    public void setCreateTimeStamp (String createTimeStamp){
        this.createTimeStamp = createTimeStamp;
    }
    public void setEventName (String eventName){
        this.eventName = eventName;
    }
    public int getCreateResult(){
        return createResult;
    }
    public int getUpdateResult(){
        return updateResult;
    }
    public String getLoginId (){
        return loginId;
    }
    public int getEventId (){
        return eventId;
    }
    public int getNumberSeats(){
        return numberSeats;
    }
    public int getNumberAvailable(){
        return numberAvailable;
    }
    public double getStartLatitude (){
        return startLatitude;
    }
    public double getStartLongitude (){
        return startLongitude;
    }
    public double getEndLatitude (){
        return endLatitude;
    }
    public double getEndLongitude () {
        return endLongitude;
    }
    public String getEventType () {
        return eventType;
    }
    public String getEvent_interval () {
        return event_interval;
    }
    public String getDaysofweek () {
        return daysofweek;
    }
    public String getReturnTrip() {
        return returnTrip;
    }
    public String getStart_Time () {
        return start_Time;
    }
    public String getEnd_Time (){
        return end_Time;
    }
    public String getCreateTimeStamp () {
        return createTimeStamp;
    }
    public String getEventName(){
        return eventName;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel in, int arg1){
        in.writeInt(getCreateResult());
        in.writeInt(getUpdateResult());
        in.writeString(getLoginId());
        in.writeInt(getEventId());
        in.writeInt(getNumberSeats());
        in.writeInt(getNumberAvailable());
        in.writeDouble(getStartLatitude());
        in.writeDouble(getStartLongitude());
        in.writeDouble(getEndLatitude());
        in.writeDouble(getEndLongitude());
        in.writeString(getEventType());
        in.writeString(getEvent_interval());
        in.writeString(getDaysofweek());
        in.writeString(getReturnTrip());
        in.writeString(getStart_Time());
        in.writeString(getEnd_Time());
        in.writeString(getCreateTimeStamp());
        in.writeString(getEventName());
    }
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
        params.add(new BasicNameValuePair("numberAvailable", Integer.toString(this.numberAvailable)));
        params.add(new BasicNameValuePair("startTime", this.start_Time));
        params.add(new BasicNameValuePair("endTime", this.end_Time));
        params.add(new BasicNameValuePair("eventType", this.eventType));
        params.add(new BasicNameValuePair("event_interval", this.event_interval));
        params.add(new BasicNameValuePair("startLatitude", this.startLatitude.toString()));
        params.add(new BasicNameValuePair("startLongitude", this.startLongitude.toString()));
        params.add(new BasicNameValuePair("endLatitude", this.endLatitude.toString()));
        params.add(new BasicNameValuePair("endLongitude", this.endLongitude.toString()));
        params.add(new BasicNameValuePair("daysofweek", this.daysofweek));
        params.add(new BasicNameValuePair("returnTrip",this.returnTrip));
        params.add(new BasicNameValuePair("eventName", this.eventName));

        // getting JSON Object
        // Note that create event url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_event,"POST", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully created event
                this.eventId = json.getInt("event");
                createResult = this.eventId;


            } else {
                // failed to create event
                createResult = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return createResult;
    }
    public Event read(int eventId){
        //This retrieves  the event from the mySQL database.

        //Event retrieveEvent = new Event();

        String url_read_event = "http://www.hovhelper.com/read_event.php";


        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_EVENT= "event";
        String TAG_EVENTID = "eventId";
        String TAG_LOGINID = "loginId";
        String TAG_NUMBERSEATS = "numberSeats";
        String TAG_NUMBERAVAILABLE = "numberAvailable";
        String TAG_STARTTIME = "startTime";
        String TAG_ENDTIME = "endTime";
        String TAG_DAYSOFWEEK = "daysofweek";
        String TAG_EVENT_INTERVAL = "event_interval";
        String TAG_RETURNTRIP = "returnTrip";
        String TAG_STARTLATITUDE = "startLatitude";
        String TAG_ENDLATITUDE = "endLatitude";
        String TAG_ENDLONGITUDE = "endLongitude";
        String TAG_EVENTTYPE = "eventType";
        String TAG_CREATEDTIMESTAMP = "createdTimeStamp";
        String TAG_EVENTNAME = "eventName";


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
                this.returnTrip = (event.getString(TAG_RETURNTRIP));
                this.startLatitude = (event.getDouble(TAG_STARTLATITUDE));
                this.startLongitude = (event.getDouble(TAG_ENDLATITUDE));
                this.endLatitude = (event.getDouble(TAG_ENDLATITUDE));
                this.endLongitude = (event.getDouble(TAG_ENDLONGITUDE));
                this.eventType = (event.getString(TAG_EVENTTYPE));
                this.createTimeStamp = (event.getString(TAG_CREATEDTIMESTAMP));
                this.eventName = (event.getString(TAG_EVENTNAME));

            } else {
                // Event with EventId not found
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
    public String update(String loginId, String password, int eventID){

        //This would update an event to the mySQL database.
        //Test Value

        //ToDo Consider creating a counter to limit the number of entries created by one user over a period of time.

        // url to update new product
        String url_update_event = "http://www.hovhelper.com/update_event.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_MESSAGE = "message";
        String updateResult = "";

        // Building Parameters
        //ToDo remove deprecated approach and use URLBuilder instead

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("loginId", loginId));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("eventId", Integer.toString(this.eventId)));
        params.add(new BasicNameValuePair("numberSeats", Integer.toString(this.numberSeats)));
        params.add(new BasicNameValuePair("numberAvailable", Integer.toString(this.numberAvailable)));
        params.add(new BasicNameValuePair("startTime", this.start_Time));
        params.add(new BasicNameValuePair("endTime", this.end_Time));
        params.add(new BasicNameValuePair("eventType", this.eventType));
        params.add(new BasicNameValuePair("event_interval", this.event_interval));
        params.add(new BasicNameValuePair("returnTrip", this.returnTrip));
        params.add(new BasicNameValuePair("startLatitude", this.startLatitude.toString()));
        params.add(new BasicNameValuePair("startLongitude", this.startLongitude.toString()));
        params.add(new BasicNameValuePair("endLatitude", this.endLatitude.toString()));
        params.add(new BasicNameValuePair("endLongitude", this.endLongitude.toString()));
        params.add(new BasicNameValuePair("daysofweek", this.daysofweek));
        params.add(new BasicNameValuePair("eventName", this.eventName));


        // getting JSON Object
        // Note that update event url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_update_event,"POST", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully updated  event
                updateResult= json.getString(TAG_MESSAGE);

            } else {
                // failed to create event
                updateResult = "Update Failed";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return updateResult;
    }
    public String delete (String loginId, String password, int eventId) {
        // This method deletes an event and report success or failure

        String url_delete_event = "http://www.hovhelper.com/delete_event.php";

       /* In generate this method needs to determine who is deleting an event and if they have
       authorization to do so.  If they do not have permission it should be rejected with
       failedReason being set to 1. Not sure if we actually need the password perhaps there
       is a better approach.

       LoginID should be checked in the delete_event.php vice here, if there is any reason
       a call could be made directly to the php file it would be safer to ensure it is examined
       there.

       failedReason is signal to the calling method
       This was done as there may be several reasons that a deletion would not complete
       1. Login Id didn't match
       2. event missing
       3. password failure

       */

        // failedReason captures the feedback from the delete_event.php
        String failedReason = "something went wrong";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_MESSAGE = "message";

        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("eventId", Integer.toString(eventId)));
        params.add(new BasicNameValuePair("loginId", loginId));
        params.add(new BasicNameValuePair("password", password));

        // deleting event by making HTTP request
        JSONObject json = jsonParser.makeHttpRequest(url_delete_event, "POST", params);

        try {
            int success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
                // successfully deleted event
                failedReason = json.getString(TAG_MESSAGE);

            } else {
                // failed to create event, capture reason
                failedReason = json.getString(TAG_MESSAGE);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return failedReason;}
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>(){

        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);

        }

        @Override
        public Event[] newArray(int size){
            return new Event[size];
        }
    };
}

