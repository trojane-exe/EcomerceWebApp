package org.example.cartentity.FeignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "localhost:8081/api/products")
public interface ProductFeignClient {

    @PatchMapping("/update-stock/{id}")
    void updateStock(@RequestParam("id") Integer id,@RequestParam("stock") int stock);

    @GetMapping("")
    void getAllProducts();

    @Get

}
