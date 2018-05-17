package main.Screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Profile extends VBox {

//    @FXML
//    private VBox personalVbox;

//    @FXML
//    private VBox paymentVbox;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private GridPane paymentGrid;

    @FXML
    private GridPane customerGrid;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField postCodeField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField cardHolderField;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField verCodeField;
    @FXML
    private TextField validMonthField;
    @FXML
    private TextField validYearField;

    public Profile(EventHandler<ActionEvent> closeFn) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/screen/profile.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        populatePersonalVbox();
        populatePersonal();
    }

    private void populatePersonal() {


//        paymentVbox.getChildren().addAll(paymentInputs.values());
    }

    private void populatePersonalVbox() {

//        personalVbox.getChildren().addAll(personalInputs.values());
    }

    private void saveData() {
        //TODO
    }

}
