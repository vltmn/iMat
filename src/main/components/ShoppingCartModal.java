package main.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ShoppingCartModal extends VBox {

    @FXML
    private ListView<ShoppingItem> list;
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
        //TODO bind to shoppingcart event
    }

    private void updateList() {
    }

    private void initializeList() {


    }
}
