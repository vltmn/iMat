package main.Screen;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

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
    private void onClick(){
        eventHandler.handle(null);
    }
}
