
package com.shopmart.order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    
    @Autowired
    private InventoryClient inventoryClient;
    
    @PostMapping("/create")
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "createOrderFallback")
    public String createOrder(@RequestParam String productId, @RequestParam int qty) {
        log.info("Creating order for product {}", productId);
        String orderId = UUID.randomUUID().toString();
        
        // 1. Deduct Inventory (Synchronous Feign Client)
        String inventoryResponse = inventoryClient.deductInventory(productId, qty);
        log.info("Inventory response: {}", inventoryResponse);
        
        return "Order " + orderId + " created. Inventory status: " + inventoryResponse;
    }
    
    public String createOrderFallback(String productId, int qty, Throwable t) {
        log.error("Fallback triggered due to: {}", t.getMessage());
        return "Order service is currently unavailable or inventory failed. Please try again later.";
    }
}
