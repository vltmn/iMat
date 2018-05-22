package main.util.Model.FieldValidation;

import javafx.scene.control.TextField;

public class EmailField extends RegexField {
    public EmailField(TextField textField) {
        super(textField, ".*@.*\\..*");
    }
}
