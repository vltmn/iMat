package main.controllers;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchBar extends HBox {
    private static final double DELAY_MS = 500;

    @FXML
    private TextField textField;

    private PauseTransition pt;

    public SearchBar() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/SearchBar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFilterCallback(ChangeListener<String> cl) {
        //TODO show loading icon
        textField.textProperty().addListener((obs, old, newVal) -> {
            if(pt == null) {
                pt = new PauseTransition(Duration.millis(DELAY_MS));
            }else if(pt.statusProperty().getValue().equals(Animation.Status.RUNNING)) {
                pt.stop();
            }
            pt.setOnFinished(evt -> {
                cl.changed(obs, old, newVal);
            });
            pt.playFromStart();
        });
    }
}
