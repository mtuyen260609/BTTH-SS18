
package com.shopmart.inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    @GetMapping("/{id}")
    public String getProduct(@PathVariable String id) {
        return "Product " + id + " details";
    }
    
    @PostMapping("/deduct")
    public String deductInventory(@RequestParam String id, @RequestParam int qty) {
        log.info("Deducting {} from product {}", qty, id);
        return "Deducted " + qty + " from product " + id;
    }
}
