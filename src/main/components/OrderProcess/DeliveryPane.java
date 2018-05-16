package main.components.OrderProcess;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class DeliveryPane extends VBox {
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postNoLabel;
    @FXML
    private HBox savedPane;
    @FXML
    private GridPane inputGrid;
    @FXML
    private RadioButton prefilledBtn;
    @FXML
    private RadioButton inputBtn;
    @FXML
    private VBox prefilledGrid;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField postNumField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField stateField;

    private ToggleGroup btnGroup;

    private Customer cust;



    public DeliveryPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/OrderProcess/deliveryPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        populateInputs();
        setActions();
        initData();
    }

    private void populateInputs() {

    }

    private void setActions() {
        prefilledBtn.setUserData("prefilled");
        inputBtn.setUserData("inputBtn");
        prefilledBtn.setToggleGroup(btnGroup);
        inputBtn.setToggleGroup(btnGroup);

        btnGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if("prefilled".equals(newValue.getUserData())) {
                prefilledGrid.disableProperty().setValue(false);
                inputGrid.disableProperty().setValue(true);
            }else if("inputBtn".equals(newValue.getUserData())) {
                prefilledGrid.disableProperty().setValue(true);
                inputGrid.disableProperty().setValue(false);
            }
        });
    }

    private void initData() {
        cust = IMatDataHandler.getInstance().getCustomer();
        if(!IMatDataHandler.getInstance().isCustomerComplete()) {
            //cust is not complete, select empty text input
            savedPane.disableProperty().setValue(true);
            inputBtn.setSelected(true);
        }

    }
}
