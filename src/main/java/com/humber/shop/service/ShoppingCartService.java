package com.humber.shop.service;

import com.humber.shop.model.CartItem;
import com.humber.shop.model.Product;
import com.humber.shop.model.ShoppingCart;
import com.humber.shop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private ProductService productService;

    public ShoppingCart getCartForUser(String webUserId) {
        return cartRepository.findByWebUserId(webUserId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setWebUserId(webUserId);
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
    }

    public void addItemToCart(String webUserId, String productId, int quantity) {
        ShoppingCart cart = getCartForUser(webUserId);
        Product product = productService.getProductById(productId);
        
        if (product != null) {
            Optional<CartItem> existingItem = cart.getItems().stream()
                    .filter(item -> item.getProductId().equals(productId))
                    .findFirst();

            if (existingItem.isPresent()) {
                existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
            } else {
                CartItem newItem = new CartItem(productId, product.getName(), product.getPrice(), quantity);
                cart.getItems().add(newItem);
            }
            updateTotalPrice(cart);
            cartRepository.save(cart);
        }
    }

    public void removeItemFromCart(String webUserId, String productId) {
        ShoppingCart cart = getCartForUser(webUserId);
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    public void clearCart(String webUserId) {
        ShoppingCart cart = getCartForUser(webUserId);
        cart.getItems().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }

    private void updateTotalPrice(ShoppingCart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(total);
    }
}
