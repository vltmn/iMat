package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainShop extends AnchorPane {

//    @FXML
//    private GridPane productGrid;

//    @FXML
//    private FlowPane productFlow;

//    @FXML
//    private ScrollPane gridScroll;

    private List<ProductCard> allProducts = new ArrayList<>();

    public MainShop() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/main_shop.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        populateGrid();
    }

    private void populateGrid() {
//        gridScroll.setFitToHeight(true);
//        gridScroll.setFitToWidth(true);



//        allProducts = IMatDataHandler.getInstance().getProducts().stream().limit(40)
//                .map(ProductCard::new).collect(Collectors.toList());
//        productFlow.setPrefHeight(allProducts.size() * 150);
//        allProducts.forEach(productCard -> productFlow.getChildren().add(productCard));


        //populate
    }
}
