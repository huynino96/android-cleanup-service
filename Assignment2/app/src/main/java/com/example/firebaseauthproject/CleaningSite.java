package com.example.firebaseauthproject;

import java.util.List;

public class CleaningSite {
    private String siteName;
    private String address;
    private double lat;
    private double lon;
    private String ownerId;
    private List<Volunteer> volunteers;

    public CleaningSite() {

    }

    public CleaningSite(String siteName, String address, double lat, double lon, String ownerId) {
        this.siteName = siteName;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.ownerId = ownerId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }
}
