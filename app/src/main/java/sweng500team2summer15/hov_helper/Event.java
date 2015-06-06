package sweng500team2summer15.hov_helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Terry on 6/4/2015.
 */
public class Event {
    int loginId;
    int eventId;
    int numberSeats;

    //Date startTime;

    String frequency;
    String eventType;

    /*Location Object needs to be defined*/
    //String startLocation;
    //String endLocation;
    //SimpleDateFormat format = new SimpleDateFormat("");

    public int create(int loginId) {
        //This would add the event to the mySQL database.
        //Test Value
        int eventId = 0;
        // Test Code
        if(loginId == 1234)
            {eventId = 7777;}
        else eventId = 8888;

          return eventId;
    }


}
