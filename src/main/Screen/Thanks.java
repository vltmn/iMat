package main.Screen;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class Thanks extends VBox{
    private final EventHandler eventHandler;

    public Thanks(EventHandler eventHandler){
        this.eventHandler = eventHandler;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/screen/thanks.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onProceedClick(){
        eventHandler.handle(null);
    }

    @FXML
    private void onTerminateClick(){
        IMatDataHandler.getInstance().shutDown();
        Platform.exit();
    }
}
