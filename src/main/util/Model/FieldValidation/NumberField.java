package main.util.Model.FieldValidation;


import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class NumberField implements ValidationField<Number>{
    private final TextField textField;
    private final int numChars;

    public NumberField(TextField textField, int numChars) {
        this.textField = textField;
        this.numChars = numChars;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < numChars; i++) sb.append("#");
        textField.setPromptText(sb.toString());
        textField.setTooltip(new Tooltip(sb.toString()));
    }

    public NumberField(TextField textField) {
        this(textField, 0);
    }

    public TextField getTextField() {
        return textField;
    }

    public int getNumChars() {
        return numChars;
    }

    public boolean validate() {
        String text = textField.getText();
        if("".equals(text) || text == null) return false;
        if(!text.matches("-?\\d+(\\.\\d+)?")) return false;
        if(text.length() != numChars && numChars != 0) return false;
        return true;
    }

    @Override
    public Number getValue() {
        if(!validate()) return null;
        String toParse = textField.getText();
        try {
            return Double.parseDouble(toParse);
        } catch(NumberFormatException ex) {
            return null;
        }

    }
}
