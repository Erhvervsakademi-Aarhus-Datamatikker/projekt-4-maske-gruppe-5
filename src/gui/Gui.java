package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Forestilling;
import model.Kunde;
import model.Plads;
import storage.Storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javafx.scene.Node;

public class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GridPane pane = new GridPane();
        pane.setMinSize(900, 600);
        App.initStorage();
        initContent(pane);
        Scene scene = new Scene(pane);
        stage.setTitle("Teater Bestillinger");
        stage.setScene(scene);
        stage.show();
    }

    // ListView laves
    private static LabelWithList<Forestilling> forestillingsListe = new LabelWithList<Forestilling>("Forestillinger");
    private static LabelWithList<Kunde> kundeListe = new LabelWithList<Kunde>("Kunder");
    private static LabelWithList<Plads> pladsListe = new  LabelWithList<Plads>("Pladser");

    // Textfield laves
    private static LabelWithTextField navn = new LabelWithTextField("Navn");
    private static LabelWithTextField startDato = new LabelWithTextField("Start dato");
    private static LabelWithTextField slutDato = new LabelWithTextField("Slut dato");
    private static LabelWithTextField kundeNavn = new LabelWithTextField("Kunde navn");
    private static LabelWithTextField kundeMobil = new LabelWithTextField("Kunde mobil");
    private static LabelWithTextField pladsDato = new LabelWithTextField("Dato");

    private void initContent(GridPane pane) {
        pane.setHgap(10);
        pane.setVgap(20);


        // Listview tilføjes til pane
        pane.add(forestillingsListe, 1, 1);
        for (Forestilling f : Storage.getForestillinger()) {
            forestillingsListe.getListView().getItems().add(f);
        }
        pane.add(kundeListe, 2, 1);
        for (Kunde k : Storage.getKunder()) {
            kundeListe.getListView().getItems().add(k);
        }
        pane.add(pladsListe, 3, 1);

        pladsListe.getListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // *_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
        //        GEMINI HAR LAVET MEN FORSTÅR GODT (og ændret det lidt selv) OG HAR SELV LAVET KOMMENTERET
        //                  KUN LAVET FORDI NICKLAS IKKE GAD AT HOLDE COMMAND/CONTROL NEDE
        //                                         -- Nicklas

        // Add an event filter to the ListView to handle mouse press events.
        // This allows us to implement a custom selection behavior.
        pladsListe.getListView().addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            // Get the node that was clicked. In JavaFX, everything you see on the screen is a Node.
            Node node = evt.getPickResult().getIntersectedNode();

            // The clicked node might be a child of the ListCell (eks. the text inside the cell).
            // We need to traverse up the scene graph to find the actual ListCell.
            // A ListCell is the container for each item in the ListView.
            while (node != null && !(node instanceof ListCell)) {
                node = node.getParent(); // Move up to the parent node.
            }

            // If we found a ListCell and ONLY ListCell (not only NULL check), AND cast the node into a ListCell
            // called "cell"
            if (node instanceof ListCell cell) {
                // Consume the event. This stops the ListView's default selection behavior from happening.
                // Basically saying "i got this, dont do as you would normally", so it dosent deselect everything
                // else like it normally does.
                evt.consume();

                // Cast the node we clicked on into a ListCell so we can work with it as an ListCell
                // Make "pladsListe.getListView()" into a variable so we make code more clear
                ListView<Plads> lv = pladsListe.getListView();

                // We only want to do something if the clicked cell is not empty.
                if (!cell.isEmpty()) {
                    // Get the index of the item in the ListView that corresponds to the cell we clicked
                    int index = cell.getIndex();
                    // Check if the item at this index is already selected.
                    if (lv.getSelectionModel().getSelectedIndices().contains(index)) {
                        // If it's already selected, deselect it.
                        lv.getSelectionModel().clearSelection(index);
                    } else {
                        // If it's not selected, select it.
                        lv.getSelectionModel().select(index);
                    }
                }
            }
        });

        // *_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_

        for (Plads p : Storage.getPladser()) {
            pladsListe.getListView().getItems().add(p);
        }

        // Textfield tilføjes til pane
        pane.add(navn, 1, 2);
        pane.add(startDato, 1, 3);
        pane.add(slutDato, 1, 4);
        pane.add(kundeNavn, 2, 2);
        pane.add(kundeMobil, 2, 3);
        pane.add(pladsDato, 3, 2);

        Button forestillingButton = new Button("Opret forestilling");
        Button kundeButton = new Button("Opret kunden");
        Button opretBestillingButton = new Button("Opret bestilling");

        pane.add(forestillingButton, 1, 5);
        pane.add(kundeButton, 2, 4);
        pane.add(opretBestillingButton, 3, 3);

        GridPane.setHalignment(forestillingButton, HPos.CENTER);
        GridPane.setHalignment(kundeButton, HPos.CENTER);
        GridPane.setHalignment(opretBestillingButton, HPos.CENTER);


        forestillingButton.setOnAction(e -> {
            addForestilling();
        });
        kundeButton.setOnAction(e -> {
            addKunde();
        });
        opretBestillingButton.setOnAction(e -> {
            addBestilling();
        });

    }

    private static void addForestilling() {
        try {
            String navnStr = navn.getText();
            String startDatoStr = startDato.getText();
            String slutDatoStr = slutDato.getText();

            // Tjekker om et af felter er tomme, hvis tomt så stop/return
            if (navnStr.isEmpty() || startDatoStr.isEmpty() || slutDatoStr.isEmpty()) {
                System.out.println("Please fill all relevant fields");
                return;
            }

            // Laver forestilllingen
            Controller.lavForestilling(navnStr, LocalDate.parse(startDatoStr), LocalDate.parse(slutDatoStr));

            // Sletter alle items og sætter dem ind igen så vores nye forestilling kommer øverst
            forestillingsListe.getListView().getItems().clear();
            for (Forestilling f : Storage.getForestillinger()) {
                forestillingsListe.getListView().getItems().add(f);
            }

            // Clear' alle input felterne så man ikke kommer til at trykke flere gange
            navn.getInputField().clear();
            startDato.getInputField().clear();
            slutDato.getInputField().clear();

        } catch (DateTimeParseException e) { // Hvis LocalDate formaten ikke er som den skal være med LocalDate.parse
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private static void addKunde() {
        String navnStr = kundeNavn.getText();
        String mobilStr = kundeMobil.getText();

        // Tjekker om et af felterne er tomme, hvis ja stop/return
        if (navnStr.isEmpty() || mobilStr.isEmpty()) {
            System.out.println("Please fill all relevant fields for Kunde");
            return;
        }

        // Laver kunde
        Controller.lavKunde(navnStr, mobilStr);

        // Sletter items af kundeListe for at man kan indsætte dem således at den man sidst har indsat kommer øverst
        kundeListe.getListView().getItems().clear();
        for (Kunde k : Storage.getKunder()) {
            kundeListe.getListView().getItems().add(k);
        }

        // Clear' inputfelterne så man ikke trykker mere en én gang, og som hjælp
        kundeNavn.getInputField().clear();
        kundeMobil.getInputField().clear();
    }

    private static void addBestilling() {
        System.out.println("\n  Bestilling:");
        System.out.println(forestillingsListe.getListView().getSelectionModel().getSelectedItems());
        System.out.println(kundeListe.getListView().getSelectionModel().getSelectedItems());
        System.out.println(pladsListe.getListView().getSelectionModel().getSelectedItems());

        Controller.opretBestillingMedPladser(Storage.getForestillinger().get(forestillingsListe.getListView().getSelectionModel().getSelectedIndex()),
                Storage.getKunder().get(kundeListe.getListView().getSelectionModel().getSelectedIndex()),
                LocalDate.parse(pladsDato.getText()),
                new ArrayList<Plads>(pladsListe.getListView().getSelectionModel().getSelectedItems()));
    }
}