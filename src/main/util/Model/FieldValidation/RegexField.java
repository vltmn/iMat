package main.util.Model.FieldValidation;

import javafx.scene.control.TextField;

public class RegexField extends NonEmptyField {
    private final String _pattern;
    protected RegexField(TextField textField, String pattern) {
        super(textField);
        _pattern = pattern;
    }

    @Override
    public boolean validate() {
        return getValue().matches(_pattern);
    }
}
