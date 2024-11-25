package org.example.ecomercewebapp.Services;

import org.example.ecomercewebapp.Model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;




public interface ProductService {

    public String addProduct(Product product, MultipartFile file) throws IOException;

    Boolean isValidImageType(String type);

    public String updateProduct(Integer id, Product product);
    public String deleteProduct(Integer id);
    public List<Product> allProducts();
    public Product getProduct(Integer id);
    public List<Product> searchByName(String name);
    public String updateStock(Integer id, int newStock);
    public List<Product> searchByCategory(String category);
}
