package main.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.util.BackendUtil;
import main.util.MiscUtil;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;


import java.io.IOException;

public class CartRowCell extends HBox {

    private Product product;
    @FXML
    private Label productName;

    @FXML
    private ImageView productImage;

    @FXML
    private Label totalLabel;

    @FXML
    private VBox nameAndQtyPane;

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
        editQuantity = new EditQuantity(product);
        nameAndQtyPane.getChildren().add(editQuantity);
    }

    public void updateData() {
        totalLabel.setText(MiscUtil.getInstance().formatAsCurrency(BackendUtil.getInstance().getProductCartAmount(product)));
    }

    public int getProductId() {
        return product.getProductId();
    }
}
