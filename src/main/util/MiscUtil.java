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

    private NumberFormat nf;

    private MiscUtil() {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
        char decimalSeparator = ':';
        dfs.setDecimalSeparator(decimalSeparator);
        dfs.setMonetaryDecimalSeparator(decimalSeparator);
        nf = new DecimalFormat("0.00", dfs);
    }

    public String formatAsCurrency(double value) {
        return nf.format(value);
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
}
