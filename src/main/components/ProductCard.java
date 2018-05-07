package main.components;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import main.util.BackendUtil;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ProductCard extends VBox {
    private final static LinearGradient GRADIENT_OVERLAY = new LinearGradient(
            0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.TRANSPARENT),
            new Stop(0.7, new Color(1, 1, 1, 0.2)),
            new Stop(0.85, new Color(1, 1, 1, 0.9)),
            new Stop(1, Color.WHITE)
    );

    private double editAmount = 1;

    @FXML
    private Rectangle buyBtn;

    @FXML
    private Label productName;

    @FXML
    private Pane editQuantityWrapper;

    @FXML
    private ImageView productImage;

    private EditQuantity editQuantity;

    private EventHandler<MouseEvent> addEvent = event -> {
        BackendUtil.getInstance().addProductAmountToCart(this.product, editAmount);
    };

    private EventHandler<MouseEvent> subEvent = event -> {
        BackendUtil.getInstance().removeProductAmountFromCart(this.product, editAmount);
    };

    private Product product;
    public ProductCard(Product p) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/product_card.fxml"));
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

        if(product.getUnitSuffix().toLowerCase().equals("kg")) {
            editAmount = .3;
        } else {
            editAmount = 1;
        }
        editQuantity = new EditQuantity(addEvent, subEvent, p.getUnitSuffix());
        updateQuantity();
        editQuantityWrapper.getChildren().add(editQuantity);
    }

    public Product getProduct() {
        return product;
    }


    public void updateQuantity() {
        updateQuantity(BackendUtil.getInstance().getProductCartAmount(product));
    }
    public void updateQuantity(double newAmount) {
        editQuantity.setQuantity(newAmount);
    }
}
