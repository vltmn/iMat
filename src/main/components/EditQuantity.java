package main.components;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.util.BackendUtil;
import main.util.MiscUtil;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

//TODO fix editing textfield, hide commas if discrete amount

public class EditQuantity extends HBox {

    @FXML
    private TextField qtyField;

    @FXML
    private Button addBtn;

    @FXML
    private Button subBtn;

    @FXML
    private Label unitLabel;

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

    public EditQuantity(Product p) {
        this();
        double editAmount = MiscUtil.getInstance().getProductEditAmount(p);
        EventHandler<MouseEvent> addEvent = event -> BackendUtil.getInstance()
                .addProductAmountToCart(p, editAmount);
        EventHandler<MouseEvent> subEvent = event -> BackendUtil.getInstance()
                .removeProductAmountFromCart(p, editAmount);
        this.addEvent = addEvent;
        this.subEvent = subEvent;
        unitLabel.setText(p.getUnitSuffix());

    }
    public EditQuantity(EventHandler<MouseEvent> addEvent, EventHandler<MouseEvent> subEvent, String unit) {
        this();
        this.addEvent = addEvent;
        this.subEvent = subEvent;
        unitLabel.setText(unit);
    }

    public void setQuantity(double value) {
        qtyField.setText(String.valueOf(value));
    }


}