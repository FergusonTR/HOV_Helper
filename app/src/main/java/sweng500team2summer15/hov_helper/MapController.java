package sweng500team2summer15.hov_helper;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Steve on 6/6/2015.
 */
public class MapController implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public abstract interface MapControllerCallback {
        public void handleNewLocation(Location location);
    }

    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapController.class.getSimpleName();
    private LocationRequest mLocationRequest;
    private Context mContext;
    private MapControllerCallback mapControllerCallback;

    public MapController(Context context, MapControllerCallback callback) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        mContext = context;
        mapControllerCallback = callback;
    }

    /**
     * Get the current location string (street, city, state, zip code)
     * @return
     */
    public Location getCurrentLocation()
    {
        Location currentLocation = null;
        // TODO:

        return currentLocation;
    }

    public String getCurrentLocationStreetAddress()
    {
        String currentStreetAddress = "";
        // TODO:

        return currentStreetAddress;
    }


    public String getCurrentLocationCity()
    {
        String currentCity = "";
        // TODO:

        return currentCity;
    }

    public String getCurrentLocationState()
    {
        String currentCity = "";
        // TODO:

        return currentCity;
    }

    public String getCurrentLocationZipCode()
    {
        String currentZipcode = "";
        // TODO:

        return currentZipcode;
    }

    public double getCurrentLatitude()
    {
        double currentLat = 0.0;
        // TODO:
        return currentLat;
    }

    public double getCurrentLongitude()
    {
        double currentLon = 0.0;
        //TODO:
        return currentLon;
    }

    public void setEventStartLocation(String startLocation)
    {
        // TODO:
    }

    public void setEventEndLocation(String endLocation)
    {
        // TODO:
    }
    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            Log.i(TAG, "Location IS NULL!!.");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            Log.i(TAG, "Handle New Location!.");
            mapControllerCallback.handleNewLocation(location);
        };
    }

    public void connect() {
        mGoogleApiClient.connect();
    }

    public void disconnect() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "FAILED CONNEcTION!.");

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "ON LOCATION CHANGED!.");
        //mapControllerCallback.handleNewLocation(location);
    }
}
