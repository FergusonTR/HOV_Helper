package sweng500team2summer15.hov_helper.map;

import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sweng500team2summer15.hov_helper.R;

/**
 * Created by Steve on 6/6/2015.
 */
public class MapsActivity extends FragmentActivity implements MapController.MapControllerCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MapController mapController;

    public static final String TAG = MapsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "CREATE!.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mapController = new MapController(this, this);

        double pickupLat = this.getIntent().getDoubleExtra("pickupLat", 0.0);
        double pickupLon = this.getIntent().getDoubleExtra("pickupLon", 0.0);
        double dropOffLat = this.getIntent().getDoubleExtra("dropOffLat", 0.0);
        double dropOffLon = this.getIntent().getDoubleExtra("dropOffLon", 0.0);
        displayRoute(pickupLat, pickupLon, dropOffLat, dropOffLon);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "RESUME!!.");
        super.onResume();
        setUpMapIfNeeded();
        if (mapController != null)
        {
            mapController.connect();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapController != null)
        {
            mapController.disconnect();
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);
    }

    public void displayRoute(double startLat, double startLon, double endLat, double endLon)
    {
        //Draw marker on start location
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(startLat, startLon))
                .title("START LOCATION");
        options.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(startLat, startLon), 15));

        // Draw marker on end location
        MarkerOptions options2 = new MarkerOptions()
                .position(new LatLng(endLat, endLon))
                .title("END LOCATION");
        mMap.addMarker(options2);


        findDirections(startLat, startLon,
                endLat, endLon, "driving");
    }

    @Override
    public void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
    {
        Log.d(TAG, "find directions");
        Map<String, String> map = new HashMap<String, String>();
        map.put(MapDirection.GetDirectionAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(MapDirection.GetDirectionAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(MapDirection.GetDirectionAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(MapDirection.GetDirectionAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(MapDirection.GetDirectionAsyncTask.DIRECTIONS_MODE, mode);

        MapDirection.GetDirectionAsyncTask asyncTask = new MapDirection.GetDirectionAsyncTask(this);
        asyncTask.execute(map);
    }

    public void handleGetDirectionsResult(ArrayList directionPoints)
    {
        Log.d(TAG, "Handle Get Directions! Number of points found: " + directionPoints.size());

        Polyline newPolyline;
        GoogleMap mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        PolylineOptions rectLine = new PolylineOptions().width(12).color(Color.GREEN);
        for(int i = 0 ; i < directionPoints.size() ; i++)
        {
            rectLine.add((LatLng)directionPoints.get(i));
        }
        newPolyline = mMap.addPolyline(rectLine);
    }

    public MapController getMapController()
    {
        return this.mapController;
    }
}
