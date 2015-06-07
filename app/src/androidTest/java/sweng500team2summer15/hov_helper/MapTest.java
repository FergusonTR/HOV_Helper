package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

/**
 * Created by Steve Lanehome on 6/7/2015.
 */
public class MapTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testCurrentLocation() {
        //Creates the map handle
        Map myMap = new Map();

        //Verify a currentLocation String is returned
        String currentLocation = myMap.getCurrentLocation();
        assertNotNull(currentLocation);
    }
}
