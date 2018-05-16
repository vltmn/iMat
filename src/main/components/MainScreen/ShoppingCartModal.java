package main.components.MainScreen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import main.components.CartRowCell;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartModal extends VBox {

    @FXML
    private VBox cartList;

    public ShoppingCartModal() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/MainScreen/ShoppingCartPopOver.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeList();
        updateList();
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(cartEvent -> updateList());
        //TODO bind to shoppingcart event
    }

    private void updateList() {
        List<CartRowCell> crcs = IMatDataHandler.getInstance().getShoppingCart().getItems().stream()
                .map(CartRowCell::new).collect(Collectors.toList());
        cartList.getChildren().clear();
        cartList.getChildren().addAll(crcs);
    }

    private void initializeList() {

//        list.setCellFactory(param -> new CartRowCell());
    }
}
