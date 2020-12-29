package com.example.firebaseauthproject.models;

public class CleaningSite {
    private String siteName;
    private double latitude;
    private double longitude;

    public CleaningSite() {

    }

    public CleaningSite(String siteName, double latitude, double longitude) {
        this.siteName = siteName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
