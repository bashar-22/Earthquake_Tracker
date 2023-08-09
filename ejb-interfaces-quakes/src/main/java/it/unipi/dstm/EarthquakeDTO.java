package it.unipi.dstm;

//import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

public class EarthquakeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private double magnitude;
    private double latitude;
    private double longitude;
    private double depth;
    private Date date;
    private String region;

    public double  getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double  magnitude) {
        this.magnitude = magnitude;
    }

    public double  getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double  getLongitude() {
        return longitude;
    }

    public void setLongitude(double  longitude) {
        this.longitude = longitude;
    }

    public double  getDepth() {
        return depth;
    }

    public void setDepth(double  depth) {
        this.depth = depth;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    @Override
    public String toString() {
        return "EarthquakeDTO{" +
                "magnitude=" + magnitude +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", depth=" + depth +
//                ", datetime=" + date +
                '}';
    }
}
