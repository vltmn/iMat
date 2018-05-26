package main.components;

import com.sun.tools.hat.internal.util.Misc;
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
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.function.Function;

//TODO fix editing textfield, hide commas if discrete amount

public class EditQuantity extends HBox {

    @FXML
    private TextField qtyField;

    @FXML
    private Button addBtn;

    @FXML
    private Button subBtn;


    private EventHandler<MouseEvent> addEvent;
    private EventHandler<MouseEvent> subEvent;

    private Function<Product, Boolean> onRemovedFn = p -> true;

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

    public EditQuantity(Product p, boolean removeItemIfEmpty, Function<Product, Boolean> onRemovedFn) {
        this(p, removeItemIfEmpty, onRemovedFn, () -> {});
    }

    public EditQuantity(Product p, Runnable onChange) {
        this(p, true, product -> true, onChange);
    }

    public EditQuantity(Product p, boolean removeItemIfEmpty, Function<Product, Boolean> onRemovedFn, Runnable onChange) {
        this();
        double editAmount = MiscUtil.getInstance().getProductEditAmount(p);
        EventHandler<MouseEvent> addEvent = event -> qtyField.setText(MiscUtil.getInstance().formatAsAmount(getCartQty(p) + editAmount));
        EventHandler<MouseEvent> subEvent = event -> qtyField.setText(MiscUtil.getInstance().formatAsAmount(getCartQty(p) - editAmount));
        this.addEvent = addEvent;
        this.subEvent = subEvent;
        this.onRemovedFn = onRemovedFn;
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(cartEvent -> {
            double cartQty = getCartQty(p);
            if (cartQty == 0) subBtn.disableProperty().setValue(true);
            else subBtn.disableProperty().setValue(false);
            if(qtyField.getText().equals("") && cartQty == 0) return;
            if(Double.parseDouble(qtyField.getText()) == cartQty) return;
            qtyField.setText(MiscUtil.getInstance().formatAsAmount(cartQty));
        });
        double cartQty = getCartQty(p);
        if(cartQty == 0) subBtn.disableProperty().setValue(true);
        qtyField.textProperty().setValue(MiscUtil.getInstance().formatAsAmount(cartQty));
        qtyField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue.equals(newValue)) return;
            if(!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                qtyField.setText(oldValue);
                return;
            }
            if(newValue.equals("")) {
                if(removeItemIfEmpty && getCartQty(p) != 0) {
                    BackendUtil.getInstance().setProductAmount(p, 0);
                }
                return;
            }
            if(oldValue.equals("") && newValue.equals(String.valueOf(0.0))) {
                qtyField.setText(oldValue);
                return;
            }
            double value = Double.parseDouble(newValue);
            if(value == getCartQty(p)) return;
            if(value == 0 && !this.onRemovedFn.apply(p)) return;
            onChange.run();
            BackendUtil.getInstance().setProductAmount(p, value);

        });
    }




    public EditQuantity(Product p) {
        this(p, true);
    }

    public EditQuantity(Product p, boolean removeIfEmpty) {
        this(p, removeIfEmpty, product -> true);
    }

    private double getCartQty(Product p) {
        return BackendUtil.getInstance().getProductCartAmount(p);
    }


}
