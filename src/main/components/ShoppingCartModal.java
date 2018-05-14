package main.components;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.util.BackendUtil;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartModal extends VBox {

    @FXML
    private VBox cartList;

    public ShoppingCartModal() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/ShoppingCartPopOver.fxml"));
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
