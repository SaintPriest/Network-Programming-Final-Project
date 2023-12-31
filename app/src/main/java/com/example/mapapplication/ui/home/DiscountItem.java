package com.example.mapapplication.ui.home;

import com.google.android.gms.maps.model.LatLng;


public class DiscountItem {
    public String name;
    public double lat;
    public double lng;

    public DiscountItem(String name, double lat, double lng)
    {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
}
