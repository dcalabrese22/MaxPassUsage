package com.dcalabrese22.dan.skipassusage;

/**
 * Created by dan on 12/19/17.
 */

public class SkiArea {

    private String resortName;
    private double latitude;
    private double longitude;
    private int timesGone;

    public SkiArea(String resortName, double latitude, double longitude, int timesGone) {
        this.resortName = resortName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timesGone = timesGone;
    }

    public SkiArea(String resortName, int timesGone) {
        this.resortName = resortName;
        this.timesGone = timesGone;
    }

    public String getResortName() {
        return resortName;
    }

    public void setResortName(String resortName) {
        this.resortName = resortName;
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

    public int getTimesGone() {
        return timesGone;
    }

    public void setTimesGone(int timesGone) {
        this.timesGone = timesGone;
    }
}
