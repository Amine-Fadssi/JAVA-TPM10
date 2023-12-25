package com.example.fxtest_02.dao.impl;


import com.example.fxtest_02.dao.ProductDAO;
import com.example.fxtest_02.entities.Product;
import com.example.fxtest_02.exception.NullObjectException;
import com.example.fxtest_02.util.ConnectionManagerDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public void save(Product product) {
        if(product == null)
            throw new NullObjectException("save","product");
        String query = "INSERT INTO product (Name, Price, Quantity) VALUES (?, ?, ?)";

        try(Connection connection = ConnectionManagerDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1,product.getName());
            statement.setDouble(2,product.getPrice());
            statement.setInt(3,product.getQuantity());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving product: " + e.getMessage());
        }
    }

    @Override
    public void removeById(Long id) {
        String query = "DELETE FROM product WHERE ID_Product=?";

        try(Connection connection = ConnectionManagerDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM product";
        List<Product> products = new ArrayList<Product>();
        try(Connection connection = ConnectionManagerDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()){
                long ID_Product = resultSet.getLong("ID_Product");
                String Name = resultSet.getString("Name");
                double Price = resultSet.getDouble("Price");
                int Quantity = resultSet.getInt("Quantity");

                Product product = new Product(ID_Product, Name, Price, Quantity);
                products.add(product);
            }

        }catch (SQLException e){
            throw new RuntimeException("Error get all clients : " + e.getMessage());
        }
        return products;
    }

    @Override
    public void update(Product product) {
        String query = "UPDATE product SET Name = ?, Price = ?, Quantity = ? WHERE ID_Product = ?";

        try (Connection connection = ConnectionManagerDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setLong(4, product.getID_product());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product: " + e.getMessage());
        }
    }

    @Override
    public List<Product> searchProductByQuery(String query) {
        List<Product> products = new ArrayList<>();
        String searchQuery = "SELECT * FROM product WHERE Name LIKE ? OR Price LIKE ? OR Quantity LIKE ? ";

        try (Connection connection = ConnectionManagerDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(searchQuery)) {

            String param = "%" + query + "%";

            statement.setString(1, param);
            statement.setString(2, param);
            statement.setString(3, param);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long ID_Product = resultSet.getLong("ID_Product");
                    String Name = resultSet.getString("Name");
                    double Price = resultSet.getDouble("Price");
                    int Quantity = resultSet.getInt("Quantity");

                    Product product = new Product(ID_Product, Name, Price, Quantity);
                    products.add(product);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching clients: " + e.getMessage());
        }

        return products;
    }
}
