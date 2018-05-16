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
import main.util.BackendUtil;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
//TODO add card type
public class PaymentPane extends VBox {
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
    private VBox prefilledGrid;
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


    public PaymentPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/OrderProcess/paymentPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setActions();
    }

    private void setActions() {
        btnGroup = new ToggleGroup();
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
        card = IMatDataHandler.getInstance().getCreditCard();
        if(!BackendUtil.getInstance().isCreditCardComplete()) {
            savedPane.disableProperty().setValue(true);
            inputBtn.setSelected(true);
            nameLabel.setText("Data saknas");
            return;
        }
        nameLabel.setText(card.getHoldersName());
        cardNumberLabel.setText(card.getCardType());
        verCodeLabel.setText(String.valueOf(card.getVerificationCode()));
        validThruLabel.setText(String.valueOf(card.getValidMonth()) + "/" + card.getValidYear());
    }

    public void show() {
        this.toFront();
    }

    public void complete() {
        this.toBack();
    }
}
