package main.util;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.Optional;

public class BackendUtil {
    private static BackendUtil ourInstance = new BackendUtil();

    public static BackendUtil getInstance() {
        return ourInstance;
    }

    private BackendUtil() {
    }

    public double getProductCartAmount(Product p) {
        return IMatDataHandler.getInstance().getShoppingCart().getItems().stream()
                .filter(si -> si.getProduct().getProductId() == p.getProductId())
                .findAny().map(ShoppingItem::getAmount).orElse((double) 0);
    }
    public ShoppingItem addProductAmountToCart(Product p) {
        return addProductAmountToCart(p, 1);
    }

    public ShoppingItem addProductAmountToCart(Product p, double i) {
        return editProductAmount(p, i);
    }

    public ShoppingItem removeProductAmountFromCart(Product p) {
        return removeProductAmountFromCart(p, 1);
    }

    public ShoppingItem removeProductAmountFromCart(Product p, double i) {
        return editProductAmount(p, -i);
    }

    private ShoppingItem editProductAmount(Product p, double amount) {
        ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
        Optional<ShoppingItem> shoppingItemOpt = shoppingCart.getItems().stream().filter(si -> si.getProduct().getProductId() == p.getProductId()).findAny();
        ShoppingItem shoppingItem = shoppingItemOpt.orElse(new ShoppingItem(p, 0));
        shoppingItem.setAmount(amount + shoppingItem.getAmount());
        shoppingCart.removeItem(shoppingItem);
        if(shoppingItem.getAmount() > 0) {
            shoppingCart.addItem(shoppingItem);
            return shoppingItem;
        }

        return shoppingItem;
    }
}
