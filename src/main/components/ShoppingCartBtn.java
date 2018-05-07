package main.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import main.util.MiscUtil;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class ShoppingCartBtn extends HBox {
    @FXML
    private Label cartValueLabel;


    public ShoppingCartBtn() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/ShoppingCartBtn.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
        shoppingCart.addShoppingCartListener((event) -> cartValueLabel.setText(MiscUtil.getInstance().formatAsCurrency(shoppingCart.getTotal())));
        cartValueLabel.setText(MiscUtil.getInstance().formatAsCurrency(shoppingCart.getTotal()));
    }
}
