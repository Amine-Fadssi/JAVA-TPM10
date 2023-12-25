package com.example.fxtest_02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("views/product-view.fxml"));
        Scene scene = new Scene(root,602,453);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
