/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import data.DopravniLetadlo;
import data.Letadla;
import data.TypLetadla;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author marti
 */
public class DopravniDialog extends LetadloDialog {

    private TextField tfKapacita = new TextField();

    public DopravniDialog(Letadla letadlo) {
        super(letadlo);
        this.setHeaderText("Dopravni Letadlo");

        grid.addRow(radek++, new Label("Kapacita: "), tfKapacita);
        DopravniLetadlo dopravniLetadlo = (DopravniLetadlo) letadlo;
        if (dopravniLetadlo != null) {
            tfKapacita.setText(Integer.toString(dopravniLetadlo.getKapacitaLetadla()));
        }
        this.setResultConverter(confirm -> {
            if (confirm == confirmBtn) {
                try {
                    if (dopravniLetadlo != null) {
                        dopravniLetadlo.setHmotnost(Float.parseFloat(tfHmotnost.getText()));
                        dopravniLetadlo.setKapacitaLetadla(Integer.parseInt(tfKapacita.getText()));
                        dopravniLetadlo.setCisloLetu(tfCisloLetu.getText());

                        return letadlo;
                    }
                    return new DopravniLetadlo(Integer.parseInt(tfKapacita.getText()), tfCisloLetu.getText(), Float.parseFloat(tfHmotnost.getText()));
                } catch (Exception e) {
                    alert();
                }

            }
            return letadlo;
        });

    }

    public DopravniDialog() {
        this(null);
    }

    public void noEdit() {
        super.noEditLetadlo();
        tfKapacita.setDisable(true);
    }
}
