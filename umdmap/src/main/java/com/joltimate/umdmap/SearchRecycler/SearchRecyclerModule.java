package com.joltimate.umdmap.SearchRecycler;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.joltimate.umdmap.map.Building;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 12/10/15.
 */
public class SearchRecyclerModule {
    private RecyclerView searchRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private BuildingRecyclerAdapter rAdapter;
    //private static ArrayList<Building> placeHolderList;
    private boolean setup; // make sure setup method is called
    private SearchRecyclerModule(){
    }
    public SearchRecyclerModule(Activity main, View recyclerView, ArrayList<Building> list){
        setup = false;
        searchRecycler = (RecyclerView) recyclerView;
        layoutManager = new LinearLayoutManager(main);
        searchRecycler.setLayoutManager(layoutManager);
        rAdapter = new BuildingRecyclerAdapter();
        searchRecycler.setAdapter(rAdapter);
        rAdapter.add(list);
        setup = true;

    }
    public void setup(){

    }
    public void add(List<Building> list){
        rAdapter.add(list);
        if ( list != null){
            //DebuggingTools.logd(list.toString());
        }
    }
}
