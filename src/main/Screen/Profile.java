package main.Screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import main.components.TextInput;
import main.util.BackendUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Profile extends VBox {

//    @FXML
//    private VBox personalVbox;

//    @FXML
//    private VBox paymentVbox;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;

    private Map<String, TextInput> personalInputs;

    private Map<String, TextInput> paymentInputs;

    public Profile(EventHandler<ActionEvent> closeFn) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/screen/profile.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        populatePersonalVbox();
        populatePaymentVbox();
    }

    private void populatePaymentVbox() {
        paymentInputs = BackendUtil.getInstance().getCreditCardFields().entrySet().stream()
                .map(es -> new TextInput(es.getKey(), es.getValue()))
                .collect(Collectors.toMap(TextInput::getName, ti -> ti));
//        paymentVbox.getChildren().addAll(paymentInputs.values());
    }

    private void populatePersonalVbox() {
        personalInputs = BackendUtil.getInstance().getCustomerFields().entrySet().stream()
                .map(es -> new TextInput(es.getKey(), es.getValue()))
                .collect(Collectors.toMap(TextInput::getName, ti -> ti));
//        personalVbox.getChildren().addAll(personalInputs.values());
    }

    private void saveData() {
        //TODO
    }

}
