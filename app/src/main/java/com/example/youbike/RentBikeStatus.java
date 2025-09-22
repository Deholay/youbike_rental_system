package com.example.youbike;

public class RentBikeStatus {

    public static boolean isIsRenting() {
        return isRenting;
    }

    public static void setIsRenting(boolean isRenting) {
        RentBikeStatus.isRenting = isRenting;
    }

    private static boolean isRenting = false;

    public static String getBikeIDNowImRenting() {
        return BikeIDNowImRenting;
    }

    public static void setBikeIDNowImRenting(String bikeIDNowImRenting) {
        BikeIDNowImRenting = bikeIDNowImRenting;
    }

    private static String BikeIDNowImRenting;

}
