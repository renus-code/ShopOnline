package com.humber.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "shopping_carts")
public class ShoppingCart {
    @Id
    private String id;
    private String webUserId;
    private List<CartItem> items = new ArrayList<>();
    private double totalPrice;
}
