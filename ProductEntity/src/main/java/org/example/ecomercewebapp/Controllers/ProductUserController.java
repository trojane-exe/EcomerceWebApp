package org.example.ecomercewebapp.Controllers;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecomercewebapp.Model.Product;
import org.example.ecomercewebapp.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Data
@RequestMapping("/api/product/shop")
@Validated
public class ProductUserController {

    private final ProductService productService;

    @Autowired
    public ProductUserController (@Qualifier("productServiceImpl") ProductService ps){this.productService = ps;}




    public static void getResonse(){
        Map<String,String> response = new HashMap<>();
        response.put("NOT FOUND","Enable to find compatible products");
        ResponseEntity.badRequest().body(response);
    }



    @GetMapping("")
    public ResponseEntity<?> getAllProducts(){
        List<Product>products = productService.allProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<?> searchByName(@RequestParam("name") String name){
        Map<String,String> response = new HashMap<>();
        List<Product> products = productService.searchByName(name);
        if(products.isEmpty()){
            response.put("NOT FOUND","Enable to find compatible products");
            return ResponseEntity.badRequest().body(response);
        }
        else{
            return ResponseEntity.ok(products);
        }
    }




    @GetMapping("/search-by-category")
    public ResponseEntity<?> searchByCat(@RequestParam("categorie")String categorie){
        //Map<String,String> response = new HashMap<>();
        List<Product>products = productService.searchByCategory(categorie);
        if(products.isEmpty()){
            /*response.put("NOT FOUND","Enable to find compatible products");
            return ResponseEntity.badRequest().body(response);*/
            getResonse();
        }
        else{
            return ResponseEntity.ok(products);
        }
        return null;
    }
}
