package org.example.cartentity.FeignClient;


import jakarta.ws.rs.Path;
import org.example.cartentity.Model.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", url = "localhost:8081/api/product")
public interface ProductFeignClient {

    @PutMapping("/update-stock/{id}")
    void updateStock(@PathVariable("id") Integer id, @RequestParam("stock") int stock);

    @GetMapping("")
    void getAllProducts();

    @PutMapping("/decreaseStock")
    void decreaseStock(@RequestParam("productId") Integer productId,@RequestParam("qte") int qte);

    @GetMapping("/{id}")
    ProductDTO getProductById(@PathVariable("id") Integer id);



    @GetMapping("img")
    public String getImage(@RequestParam("productId")Integer productId);




}
