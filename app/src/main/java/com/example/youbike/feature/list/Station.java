package com.example.youbike.feature.list;

public class Station {
    private String stationUID;
    private String stationName;
    private double positionLat;
    private double positionLon;
    private int bikesCapacity;

    public Station(String stationUID, String stationName, double positionLat, double positionLon, int bikesCapacity) {
        this.stationUID = stationUID;
        this.stationName = stationName;
        this.positionLat = positionLat;
        this.positionLon = positionLon;
        this.bikesCapacity = bikesCapacity;
    }

    public String getStationUID() { return stationUID; }
    public void setStationUID(String stationUID) { this.stationUID = stationUID; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public double getPositionLat() { return positionLat; }
    public void setPositionLat(double positionLat) { this.positionLat = positionLat; }

    public double getPositionLon() { return positionLon; }
    public void setPositionLon(double positionLon) { this.positionLon = positionLon; }

    public int getBikesCapacity() { return bikesCapacity; }
    public void setBikesCapacity(int bikesCapacity) { this.bikesCapacity = bikesCapacity; }
}
