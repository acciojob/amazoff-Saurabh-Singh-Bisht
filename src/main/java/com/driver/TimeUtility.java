package com.driver;

public class TimeUtility {
    private TimeUtility() {
    }
    public static int convertDeliveryTime(String deliveryTime) {
        String[] arr = deliveryTime.split(":");
        return Integer.parseInt(arr[0]) * 60 + Integer.parseInt(arr[1]);
    }
    public static String convertDeliveryTime(int deliveryTime) {
        String hh = String.valueOf(deliveryTime/60);
        String mm = String.valueOf(deliveryTime%60);
        if(hh.length() == 1){
            hh = "0" + hh;
        }
        if(mm.length() == 1){
            mm = "0" + mm;
        }
        return hh+":"+mm;
    }
}
