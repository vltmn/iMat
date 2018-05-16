package main.components.OrderHistory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import main.util.BackendUtil;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class HistoryProduct extends HBox {

    private ShoppingItem item;

    @FXML private Label orderProductLabel;
    @FXML private Label ammountLabel;
    @FXML private ImageView productImage;
    @FXML private Label priceLabel;
    @FXML private Button addButton;

    public HistoryProduct(ShoppingItem item){
        this.item = item;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/OrderHistory/history_product.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        orderProductLabel.setText(item.getProduct().getName());
        ammountLabel.setText(Double.toString(item.getAmount()) + " st");
        Image i = IMatDataHandler.getInstance().getFXImage(item.getProduct(), productImage.getFitWidth(), productImage.getFitHeight());
        productImage.setImage(i);
        priceLabel.setText(Double.toString(item.getProduct().getPrice()) + " kr/st");
    }

    @FXML
    private void addToCart(){
        BackendUtil.getInstance().addProductAmountToCart(item.getProduct(),item.getAmount());
    }

}
