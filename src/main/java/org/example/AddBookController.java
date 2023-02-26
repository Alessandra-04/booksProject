package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBookController {
    public TextField name;
    public TextField author;
    public TextField pages;
    public TextArea synopsis;
    public ComboBox recommendedBy;
    public Button updateBtn;
    public Button resetBtn;
    public Button deleteBtn;
    public Button saveBtn;

    private Button addButton;

    private boolean addButtonClicked = false;

    public boolean isAddButtonClicked() {
        return addButtonClicked;
    }

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        addButtonClicked = true;
        ((Stage) addButton.getScene().getWindow()).close();
    }

    public ButtonType getNameField() {
        return null;
    }

    public ButtonType getImageField() {
        return null;
    }

    public ButtonType getAuthorField() {
        return null;
    }
}
