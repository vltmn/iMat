package main.Screen;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.components.OrderProcess.DeliveryPane;
import main.components.OrderProcess.PaymentPane;

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

    public OrderProcess() {
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
                    //TODO make paymentPane disappear
                    //TODO show customer pane
                    break;
                case 3:
                    //TODO make verification disappear
                    //TODO show paymentPane
                    break;
            }
            currentStep.setValue(currentStep.get() - 1);

        });
        forwardBtn.setOnAction(event -> {
            switch (currentStep.get()) {
                case 1:
                    deliveryPane.complete();
                    paymentPane.show();
                    break;
                case 2:
                    paymentPane.complete();
                    //TODO show verification view
                    break;
                case 3:
                    //TODO place order, show order confirmation screen
                    break;
            }
            currentStep.setValue(currentStep.get() + 1);
        });
    }

    private void addScreens() {

        paymentPane = new PaymentPane();
        deliveryPane = new DeliveryPane();
//        orderStack.getChildren().addAll(paymentPane, deliveryPane);
        orderStack.getChildren().addAll(paymentPane, deliveryPane);
    }
}
