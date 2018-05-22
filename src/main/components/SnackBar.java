package main.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class SnackBar extends HBox {
    @FXML
    private Button snackBarBtn;

    @FXML
    private Label snackBarLabel;
    public SnackBar(String label, EventHandler<ActionEvent> onBtnClickHandler, EventHandler<ActionEvent> onBarClickedHandler) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/SnackBar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        snackBarLabel.setText(label);
        snackBarBtn.onActionProperty().setValue(onBtnClickHandler);
        this.onMouseClickedProperty().setValue(event -> onBarClickedHandler.handle(null));
    }
}
