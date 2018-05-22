package main.util.Model.FieldValidation;

import javafx.scene.control.TextField;

public class CardNumberField extends RegexField {
    public CardNumberField(TextField textField) {
        super(textField, "\\d{4} \\d{4} \\d{4} \\d{4}");
        setHelp("#### #### #### ####");
    }
}
