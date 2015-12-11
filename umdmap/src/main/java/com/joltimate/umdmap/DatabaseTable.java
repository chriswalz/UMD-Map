package com.joltimate.umdmap;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.joltimate.umdmap.map.Building;
import com.joltimate.umdmap.map.BuildingData;
import com.joltimate.umdmap.other.DebuggingTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by chris on 11/23/15.
 */
public class DatabaseTable {
    public static ArrayList<Building> buildings;
    public static ArrayList<Building> matchedBuildings;
    public DatabaseTable(){
        setupBuildingsList();
        updateQueryMatches("");
    }
    protected void setupBuildingsList() {
        ArrayList<Building> buildingArrayList = new ArrayList<>();
        //System.out.println("MainACtivity: " + BuildingData.buildingEntries);
        try {
            JSONObject jsonObject = new JSONObject(BuildingData.buildingEntries);
            JSONArray jsonArray = jsonObject.getJSONArray("buildings");
            System.out.println(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jO = jsonArray.getJSONObject(i);
                // Building currentBuilding = new Building(name, code, number, lat, lng);
                Building currentBuilding = new Building((String) jO.get("name"), (String) jO.get("code"), (String) jO.get("building_id"), (String) jO.get("lat"), (String) jO.get("lng"));
                //System.out.println(jO.get("lat"));
                buildingArrayList.add(currentBuilding);

            }
        } catch (JSONException je) {
            je.printStackTrace();
            System.out.println("Error");
            Log.e("BaseMapActivity", "JSONObject error");
        }
        //System.out.println(buildingArrayList);
        DatabaseTable.buildings = buildingArrayList;
    }
    public void updateQueryMatches(String q){
        //todo add length checker (must be greater than 1 character )
        String query = q.toLowerCase();
        Pattern pat = Pattern.compile(query);
        ArrayList<Building> matches = new ArrayList<>();
        for ( Building b: buildings){
            //if (b.getName().toLowerCase().matches("..")){
            Boolean isNameMatched = pat.matcher(b.getName().toLowerCase()).find();
            Boolean isCodeMatched = pat.matcher(b.getCode().toLowerCase()).find();
            if ( isNameMatched || isCodeMatched ){
                matches.add(b);
                //DebuggingTools.logd("TRUE");
            } else{
                //DebuggingTools.logd("FALSE");
            }
        }
        matchedBuildings =  matches;
    }


}