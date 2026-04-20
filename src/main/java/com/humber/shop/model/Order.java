package com.humber.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private String orderId;
    private String webUserId;
    private Date orderedDate;
    private Date shippedDate;
    private String shipToAddress;
    private OrderStatus status;
    private double totalAmount;
    private List<CartItem> orderItems;
}
