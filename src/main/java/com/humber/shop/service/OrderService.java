package com.humber.shop.service;

import com.humber.shop.model.Order;
import com.humber.shop.model.OrderStatus;
import com.humber.shop.model.ShoppingCart;
import com.humber.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartService cartService;

    public Order placeOrder(String webUserId, String shippingAddress) {
        ShoppingCart cart = cartService.getCartForUser(webUserId);
        
        if (cart.getItems().isEmpty()) {
            return null;
        }

        Order order = new Order();
        order.setWebUserId(webUserId);
        order.setOrderedDate(new Date());
        order.setShipToAddress(shippingAddress);
        order.setStatus(OrderStatus.NEW);
        order.setOrderItems(new ArrayList<>(cart.getItems()));
        order.setTotalAmount(cart.getTotalPrice());

        Order savedOrder = orderRepository.save(order);
        
        // Clear the cart after order is placed
        cartService.clearCart(webUserId);
        
        return savedOrder;
    }

    public List<Order> getOrdersForUser(String webUserId) {
        return orderRepository.findByWebUserId(webUserId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}
