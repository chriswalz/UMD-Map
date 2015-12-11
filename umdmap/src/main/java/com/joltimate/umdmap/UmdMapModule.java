package com.joltimate.umdmap;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.joltimate.umdmap.SearchRecycler.SearchRecyclerModule;
import com.joltimate.umdmap.map.Building;
import com.mapbox.mapboxsdk.views.MapView;

/**
 * Created by chris on 12/10/15.
 */
public class UmdMapModule {
    private static AppCompatActivity main;
    private DatabaseTable db;
    private SearchRecyclerModule recyclerBuildingWrapper;

    private static MapBoxModule mapBoxHandler;

    // Ids
    static int searchPageId = R.id.search_page;
    int searchViewId = R.id.search_view;
    int toolbarId = R.id.toolbar;

    int primaryColorId = R.color.primary_material_light_1;

    // End Ids

    public UmdMapModule(AppCompatActivity m, MapView mapView, RecyclerView recyclerView){
        this.main = m;
        setUpThemeColors();
        PermissionHandler.enableLocationPermission(main);
        db  = new DatabaseTable();
        mapBoxHandler = new MapBoxModule(mapView);
        mapBoxHandler.setup(main);

        recyclerBuildingWrapper = new SearchRecyclerModule(main, recyclerView, DatabaseTable.matchedBuildings);
       // recyclerBuildingWrapper.setup(main);

        setupSearchView();
    }
    public static void setCenter(Double lat, Double lng){
        mapBoxHandler.setCenter(lat, lng);
        onMapButtonClicked();

    }
    public void onSearchListButtonClicked(){
        mapBoxHandler.setVisibility(View.GONE);
        LinearLayout searchPage = (LinearLayout) main.findViewById(searchPageId);
        searchPage.setVisibility(View.VISIBLE);
    }
    public static void onMapButtonClicked(){
        mapBoxHandler.setVisibility(View.VISIBLE);
        LinearLayout searchPage = (LinearLayout) main.findViewById(searchPageId);
        SearchView searchView = (SearchView) main.findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);
        searchPage.setVisibility(View.GONE);
    }

    private void setupSearchView(){
        SearchView searchView = (SearchView) main.findViewById(searchViewId);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if ( DatabaseTable.matchedBuildings != null && DatabaseTable.matchedBuildings.size() >= 1){
                    Building b = DatabaseTable.matchedBuildings.get(0);
                    UmdMapModule.setCenter(Double.valueOf(b.getLat()),Double.valueOf(b.getLng()));
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                db.updateQueryMatches(newText);
                recyclerBuildingWrapper.add(DatabaseTable.matchedBuildings);
                return true;
            }
        });
    }
    private void setUpThemeColors() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = main.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(Color.rgb(211, 47, 47));
            window.setStatusBarColor(ContextCompat.getColor(main.getApplicationContext(), primaryColorId));
        }

        Toolbar toolbar = (Toolbar) main.findViewById(toolbarId);
        if (toolbar != null) {
            main.setSupportActionBar(toolbar);
            final ActionBar supportActionBar = main.getSupportActionBar();
            if (supportActionBar != null) {
                main.getSupportActionBar().setTitle("UMD Map");
                main.getSupportActionBar().setIcon(R.drawable.ic_launcher);
            }
        }
    }
}
