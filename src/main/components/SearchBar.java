package main.components;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class SearchBar extends HBox {
    private static final double DELAY_MS = 500;

    @FXML
    private TextField textField;

    private PauseTransition pt;

    private Predicate<Product> productPredicate = product -> true;

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

    public void resetFilter() {
        pt.durationProperty().setValue(Duration.ZERO);
        textField.textProperty().setValue("");
        pt.durationProperty().setValue(Duration.millis(DELAY_MS));
    }

    public void setFilterCallback(ChangeListener<Predicate<Product>> cl) {
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if(pt == null) {
                pt = new PauseTransition(Duration.millis(DELAY_MS));
            }else if(pt.statusProperty().getValue().equals(Animation.Status.RUNNING)) {
                pt.stop();
            }
            pt.setOnFinished(evt -> {
                Predicate<Product> old = productPredicate;
                List<Product> filtered = IMatDataHandler.getInstance().findProducts(newVal);
                productPredicate = filtered::contains;
                cl.changed(null, old, productPredicate);
            });
            pt.playFromStart();
        });
    }
}
