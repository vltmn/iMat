package main.components.MainScreen;

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
import main.components.EditQuantity;
import main.util.BackendUtil;
import main.util.MiscUtil;
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
    private Label unitLabel;

    @FXML
    private Label priceLabel;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/MainScreen/product_card.fxml"));
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
        editAmount = MiscUtil.getInstance().getProductEditAmount(p);
        editQuantity = new EditQuantity(p);
        editQuantityWrapper.getChildren().add(editQuantity);
        priceLabel.setText(MiscUtil.getInstance().formatAsCurrency(p.getPrice()));
        unitLabel.setText(p.getUnit());

    }

    public Product getProduct() {
        return product;
    }
}
