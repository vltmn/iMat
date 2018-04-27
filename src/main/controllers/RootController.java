package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private StackPane rootPane;

    private Welcome welcomePane;

    private MainShop mainPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainPane = new MainShop();
        welcomePane = new Welcome(event -> mainPane.toFront());
        rootPane.getChildren().addAll(mainPane, welcomePane);
        welcomePane.toFront();
        mainPane.toFront();



    }


}
