package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;
import junit.framework.TestCase;

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
        myEvent.loginId = 1234;
        myEvent.eventId = 1;
        myEvent.eventType = "Share";
        myEvent.numberSeats = 3;
        myEvent.frequencyId = 7;
        //myEvent.startLocation
        //myEvent.endLocation
        //myEvent.startTime

        assertEquals("Event Object Creation failed - loginId",myEvent.loginId, 1234);
        assertEquals("Event Object Creation failed - eventId",myEvent.eventId, 1);
        assertEquals("Event Object Creation failed - eventType",myEvent.eventType, "Share");
        assertEquals("Event Object Creation failed - numberSeats",myEvent.numberSeats, 3);
        assertEquals("Event Object Creation failed - frequency",myEvent.frequencyId, 7);
    }

    //TC-23 Create Event
    @SmallTest
    public void testCreateEvent(){
        Event myEvent = new Event();
        //populates this with dummy data
        myEvent.loginId = 1234;
        myEvent.eventType = "Share";
        myEvent.numberSeats = 3;
        myEvent.frequencyId = 7;
        myEvent.locationId = 400;

        //Fake user password
        String password = "Sweng_500";



        //Test with test data default values should be created in the database to have reliable response.
        //positive test case for event
        assertTrue("TC-23, Failed to create an event", myEvent.create(myEvent.loginId, password) < 0);

    }

    //TC-XX Read Event
    @SmallTest
    public void testRetrieveEvent(){

        Event myEvent = new Event();
        myEvent = myEvent.read(7777);

        //retrieves a known event and determines if it is returned
        assertEquals("TC-XX, Failed to read an event",myEvent.eventId, 7777);
        //attempts to retrieve an unknown event ensures that it fails
        //assertEquals("TC-XX, Invalid read of an event",myEvent.read(0000), 0);
    }

    //TC-30 Update Event - NumberSeats
    @SmallTest
   //Todo ADD test cases for other Update Event actions.
    public void testUpdateEvent(){

        Event myEvent = new Event();
        //populates this with dummy data
        myEvent.loginId = 1234;
        myEvent.eventId = 1;
        myEvent.eventType = "Share";
        myEvent.numberSeats = 3;
        myEvent.frequencyId = 7;
        //myEvent.startLocation
        //myEvent.endLocation
        //myEvent.startTime

        assertTrue("TC-30, Failed to update event", myEvent.update(1234, "sweng_500",1, myEvent));

    }

    //TC-32 Delete Event
    @SmallTest
     public void testDeleteEvent(){
        //this test relies on the addition of a new event that is then deleted.
        Event myEvent = new Event();
        //populates this with dummy data
        myEvent.loginId = 1234;
        myEvent.eventId = 2;
        myEvent.eventType = "Share";
        myEvent.numberSeats = 2;
        myEvent.frequencyId = 1;
        //myEvent.startLocation
        //myEvent.endLocation
        //myEvent.startTime

        //Pushes the event to the server
       int myeventID = myEvent.create(1234,"sweng_500");

        //tests that the event can be deleted.
        assertTrue("TC-32, Failed to delete an event", myEvent.delete(1234,"sweng_500",myeventID));

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


}


