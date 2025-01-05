package org.example.ecomercewebapp.Services;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.example.ecomercewebapp.Model.Product;
import org.example.ecomercewebapp.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Data
@Service
@Transactional
public class ProductServiceAdminImpl implements ProductService{

    private final ProductRepository productRepository;



    @Autowired
    public ProductServiceAdminImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @Override
    public String addProduct(Product product, MultipartFile file) throws IOException {
        if (product.getNom() == null || product.getDescription() == null || product.getPrix() == null || product.getPrix() <= 0 || product.getCategorie() == null) {
            return "missing fields";
        }
        if (file != null && !file.isEmpty()) {
            if (!isValidImageType(file.getContentType())) {
                return "error image";
            }
            product.setImage(file.getBytes());
        } else {
            product.setImage(null);
        }
        if (product.getStock() == null || product.getStock() < 0) {
            product.setStock(0);
        }
        productRepository.save(product);

        if (file != null && !file.isEmpty()) {
            return "saved with image";
        } else {
            return "saved without image";
        }
    }


    @Override
    public Boolean isValidImageType(String type){
        return type.equals("image/jpeg")||type.equals("image/png");
    }

    @Override
    public String updateProduct(Integer id, Product product, @RequestPart(value = "img", required = false) MultipartFile file) throws IOException {


        Product oldproduct = productRepository.findById(id).orElse(null);
        if(oldproduct==null){
            return "error";
        }
        else{
            if(product.getNom()!=null){
                oldproduct.setNom(product.getNom());
            }
            if(product.getDescription()!=null){
                oldproduct.setDescription(product.getDescription());
            }
            if(product.getCategorie()!=null){
                oldproduct.setCategorie(product.getCategorie());
            }
            if(product.getPrix()!=null && !(product.getPrix() <0)){
                oldproduct.setPrix(product.getPrix());
            }
            if(product.getStock()!=null && !(product.getStock()<0)){
                oldproduct.setStock(product.getStock());
            }
            if(file!=null && !file.isEmpty()){
                if(isValidImageType(file.getContentType())){
                    oldproduct.setImage(file.getBytes());
                }

            }
            productRepository.save(oldproduct);
            return "ok";
        }
    }

    @Override
    public String updateProductNoImg(Integer id, Product product) {

        Product oldproduct = productRepository.findById(id).orElse(null);
        if (oldproduct == null) {
            return "error";
        } else {
            if (product.getNom() != null) {
                oldproduct.setNom(product.getNom());
            }
            if (product.getDescription() != null) {
                oldproduct.setDescription(product.getDescription());
            }
            if (product.getCategorie() != null) {
                oldproduct.setCategorie(product.getCategorie());
            }
            if (product.getPrix() != null && !(product.getPrix() < 0)) {
                oldproduct.setPrix(product.getPrix());
            }
            if (product.getStock() != null && !(product.getStock() < 0)) {
                oldproduct.setStock(product.getStock());
            }
            if (product.getImage() != null) {
                oldproduct.setImage(Base64.getDecoder().decode(product.getImage()));
            }
            productRepository.save(oldproduct);
            return "ok";
        }
    }

    @Override
    public Integer outStock() {
        return productRepository.outStock();
    }


    @Override
    public String deleteProduct(Integer id) {
        productRepository.deleteById(id);
        return null;
    }

    @Override
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public List<Product> searchByName(String name) {

        return productRepository.findByName(name);

    }

    @Override
    public String updateStock(Integer id, int newStock) {
        Product p = productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product not found"));
        assert p!=null;
        if(newStock>=0) {
            p.setStock(newStock);

            productRepository.save(p);
            return null;
        }
        else{
            return "error";
        }
    }

    @Override
    public List<Product> searchByCategory(String category) {


        return productRepository.findByCategorie(category);
    }

    @Override
    public List<Product> availableProducts() {
        return productRepository.availableProducts();
    }

    @Override
    public List<Product> outOfStockeProducts() {
        return productRepository.outOfStockProducts();
    }

    @Override
    public String decreaseStock(Integer productId,int qte){

        Product product = productRepository.findById(productId).orElse(null);
        assert product!=null;
        if(product.getStock()<=0 ||product.getStock()<qte){
            return "error";
        }
        else{
            product.setStock(product.getStock()-qte);
            return "ok";
        }
    }

    @Override
    public String getImage(Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);

        assert product != null;

        return Base64.getEncoder().encodeToString(product.getImage());
    }

}
