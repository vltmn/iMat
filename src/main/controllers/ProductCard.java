package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ProductCard extends AnchorPane {
    @FXML
    private Label productName;
    public ProductCard(Product p) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/product_card.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productName.setText(p.getName());
    }
}
