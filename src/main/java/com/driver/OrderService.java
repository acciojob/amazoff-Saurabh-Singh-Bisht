package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class OrderService {
    private OrderRepository orderRepository = new OrderRepository();
    public void addOrder(Order order) {
        orderRepository.add(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        orderRepository.add(deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        Optional<Order> orderOpt = orderRepository.getOrderByOrderId(orderId);
        Optional<DeliveryPartner> partnerOpt = orderRepository.getPartnerByPartnerId(partnerId);
        if (orderOpt.isEmpty()){
            throw new RuntimeException("Order ID not found:");
        }
        if(partnerId.isEmpty()){
            throw new RuntimeException("Partner ID not found:");
        }
        DeliveryPartner partner = orderRepository.getPartnerByPartnerId(partnerId).get();
        partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
        orderRepository.add(partner);

        orderRepository.add(partnerId, orderId);
    }

    public Order getOrderById(String orderId) {
        Optional<Order> orderOpt = orderRepository.getOrderByOrderId(orderId);
        if(orderOpt.isPresent()){
            return orderOpt.get();
        }
        throw new RuntimeException("Order not found");
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        Optional<DeliveryPartner> partnerOpt = orderRepository.getPartnerByPartnerId(partnerId);
        if(partnerOpt.isPresent()){
            return partnerOpt.get();
        }
        throw new RuntimeException("Partner not found");
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        List<String> orderList = orderRepository.getOrderListByPartnerId(partnerId);
        return orderList.size();
    }

    public List<String> getOrderListByPartnerId(String partnerId) {
        return orderRepository.getOrderListByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        List<String> allOrders = getAllOrders();
        List<String> assignedOrders = orderRepository.getAllAssignedOrders();
        return allOrders.size() - assignedOrders.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int givenTime = TimeUtility.convertDeliveryTime(time);
        List<String> orders = orderRepository.getOrderListByPartnerId(partnerId);
        int count =0;
        for (String ord: orders){
            Order currOrder = orderRepository.getOrderByOrderId(ord).get();
            int actualTime = currOrder.getDeliveryTime();
            if(actualTime > givenTime){
                count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int max =0;
        List<String> orders = orderRepository.getOrderListByPartnerId(partnerId);
        for (String ord: orders){
            Order order = orderRepository.getOrderByOrderId(ord).get();
            if(order.getDeliveryTime() > max){
                max = order.getDeliveryTime();
            }
        }
        return TimeUtility.convertDeliveryTime(max);
    }

    public void deletePartnerById(String partnerId) {
        List<String> orderList = orderRepository.getOrderListByPartnerId(partnerId);
        for (String order: orderList){
            orderRepository.removePartnerOrderPair(order);
        }
        orderRepository.getPartner(partnerId);
    }

    public void deleteOrder(String orderId) {
        String partnerId = orderRepository.getPartnerByOrderId(orderId);
        List<String> ordersList = orderRepository.getOrderListByPartnerId(partnerId);
        ordersList.remove(orderId);
        orderRepository.deleteOrder(orderId);
    }
}
