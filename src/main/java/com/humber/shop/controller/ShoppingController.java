package com.humber.shop.controller;

import com.humber.shop.model.CustomerDTO;
import com.humber.shop.model.Product;
import com.humber.shop.model.WebUser;
import com.humber.shop.service.CustomerServiceClient;
import com.humber.shop.service.ProductService;
import com.humber.shop.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ShoppingController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        List<Product> products = productService.getAllProducts();
        
        // Add sample products if none exist
        if (products.isEmpty()) {
            productService.saveProduct(new Product(null, "Laptop", "High performance laptop", 1200.00, 10));
            productService.saveProduct(new Product(null, "Smartphone", "Latest model smartphone", 800.00, 20));
            productService.saveProduct(new Product(null, "Headphones", "Noise cancelling headphones", 150.00, 50));
            products = productService.getAllProducts();
        }
        
        WebUser user = (WebUser) session.getAttribute("loggedInUser");
        model.addAttribute("user", user);
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        WebUser user = (WebUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        String userId = user.getLoginId();
        
        model.addAttribute("cart", cartService.getCartForUser(userId));
        model.addAttribute("userId", userId);
        model.addAttribute("user", user);
        
        // Fetch customer info from CustomerService to pre-fill address
        CustomerDTO customer = customerServiceClient.getCustomerByLoginId(userId);
        model.addAttribute("customer", customer);
        
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String productId, 
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session) {
        WebUser user = (WebUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        String userId = user.getLoginId();
        cartService.addItemToCart(userId, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam String productId,
                                 HttpSession session) {
        WebUser user = (WebUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        String userId = user.getLoginId();
        cartService.removeItemFromCart(userId, productId);
        return "redirect:/cart";
    }
}
