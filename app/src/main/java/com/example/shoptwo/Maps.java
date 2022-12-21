package com.example.shoptwo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEvent;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.Map;
import java.util.prefs.PreferenceChangeEvent;

public class Maps extends AppCompatActivity {
    private MapView mapView;
    private MapController mapController;

    @Override public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        GeoPoint madrid = new GeoPoint (40.416775, -3.70379);
        mapView = (MapView) findViewById(R.id.map);
        mapView.setBuiltInZoomControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setCenter(madrid);
        mapController.setZoom(20);

        mapView.setMultiTouchControls(true);
        Marker marker = new Marker(mapView);
        marker.setPosition(madrid);
        mapView.getOverlays().add(marker);

       /* Marker startMarker = new Marker(mapView);
        startMarker.setPosition(madrid);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        OverlayItem myLocationOverlayItem = new OverlayItem("Here", "Current Position", madrid);
        mapView.getOverlays().add(startMarker);
*/

        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Toast.makeText(getApplicationContext(), p.getLatitude()+"-"+ p.getLongitude(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) { return false; }
        };
        MapEventsOverlay eventsOverlay = new MapEventsOverlay(getApplicationContext(),mapEventsReceiver);
        mapView.getOverlays().add(eventsOverlay);
    }

}
   /* private MapView mapView;
    private MapController mapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Context ctx = getApplicationContext();
        GeoPoint madrid = new GeoPoint (40.416775, -3.70379);

        mapView = (MapView) findViewById(R.id.map);
        mapView.setBuiltInZoomControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setCenter(madrid);
        mapController.setZoom(6);

        mapView.setMultiTouchControls(true);*/

