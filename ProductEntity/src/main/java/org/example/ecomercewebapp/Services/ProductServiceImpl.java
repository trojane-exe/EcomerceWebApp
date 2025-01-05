package org.example.ecomercewebapp.Services;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.example.ecomercewebapp.Model.Product;
import org.example.ecomercewebapp.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;

@Service
@Data
@Transactional

public class ProductServiceImpl implements ProductService{


    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl (ProductRepository pr){this.productRepository = pr;}
    @Override
    public String addProduct(Product product, MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public Boolean isValidImageType(String type) {
        return null;
    }

    @Override
    public String updateProduct(Integer id, Product product, MultipartFile file) throws IOException {
        return null;
    }


    @Override
    public String deleteProduct(Integer id) {
        return null;
    }

    @Override
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Integer id) {
        return null;
    }

    @Override
    public List<Product> searchByName(String name) {
        return productRepository.findByName(name);

    }

    @Override
    public String updateStock(Integer id, int newStock) {
        return null;
    }

    @Override
    public List<Product> searchByCategory(String category) {
        return productRepository.findByCategorie(category);

    }

    @Override
    public List<Product> availableProducts() {
        return null;
    }

    @Override
    public List<Product> outOfStockeProducts() {
        return null;
    }

    @Override
    public String decreaseStock(Integer productId,int qte){
        return null;
    }

    @Override
    public String getImage(Integer productId) {
        return null;
    }

    @Override
    public String updateProductNoImg(Integer id, Product product) {
        return null;
    }

    @Override
    public Integer outStock() {
        return null;
    }
}
