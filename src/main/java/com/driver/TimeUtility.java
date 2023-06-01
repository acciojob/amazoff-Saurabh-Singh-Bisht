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
        return String.format("%s:%s", hh, mm);
    }
}
