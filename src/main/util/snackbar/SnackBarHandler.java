package main.util.snackbar;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.ArrayDeque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class SnackBarHandler {
    private Pane snackBarPane;

    private static final Duration animationDuration = Duration.millis(200);

    private EventHandler<ActionEvent> BAR_CLICK_HANDLER =  event -> {};

    private Queue<SnackBarWrapper> snackBarQueue = new ArrayDeque<>();

    private PauseTransition snackBarShowPause = new PauseTransition();

    private TranslateTransition showHideTransition;
    private static SnackBarHandler ourInstance = new SnackBarHandler();

    public static SnackBarHandler getInstance() {
        return ourInstance;
    }

    private SnackBarHandler() {

    }

    private void animatePaneUp() {
        showHideTransition.setFromY(100);
        showHideTransition.setToY(0);
        showHideTransition.playFromStart();
    }

    private void animatePaneDown() {
        showHideTransition.setFromY(0);
        showHideTransition.setToY(100);
        showHideTransition.playFromStart();
    }

    public void initialize(Pane snackBarPane) {

        this.snackBarPane = snackBarPane;
        showHideTransition = new TranslateTransition(animationDuration, snackBarPane);
        BAR_CLICK_HANDLER = event -> {
            snackBarShowPause.stop();
            animatePaneDown();
            showHideTransition.setOnFinished(event1 -> {
                showHideTransition.setOnFinished(event2 -> {
                });
                snackBarPane.getChildren().clear();
                nextQueueItem();
            });
        };
        snackBarShowPause.setOnFinished(event -> {
            showHideTransition.setOnFinished(event1 -> {
                showHideTransition.setOnFinished(event2 -> {});
                snackBarPane.getChildren().clear();
                nextQueueItem();

            });
            animatePaneDown();


        });
    }

    private void nextQueueItem() {
        if(snackBarQueue.isEmpty()) return;
        SnackBarWrapper poll = snackBarQueue.poll();
        if(poll == null) return;
        snackBarShowPause.setDuration(Duration.millis(poll.getMillisToShow()));
        snackBarPane.getChildren().add(poll.getSnackBar());
        showHideTransition.setOnFinished(event -> {
            snackBarShowPause.playFromStart();
            showHideTransition.setOnFinished(event1 -> {});
        });
        animatePaneUp();
    }

    public void showSnackBar(String text, EventHandler<ActionEvent> onBtnClick) {
        SnackBarWrapper bar = new SimpleSnackBar(text, onBtnClick, BAR_CLICK_HANDLER);
        handleSnackBar(bar);
    }

    private void handleSnackBar(SnackBarWrapper bar) {
        if(snackBarShowPause.statusProperty().getValue().equals(Animation.Status.RUNNING)) {
            snackBarQueue.add(bar);
            return;
        }
        snackBarShowPause.setDuration(Duration.millis(bar.getMillisToShow()));
        snackBarPane.getChildren().add(bar.getSnackBar());
        showHideTransition.setOnFinished(event -> {
            snackBarShowPause.playFromStart();
            showHideTransition.setOnFinished(event1 -> {});
        });
        animatePaneUp();
    }
    public void showProductUndoSnackbar(ShoppingItem si, EventHandler<ActionEvent> onBtnClick) {
        SnackBarWrapper bar = new UndoCartRemoveSnackBar(si, event -> {
            onBtnClick.handle(event);
            BAR_CLICK_HANDLER.handle(event);
        }, BAR_CLICK_HANDLER);
        handleSnackBar(bar);

    }

    public void showCartClearSnackBar(List<ShoppingItem> itemsCleared, EventHandler<ActionEvent> onBtnClick) {
        int size = itemsCleared.size();
        String snackBarLabel = String.format("%d varor togs bort. ", size);
        SnackBarWrapper bar = new SimpleSnackBar(snackBarLabel, event -> {
            onBtnClick.handle(event);
            BAR_CLICK_HANDLER.handle(event);
        }, BAR_CLICK_HANDLER);
        handleSnackBar(bar);
    }
}
