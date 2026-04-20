package com.humber.shop.controller;

import com.humber.shop.model.Order;
import com.humber.shop.model.WebUser;
import com.humber.shop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/place")
    public String placeOrder(@RequestParam String shippingAddress,
                             HttpSession session) {
        WebUser user = (WebUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        orderService.placeOrder(user.getLoginId(), shippingAddress);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String listOrders(HttpSession session, Model model) {
        WebUser user = (WebUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        String userId = user.getLoginId();
        List<Order> orders = orderService.getOrdersForUser(userId);
        model.addAttribute("orders", orders);
        model.addAttribute("userId", userId);
        model.addAttribute("user", user);
        return "orders";
    }

    @GetMapping("/order/detail")
    public String orderDetail(@RequestParam String orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "order_detail";
    }
}
