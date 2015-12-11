package com.joltimate.umdmap.activity;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.joltimate.umdmap.MapApplication;
import com.joltimate.umdmap.PermissionHandler;
import com.joltimate.umdmap.R;
import com.joltimate.umdmap.other.DebuggingTools;

// this "Base" handles Google analytics and Location updates.
public class BaseMapActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final int REQUEST_RESOLVE_ERROR = 1001;

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    LocationRequest locationRequest = getLocationRequest();
    private boolean mResolvingError = false;
    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.joltimate.umdmap.R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        //setupBuildingsList();
        MapApplication application = (MapApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("MapView");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_custom, menu);
        return super.onCreateOptionsMenu(menu);
    }


/*
    protected void replaceMapView(String layer) {

        if (TextUtils.isEmpty(layer) || TextUtils.isEmpty(currentMap) || currentMap.equalsIgnoreCase(layer)) {
            return;
        }

        ITileLayer source;
        BoundingBox box;

        source = new MapboxTileLayer(layer);

        mv.setTileSource(source);
        box = source.getBoundingBox();
        mv.setScrollableAreaLimit(box);
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        currentMap = layer;

    } */


    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getApplicationContext(), "Helo", Toast.LENGTH_LONG);
        //System.out.println("New location");
        mLastLocation = location;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.i(TAG, "Setting screen name: " + screenName);
       /* t.setScreenName("Image~" + screenName);
        t.send(new HitBuilders.ScreenViewBuilder().build()); */
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        } else {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        DebuggingTools.logd("BaseJolt", "App is connected");
        initalizeLocationUpdates();
    }

    private void initalizeLocationUpdates() {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        //updateLocation(mLastLocation);
        startLocationUpdates();
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20000);
        locationRequest.setFastestInterval(6000);
        return locationRequest;
    }

    private void startLocationUpdates() {
        if (PermissionHandler.isLocationPermissionGranted(this)) {
            //LocationRequest locationReq = createLocationRequest();
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationReq, this);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
            DebuggingTools.logd("BaseJolt", "there is permission");
        } else {
            // do nothing permission wasnt granted :(
            DebuggingTools.logd("BaseJolt", "no permission");
        }
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (connectionResult.hasResolution()) {
            try {
                mResolvingError = true;
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            Toast.makeText(getApplicationContext(), "No location " + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
            mResolvingError = true;
        }
    }


}

//mv.loadFromGeoJSONURL("https://gist.githubusercontent.com/tmcw/10307131/raw/21c0a20312a2833afeee3b46028c3ed0e9756d4c/map.geojson");
//        setButtonListeners();