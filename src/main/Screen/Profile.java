package main.Screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.util.Model.FieldValidation.*;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Profile extends VBox {
    private final static String BAD_INPUT = "error";

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

    private List<ValidationField<Number>> numFields = new ArrayList<>();
    private List<ValidationField<String>> stringFields = new ArrayList<>();
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
        addFieldsToLists();
        populatePersonal();
        populatePayment();
        addBtnHandlers();
    }

    private void resetView() {
        populatePayment();
        populatePersonal();
    }
    private void addFieldsToLists() {
        numFields.add(new NumberField(verCodeField, 3));
        numFields.add(new NumberField(validMonthField, 2));
        numFields.add(new NumberField(validYearField, 2));
        stringFields.add(new PostCodeField(postCodeField));
        stringFields.add(new CardNumberField(cardNumberField));
        stringFields.add(new NonEmptyField(firstNameField));
        stringFields.add(new NonEmptyField(lastNameField));
        stringFields.add(new PhoneField(phoneField));
        stringFields.add(new EmailField(emailField));
        stringFields.add(new NonEmptyField(addressField));
        stringFields.add(new NonEmptyField(stateField));
        stringFields.add(new NonEmptyField(cardHolderField));
    }

    private void addBtnHandlers() {
        cancelBtn.onActionProperty().setValue(event -> {
            resetView();
            closeFn.handle(event);
        });
        saveBtn.onActionProperty().setValue(event -> {
            if(saveData()) closeFn.handle(event);
        });
    }

    private void populatePayment() {
        CreditCard c = IMatDataHandler.getInstance().getCreditCard();
        cardHolderField.setText(c.getHoldersName());
        cardNumberField.setText(c.getCardNumber());
        verCodeField.setText(String.valueOf(c.getVerificationCode()));
        validMonthField.setText(String.valueOf(c.getValidMonth()));
        validYearField.setText(String.valueOf(c.getValidYear()));
    }

    private void populatePersonal() {
        Customer c = IMatDataHandler.getInstance().getCustomer();
        firstNameField.setText(c.getFirstName());
        lastNameField.setText(c.getLastName());
        phoneField.setText(c.getPhoneNumber());
        emailField.setText(c.getEmail());
        addressField.setText(c.getAddress());
        postCodeField.setText(c.getPostCode());
        stateField.setText(c.getPostAddress());

    }

    private boolean saveData() {
        //remove all errors
        numFields.forEach(nf -> nf.getTextField().getStyleClass().removeIf(s -> s.equalsIgnoreCase(BAD_INPUT)));
        stringFields.forEach(nf -> nf.getTextField().getStyleClass().removeIf(s -> s.equalsIgnoreCase(BAD_INPUT)));

        //validate all textfields
        List<ValidationField> badFields = new ArrayList<>();
        badFields.addAll(numFields.stream()
                .filter(nf -> !nf.validate()).collect(Collectors.toList()));
        badFields.addAll(stringFields.stream()
                .filter(sf -> !sf.validate()).collect(Collectors.toList()));
        badFields.forEach(vf -> vf.getTextField().getStyleClass().add(BAD_INPUT));
        if(!badFields.isEmpty()) return false;

        Customer cu = IMatDataHandler.getInstance().getCustomer();
        cu.setFirstName(firstNameField.getText());
        cu.setLastName(lastNameField.getText());
        cu.setPhoneNumber(phoneField.getText());
        cu.setEmail(emailField.getText());
        cu.setAddress(addressField.getText());
        cu.setPostCode(postCodeField.getText());
        cu.setPostAddress(stateField.getText());

        CreditCard cc = IMatDataHandler.getInstance().getCreditCard();
        cc.setHoldersName(cardHolderField.getText());
        cc.setCardNumber(cardNumberField.getText());
        cc.setVerificationCode(Integer.parseInt(verCodeField.getText()));
        cc.setValidYear(Integer.parseInt(validYearField.getText()));
        cc.setValidMonth(Integer.parseInt(validMonthField.getText()));
        return true;

    }

}
