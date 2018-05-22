package main.util.snackbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.components.SnackBar;
import main.util.MiscUtil;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class UndoCartRemoveSnackBar extends SnackBarBase{
    private final static String labelFormat = "%s %s %s togs bort. ";
    protected UndoCartRemoveSnackBar(ShoppingItem i, EventHandler<ActionEvent> onBtnClickHandler, EventHandler<ActionEvent> onBarClickedHandler) {
        super(
                new SnackBar(
                        String.format(labelFormat, MiscUtil.getInstance().formatAsAmount(i.getAmount()), i.getProduct().getUnitSuffix(), i.getProduct().getName()),
                        onBtnClickHandler,
                        onBarClickedHandler));
    }

}
