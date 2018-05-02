package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainShop extends AnchorPane {
    private static final String SEARCH_FILTER = "SEARCH";
    public static final String CATEGORY_FILTER = "CATEGORY";

//    @FXML
//    private GridPane productGrid;

    @FXML
    private SplitPane splitPane;

    @FXML
    private FlowPane productFlow;

    @FXML
    private ScrollPane gridScroll;

    @FXML
    private SearchBar searchBar;

    @FXML
    private CategoriesList categoriesList;

    private Map<String, Predicate<Product>> productFilters = new HashMap<>();

    private final static int PRODUCT_LIMIT = 100;

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
        setupView();
        populateGrid();

    }

    private void setupView() {
        searchBar.setFilterCallback(((observable, oldValue, newValue) -> {
            List<Integer> productIds = IMatDataHandler.getInstance().findProducts(newValue).stream().map(Product::getProductId).collect(Collectors.toList());
            Predicate<Product> pp = product -> productIds.contains(product.getProductId());
            productFilters.put(SEARCH_FILTER, pp);
            filter();
        }));
        categoriesList.setFilterCallback(((observable, oldValue, newValue) ->  {
            productFilters.put(CATEGORY_FILTER, newValue);
            filter();
        }));
    }

    private void filter() {
        productFlow.getChildren().clear();
        productFlow.getChildren().addAll(allProducts);
        productFilters.values().forEach(pf -> productFlow.getChildren().removeIf(node -> {
            ProductCard pc = (ProductCard) node;
            return !pf.test(pc.getProduct());
        }));
    }

    private void populateGrid() {
        allProducts = IMatDataHandler.getInstance().getProducts().stream()
                //.limit(PRODUCT_LIMIT)
                .sorted(Comparator.comparing(Product::getName))
                .map(ProductCard::new).collect(Collectors.toList());
        allProducts.forEach(productCard -> productFlow.getChildren().add(productCard));
        if(allProducts.stream().findAny().isPresent()) {
            ProductCard one = allProducts.stream().findAny().get();
            Product p = one.getProduct();
            double productCardHeight = one.getPrefHeight();
            productFlow.setPadding(new Insets(productCardHeight, 0, productCardHeight, 0));
        }
    }
}
