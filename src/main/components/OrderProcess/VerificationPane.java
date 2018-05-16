package main.components.OrderProcess;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.util.BackendUtil;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class VerificationPane extends VBox {
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label postAddressLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label cardHolderLabel;
    @FXML
    private Label cardNumberLabel;
    @FXML
    private Label validityLabel;

    public VerificationPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/OrderProcess/verificationPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void show() {
        Customer customer = IMatDataHandler.getInstance().getCustomer();
        CreditCard card = IMatDataHandler.getInstance().getCreditCard();
        nameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        addressLabel.setText(customer.getAddress());
        postAddressLabel.setText(customer.getPostCode() + " " + customer.getPostAddress());
        emailLabel.setText(customer.getEmail());
        phoneLabel.setText(customer.getPhoneNumber());
        cardHolderLabel.setText(card.getHoldersName());
        cardNumberLabel.setText(card.getCardNumber());
        validityLabel.setText(String.valueOf(BackendUtil.getInstance().getCreditCardValidity(card)));
        this.toFront();
    }

    public void complete() {
        IMatDataHandler.getInstance().placeOrder();
    }
}
