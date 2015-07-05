package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;
import junit.framework.TestCase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sweng500team2summer15.hov_helper.event.management.Event;



/**
 * Created by Terry on 6/5/2015.
 */
public class EventTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    //Test Event Object Creation
    @SmallTest
    public void testEventObj() {
        //Creates the local copy of the event

        Event myEvent = new Event();
        //populates this with dummy data
        myEvent.loginId = "testLoginId";
        myEvent.eventId = 1;
        myEvent.eventType = "Share";
        myEvent.numberSeats = 3;
        //myEvent.frequencyId = 7;
        //myEvent.startLocation
        //myEvent.endLocation
        //myEvent.startTime

        assertEquals("Event Object Creation failed - loginId",myEvent.loginId, "1234");
        assertEquals("Event Object Creation failed - eventId",myEvent.eventId, 1);
        assertEquals("Event Object Creation failed - eventType",myEvent.eventType, "Share");
        assertEquals("Event Object Creation failed - numberSeats",myEvent.numberSeats, 3);
        //assertEquals("Event Object Creation failed - frequency",myEvent.frequencyId, 7);
    }

    //TC-23 Create Event
    @SmallTest
    public void testCreateEvent(){

        //Creates a local instance of event
        Event myEvent = new Event();

        //populates this with dummy data
        DateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start_today = Calendar.getInstance().getTime();
        Date end_today = Calendar.getInstance().getTime();

        //Fake user password
        String password = "Sweng_500";
        myEvent.loginId = "testLoginId";
        myEvent.eventType = "Share";
        myEvent.numberSeats = 3;
        myEvent.numberAvailable = 2;
        myEvent.start_Time = dateformat.format(start_today);
        myEvent.end_Time = dateformat.format(end_today);

        //Test with test data default values should be created in the database to have reliable response.
        //positive test case for event
        assertTrue("TC-23, Failed to create an event", myEvent.create(myEvent.loginId, password) > 0);

        //clean up
        myEvent.delete("testLoginId","Sweng_500",myEvent.eventId);
    }

    //TC-XX Read Event
    @SmallTest
    public void testRetrieveEvent(){

        Event myEvent = new Event();
        myEvent.create("testLoginId","Sweng_500");
        int testEventId = myEvent.eventId;
        myEvent.read(testEventId);

        //retrieves a known event and determines if it is returned
        assertEquals("TC-XX, Failed to read an event",myEvent.eventId, testEventId);

        //delete the created event and retest
        myEvent.delete("testLoginId","Sweng_500",testEventId);

        //attempts to retrieve an unknown event ensures that it fails
        //myEvent.read(testEventId);
        //assertEquals("TC-XX, Invalid read of an event",myEvent.read(testEventId), 0);
    }

    //TC-30 Update Event - NumberSeats
    @SmallTest
   //Todo ADD test cases for other Update Event actions.
    public void testUpdateEvent(){

        //create a temporary event to test the create method
        Event myEvent = new Event();
        myEvent.create("testLoginId","Sweng_500");
        int myeventID = myEvent.eventId;

        //populates this with some dummy data
        myEvent.eventType = "Share";
        myEvent.numberSeats = 3;


        assertEquals("TC-30, Failed to update event", (myEvent.update("testLoginId","Sweng_500",myeventID)),"Event update completed.");

        //clean up the event that was created
        myEvent.delete("testLoginId","Sweng_500",myeventID);


    }

    //TC-32 Delete Event
    @SmallTest
     public void testDeleteEvent(){
        //this test relies on the addition of a new event that is then deleted.
        Event myEvent = new Event();
        //populates this with dummy data
        myEvent.loginId = "testLoginId";
        myEvent.eventId = 2;
        myEvent.eventType = "Share";
        myEvent.numberSeats = 2;

        //Pushes the event to the server
       int myeventID = myEvent.create("testLoginId","sweng_500");

        //tests that the event can be deleted.
        assertEquals("TC-32, Failed to delete an event",(myEvent.delete("testLoginId", "sweng_500", myeventID)), "Event deleted");

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


}


