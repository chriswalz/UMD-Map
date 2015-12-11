package com.joltimate.umdmap;


import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;

public class MapApplication extends Application {
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        // PsiMethod:onCreateFabric.with(this, new Crashlytics());
        analytics = GoogleAnalytics.getInstance(this);
        // Set the log level to verbose.
        //analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        analytics.setLocalDispatchPeriod(450);

        tracker = analytics.newTracker(getString(R.string.api_analytics)); //todo Replace with actual tracker/property Id
        //tracker.enableExceptionReporting(true); Don't enable this fucks with crash reporting!!

        tracker.enableAutoActivityTracking(true);

        tracker.setScreenName("MapApplication");

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("Entered")
                .setLabel("Entered")
                .build());
    }
    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
            //mTracker.enableExceptionReporting(true);
        }
        return mTracker;
    }
}