package main.components;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;


public class EditQuantity extends HBox {

    @FXML
    private TextField qtyField;

    @FXML
    private Button addBtn;

    @FXML
    private Button subBtn;

    private EventHandler<MouseEvent> addEvent;
    private EventHandler<MouseEvent> subEvent;

    private EditQuantity() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/EditQuantity.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addBtn.setOnAction(event -> addEvent.handle(null));
        subBtn.setOnAction(event -> subEvent.handle(null));
    }

    public EditQuantity(EventHandler<MouseEvent> addEvent, EventHandler<MouseEvent> subEvent) {
        this();
        this.addEvent = addEvent;
        this.subEvent = subEvent;

    }

    public void setQuantity(double value) {
        qtyField.setText(String.valueOf(value));
    }


}
