package com.driver;

import java.util.*;

public class OrderRepository {
    private Map<String, Order> orderMap = new HashMap<>();
    private Map<String, DeliveryPartner> partnerMap = new HashMap<>();
    private Map<String,List<String>> partnerOrderPairMap = new HashMap<>();
    private Map<String,String> orderPartnerPairMap = new HashMap<>();
    public void add(Order order) {
        orderMap.put(order.getId(), order);
    }

    public void add(DeliveryPartner deliveryPartner) {
        partnerMap.put(deliveryPartner.getId(), deliveryPartner);
    }

    public Optional<Order> getOrderByOrderId(String orderId) {
        if(orderMap.containsKey(orderId)){
            return Optional.of(orderMap.get(orderId));
        }
        return Optional.empty();
    }

    public Optional<DeliveryPartner> getPartnerByPartnerId(String partnerId) {
        if(partnerMap.containsKey(partnerId)){
            return Optional.of(partnerMap.get(partnerId));
        }
        return Optional.empty();
    }

    public void add(String partnerId, String orderId) {
        List<String> orderList = partnerOrderPairMap.getOrDefault(partnerId, new ArrayList<>());
        orderList.add(orderId);
        partnerOrderPairMap.put(partnerId, orderList);
        orderPartnerPairMap.put(orderId, partnerId);
    }

    public List<String> getOrderListByPartnerId(String partnerId) {
        return new ArrayList<>(partnerOrderPairMap.getOrDefault(partnerId, new ArrayList<>()));
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public List<String> getAllAssignedOrders() {
        return new ArrayList<>(orderPartnerPairMap.keySet());
    }

    public void removePartnerOrderPair(String order) {
        orderPartnerPairMap.remove(order);
    }

    public void getPartner(String partnerId) {
        partnerMap.remove(partnerId);
        partnerOrderPairMap.remove(partnerId);
    }

    public String getPartnerByOrderId(String orderId) {
        return orderPartnerPairMap.get(orderId);
    }

    public void deleteOrder(String orderId) {
        orderMap.remove(orderId);
        orderPartnerPairMap.remove(orderId);
    }
}
