package main.components.OrderHistory;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrevOrder extends HBox {

    private static final DateFormat orderDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private EventHandler eventHandler;
    private EventHandler clearEvent;

    private BooleanProperty selected = new SimpleBooleanProperty();

    @FXML private Label orderDateLabel;

    public PrevOrder(Order order, EventHandler eventHandler, EventHandler clearEvent){
        this.eventHandler = eventHandler;
        this.clearEvent = clearEvent;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/OrderHistory/prev_order.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        orderDateLabel.setText(orderDateFormat.format(order.getDate()));

        selected.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.getStyleClass().add("selected");
            } else {
                this.getStyleClass().removeIf(s -> s.equalsIgnoreCase("selected"));
            }
        });
    }

    @FXML
    private void onClick(){
        eventHandler.handle(null);
        clearEvent.handle(null);
        selected.set(true);
    }

    public void setSelected(boolean bool){
        selected.setValue(bool);
    }
}
