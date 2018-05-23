package main.components.OrderProcess;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.components.MainScreen.ShoppingCartModal;
import main.util.BackendUtil;
import main.util.MiscUtil;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class ConfirmationPane extends VBox {
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

    @FXML Label totalPriceLabel;

    @FXML
    private VBox orderBox;

    public ConfirmationPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/OrderProcess/verificationPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ShoppingCartModal scm = new ShoppingCartModal();
        scm.setButtonVisibility(false);
        orderBox.getChildren().add(scm);
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
        totalPriceLabel.setText(String.format(
                "Total: %s SEK", MiscUtil.getInstance().formatAsCurrency(IMatDataHandler.getInstance().getShoppingCart().getTotal())
        ));
        this.toFront();
    }

    public void complete() {
        IMatDataHandler.getInstance().placeOrder();
    }
}
