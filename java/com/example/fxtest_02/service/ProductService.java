package com.example.fxtest_02.service;




import com.example.fxtest_02.entities.Product;

import java.util.List;

public interface ProductService {
    public void addProduct(Product products);
    public void deleteProductBy(long id);
    public List<Product> getAllProducts();
    public void updateProduct(Product product);
    public List<Product> searchProductByQuery(String query);
}
