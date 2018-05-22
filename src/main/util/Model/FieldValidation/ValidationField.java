package main.util.Model.FieldValidation;

import javafx.scene.control.TextField;

public interface ValidationField<T> {

    public int getNumChars();

    public TextField getTextField();

    public boolean validate();

    public T getValue();
}
