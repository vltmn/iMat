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
import se.chalmers.cse.dat216.project.ShoppingItem;


import java.io.IOException;

public class CartRowCell extends HBox {

    @FXML
    private Label productName;

    @FXML
    private ImageView productImage;

    @FXML
    private Label totalLabel;

    @FXML
    private VBox nameAndQtyPane;

    public CartRowCell(ShoppingItem item) {
        this();
        setData(item);
    }

    private EditQuantity editQuantity;

    public CartRowCell() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/CartRowCell.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setData( ShoppingItem item) {
        productName.setText(item.getProduct().getName());
        Image prodImage = IMatDataHandler.getInstance().getFXImage(item.getProduct(), productImage.getFitWidth(), productImage.getFitHeight());
        productImage.setImage(prodImage);
        totalLabel.setText(MiscUtil.getInstance().formatAsCurrency(item.getTotal()));
        editQuantity = new EditQuantity(item.getProduct());
        nameAndQtyPane.getChildren().add(editQuantity);
        updateData(item);
    }

    public void updateData(ShoppingItem item) {
        editQuantity.setQuantity(item.getAmount());
    }
}
