package com.humber.shop.service;

import com.humber.shop.model.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String CUSTOMER_SERVICE_URL = "http://localhost:8081/api/customers/";

    public CustomerDTO getCustomerByLoginId(String loginId) {
        try {
            return restTemplate.getForObject(CUSTOMER_SERVICE_URL + loginId, CustomerDTO.class);
        } catch (Exception e) {
            // Log error or return null if service is down/user not found
            System.err.println("Error fetching customer from CustomerService: " + e.getMessage());
            return null;
        }
    }
}
