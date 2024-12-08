package org.example.ecomercewebapp.FeignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cart-service", url = "localhost:8082/api/carts")
public interface CartFeignClient {
    @PostMapping("/create-cart")
    ResponseEntity<?> createCart(@RequestParam("userId")Integer userId);
}
