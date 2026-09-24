
package com.shopmart.order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/api/inventory/{id}")
    String getProduct(@PathVariable("id") String id);
    
    @PostMapping("/api/inventory/deduct")
    String deductInventory(@RequestParam("id") String id, @RequestParam("qty") int qty);
}
