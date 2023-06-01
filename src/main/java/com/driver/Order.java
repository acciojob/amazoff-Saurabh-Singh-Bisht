package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.id = id;
        this.deliveryTime = TimeUtility.convertDeliveryTime(deliveryTime);
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

//    private int convertDeliveryTime(String deliveryTime) {
//        String[] arr = deliveryTime.split(":");
//        return Integer.parseInt(arr[0]) * 60 + Integer.parseInt(arr[1]);
//    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
    public String getDeliveryTimeString(){
        return TimeUtility.convertDeliveryTime(this.deliveryTime);
    }

//    private String convertDeliveryTime(int deliveryTime) {
//        String hh = String.valueOf(deliveryTime/60);
//        String mm = String.valueOf(deliveryTime%60);
//        return String.format("%s:%s", hh, mm);
//    }
}
