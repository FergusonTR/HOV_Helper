package sweng500team2summer15.hov_helper;

import android.location.Location;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

/**
 * Created by Steve Lanehome on 6/7/2015.
 */
//TC-02 - Retrieve Current Location
public class MapControllerTest extends TestCase {

    MapController myMapController;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MapsActivity activity = new MapsActivity();
        //Creates the map handle
        myMapController = new MapController(activity, activity);
    }

    @SmallTest
    public void testCurrentLocation() {
        //Verify a currentLocation String is returned
        Location currentLocation = myMapController.getCurrentLocation();
        assertNotNull(currentLocation);
    }

    @SmallTest
    public void testCurrentLocationStreetAddress() {
        //Verify a currentLocation String is returned
        String currentLocation = myMapController.getCurrentLocationStreetAddress();
        assertEquals(false, currentLocation.isEmpty());
    }

    @SmallTest
    public void testCurrentLocationCity() {
        //Verify a currentLocation String is returned
        String currentLocation = myMapController.getCurrentLocationCity();
        assertEquals(false, currentLocation.isEmpty());
    }

    @SmallTest
    public void testCurrentLocationState() {
        //Verify a currentLocation String is returned
        String currentLocation = myMapController.getCurrentLocationState();
        assertEquals(false, currentLocation.isEmpty());
    }

    @SmallTest
    public void testCurrentLocationZipCode() {
        //Verify a currentLocation String is returned
        String currentLocation = myMapController.getCurrentLocationZipCode();
        assertEquals(false, currentLocation.isEmpty());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
