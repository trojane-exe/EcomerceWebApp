package org.example.ecomercewebapp.Services;

import org.example.ecomercewebapp.Model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;


public interface ProductService {

    public String addProduct(Product product, MultipartFile file) throws IOException;

    Boolean isValidImageType(String type);

    public String updateProduct(Integer id, Product product, MultipartFile file) throws IOException;
    public String deleteProduct(Integer id);
    public List<Product> allProducts();
    public Product getProduct(Integer id);
    public List<Product> searchByName(String name);
    public String updateStock(Integer id, int newStock);
    public List<Product> searchByCategory(String category);
    public List<Product> availableProducts();
    public List<Product> outOfStockeProducts();
    public String decreaseStock(Integer productId,int qte);
    public String getImage(Integer productId);

    public String updateProductNoImg(Integer id, Product product);

    public Integer outStock();
}
