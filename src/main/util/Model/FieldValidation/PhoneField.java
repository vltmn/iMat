package main.util.Model.FieldValidation;

import javafx.scene.control.TextField;

public class PhoneField extends RegexField {
    public PhoneField(TextField textField) {
        super(textField, "\\d{3}-\\d{3} \\d{2} \\d{2}");
        setHelp("07#-### ## ##");
    }
}
