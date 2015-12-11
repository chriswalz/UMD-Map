package com.joltimate.umdmap.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.joltimate.umdmap.R;

import com.joltimate.umdmap.UmdMapModule;
import com.mapbox.mapboxsdk.views.MapView;
import com.mopub.mobileads.MoPubView;

import io.fabric.sdk.android.Fabric;

/**
 * Created by chris on 10/18/15.
 * Design good interfaces in classes
 * Fix settings menu
 * Where's commons 1 ??? am I skipping first result
 * Button to turn on / off following user location
 * show search suggestions
 */
public class MainActivity extends BaseMapActivity {
    private UmdMapModule umdMapHandler;
    private MoPubView moPubView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this);

        umdMapHandler = new UmdMapModule(this, (MapView)findViewById(R.id.mapview), (RecyclerView)findViewById(R.id.recycler_search));
        //enableLocationPermission();
        //setupMapBox();

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        moPubView = (MoPubView) findViewById(R.id.mopub_sample_ad);
        // TODO: Replace this test id with your personal ad unit id
        moPubView.setAdUnitId(getString(R.string.mopub_id));
        moPubView.loadAd();


        //testSearch();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:
                umdMapHandler.onSearchListButtonClicked();
                return true;
            case R.id.map_button:
                umdMapHandler.onMapButtonClicked();
                return true;
            /*case R.id.menuItemStreets:
                replaceMapView(getString(R.string.streetMapId));
                return true; */
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onDestroy(){
        moPubView.destroy();
        super.onDestroy();
    }
}
