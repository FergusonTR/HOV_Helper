package sweng500team2summer15.hov_helper.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sweng500team2summer15.hov_helper.R;
import sweng500team2summer15.hov_helper.event.management.Event;
import sweng500team2summer15.hov_helper.eventdisplay.EventDetailsActivity;

/**
 * Created by user on 7/18/2015.
 */
public class MapFragment extends Fragment implements MapController.MapControllerCallback, GoogleMap.OnMarkerClickListener{
    private MapView mMapView;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MapController mapController;
    private ArrayList<Event> arrayListOfEvents = new ArrayList<Event>();
    private Map<String, Event> markerToEventMap = new HashMap<String, Event>();

    public static final String TAG = MapFragment.class.getSimpleName();

    public void setEvents(ArrayList<Event> arrayListOfEvents)
    {
        this.arrayListOfEvents = arrayListOfEvents;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapController = new MapController(this.getActivity().getApplicationContext(), this);

        mMap = mMapView.getMap();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);

        for (Event e : this.arrayListOfEvents)
        {
            // latitude and longitude
            double latitude = e.startLatitude;
            double longitude = e.startLongitude;

            // create marker
            MarkerOptions marker;
            if (e.eventType == "Ride")
            {
                BitmapDescriptor rideBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_thumb);
                marker = new MarkerOptions().position(
                        new LatLng(latitude, longitude)).title("Ride");
                // Changing marker icon
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                marker.icon(rideBitmap);

            }
            else
            {
                BitmapDescriptor shareBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
                marker = new MarkerOptions().position(
                        new LatLng(latitude, longitude)).title("Drive");
                marker.icon(shareBitmap);
            }

            // adding marker
            Marker addedMarker = mMap.addMarker(marker);
            markerToEventMap.put(addedMarker.getId(), e);
            Log.i(TAG, "add marker: " + addedMarker.getId());
        }

        // center on search position
        CameraPosition cameraPosition = new CameraPosition.Builder()
                  .target(new LatLng(40.82, -77.8561126)).zoom(12).build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void handleNewLocation(Location location) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) { //Called when a marker has been clicked or tapped.
        Log.i(TAG, "CLICKED EVENT MARKER ID: " + marker.getId());
        Event clickedEvent =  markerToEventMap.get(marker.getId());
        if (clickedEvent != null)
        {
            displayEventDetailsActivity(clickedEvent);
            Log.i(TAG, "EVENT FOUND!");
        }
        else{
            Log.i(TAG, "CLICKED EVENT IS NULL!!!!");
        }

        return false;
    }

    private void displayEventDetailsActivity(Event event)
    {
        Intent intent = new Intent(this.getActivity().getApplicationContext(),EventDetailsActivity.class);
        intent.putExtra("eventForDetails", event);
        startActivity(intent);
    }
}
