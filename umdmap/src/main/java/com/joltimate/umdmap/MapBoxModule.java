package com.joltimate.umdmap;

import android.app.Activity;
import android.util.Log;

import com.joltimate.umdmap.map.Building;
import com.joltimate.umdmap.map.BuildingData;
import com.mapbox.mapboxsdk.api.ILatLng;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.GpsLocationProvider;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.views.MapView;
import com.mapbox.mapboxsdk.views.util.TilesLoadedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chris on 12/10/15.
 */
public class MapBoxModule {

    private MapView mv;
    private UserLocationOverlay myLocationOverlay;
    private String currentMap = null;
    private ArrayList<Marker> markers = new ArrayList<>();
    private LatLng umdCoords = new LatLng(38.9875, -76.9400);

    //findViewById(com.joltimate.umdmap.R.id.mapview);
    public MapBoxModule(MapView m){
        mv = (MapView) m;
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        mv.setZoom(18);
        mv.setCenter(new ILatLng() {
            @Override
            public double getLatitude() {
                return umdCoords.getLatitude();
            }

            @Override
            public double getLongitude() {
                return umdCoords.getLongitude();
            }

            @Override
            public double getAltitude() {
                return 0;
            }
        });

    }
    public void setup(Activity main){
        currentMap = main.getString(R.string.satelliteMapId);

        // Show user location (purposely not in follow mode)
        mv.setUserLocationEnabled(true);
        //mv.setUserLocationTrackingMode(UserLocationOverlay.TrackingMode.FOLLOW);

        Marker m;
        m = new Marker(mv, "University of Maryland", "College Park, MD", umdCoords);
        m.setIcon(new Icon(main, Icon.Size.LARGE, "city", "3887be"));
        mv.addMarker(m);

        mv.setOnTilesLoadedListener(new TilesLoadedListener() {
            @Override
            public boolean onTilesLoaded() {
                return false;
            }

            @Override
            public boolean onTilesLoadStarted() {
                // TODO Auto-generated method stub
                return false;
            }
        });


        // mv.getOverlays().add(myLocationOverlay);
        //parseJson();
        addBuildingMarkers(DatabaseTable.buildings, main);

        UserLocationOverlay myLocationOverlay = new UserLocationOverlay(new GpsLocationProvider(main.getApplicationContext()), mv);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.setDrawAccuracyEnabled(true);
        mv.getOverlays().add(myLocationOverlay);
    }

    public void setVisibility(int visibility){
        mv.setVisibility(visibility);
    }

    private void addBuildingMarkers(ArrayList<Building> buildings, Activity main) {
        Marker m;
        for (Building b : buildings) {
            m = new Marker(mv, b.getName(), b.getCode() + " " + b.getNumber(), new LatLng(Double.valueOf(b.getLat()), Double.valueOf(b.getLng())));
            m.setIcon(new Icon(main, Icon.Size.SMALL, "building", "e55e5e"));
            mv.addMarker(m);
            markers.add(m);
        }
    }
    public void setCenter(final Double lat, final Double lng){
        mv.setCenter(new ILatLng() {
            @Override
            public double getLatitude() {
                return lat;
                //return Double.valueOf(matchedBuildings.get(0).getLat());
            }

            @Override
            public double getLongitude() {
                return lng;
                //return Double.valueOf(matchedBuildings.get(0).getLng());
            }

            @Override
            public double getAltitude() {
                return 0;
            }
        });
    }

}
