package main.util.snackbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.components.SnackBar;

public class SimpleSnackBar extends SnackBarBase {
    protected SimpleSnackBar(String text, EventHandler<ActionEvent> onBtnClickHandler, EventHandler<ActionEvent> onBarClickedHandler) {
        super(
                new SnackBar(text, onBtnClickHandler, onBarClickedHandler), 4000);
    }
}
