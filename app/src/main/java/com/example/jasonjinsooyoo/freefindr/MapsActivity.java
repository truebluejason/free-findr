package com.example.jasonjinsooyoo.freefindr;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.GridMarkerClusterer;
import org.osmdroid.bonuspack.overlays.InfoWindow;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

/**
 * Created by jasonjinsooyoo on 2017-03-18.
 */

public class MapsActivity extends Activity implements MapEventsReceiver {
    // TODO: 4. Implement map

    private static final String TAG = "MapActivity";
    private static final String MAP_CURRENT_ZOOM_KEY = "map-current-zoom";
    private static final String MAP_CENTER_LAT_KEY = "map-center-lat";
    private static final String MAP_CENTER_LON_KEY = "map-center-lon";

    private ArrayList<Event> events;
    private MapView map;
    private IMapController mapController;
    private MyLocationNewOverlay myLocationOverlay;
    private EventMarkerInfoWindow eventMarkerInfoWindow;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MAP_CURRENT_ZOOM_KEY, map.getZoomLevel());
        outState.putDouble(MAP_CENTER_LAT_KEY, map.getMapCenter().getLatitude());
        outState.putDouble(MAP_CENTER_LON_KEY, map.getMapCenter().getLongitude());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);


        Intent intent = getIntent();
        ArrayList<Parcelable> eventParcels = intent.getParcelableArrayListExtra("events");
        events = new ArrayList<Event>();
        for (Parcelable p : eventParcels) {
            events.add((Event) p);
        }

        map = (MapView) findViewById(R.id.mapView);

        eventMarkerInfoWindow = new EventMarkerInfoWindow(R.layout.bonuspack_bubble, map);

        // handle map events

        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, this);
        map.getOverlays().add(0, mapEventsOverlay);

        // markers list
        GridMarkerClusterer EventMarkers = new GridMarkerClusterer(this);
        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_cluster);
        Bitmap clusterIcon = ((BitmapDrawable) clusterIconD).getBitmap();
        map.getOverlays().add(EventMarkers);
        EventMarkers.setIcon(clusterIcon);
        EventMarkers.setGridSize(80);

        for (final Event event : events) {
            EventMarkers.add(createEventMarker(event));
        }

        map.invalidate();

        map.setMultiTouchControls(true);
        map.setBuiltInZoomControls(true);
        map.setMinZoomLevel(3);

        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        GpsMyLocationProvider imlp = new GpsMyLocationProvider(this.getBaseContext());
        imlp.setLocationUpdateMinDistance(1000);
        imlp.setLocationUpdateMinTime(60000);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        myLocationOverlay = new MyLocationNewOverlay(this.getBaseContext(), imlp, this.map);
        myLocationOverlay.enableMyLocation();
        map.getOverlays().add(this.myLocationOverlay);

        mapController = map.getController();
        if (savedInstanceState != null) {
            mapController.setZoom(savedInstanceState.getInt(MAP_CURRENT_ZOOM_KEY));
            mapController.setCenter(new GeoPoint(savedInstanceState.getDouble(MAP_CENTER_LAT_KEY), savedInstanceState.getDouble(MAP_CENTER_LON_KEY)));
        } else {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location userLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (userLocation != null) {
                mapController.setZoom(16);
                mapController.animateTo(new GeoPoint(userLocation));
            } else {
                mapController.setZoom(13);
                mapController.setCenter(new GeoPoint(49.266384, -123.245340));

                Toast.makeText(this,"Location not found", Toast.LENGTH_LONG).show();
            }
        }



    }

    @Override
    protected void onPause() {
        super.onPause();
        myLocationOverlay.disableMyLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myLocationOverlay.enableMyLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_my_location:
                try {
                    LocationManager locationManager =
                            (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    GeoPoint userLocation = new GeoPoint(locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
                    mapController.animateTo(userLocation);
                    return true;
                } catch (NullPointerException ex) {
                    Toast.makeText(this, "Location not found", Toast.LENGTH_LONG).show();

                    Log.e(TAG, "Location not found");
                    return true;
                }
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Creates a event marker
    private Marker createEventMarker(Event event) {
        GeoPoint EventLocation = new GeoPoint((int) (event.getLat() * 1000000), (int) (event.getLon() * 1000000));
        Marker marker = new Marker(map);
        marker.setRelatedObject(event);
        marker.setInfoWindow(eventMarkerInfoWindow);
        marker.setPosition(EventLocation);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        marker.setTitle(event.getName());

        return marker;
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        InfoWindow.closeAllInfoWindowsOn(map);
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        return false;
    }

    private class EventMarkerInfoWindow extends MarkerInfoWindow {

        public EventMarkerInfoWindow(int layoutResId, final MapView mapView) {
            super(layoutResId, mapView);
        }


        @Override
        public void onOpen(Object item) {
            Marker marker = (Marker) item;
            final Event markerEvent = (Event) marker.getRelatedObject();
            super.onOpen(item);
            closeAllInfoWindowsOn(map);

            LinearLayout layout = (LinearLayout) getView().findViewById(R.id.map_bubble_layout);
            layout.setClickable(true);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MapsActivity.this, SingleEventActivity.class);
                    intent.putExtra("event", markerEvent);
                }
            });
        }
    }
}
