package main.util;

import se.chalmers.cse.dat216.project.Product;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class MiscUtil {
    private static MiscUtil ourInstance = new MiscUtil();

    public static MiscUtil getInstance() {
        return ourInstance;
    }

    private NumberFormat currencyFormat;
    private NumberFormat amountFormat;

    private MiscUtil() {
        DecimalFormatSymbols curDfs = DecimalFormatSymbols.getInstance();
        char decimalSeparator = ':';
        curDfs.setDecimalSeparator(decimalSeparator);
        curDfs.setMonetaryDecimalSeparator(decimalSeparator);
        currencyFormat = new DecimalFormat("0.00", curDfs);
        DecimalFormatSymbols amountDfs = DecimalFormatSymbols.getInstance();
        amountDfs.setDecimalSeparator('.');
        amountFormat = new DecimalFormat("0.##", amountDfs);
    }

    public String formatAsCurrency(double value) {
        return currencyFormat.format(value);
    }

    public double getProductEditAmount(Product p) {
        double editAmount;
        if(p.getUnitSuffix().toLowerCase().equals("kg")) {
            editAmount = .3;
        } else {
            editAmount = 1;
        }
        return editAmount;
    }
    public String formatAsAmount(double value) {
        return amountFormat.format(value);
    }
}
