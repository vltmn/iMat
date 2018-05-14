package main.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TextInput extends HBox {

    private final String name;

    @FXML
    private Label textLabel;

    @FXML
    private TextField textField;

    public TextInput(String name) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/TextInput.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.name = name;
    }

    public TextInput(String name, String labelText, String textFieldValue) {
        this(name, labelText);
        textField.setText(textFieldValue);
    }

    public TextInput(String name, String labelText) {
        this(name);
        textLabel.setText(labelText + ": ");
    }

    public String getName() {
        return name;
    }

    public void setValue(String textFieldValue) {
        textField.setText(textFieldValue);
    }

    public String getValue() {
        return textField.getText();
    }
}
