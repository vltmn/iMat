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
import main.util.Model.FieldValidation.*;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryPane extends VBox {
    private final static String BAD_INPUT = "error";
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
    private GridPane prefilledGrid;
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
    @FXML
    private TextField phoneField;

    private ToggleGroup btnGroup;

    private Customer cust;

    private List<ValidationField<String>> stringFields = new ArrayList<>();

    private final static String PREFILLED = "PREFILLED";

    private final static String MANUAL = "MANUAL";

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
        stringFields.add(new NonEmptyField(firstNameField));
        stringFields.add(new NonEmptyField(lastNameField));
        stringFields.add(new EmailField(emailField));
        stringFields.add(new NonEmptyField(addressField));
        stringFields.add(new PostCodeField(postNumField));
        stringFields.add(new NonEmptyField(stateField));
        stringFields.add(new PhoneField(phoneField));
    }

    private void setActions() {
        btnGroup = new ToggleGroup();
        prefilledBtn.setUserData(PREFILLED);
        inputBtn.setUserData(MANUAL);
        prefilledBtn.setToggleGroup(btnGroup);
        inputBtn.setToggleGroup(btnGroup);

        btnGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (PREFILLED.equals(newValue.getUserData())) {
                prefilledGrid.disableProperty().setValue(false);
                inputGrid.disableProperty().setValue(true);
            } else if (MANUAL.equals(newValue.getUserData())) {
                prefilledGrid.disableProperty().setValue(true);
                inputGrid.disableProperty().setValue(false);
            }
        });
    }

    private void initData() {
        cust = IMatDataHandler.getInstance().getCustomer();
        if (!IMatDataHandler.getInstance().isCustomerComplete()) {
            //cust is not complete, select empty text input
            savedPane.disableProperty().setValue(true);
            inputBtn.setSelected(true);
            nameLabel.setText("Data saknas");
            return;
        }
        prefilledBtn.setSelected(true);
        nameLabel.setText(cust.getFirstName() + " " + cust.getLastName());
        phoneLabel.setText(cust.getPhoneNumber());
        emailLabel.setText(cust.getEmail());
        streetLabel.setText(cust.getAddress());
        postNoLabel.setText(cust.getPostCode() + " " + cust.getPostAddress());


    }

    public boolean complete() {
        if (btnGroup.selectedToggleProperty().getValue().getUserData().equals(MANUAL)) {
            if (!updateCustomerWithManualData()) {
                return false;
            }
        } else if (!btnGroup.selectedToggleProperty().getValue().getUserData().equals(PREFILLED)) {
            //bad btn
            return false;
        }

        this.toBack();
        //TODO animate movement to left
        return true;
    }

    private boolean updateCustomerWithManualData() {
        stringFields.forEach(sf -> sf.getTextField().getStyleClass().removeIf(s -> s.equalsIgnoreCase(BAD_INPUT)));
        List<ValidationField<String>> badFields = stringFields.stream().filter(sf -> !sf.validate()).collect(Collectors.toList());

        badFields.forEach(sf -> sf.getTextField().getStyleClass().add(BAD_INPUT));
        if(!badFields.isEmpty()) return false;

        cust.setAddress(addressField.getText());
        cust.setFirstName(firstNameField.getText());
        cust.setLastName(lastNameField.getText());
        cust.setEmail(emailField.getText());
        cust.setPostAddress(stateField.getText());
        cust.setPostCode(postNumField.getText());
        cust.setPhoneNumber(phoneField.getText());
        return true;
    }

}
