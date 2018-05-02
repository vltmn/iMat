package main.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Welcome extends VBox {

    @FXML
    private Button StartShoppingBtn;

    public Welcome(EventHandler<ActionEvent> startShoppingHandler) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/welcome.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StartShoppingBtn.setOnAction(startShoppingHandler);

    }
}
