package main.util;

import se.chalmers.cse.dat216.project.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
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

    public Map<String, String> getCustomerFields() {
        Map<String, String> toReturn = new HashMap<>();
        toReturn.put("firstName", "Förnamn");
        toReturn.put("lastName", "Efternamn");
        toReturn.put("phoneNumber", "Telefonnr.");
        toReturn.put("email", "E-post");
        toReturn.put("address", "Address");
        toReturn.put("postCode", "Postnr.");
        toReturn.put("postAddress", "Postort");
        return toReturn;
    }

    public boolean isCreditCardComplete() {
        CreditCard dbCard = IMatDataHandler.getInstance().getCreditCard();
        if(dbCard.getCardNumber() == null) return false;
        if("".equals(dbCard.getCardNumber())) return false;
        //TODO add card type
        if("".equals(dbCard.getHoldersName())) return false;
        return true;
    }

    public String getCreditCardValidity(CreditCard card) {
        NumberFormat nf = new DecimalFormat("00");
        return nf.format(card.getValidMonth()) + "/" + nf.format(card.getValidYear());
    }
    public Map<String, String> getCreditCardFields() {
        Map<String, String> toReturn = new HashMap<>();
        toReturn.put("holdersName", "Kortinnehavare");
        toReturn.put("validMonth", "Giltig t.o.m. månad");
        toReturn.put("validYear", "Giltig t.o.m. år");
        toReturn.put("cartNumber", "Kortnummer");
        toReturn.put("verificationCode", "Verifikationsnr.");
        return toReturn;
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
        shoppingItem.setAmount(round(amount + shoppingItem.getAmount(),1 ));
        shoppingCart.removeItem(shoppingItem);
        if(shoppingItem.getAmount() > 0) {
            shoppingCart.addItem(shoppingItem);
            return shoppingItem;
        }

        return shoppingItem;
    }

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
