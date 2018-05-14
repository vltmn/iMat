package main.Screen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private StackPane rootPane;

    private Welcome welcomePane;

    private MainShop mainPane;

    private Profile profilePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainPane = new MainShop();
        welcomePane = new Welcome(event -> mainPane.toFront());
        profilePane = new Profile(event -> mainPane.toFront());
        rootPane.getChildren().addAll(mainPane, welcomePane, profilePane);
        welcomePane.toFront();
        mainPane.toFront();
//        profilePane.toFront();


    }


}
