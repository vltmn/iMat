package main.components.MainScreen;

import javafx.beans.binding.NumberExpression;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.components.CartRowCell;
import main.util.snackbar.SnackBarHandler;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartModal extends VBox {


    @FXML
    private VBox cartList;

    @FXML
    private ScrollPane cartScrollPane;

    @FXML
    private Pane realContainer;

    @FXML
    private HBox shoppingCartBtns;

    private EventHandler<ActionEvent> showChartEvent;
    private List<CartRowCell> crcs = new ArrayList<>();

    public ShoppingCartModal() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/MainScreen/ShoppingCartModal.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeList();
        updateList();
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(cartEvent -> {
            updateList();
            if(IMatDataHandler.getInstance().getShoppingCart().getItems().size() < 1) this.setVisible(false);
        });
    }

    private ShoppingCartModal(EventHandler<ActionEvent> showChartEvent) {
        this();

        this.showChartEvent = showChartEvent;
    }

    public ShoppingCartModal(EventHandler<ActionEvent> showChartEvent, NumberExpression ne) {
        this(showChartEvent);
        cartScrollPane.maxHeightProperty().bind(ne);
    }

    private void updateList() {

        cartList.getChildren().clear();
        List<Integer> prodIds = IMatDataHandler.getInstance().getShoppingCart().getItems().stream()
                .map(si -> si.getProduct().getProductId())
                .collect(Collectors.toList());
        crcs.stream()
                .filter(crc -> prodIds.contains(crc.getProductId()))
                .forEach(crc -> {
                    crc.updateData();
                    cartList.getChildren().add(crc);
                });
    }

    private void initializeList() {
        crcs = IMatDataHandler.getInstance().getProducts().stream()
                .map(CartRowCell::new).collect(Collectors.toList());
    }

    public void setButtonVisibility(boolean visible) {
        if (visible && !realContainer.getChildren().contains(shoppingCartBtns)) {
            realContainer.getChildren().add(shoppingCartBtns);
        } else if (!visible) {
            realContainer.getChildren().remove(shoppingCartBtns);
        }
    }

    @FXML
    private void onGoToChartClicked() {
        if (showChartEvent == null) return;
        showChartEvent.handle(null);
    }

    @FXML
    private void onClearCartClicked() {
        List<ShoppingItem> items = new ArrayList<>(IMatDataHandler.getInstance().getShoppingCart().getItems());
        items.forEach(si -> IMatDataHandler.getInstance().getShoppingCart().removeItem(si));
        SnackBarHandler.getInstance().showCartClearSnackBar(items, event -> {
            items.forEach(i -> IMatDataHandler.getInstance().getShoppingCart().addItem(i));
        });
    }
}
