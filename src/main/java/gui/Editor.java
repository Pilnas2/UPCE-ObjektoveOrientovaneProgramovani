/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import data.DopravniLetadlo;
import data.Letadla;
import data.NakladniLetadlo;
import data.VojenskeLetadlo;
import java.util.Optional;
import java.util.function.Function;
import javafx.scene.control.Dialog;

/**
 *
 * @author marti
 */
public class Editor implements Function<Letadla, Letadla> {

    public Letadla apply(Letadla t) {
        if (t != null) {
            switch (t.getTyp()) {
                case DOPRAVNI_LETADLO -> {
                    DopravniLetadlo letadlo = (DopravniLetadlo) t;
                    DopravniDialog dialog = new DopravniDialog(t);
                    Optional<DopravniLetadlo> dopravniLetadlo = dialog.showAndWait();
                    letadlo.setCisloLetu(dopravniLetadlo.get().getCisloLetu());
                    letadlo.setHmotnost(dopravniLetadlo.get().getHmotnost());
                    letadlo.setKapacitaLetadla(dopravniLetadlo.get().getKapacitaLetadla());
                    return letadlo;
                }

                case NAKLADNI_LETADLO -> {
                    NakladniLetadlo letadlo = (NakladniLetadlo) t;
                    NakladniDialog dialog = new NakladniDialog(t);
                    Optional<NakladniLetadlo> nakladniLetadlo = dialog.showAndWait();
                    letadlo.setCisloLetu(nakladniLetadlo.get().getCisloLetu());
                    letadlo.setHmotnost(nakladniLetadlo.get().getHmotnost());
                    letadlo.setHmotmostNakladu(nakladniLetadlo.get().getHmotmostNakladu());
                    return letadlo;
                }
                case VOJENSKE_LETADLO -> {
                    VojenskeLetadlo letadlo = (VojenskeLetadlo) t;
                    VojenskeDialog dialog = new VojenskeDialog(t);
                    Optional<VojenskeLetadlo> vojenskeLetadlo = dialog.showAndWait();
                    letadlo.setCisloLetu(vojenskeLetadlo.get().getCisloLetu());
                    letadlo.setHmotnost(vojenskeLetadlo.get().getHmotnost());
                    letadlo.setPocetZbrani(vojenskeLetadlo.get().getPocetZbrani());
                    return letadlo;
                }
            }
            return t;
        }
        return null;
    }

}
