package com.example.aleksandr.testtaskn;

/**
 * Created by aleksandr on 30.04.18.
 */

public class Place {

    private String lon;
    private String lat;
    private String address;
    private String date;

    public Place(String lon, String lat, String address,String date){

        this.lon=lon;
        this.lat = lat;
        this.address = address;
        this.date = date;
    }

    public String getLon() {
        return this.lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}