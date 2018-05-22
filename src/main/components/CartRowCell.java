package main.components;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.util.BackendUtil;
import main.util.MiscUtil;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class CartRowCell extends StackPane {

    private Product product;
    @FXML
    private Label productName;

    @FXML
    private ImageView productImage;

    @FXML
    private Label totalLabel;

    @FXML
    private VBox nameAndQtyPane;

    @FXML
    private HBox undoPane;

    private EditQuantity editQuantity;

    public CartRowCell(Product p) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/CartRowCell.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.product = p;
        initData();

    }

    private void initData() {
        productName.setText(product.getName());
        Image prodImage = IMatDataHandler.getInstance().getFXImage(product, productImage.getFitWidth(), productImage.getFitHeight());
        productImage.setImage(prodImage);
        editQuantity = new EditQuantity(product, false, product -> undoFn());
        nameAndQtyPane.getChildren().add(editQuantity);
    }

    public void updateData() {
        totalLabel.setText(MiscUtil.getInstance().formatAsCurrency(BackendUtil.getInstance().getProductCartAmount(product) * product.getPrice()));
    }

    private boolean undoFn() {
        if (BackendUtil.getInstance().getProductCartAmount(product) == 0) return true;

        double containerHeight = getHeight();
        animateVisibilty(containerHeight, true, event -> {
            BackendUtil.getInstance().removedUndoHandler(product, event1 -> {
                animateVisibilty(containerHeight, false, event2 -> {});
            });
        });
        return false;
    }

    public void animateVisibilty(double containerHeight, boolean toRemovedState, EventHandler<ActionEvent> onFinished) {
        Duration dur = Duration.millis(150);
        this.prefHeightProperty().setValue(containerHeight);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(this.translateXProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(this.prefHeightProperty(), containerHeight, Interpolator.EASE_BOTH)),
                new KeyFrame(dur,
                        new KeyValue(this.translateXProperty(), this.getWidth(), Interpolator.EASE_BOTH),
                        new KeyValue(this.prefHeightProperty(), containerHeight, Interpolator.EASE_BOTH)),
                new KeyFrame(dur.multiply(2),
                        new KeyValue(this.prefHeightProperty(), 0, Interpolator.EASE_BOTH)
                )
        );
        timeline.setOnFinished(onFinished);
        if(!toRemovedState) {
            timeline.setRate( -1);
            timeline.jumpTo(timeline.getTotalDuration());
        }

        timeline.play();
    }

    @FXML
    private void onUndoClick() {
        BackendUtil.getInstance().stopRemoval(product);
        undoPane.toBack();
    }

    @FXML
    private void onRemoveClick() {
        undoFn();
    }

    public int getProductId() {
        return product.getProductId();
    }
}
