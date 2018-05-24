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

    @FXML
    private Label cartCount;

    private double oldTotal = 0;

    private Color baseColor = null;

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
        shoppingCart.addShoppingCartListener((event) -> {
            cartValueLabel.setText(MiscUtil.getInstance().formatAsCurrency(shoppingCart.getTotal()));
            cartCount.setText(String.valueOf(IMatDataHandler.getInstance().getShoppingCart().getItems().size()));
        });
        cartValueLabel.setText(MiscUtil.getInstance().formatAsCurrency(shoppingCart.getTotal()));
        cartCount.setText(String.valueOf(IMatDataHandler.getInstance().getShoppingCart().getItems().size()));
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
        if(baseColor == null) {
            baseColor = Color.valueOf(getBackground().getFills().get(0).getFill().toString());
        }
        Duration duration = Duration.millis(800);
        Color newColor = baseColor.brighter();
        BackgroundFill bgf = getBackground().getFills().get(0);

        Background bg = getBackground();
        Animation anim = new Transition() {
            {
                setCycleDuration(duration.multiply(2));
                setInterpolator(Interpolator.EASE_BOTH);
            }
            @Override
            protected void interpolate(double frac) {
                Color toUse = newColor.interpolate(baseColor,
                        Math.abs(
                                frac * 2 - 1
                        ));
                setBackground(
                        new Background(new BackgroundFill(toUse, bgf.getRadii(), bgf.getInsets()))
                );

            }
        };
        anim.play();
    }
}
