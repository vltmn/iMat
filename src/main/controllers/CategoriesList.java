package main.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//TODO make this a VBox with everything and favorites also
public class CategoriesList extends ListView<ProductCategory> {
    private final static List<ProductCategory> productCategories = IMatDataHandler.getInstance().getProducts().stream().map(Product::getCategory).distinct().collect(Collectors.toList());

    private Predicate<Product> productPredicate = product -> true;

    public CategoriesList() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/CategoriesList.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //TODO filter productcategories to only available ones
        this.setItems(FXCollections.observableArrayList(productCategories));
        this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    public void setFilterCallback(ChangeListener<Predicate<Product>> productFilterListener) {
        this.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super ProductCategory>) c -> {
            Predicate<Product> old = productPredicate;
            productPredicate = product -> c.getList().contains(product.getCategory());
            productFilterListener.changed(null, old, productPredicate);
        });

    }
}
