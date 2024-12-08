package org.example.cartentity.Controllers;


import lombok.Data;
import org.example.cartentity.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Data
@RestController
@RequestMapping("/api/carts")
@Validated
public class CartController {


    private final CartService cartService;


    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/create-cart")
    public ResponseEntity<?>createCart(@RequestParam("userId") Integer userId){
        Map<String,String> response = new HashMap<>();
        String result = cartService.createCart(userId);
        if(result.equals("creation error")){
            response.put("ERROR","Enable to create the cart try later");
            return ResponseEntity.badRequest().body(response);
        }
        else {
            response.put("CREATED","Cart created successfully");
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestParam("userId") Integer userId, @RequestParam("productId") Integer productId, @RequestParam("qte") int qte) {
        Map<String, String> response = new HashMap<>();
        String result = cartService.addToCart(userId, productId, qte);
        if (result.equals("error stock")) {
            response.put("ERROR", "Insufficient product stock");
            return ResponseEntity.badRequest().body(response);
        } else if (result.equals("error")) {
            response.put("ERROR", "Product not found or cart not found");
            return ResponseEntity.badRequest().body(response);
        } else {
            response.put("SUCCESS", "Product added to cart");
            return ResponseEntity.ok().body(response);
        }
    }
}
