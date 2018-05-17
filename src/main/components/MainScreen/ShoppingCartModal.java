package main.components.MainScreen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import main.components.CartRowCell;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartModal extends VBox {

    @FXML
    private VBox cartList;
    private EventHandler<ActionEvent> showChartEvent;


    public ShoppingCartModal() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/MainScreen/ShoppingCartPopOver.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeList();
        updateList();
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(cartEvent -> updateList());
    }

    public ShoppingCartModal(EventHandler<ActionEvent> showChartEvent) {
        this();

        this.showChartEvent = showChartEvent;
    }

    private void updateList() {
        List<CartRowCell> crcs = IMatDataHandler.getInstance().getShoppingCart().getItems().stream()
                .sorted(Comparator.comparingInt(o -> o.getProduct().getProductId()))
                .map(CartRowCell::new).collect(Collectors.toList());
        cartList.getChildren().clear();
        cartList.getChildren().addAll(crcs);
    }

    private void initializeList() {

//        list.setCellFactory(param -> new CartRowCell());
    }

    @FXML
    private void onGoToChartClicked() {
        if(showChartEvent == null) return;
        showChartEvent.handle(null);
    }
}
