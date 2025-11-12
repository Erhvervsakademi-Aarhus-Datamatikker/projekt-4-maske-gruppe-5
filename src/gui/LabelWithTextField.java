package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class LabelWithTextField extends HBox {
    private TextField textField;

    public LabelWithTextField(String labelText) {
        Label inputLabel = new Label(labelText);
        textField = new TextField();

        inputLabel.setMinWidth(90);
        inputLabel.setPrefWidth(90);

        this.setSpacing(10);

        HBox.setHgrow(textField, Priority.ALWAYS);

        this.getChildren().addAll(inputLabel, textField);

        this.setPadding(new Insets(5));

    }

    public TextField getInputField() {
        return textField;
    }

    public String getText() {
        return textField.getText();
    }
}
