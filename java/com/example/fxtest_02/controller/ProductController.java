package com.example.fxtest_02.controller;

import com.example.fxtest_02.dao.impl.ProductDAOImpl;
import javafx.scene.control.Alert;
import com.example.fxtest_02.entities.Product;
import com.example.fxtest_02.service.ProductService;
import com.example.fxtest_02.service.impl.ProductServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML public TextField name;
    @FXML public TextField price;
    @FXML public TextField quantity;
    @FXML public ComboBox<String> categories;
    @FXML public TextField searchProduct;
    @FXML public TableView<Product> tableProduct;
    @FXML public TableColumn<?,?> columnID;
    @FXML public TableColumn<Product,String> columnName;
    @FXML public TableColumn<Product,Double> columnPrice;
    @FXML public TableColumn<Product,Integer> columnQuantity;
    @FXML public TableColumn<Product,String> columnCategories;
    ObservableList<Product> data = FXCollections.observableArrayList();

    ProductService service = new ProductServiceImpl(new ProductDAOImpl());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columnID.setCellValueFactory(new PropertyValueFactory<>("ID_product"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnCategories.setCellValueFactory(new PropertyValueFactory<>(""));

        data.addAll(service.getAllProducts());
        tableProduct.setItems(data);

        //Listener to detect searching changes
        searchProduct.textProperty().addListener(((obs, oldValue, newValue) -> {
            data.clear();
            data.addAll(service.searchProductByQuery(newValue));
        }));

        //Listener to detect tableProduct selection changes
        tableProduct.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showSelectedProduct(newSelection);
            }
        });
    }
    public void addProduct() {
        String productName = name.getText();
        String priceText = price.getText();
        String quantityText = quantity.getText();

        if (productName.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields",
                    "Please fill in all fields to add a product.");
        } else {
            try {
                double productPrice = Double.parseDouble(priceText);
                int productQuantity = Integer.parseInt(quantityText);

                Product product = new Product();
                product.setName(productName);
                product.setPrice(productPrice);
                product.setQuantity(productQuantity);

                service.addProduct(product);
                clearInput();
                loadProduct();
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Format",
                        "Please enter a valid price and a valid quantity.");
            }
        }
    }

    public void deleteProduct() {
        Product product = tableProduct.getSelectionModel().getSelectedItem();

        if (product == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No Product Selected",
                    "Please select a product before deleting.");
        } else {
            service.deleteProductBy(product.getID_product());
            loadProduct();
            clearInput();
        }
    }

    public void editProduct(){
        Product productSelected = tableProduct.getSelectionModel().getSelectedItem();
        String productName = name.getText();
        String priceText = price.getText();
        String quantityText = quantity.getText();
        if (productName.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields",
                    "Please fill in all fields to edit a product.");
        } else {
            try {
                double productPrice = Double.parseDouble(priceText);
                int productQuantity = Integer.parseInt(quantityText);
                Product product = new Product();
                product.setID_product(productSelected.getID_product());
                product.setName(productName);
                product.setPrice(productPrice);
                product.setQuantity(productQuantity);

                service.updateProduct(product);
                clearInput();
                loadProduct();

            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Format",
                        "Please enter a valid price and a valid quantity.");
            }
        }
    }
    public void searchProduct(){

    }
    private void loadProduct(){
        data.clear();
        data.addAll(service.getAllProducts());
    }
    private void clearInput(){
        name.setText("");
        price.setText("");
        quantity.setText("");
    }
    private void showSelectedProduct(Product product){
        tableProduct.getSelectionModel().getSelectedItem();
        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        quantity.setText(String.valueOf(product.getQuantity()));
    }
    private static void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }
}
