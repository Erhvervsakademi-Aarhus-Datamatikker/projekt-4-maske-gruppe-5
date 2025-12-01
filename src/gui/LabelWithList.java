package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class LabelWithList<T> extends VBox {
    private ListView<T> showListView;

    public LabelWithList(String titel) {
        Label titleLabel = new Label(titel);
        titleLabel.setStyle("-fx-font-weight: bold;");

        showListView = new ListView<>();

        showListView.setPrefHeight(150);
        showListView.setPrefWidth(250);

        this.getChildren().addAll(titleLabel, showListView);

        this.setPadding(new Insets(10));
        this.setSpacing(5);
    }

    public ListView<T> getListView() {
        return showListView;
    }

}
