package org.example.ecomercewebapp.Services;

import org.example.ecomercewebapp.Model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class ProductServiceImpl implements ProductService{
    @Override
    public String addProduct(Product product, MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public Boolean isValidImageType(String type) {
        return null;
    }

    @Override
    public String updateProduct(Integer id, Product product) {
        return null;
    }

    @Override
    public String deleteProduct(Integer id) {
        return null;
    }

    @Override
    public List<Product> allProducts() {
        return null;
    }

    @Override
    public Product getProduct(Integer id) {
        return null;
    }

    @Override
    public List<Product> searchByName(String name) {
        return null;
    }

    @Override
    public String updateStock(Integer id, int newStock) {
        return null;
    }

    @Override
    public List<Product> searchByCategory(String category) {
        return null;
    }
}
