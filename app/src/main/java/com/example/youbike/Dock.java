package com.example.youbike;

public class Dock {
    private String dockUID;
    private String stationUID;
    private String dockID;
    private String bikeID;
    private String bikeStatus;

    public Dock(String dockUID, String stationUID, String dockID, String bikeID, String bikeStatus) {
        this.dockUID = dockUID;
        this.stationUID = stationUID;
        this.dockID = dockID;
        this.bikeID = bikeID;
        this.bikeStatus = bikeStatus;
    }

    public String getDockUID() {
        return dockUID;
    }

    public String getStationUID() {
        return stationUID;
    }

    public String getDockID() {
        return dockID;
    }

    public String getBikeID() {
        return bikeID;
    }

    public String getbikeStatus() {
        return bikeStatus;
    }
}
