package main.components.MainScreen;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.util.MiscUtil;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;

import java.io.IOException;

public class ShoppingCartBtn extends HBox {
    @FXML
    private Label cartValueLabel;

    private double oldTotal = 0;

    public ShoppingCartBtn() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/MainScreen/ShoppingCartBtn.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
        shoppingCart.addShoppingCartListener((event) -> cartValueLabel.setText(MiscUtil.getInstance().formatAsCurrency(shoppingCart.getTotal())));
        cartValueLabel.setText(MiscUtil.getInstance().formatAsCurrency(shoppingCart.getTotal()));
        oldTotal = IMatDataHandler.getInstance().getShoppingCart().getTotal();
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(event -> {
            double newTotal = IMatDataHandler.getInstance().getShoppingCart().getTotal();
            if( newTotal > oldTotal) {
                animateAdd();
            }
            oldTotal = newTotal;
        });
    }

    public void animateAdd() {
        Duration duration = Duration.millis(800);
        Color oldColor = Color.valueOf(getBackground().getFills().get(0).getFill().toString());
        Color newColor = oldColor.brighter();
        BackgroundFill bgf = getBackground().getFills().get(0);

        Background bg = getBackground();
        Animation anim = new Transition() {
            {
                setCycleDuration(duration.multiply(2));
                setInterpolator(Interpolator.EASE_BOTH);
            }
            @Override
            protected void interpolate(double frac) {
                Color toUse = newColor.interpolate(oldColor,
                        Math.abs(
                                frac * 2 - 1
                        ));
                setBackground(
                        new Background(new BackgroundFill(toUse, bgf.getRadii(), bgf.getInsets()))
                );

            }
        };
        anim.play();

//        Background curBg = new Background(new BackgroundFill(oldColor,
//                getBackground().getFills().get(0).getRadii(), Insets.EMPTY));
//        Color newC  =oldColor.brighter().brighter();
//        getBackground()
//        Background blinkBg = new BackgroundFill(oldColor.brighter().brighter(),
//                curBg.getFills().get(0).getRadii(), Insets.EMPTY);
//        Duration duration = Duration.millis(500);
//        Timeline tl = new Timeline(
//                new KeyFrame(Duration.ZERO,
//                        new KeyValue(backgroundProperty(), curBg)),
//                new KeyFrame(duration,
//                        new KeyValue(backgroundProperty(), blinkBg, Interpolator.EASE_BOTH)),
//                new KeyFrame(duration.multiply(2),
//                        new KeyValue(backgroundProperty(), curBg, Interpolator.EASE_BOTH))
//        );
//        tl.playFromStart();
    }
}
