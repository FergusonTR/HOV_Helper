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

    @SmallTest
    public void testEvent() {
        //Creates the event
        Event myEvent = new Event();
        //populates this with dummy data
        myEvent.loginId = 1234;
        myEvent.eventId = 1;
        myEvent.eventType = "Share";
        myEvent.numberSeats = 3;
        myEvent.frequency = "weekly";

        //myEvent.startLocation
        //myEvent.endLocation
        //myEvent.startTime

        myEvent.create(1234);

        assertEquals(myEvent.loginId, 1234);
        assertEquals(myEvent.eventId, 1);
        assertEquals(myEvent.eventType, "Share");
        assertEquals(myEvent.numberSeats, 3);
        assertEquals(myEvent.frequency, "weekly");
        assertEquals(myEvent.create(1234), 7777);
        assertEquals(myEvent.create(2345), 8888);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


}


