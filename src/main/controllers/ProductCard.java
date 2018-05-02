package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ProductCard extends AnchorPane {
    private final static LinearGradient GRADIENT_OVERLAY = new LinearGradient(
            0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.TRANSPARENT),
            new Stop(0.7, new Color(1, 1, 1, 0.2)),
            new Stop(0.85, new Color(1, 1, 1, 0.9)),
            new Stop(1, Color.WHITE)
    );

    @FXML
    private Label productName;

//    @FXML
//    private Rectangle gradientOverlay;

    @FXML
    private ImageView productImage;

    private Product product;
    public ProductCard(Product p) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/product_card.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        product = p;
        productName.setText(p.getName());
        Image i = IMatDataHandler.getInstance().getFXImage(p, productImage.getFitWidth(), productImage.getFitHeight());
        productImage.setImage(i);
//        gradientOverlay.setFill(GRADIENT_OVERLAY);
    }

    public Product getProduct() {
        return product;
    }
}
