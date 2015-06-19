package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;
import sweng500team2summer15.hov_helper.Map;

import junit.framework.TestCase;

/**
 * Created by Steve Lanehome on 6/7/2015.
 */
//TC-02 - Retrieve Current Location
public class MapTest extends TestCase {

    Map myMap;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //Creates the map handle
        myMap = new Map();
    }

    @SmallTest
    public void testCurrentLocation() {
        //Verify a currentLocation String is returned
        String currentLocation = myMap.getCurrentLocation();
        assertEquals(false, currentLocation.isEmpty());
    }

    @SmallTest
    public void testCurrentLocationStreetAddress() {
        //Verify a currentLocation String is returned
        String currentLocation = myMap.getCurrentLocationStreetAddress();
        assertEquals(false, currentLocation.isEmpty());
    }

    @SmallTest
    public void testCurrentLocationCity() {
        //Verify a currentLocation String is returned
        String currentLocation = myMap.getCurrentLocationCity();
        assertEquals(false, currentLocation.isEmpty());
    }

    @SmallTest
    public void testCurrentLocationState() {
        //Verify a currentLocation String is returned
        String currentLocation = myMap.getCurrentLocationState();
        assertEquals(false, currentLocation.isEmpty());
    }

    @SmallTest
    public void testCurrentLocationZipCode() {
        //Verify a currentLocation String is returned
        String currentLocation = myMap.getCurrentLocationZipCode();
        assertEquals(false, currentLocation.isEmpty());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
