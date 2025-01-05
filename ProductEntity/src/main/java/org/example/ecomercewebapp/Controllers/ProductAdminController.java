package org.example.ecomercewebapp.Controllers;


import lombok.Data;
import org.example.ecomercewebapp.Model.Product;
import org.example.ecomercewebapp.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@RestController
@RequestMapping("/api/product")
@Validated
public class ProductAdminController {



    private final ProductService productService;


    @Autowired
    public ProductAdminController(@Qualifier("productServiceAdminImpl") ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/find-name")
    public ResponseEntity<?> searchByName(@RequestParam("name")String name){

        Map<String,String> response = new HashMap<>();
        List<Product>products = productService.searchByName(name);
        if(products.isEmpty()){
            response.put("NOT FOUND","Enable to find a compatible products");
            return ResponseEntity.badRequest().body(response);
        }
        else {
            return ResponseEntity.ok(products);
        }
    }
    @GetMapping("/find-category")
    public ResponseEntity<?> searchByCategory(@RequestParam("categorie") String categorie){
        List<Product> products = productService.searchByCategory(categorie);
        if(products.isEmpty()){
            ProductUserController.getResponse();
        }
        else{
            return ResponseEntity.ok(products);
        }
        return null;
    }


    @GetMapping("img")
    public ResponseEntity<String>getImage(@RequestParam("productId")Integer productId){
        return ResponseEntity.ok(productService.getImage(productId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id){
        Map<String,String> response = new HashMap<>();
        Product product = productService.getProduct(id);
        if(product == null){
            response.put("NOT FOUND","Cant find a product with this id");
            return ResponseEntity.badRequest().body(response);
        }
        else{
            return ResponseEntity.ok(product);
        }
    }

    @GetMapping("/numberOut")
    public ResponseEntity<Integer> getOutCount(){
        return ResponseEntity.ok(productService.outStock());
    }

    @PutMapping("/update-stock/{id}")
        public ResponseEntity<?>updateStock(@PathVariable("id") Integer id ,@RequestParam("stock") int stock) {
        Map<String, String> response = new HashMap<>();
        String result = productService.updateStock(id, stock);

        return switch (result) {
            case "error":
                response.put("ERROR", "Product not found or invalid stock number");
                yield ResponseEntity.badRequest().body(response);
            case null:
                response.put("SUCCESS", "Stock updated successfully");
                yield ResponseEntity.ok().body(response);
            default:
                throw new IllegalStateException("Unexpected value: " + result);
        };
    }



    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product>products = productService.allProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Product>> getAvailableProducts(){
        List<Product>products = productService.availableProducts();

        return ResponseEntity.ok(products);
    }
    @GetMapping("/out")
    public ResponseEntity<List<Product>> getOutProducts(){
        List<Product>products = productService.outOfStockeProducts();

        return ResponseEntity.ok(products);
    }
    @PutMapping("/decreaseStock")
    public ResponseEntity<?>decreaseStock(@RequestParam("productId")Integer productId,@RequestParam("qte")int qte){
        Map<String,String> response = new HashMap<>();
        String result = productService.decreaseStock(productId,qte);
        if(result.equals("error")){
            response.put("ERROR","Insufficient product stock");
            return ResponseEntity.badRequest().body(response);
        }
        else{
            response.put("SUCCESS","Stock updated successfully");
            return ResponseEntity.ok().body(response);
        }
    }


    @PostMapping("/add-product")
    public ResponseEntity<?>addProduct(@ModelAttribute Product product,@RequestParam("img") MultipartFile img)throws IOException {

        Map<String,String> response = new HashMap<>();
        String result = productService.addProduct(product,img);
        return switch (result) {
            case "missing fields" -> {
                response.put("ERROR", "Enable to add the product :" +
                        "Missing fields");
                yield ResponseEntity.badRequest().body(response);
            }
            case "error image" -> {
                response.put("ERROR", "Enable to add the product :" +
                        "Invalid image type");
                yield ResponseEntity.badRequest().body(response);
            }
            case "saved with image" -> {
                response.put("SUCCESS", "Product added successfully");
                yield ResponseEntity.ok().body(response);
            }
            case "saved without image" -> {
                response.put("SUCCESS", "Product added without image");
                yield ResponseEntity.ok().body(response);
            }
            default -> {
                response.put("UNEXPECTED ERROR", new IOException("UNKNOWN ERROR").getMessage());
                yield ResponseEntity.badRequest().body(response);
            }
        };
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<?>updateProduct(@PathVariable("id") Integer id,@ModelAttribute Product product,@RequestParam("img") MultipartFile img) throws IOException {
        Map<String,String> response = new HashMap<>();
        String result = productService.updateProduct(id,product,img);
        if(result.equals("ok")){
            response.put("SUCCESS","Product updated successfully");
            return ResponseEntity.ok().body(response);
        }
        else{
            response.put("ENABLE TO UPDATE","Invalid inputs");
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PutMapping("/update-product-noImg/{id}")
    public ResponseEntity<?>updateProductNoImg(@PathVariable("id") Integer id,@ModelAttribute Product product) throws IOException {
        Map<String,String> response = new HashMap<>();
        String result = productService.updateProductNoImg(id,product);
        if(result.equals("ok")){
            response.put("SUCCESS","Product updated successfully");
            return ResponseEntity.ok().body(response);
        }
        else{
            response.put("ENABLE TO UPDATE","Invalid inputs");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
