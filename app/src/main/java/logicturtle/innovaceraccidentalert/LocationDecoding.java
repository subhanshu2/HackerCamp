package com.a2doodh.a2doodhapp.GPS;

import android.location.Address;
import android.location.Geocoder;

import com.a2doodh.a2doodhapp.Activity.A2doodhApp;

import java.io.IOException;
import java.util.List;

/**
 * Created by user on 23-Jul-17.
 */

public class LocationDecoding {

    private double latitude, longitude;
    List<Address> address;
    Address addresses;

    public LocationDecoding(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Address decodeLocation() {

        Geocoder geocoder = new Geocoder(A2doodhApp.getAppContext());
        try {
            address = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (address != null && address.size() > 0) {
            addresses = address.get(0);
        }
        return addresses;
    }

    public String getCity() {
        return addresses.getLocality();
    }

    public String getZipCode() {
        return addresses.getPostalCode();
    }

    public String getState() {
        return addresses.getAdminArea();
    }

    public String getDistrict() {
        return addresses.getSubAdminArea();
    }

    public String getAddress() {
        return addresses.getSubLocality();
    }


}
