package com.example.fxtest_02.dao;


import com.example.fxtest_02.entities.Product;

import java.util.List;

public interface ProductDAO extends DAO<Product,Long>{
    List<Product> searchProductByQuery(String query);
}
