package main.util;

import javafx.animation.PauseTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import main.components.SnackBar;
import main.util.snackbar.SnackBarHandler;
import se.chalmers.cse.dat216.project.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Consumer;

public class BackendUtil {
    private static BackendUtil ourInstance = new BackendUtil();

    public static BackendUtil getInstance() {
        return ourInstance;
    }

    private final ObservableList<ShoppingItem> undoQueue = FXCollections.observableArrayList(new ArrayList<>());

    private final Map<Product, PauseTransition> undoTimers = new HashMap<>();

    private BackendUtil() {
    }

    public double getProductCartAmount(Product p) {
        return IMatDataHandler.getInstance().getShoppingCart().getItems().stream()
                .filter(si -> si.getProduct().getProductId() == p.getProductId())
                .findAny().map(ShoppingItem::getAmount).orElse((double) 0);
    }

    public boolean isCreditCardComplete() {
        CreditCard dbCard = IMatDataHandler.getInstance().getCreditCard();
        if(dbCard.getCardNumber() == null) return false;
        if("".equals(dbCard.getCardNumber())) return false;
        //TODO add card type
        return !"".equals(dbCard.getHoldersName());
    }

    public String getCreditCardValidity(CreditCard card) {
        NumberFormat nf = new DecimalFormat("00");
        return nf.format(card.getValidMonth()) + "/" + nf.format(card.getValidYear());
    }
    public ShoppingItem addProductAmountToCart(Product p) {
        return addProductAmountToCart(p, 1);
    }

    public ShoppingItem addProductAmountToCart(Product p, double i) {
        return editProductAmount(p, i);
    }

    public ShoppingItem removeProductAmountFromCart(Product p) {
        return removeProductAmountFromCart(p, 1);
    }

    public ShoppingItem removeProductAmountFromCart(Product p, double i) {
        return editProductAmount(p, -i);
    }

    private ShoppingItem editProductAmount(Product p, double amount) {
        ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
        Optional<ShoppingItem> shoppingItemOpt = shoppingCart.getItems().stream().filter(si -> si.getProduct().getProductId() == p.getProductId()).findAny();
        ShoppingItem shoppingItem = shoppingItemOpt.orElse(new ShoppingItem(p, 0));
        shoppingItem.setAmount(round(amount + shoppingItem.getAmount(),1 ));
        shoppingCart.removeItem(shoppingItem);
        if(shoppingItem.getAmount() > 0) {
            shoppingCart.addItem(shoppingItem);
            return shoppingItem;
        }

        return shoppingItem;
    }

    public ShoppingItem setProductAmount(Product p, double amount) {
        ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
        Optional<ShoppingItem> shoppingItemOpt = shoppingCart.getItems().stream().filter(si -> si.getProduct().getProductId() == p.getProductId()).findAny();
        ShoppingItem shoppingItem = shoppingItemOpt.orElse(new ShoppingItem(p, 0));
        shoppingItem.setAmount(round(amount,1 ));
        shoppingCart.removeItem(shoppingItem);
        if(shoppingItem.getAmount() > 0) {
            shoppingCart.addItem(shoppingItem);
            return shoppingItem;
        }

        return shoppingItem;
    }

    public void addToUndoList(Product p, Consumer<Product> onRemovedFromUndo) {
        PauseTransition pt = new PauseTransition(Duration.seconds(3));
        undoTimers.put(p, pt);
        Optional<ShoppingItem> sci = IMatDataHandler.getInstance().getShoppingCart().getItems().stream()
                .filter(si -> si.getProduct().equals(p))
                .findAny();
        if(!sci.isPresent()) return;
        ShoppingItem shoppingItem = sci.get();
        undoQueue.add(shoppingItem);
        pt.onFinishedProperty().setValue(event -> {
            undoQueue.remove(shoppingItem);
            setProductAmount(p, 0);
            undoTimers.remove(p);
        });
        pt.playFromStart();
    }


    public void removedUndoHandler(Product p, EventHandler<ActionEvent> undoFn) {
        Optional<ShoppingItem> oSi = IMatDataHandler.getInstance().getShoppingCart().getItems().stream().filter(sci -> sci.getProduct().getProductId() == p.getProductId())
                .findAny();
        if(!oSi.isPresent()) return;
        ShoppingItem si = oSi.get();
        IMatDataHandler.getInstance().getShoppingCart().removeItem(si);
        SnackBarHandler.getInstance().showProductUndoSnackbar(si, event -> {
            setProductAmount(si.getProduct(), si.getAmount());
            undoFn.handle(event);
        });
    }
    public void removedUndoHandler(Product p) {
        removedUndoHandler(p, event -> {});
    }

    public void stopRemoval(Product p) {
        if(!undoTimers.containsKey(p)) return;
        PauseTransition pt = undoTimers.get(p);
        pt.stop();
        undoTimers.remove(p);
        double productCartAmount = getProductCartAmount(p);
        setProductAmount(p, 1);
        setProductAmount(p, productCartAmount);
    }
    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
