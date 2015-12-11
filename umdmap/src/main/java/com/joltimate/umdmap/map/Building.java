package com.joltimate.umdmap.map;

import java.util.List;

/**
 * Created by chris on 10/18/15.
 */
public class Building {
    String name;
    String code;
    String number;
    String lat;
    String lng;
    private Building(){
    }
    public Building(String name){
        this.name = name;
    }
    public Building(String name, String code, String number, String lat, String lng){
        this.name = name;
        this.code = code;
        this.number = number;
        this.lat = lat;
        this.lng = lng;
    }
    @Override
    public String toString(){
        return name + " " + code + " " + number + " "  + lat + " "  + lng + " " ;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getNumber() {
        return number;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public static Building getBuildingNamed(String name, List<Building> list){
        for (Building b: list){
            if ( name.equals(b.getName()) || name.equals(b.getCode())){
                return b;
            }
        }
        return null;
    }
}
