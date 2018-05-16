package main.components.OrderProcess;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class VerificationPane extends VBox {
    public VerificationPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/OrderProcess/verificationPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void show() {
        //TODO show data
        this.toFront();
    }

    public void complete() {
        //TODO place order
        IMatDataHandler.getInstance().placeOrder();
    }
}
