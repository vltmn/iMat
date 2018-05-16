package main.Screen;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.components.OrderProcess.DeliveryPane;
import main.components.OrderProcess.PaymentPane;
import main.components.OrderProcess.VerificationPane;

import java.io.IOException;

public class OrderProcess extends VBox {


    @FXML
    private StackPane orderStack;

    private DeliveryPane deliveryPane;
    private PaymentPane paymentPane;

    private ObjectProperty<Integer> currentStep = new SimpleIntegerProperty(1).asObject();
    @FXML
    private Button backBtn;
    @FXML
    private Button forwardBtn;
    private VerificationPane verificationPane;
    private EventHandler<ActionEvent> doneHandler;

    public OrderProcess(EventHandler<ActionEvent> doneHandler) {
        this.doneHandler = doneHandler;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/screen/order_process.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addScreens();
        initBtns();
    }

    private void initBtns() {
        backBtn.disableProperty().setValue(true);
        currentStep.addListener((observable, oldValue, newValue) -> backBtn.disableProperty().setValue(newValue == 1));
        backBtn.setOnAction(event -> {
            switch(currentStep.get()) {
                case 2:
                    deliveryPane.toFront();
                    break;
                case 3:
                    forwardBtn.textProperty().setValue("Gå vidare");
                    paymentPane.toFront();
                    break;
                default:
                    return;
            }
            currentStep.setValue(currentStep.get() - 1);

        });
        forwardBtn.setOnAction(event -> {
            boolean complete;
            switch (currentStep.get()) {
                case 1:
                    complete = deliveryPane.complete();
                    if(!complete) {
                        return;
                    }
                    paymentPane.show();
                    break;
                case 2:
                    complete = paymentPane.complete();
                    if(!complete) return;
                    verificationPane.show();
                    forwardBtn.textProperty().setValue("Lägg beställning");
                    break;
                case 3:
                    verificationPane.complete();
                    doneHandler.handle(event);
                    resetView();
                    break;
            }
            currentStep.setValue(currentStep.get() + 1);
        });
    }

    public void resetView() {
        //TODO test this
        currentStep = new SimpleIntegerProperty(1).asObject();
        forwardBtn.textProperty().setValue("Gå vidare");
        orderStack.getChildren().clear();
        addScreens();
        initBtns();
    }

    private void addScreens() {

        paymentPane = new PaymentPane();
        deliveryPane = new DeliveryPane();
        verificationPane = new VerificationPane();
//        orderStack.getChildren().addAll(paymentPane, deliveryPane);
        orderStack.getChildren().addAll(verificationPane, paymentPane, deliveryPane);
    }
}
