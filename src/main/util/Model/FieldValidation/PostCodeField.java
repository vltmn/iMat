package main.util.Model.FieldValidation;

import javafx.scene.control.TextField;

public class PostCodeField extends RegexField {
    public PostCodeField(TextField textField) {
        super(textField, "\\d{3} \\d{2}");
        setHelp("### ##");
    }
}
