package main.Screen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import main.components.MainScreen.ShoppingCartModal;
import main.components.OrderHistory.HistoryProduct;
import main.components.OrderHistory.PrevOrder;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class OrderHistory extends HBox{

    private List<Order> orders;

    @FXML private FlowPane historyFlow;
    @FXML private FlowPane productFlow;


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
        this.getChildren().add(new ShoppingCartModal());
    }

    public OrderHistory() {
        this(IMatDataHandler.getInstance().getOrders());
    }

    private void populateHistoryList(){
        for (Order ord : orders){
            PrevOrder prevOrder = new PrevOrder(ord, event -> populateProductList(ord));
            historyFlow.getChildren().add(prevOrder);
        }
    }

    private void populateProductList(Order order){
        productFlow.getChildren().clear();
        for (ShoppingItem item : order.getItems()){
            productFlow.getChildren().add(new HistoryProduct(item));
        }
    }

}
