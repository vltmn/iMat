package main.Screen;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import main.components.OrderProcess.DeliveryPane;
import main.components.OrderProcess.PaymentPane;
import main.components.OrderProcess.VerificationPane;
import main.components.OrderProcess.SequenceMap;

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
    private Pane sequenceMapWrapper;
    @FXML
    private Button forwardBtn;
    private VerificationPane verificationPane;
    private EventHandler<ActionEvent> doneHandler;
    private EventHandler<ActionEvent> closeHandler;
    private SequenceMap sequenceMap;

    public OrderProcess(EventHandler<ActionEvent> doneHandler, EventHandler<ActionEvent> closeHandler) {
        this.doneHandler = doneHandler;
        this.closeHandler = closeHandler;
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
        currentStep.addListener((observable, oldValue, newValue) -> {
            if(newValue == 1) {
                backBtn.disableProperty().set(true);
            } else if(newValue > 1) {
                backBtn.disableProperty().set(false);
            }

            if(newValue == 3) {
                forwardBtn.textProperty().setValue("Lägg order");
            } else {
                forwardBtn.textProperty().setValue("Gå vidare");
            }
            sequenceMap.setStep(newValue);
        });
        backBtn.setOnAction(event -> {
            switch(currentStep.get()) {
                case 1:
                    doneHandler.handle(event);
                    return;
                case 2:
                    deliveryPane.toFront();
                    break;
                case 3:
                    paymentPane.toFront();
                    break;
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
                    break;
                case 3:
                    verificationPane.complete();
                    doneHandler.handle(event);
                    resetView();
                    return;
            }
            currentStep.setValue(currentStep.get() + 1);
        });
    }

    public void resetView() {
        currentStep = new SimpleIntegerProperty(1).asObject();
        forwardBtn.textProperty().setValue("Gå vidare");
        orderStack.getChildren().clear();
        sequenceMapWrapper.getChildren().clear();
        addScreens();
        initBtns();
        //do this twice, will set the backbtn to correct label
        backBtn.disableProperty().set(true);
    }

    private void addScreens() {
        sequenceMap = new SequenceMap();
        HBox.setHgrow(sequenceMap, Priority.ALWAYS);
        sequenceMapWrapper.getChildren().add(sequenceMap);
        sequenceMap.setStep(1);
        paymentPane = new PaymentPane();
        deliveryPane = new DeliveryPane();
        verificationPane = new VerificationPane();
//        orderStack.getChildren().addAll(paymentPane, deliveryPane);
        orderStack.getChildren().addAll(verificationPane, paymentPane, deliveryPane);
    }

    @FXML
    private void onCloseBtnClicked() {
        closeHandler.handle(null);
    }
}
