package main.Screen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OrderProcess extends VBox {


    public OrderProcess() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/screen/order_process.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initData();
    }

    private void initData() {
        
    }
}
