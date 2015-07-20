package sweng500team2summer15.hov_helper;

import android.location.Address;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

import sweng500team2summer15.hov_helper.map.MapController;
import sweng500team2summer15.hov_helper.map.MapsActivity;

/**
 * Created by Steve Lanehome on 6/7/2015.
 */
//TC-02 - Retrieve Current Location
public class MapControllerTest extends ActivityInstrumentationTestCase2<MapsActivity> {

    MapController myMapController;
    MapsActivity myMapsActivity;
    public static final String TAG = MapControllerTest.class.getSimpleName();

    public MapControllerTest() {
        super(MapsActivity.class);
    }

//    public MapControllerTest(Class<MapsActivity> activityClass) {
//        super(activityClass);
//    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myMapsActivity = getActivity();
        if (myMapsActivity == null)
        {
            Log.e(TAG, "MY MAPS ACTIVITY IS NULL!!!");
        }
        else
        {
            myMapController = myMapsActivity.getMapController();
            // setup test location at Beaver Stadium Penn State
            sendLocation(40.8122837,-77.8561126);
            Thread.sleep(500);
        }

        //activity.onCreate(new Bundle());
        //Creates the map handle
        //myMapController = new MapController(activity, null);
    }

    static void sendLocation(double latitude, double longitude) {
        try {
            Socket socket = new Socket("10.0.2.2", 5554);
            //Socket socket = new Socket("127.0.0.1", 5554);
            socket.setKeepAlive(true);
            String str = "geo fix " + longitude + " " + latitude ;
            Writer w = new OutputStreamWriter(socket.getOutputStream());
            w.write(str + "\r\n");
            w.flush();
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SmallTest
    public void testCurrentLocation() {
        Log.e(TAG, "TEST CURRENT LOCATION. Should be at 40.8122837,-77.8561126");
        myMapController = myMapsActivity.getMapController();
        if (myMapController != null)
        {
            //Verify a currentLocation String is returned
            Location currentLocation = myMapController.getCurrentLocation();
            assertNotNull(currentLocation);
            double currentLat = currentLocation.getLatitude();
            assertEquals(40.8122837, currentLat, 0.01);
            double currentLon = currentLocation.getLongitude();
            assertEquals(-77.8561126, currentLon, 0.01);

            Log.e(TAG, "CURRENT LOCATION. LAT: " + currentLocation.getLatitude() + " , LON: " + currentLocation.getLongitude());
        }
        else
        {
            Log.e(TAG, "mapcontroller is NULL");
        }
    }

    @SmallTest
    public void testGetStreetAddressFromLatLon() {
        // test location (Beaver Stadium, PennState)
        String expectedAddressLine1 = "Penn State University";
        String expectedAddressLine2 = "1 Beasver Stadium"; // mispelling of 'Beaver' from google maps
        String expectedAddressLine3 = "State College, PA 16801";
        Address result = myMapController.getStreetAddressFromLatLon(this.myMapsActivity, 40.8122837, -77.8561126);
        Log.e(TAG, "Address Found: " + result);
        String addressLine1 = result.getAddressLine(0);
        String addressLine2 = result.getAddressLine(1);
        String addressLine3 = result.getAddressLine(2);
        assertNotNull(result);
        assertEquals(expectedAddressLine1, addressLine1);
        assertEquals(expectedAddressLine2, addressLine2);
        assertEquals(expectedAddressLine3, addressLine3);
    }

    @SmallTest
    public void testGetLatLonFromStreetAddress() {
        // test location (Beaver Stadium, PennState)
        String testAddressLine = "Penn State University, 1 Beasver Stadium, State College, PA 16801";
        double expectedLat = 40.8122837;
        double expectedLon = -77.8561126;
        LatLng latLng = MapController.getGeoCoordinateFromAddress(this.myMapsActivity,testAddressLine);
        Log.e(TAG, "Lat/Lon Found: " + latLng);
        assertNotNull(latLng);
        assertEquals(expectedLat, latLng.latitude, .01);
        assertEquals(expectedLon, latLng.longitude, .01);
    }
//
//    @SmallTest
//    public void testCurrentLocationState() {
//        //Verify a currentLocation String is returned
//        String currentLocation = myMapController.getCurrentLocationState();
//        assertEquals(false, currentLocation.isEmpty());
//    }
//
//    @SmallTest
//    public void testCurrentLocationZipCode() {
//        //Verify a currentLocation String is returned
//        String currentLocation = myMapController.getCurrentLocationZipCode();
//        assertEquals(false, currentLocation.isEmpty());
//    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
