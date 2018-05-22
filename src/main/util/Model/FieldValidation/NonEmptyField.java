package main.util.Model.FieldValidation;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class NonEmptyField implements ValidationField<String> {
    private final TextField textField;

    public NonEmptyField(TextField textField) {
        this.textField = textField;
    }

    @Override
    public int getNumChars() {
        return 0;
    }

    @Override
    public TextField getTextField() {
        return textField;
    }

    @Override
    public boolean validate() {
        return textField.getText().length() > 0;
    }

    @Override
    public String getValue() {
        return textField.getText().length() > 0 ? textField.getText() : "";
    }

    protected void setHelp(String value) {
        textField.setTooltip(new Tooltip("Skriv in enligt: " + value));
        textField.setPromptText(value);
    }
}
