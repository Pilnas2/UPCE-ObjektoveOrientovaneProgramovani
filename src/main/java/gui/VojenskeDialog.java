/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import data.DopravniLetadlo;
import data.Letadla;
import data.VojenskeLetadlo;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author marti
 */
public class VojenskeDialog extends LetadloDialog{
    
    private TextField tfPocetZbrani = new TextField();

    public VojenskeDialog(Letadla letadlo) {
    super(letadlo);
    this.setHeaderText("Vojenske Letadlo");
    grid.addRow(radek++, new Label("Počet zbraní: "), tfPocetZbrani);
        VojenskeLetadlo vojenskeLetadlo = (VojenskeLetadlo) letadlo;
        if (vojenskeLetadlo != null) {
            tfPocetZbrani.setText(Integer.toString(vojenskeLetadlo.getPocetZbrani()));
        }
        this.setResultConverter(confirm -> {
            if (confirm == confirmBtn) {
                try {
                    if (vojenskeLetadlo != null) {
                        vojenskeLetadlo.setHmotnost(Float.parseFloat(tfHmotnost.getText()));
                        vojenskeLetadlo.setPocetZbrani(Integer.parseInt(tfPocetZbrani.getText()));
                        vojenskeLetadlo.setCisloLetu(tfCisloLetu.getText());

                        return letadlo;
                    }
                    return new DopravniLetadlo(Integer.parseInt(tfPocetZbrani.getText()), tfCisloLetu.getText(), Float.parseFloat(tfHmotnost.getText()));
                } catch (Exception e) {
                    alert();
                }

            }
            return letadlo;
        });

    }

    public VojenskeDialog() {
        this(null);
    }

    public void noEdit() {
        super.noEditLetadlo();
        tfPocetZbrani.setDisable(true);
    }
    
}
