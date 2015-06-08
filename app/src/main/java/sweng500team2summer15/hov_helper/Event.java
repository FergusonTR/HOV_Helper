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

    String frequency;
    String eventType;

    /*Location Object needs to be defined*/
    //String startLocation;
    //String endLocation;
    //Date startTime;
    // SimpleDateFormat format = new SimpleDateFormat("");

   public int create(int loginId, String password) {
        //This would add the event to the mySQL database.
        //Test Value
        int eventId = 0;
        //ToDo SQL based addition of the event data to the database and obtaining the eventID assigned.


        // Test Code
        //if(loginId == 1234)
        //   {eventId = 7777;}
        //else eventId = 8888;

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
