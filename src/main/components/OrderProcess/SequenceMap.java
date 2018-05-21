package main.components.OrderProcess;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.function.Predicate;

public class SequenceMap extends StackPane {
    public static final String FILLED = "filled";
    @FXML
    private HBox line11;

    @FXML
    private HBox line12;

    @FXML
    private HBox line21;

    @FXML
    private HBox line22;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    public SequenceMap() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/OrderProcess/sequence-map.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStep(int step) {
        Predicate<String> removePredicate = s -> s.equalsIgnoreCase(FILLED);
        switch(step) {
            case 1:
                setStep(2);
                line11.getStyleClass().removeIf(removePredicate);
                line12.getStyleClass().removeIf(removePredicate);
                circle2.getStyleClass().removeIf(removePredicate);
                break;
            case 2:
                line11.getStyleClass().add(FILLED);
                line12.getStyleClass().add(FILLED);
                circle2.getStyleClass().add(FILLED);
                circle3.getStyleClass().removeIf(removePredicate);
                line21.getStyleClass().removeIf(removePredicate);
                line22.getStyleClass().removeIf(removePredicate);
                break;
            case 3:
                setStep(2);
                line21.getStyleClass().add(FILLED);
                line22.getStyleClass().add(FILLED);
                circle3.getStyleClass().add(FILLED);
                break;
            default:
                break;
        }
    }
}
