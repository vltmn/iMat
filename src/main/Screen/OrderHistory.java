package main.Screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.components.MainScreen.ShoppingCartModal;
import main.components.OrderHistory.HistoryProduct;
import main.components.OrderHistory.PrevOrder;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends HBox {

    private EventHandler<ActionEvent> closeHandler;
    private List<Order> orders;
    private List<PrevOrder> prevOrders = new ArrayList<PrevOrder>();

    @FXML private HBox parentHBox;
    @FXML private VBox historyVBox;
    @FXML private VBox productVBox;


    public OrderHistory(List<Order> orders){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/screen/order_history.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.orders = orders;
        populateHistoryList();
        parentHBox.getChildren().add(new ShoppingCartModal());
    }

    public OrderHistory(EventHandler closeEvent) {
        this(IMatDataHandler.getInstance().getOrders());
        this.closeHandler = closeEvent;
    }

    private void populateHistoryList(){
        for (Order ord : orders){
            PrevOrder prevOrder = new PrevOrder(ord, event -> populateProductList(ord), event -> {
                for (PrevOrder prevO : prevOrders) {
                    prevO.setSelected(false);
                }
            });
            historyVBox.getChildren().add(prevOrder);
            prevOrders.add(prevOrder);
        }
    }

    private void populateProductList(Order order){
        productVBox.getChildren().clear();
        for (ShoppingItem item : order.getItems()){
            productVBox.getChildren().add(new HistoryProduct(item));
        }
        if (productVBox.getChildren().size() > 0) {
            productVBox.getChildren().get(productVBox.getChildren().size() - 1).getStyleClass().add("last-child");
        }
    }

    @FXML
    private void onCloseBtnClicked() {
        closeHandler.handle(null);
    }

}
