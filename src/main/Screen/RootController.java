package main.Screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private OrderProcess orderProcess;
    private OrderHistory orderHistory;
    private Thanks thanksPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainPane = new MainShop(event -> orderProcess.toFront(), event ->  profilePane.toFront(), event -> orderHistory.toFront());
        welcomePane = new Welcome(event -> mainPane.toFront());
        profilePane = new Profile(event -> mainPane.toFront());
        orderHistory = new OrderHistory(event -> mainPane.toFront());
        thanksPane = new Thanks(event -> thanksPane.toBack());
        orderProcess = new OrderProcess(event -> {
            mainPane.toFront();
            thanksPane.toFront();
        }, event -> mainPane.toFront());
        rootPane.getChildren().addAll(mainPane, welcomePane, profilePane, orderProcess, orderHistory, thanksPane);
        welcomePane.toFront();
        mainPane.toFront();
//        profilePane.toFront();


    }


}
