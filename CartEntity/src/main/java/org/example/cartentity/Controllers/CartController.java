package org.example.cartentity.Controllers;


import lombok.Data;
import org.example.cartentity.Model.CartItems;
import org.example.cartentity.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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


    @GetMapping("/items")
    public ResponseEntity<List<CartItems>> getUserCartItems(@RequestParam("userId") Integer userId){
        return ResponseEntity.ok(cartService.getUserCartItems(userId));
    }

    @GetMapping("/cartId")
    public ResponseEntity<Integer> getCArtId(@RequestParam("userId")Integer userId){
        return ResponseEntity.ok(cartService.getCartId(userId));
    }



    @PostMapping("/create-cart")
    public ResponseEntity<?>createCart(@RequestParam("userId") Integer userId) {
        Map<String, String> response = new HashMap<>();
        String result = cartService.createCart(userId);
        if (result.equals("creation error")) {
            response.put("ERROR", "Enable to create the cart try later");
            return ResponseEntity.badRequest().body(response);
        } else {
            response.put("CREATED", "Cart created successfully");
            return ResponseEntity.ok().body(response);
        }
    }
    @PutMapping("updateCart")
    public ResponseEntity<?>updateCartQuantity(@RequestParam("userId")Integer userId,@RequestParam("cartItemId")Integer cartItemId,@RequestParam("qte")int qte){
        Map<String,String> response = new HashMap<>();
        String result = cartService.updateQte(userId, cartItemId, qte);
        return switch (result) {
            case "cart null" -> {
                response.put("ERROR", "Cart is null");
                yield ResponseEntity.badRequest().body(response);
            }

            case "product null" -> {
                response.put("ERROR", "Product is null");
                yield ResponseEntity.badRequest().body(response);
            }
            case "cartItems null"->{
                response.put("ERROR","CartItems is null");
                yield ResponseEntity.badRequest().body(response);
            }
            case "error stock"->{
                response.put("ERROR","insufficient stock");
                yield ResponseEntity.badRequest().body(response);
            }
            case "ok"->{
                response.put("SUCCESS","Order updated successfully");
                yield ResponseEntity.ok().body(response);
            }
            default ->{
                response.put("UNEXPECTED ERROR","An unknown error occurred try again later");
                yield ResponseEntity.badRequest().body(response);
            }
        };
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



    @DeleteMapping("/removeItem")
    public ResponseEntity<?>deleteItem(@RequestParam("userId")Integer userId,@RequestParam("cartItemId")Integer cartItemId){
        Map<String,String> response = new HashMap<>();
        String resulat = cartService.removeProductFromCart(userId, cartItemId);
        return switch (resulat){
            case "cart null"->{
                response.put("ERROR","Cart is null");
                yield ResponseEntity.badRequest().body(response);
            }
            case "item null"->{
                response.put("ERROR","CartItem is null");
                yield ResponseEntity.badRequest().body(response);
            }
            case "ok"->{
                response.put("SUCCESS","Items is removed ");
                yield ResponseEntity.ok().body(response);
            }
            default -> {
                response.put("ERROR","UNEXPECTED ERROR ");
                yield ResponseEntity.badRequest().body(response);
            }
        };
    }

    @DeleteMapping("/cancelCart")
    public ResponseEntity<?>cancelCart(@RequestParam("userId")Integer userId){

        Map<String,String> response = new HashMap<>();
        String result = cartService.cancelCart(userId);
        return switch (result){
            case "cart null"->{
                response.put("ERROR","Cart is null");
                yield ResponseEntity.badRequest().body(response);
            }
            case "items null"->{
                response.put("ERROR","Cart is already empty");
                yield ResponseEntity.badRequest().body(response);
            }
            case "ok"->{
                response.put("SUCCESS","Cart is canceled");
                yield ResponseEntity.ok().body(response);
            }
            default -> {
                response.put("WARNING","Unknown error occured");
                yield ResponseEntity.badRequest().body(response);
            }
        };



    }

    @DeleteMapping("/deleteCart/{id}")
    public ResponseEntity<?>deleteCart(@PathVariable("id") Integer userId){
        Map<String,String>response = new HashMap<>();
        String result = cartService.deleteCart(userId);
        if(result.equals("deleted")){
            response.put("Deleted","Cart deleted");
            return ResponseEntity.ok(response);
        }
        else{
            response.put("ERROR","NOT DELETED");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/totalToPay")
    public ResponseEntity<Float> totalToPay(@RequestParam("userId")Integer userId){
        return ResponseEntity.ok(cartService.totalToPay(userId));
    }

}
