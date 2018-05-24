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
import javafx.util.Pair;
import main.util.BackendUtil;
import main.util.Model.FieldValidation.CardNumberField;
import main.util.Model.FieldValidation.NonEmptyField;
import main.util.Model.FieldValidation.NumberField;
import main.util.Model.FieldValidation.ValidationField;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentPane extends VBox {

    private final static String BAD_INPUT = "error";
    @FXML
    private RadioButton prefilledBtn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label cardNumberLabel;
    @FXML
    private Label verCodeLabel;
    @FXML
    private Label validThruLabel;
    @FXML
    private GridPane prefilledGrid;
    @FXML
    private RadioButton inputBtn;
    @FXML
    private GridPane inputGrid;
    @FXML
    private TextField cardHolderField;
    @FXML
    private TextField cardNoField;
    @FXML
    private TextField verCodeField;
    @FXML
    private TextField validMonthField;
    @FXML
    private TextField validYearField;
    @FXML
    private HBox savedPane;

    private ToggleGroup btnGroup;
    private CreditCard card;

    private final static String PREFILLED = "PREFILLED";

    private final static String MANUAL = "MANUAL";
    private List<ValidationField<String>> stringFields = new ArrayList<>();
    private List<ValidationField<Number>> numFields = new ArrayList<>();

    public PaymentPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/OrderProcess/paymentPane.fxml"));
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
        stringFields.add(new NonEmptyField(cardHolderField));
        stringFields.add(new CardNumberField(cardNoField));
        numFields.add(new NumberField(verCodeField, 3));
        numFields.add(new NumberField(validMonthField, 2));
        numFields.add(new NumberField(validYearField, 2));
    }

    private void setActions() {
        btnGroup = new ToggleGroup();
        prefilledBtn.setUserData(PREFILLED);
        inputBtn.setUserData(MANUAL);
        prefilledBtn.setToggleGroup(btnGroup);
        inputBtn.setToggleGroup(btnGroup);

        btnGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(PREFILLED.equals(newValue.getUserData())) {
                prefilledGrid.disableProperty().setValue(false);
                inputGrid.disableProperty().setValue(true);
            }else if(MANUAL.equals(newValue.getUserData())) {
                prefilledGrid.disableProperty().setValue(true);
                inputGrid.disableProperty().setValue(false);
            }
        });
    }
    private void initData() {
        card = IMatDataHandler.getInstance().getCreditCard();
        if(!BackendUtil.getInstance().isCreditCardComplete()) {
            savedPane.disableProperty().setValue(true);
            inputBtn.setSelected(true);
            nameLabel.setText("Data saknas");
            return;
        }
        prefilledBtn.setSelected(true);
        nameLabel.setText(card.getHoldersName());
        cardNumberLabel.setText(card.getCardNumber());
        verCodeLabel.setText(String.valueOf(card.getVerificationCode()));
        validThruLabel.setText(BackendUtil.getInstance().getCreditCardValidity(card));
    }

    public void show() {
        this.toFront();
    }

    public boolean complete() {
        if(btnGroup.selectedToggleProperty().getValue().getUserData().equals(MANUAL)) {
            if(!updateCreditCardWithManualData()) {
                return false;
            }
        } else if(btnGroup.selectedToggleProperty().getValue().getUserData().equals(PREFILLED)) {
            updateBackendWithPrefilled();
        } else {
            //bad btn
            return false;
        }
        this.toBack();
        return true;
    }

    private void updateBackendWithPrefilled() {
        card.setHoldersName(nameLabel.getText());
        card.setCardNumber(cardNumberLabel.getText());
        Pair<Integer, Integer> monthYearFromLabel = BackendUtil.getInstance().getMonthYearFromLabel(validThruLabel.getText());
        card.setValidMonth(monthYearFromLabel.getKey());
        card.setValidYear(monthYearFromLabel.getValue());
        card.setVerificationCode(Integer.parseInt(verCodeLabel.getText()));
    }

    private boolean updateCreditCardWithManualData() {
        stringFields.forEach(sf -> sf.getTextField().getStyleClass()
                .removeIf(s -> s.equalsIgnoreCase(BAD_INPUT)));
        numFields.forEach(nf -> nf.getTextField().getStyleClass()
                .removeIf(s -> s.equalsIgnoreCase(BAD_INPUT)));

        List<ValidationField> badFields = new ArrayList<>();
        badFields.addAll(stringFields.stream()
                .filter(sf -> !sf.validate()).collect(Collectors.toList()));
        badFields.addAll(numFields.stream()
                .filter(nf -> !nf.validate()).collect(Collectors.toList()));
        badFields.forEach(f -> f.getTextField().getStyleClass().add(BAD_INPUT));
        if(!badFields.isEmpty()) return false;

        card.setHoldersName(cardHolderField.getText());
        card.setCardNumber(cardNoField.getText());
        card.setValidMonth(Integer.parseInt(validMonthField.getText()));
        card.setValidYear(Integer.parseInt(validYearField.getText()));
        card.setVerificationCode(Integer.parseInt(verCodeField.getText()));

        return true;
    }
}
