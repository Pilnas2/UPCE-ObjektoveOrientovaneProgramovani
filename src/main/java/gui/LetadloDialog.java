/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import data.Letadla;
import data.TypLetadla;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author marti
 */
class LetadloDialog extends Dialog {

    protected ButtonType confirmBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    protected GridPane grid = new GridPane();
    private static final int GAP = 10;
    protected TextField tfHmotnost = new TextField();
    protected TextField tfCisloLetu = new TextField();
    protected int radek;

    protected LetadloDialog(Letadla letadla) {
        this.setTitle(letadla == null ? "Nový" : "Edit");
        this.getDialogPane().getButtonTypes().addAll(confirmBtn, ButtonType.CANCEL);
        grid.setHgap(GAP);
        grid.setVgap(GAP);
        grid.setPadding(new Insets(40, 160, 15, 15));
        this.radek = 0;

        if (letadla != null) {
            tfHmotnost.setText(Double.toString(letadla.getHmotnost()));
            tfCisloLetu.setText(letadla.getCisloLetu());
        }
        grid.addRow(radek++, new Label("Číslo letu: "), tfCisloLetu);
        grid.addRow(radek++, new Label("Hmotnost: "), tfHmotnost);

        this.getDialogPane().setContent(grid);
    }

    protected LetadloDialog() {
        this(null);
    }

    protected void alert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Špatné hodnoty!");
        alert.showAndWait();
    }

    protected void noEditLetadlo() {
        this.setTitle("Najdi");
        tfHmotnost.setDisable(true);
        tfCisloLetu.setDisable(true);
    }
}
