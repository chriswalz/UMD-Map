package com.joltimate.umdmap;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by chris on 12/10/15.
 */
public class PermissionHandler {
    private final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 4;
    private PermissionHandler(){

    }
    public static boolean isLocationPermissionGranted(Activity main) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(main, Manifest.permission.ACCESS_FINE_LOCATION);
        //return true;
    }
    public static void enableLocationPermission(Activity main){
        if ( !isLocationPermissionGranted(main)){
            if (ActivityCompat.shouldShowRequestPermissionRationale(main,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
            }
            ActivityCompat.requestPermissions(main,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
}
