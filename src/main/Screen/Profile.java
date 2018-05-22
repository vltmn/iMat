package main.Screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

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
    private EventHandler<ActionEvent> closeFn;

    public Profile(EventHandler<ActionEvent> closeFn) {
        this.closeFn = closeFn;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/screen/profile.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        populatePersonalVbox();
        populatePayment();
        addBtnHandlers();
    }

    private void addBtnHandlers() {
        cancelBtn.onActionProperty().setValue(event -> closeFn.handle(event));
    }

    private void populatePayment() {
        CreditCard c = IMatDataHandler.getInstance().getCreditCard();
        cardHolderField.setText(c.getHoldersName());
        cardNumberField.setText(c.getCardNumber());
        verCodeField.setText(String.valueOf(c.getVerificationCode()));
        validMonthField.setText(String.valueOf(c.getValidMonth()));
        validYearField.setText(String.valueOf(c.getValidYear()));
    }

    private void populatePersonalVbox() {
        Customer c = IMatDataHandler.getInstance().getCustomer();
        firstNameField.setText(c.getFirstName());
        lastNameField.setText(c.getLastName());
        phoneField.setText(c.getPhoneNumber());
        emailField.setText(c.getEmail());
        addressField.setText(c.getAddress());
        postCodeField.setText(c.getPostCode());
        stateField.setText(c.getPostAddress());

    }

    private void saveData() {
        //TODO

    }

}
