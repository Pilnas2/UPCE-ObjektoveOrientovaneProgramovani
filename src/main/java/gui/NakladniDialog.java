/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import data.DopravniLetadlo;
import data.Letadla;
import data.NakladniLetadlo;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author marti
 */
class NakladniDialog extends LetadloDialog {

    private TextField tfHmotnostNakladu = new TextField();

    public NakladniDialog(Letadla letadlo) {
        super(letadlo);
        this.setHeaderText("Nakladni Letadlo");

        grid.addRow(radek++, new Label("Kapacita nákladu: "), tfHmotnostNakladu);
        NakladniLetadlo nakladniLetadlo = (NakladniLetadlo) letadlo;
        if (nakladniLetadlo != null) {
            tfHmotnostNakladu.setText(Double.toString(nakladniLetadlo.getHmotmostNakladu()));
        }
        this.setResultConverter(confirm -> {
            if (confirm == confirmBtn) {
                try {
                    if (nakladniLetadlo != null) {
                        nakladniLetadlo.setHmotnost(Float.parseFloat(tfHmotnost.getText()));
                        nakladniLetadlo.setHmotmostNakladu(Double.parseDouble(tfHmotnostNakladu.getText()));
                        nakladniLetadlo.setCisloLetu(tfCisloLetu.getText());

                        return letadlo;
                    }
                    return new NakladniLetadlo(Double.parseDouble(tfHmotnostNakladu.getText()), tfCisloLetu.getText(), Float.parseFloat(tfHmotnost.getText()));
                } catch (Exception e) {
                    alert();
                }

            }
            return letadlo;
        });

    }

    public NakladniDialog() {
        this(null);
    }

    public void noEdit() {
        super.noEditLetadlo();
        tfHmotnostNakladu.setDisable(true);
    }

}
