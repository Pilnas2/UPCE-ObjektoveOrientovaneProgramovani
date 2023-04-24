/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author marti
 */
public abstract class Letadla implements Serializable {

    private static Random rng = new Random();

    private int id;
    private TypLetadla typ;
    private String cisloLetu;
    private float hmotnost;

    protected Letadla(TypLetadla typ, String cisloLetu) {
        this.typ = typ;
        this.cisloLetu = cisloLetu;
    }

    public Letadla(TypLetadla typ, String cisloLetu, float hmotnost) {
        if (typ == null) {
            throw new NullPointerException();
        }

        if (hmotnost <= 0) {
            throw new IllegalArgumentException();
        }
        this.typ = typ;
        this.id = createId();
        this.hmotnost = hmotnost;
        this.cisloLetu = cisloLetu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypLetadla getTyp() {
        return typ;
    }

    public String getCisloLetu() {
        return cisloLetu;
    }

    public float getHmotnost() {
        return hmotnost;
    }

    public void setCisloLetu(String cisloLetu) {
        this.cisloLetu = cisloLetu;
    }

    public void setHmotnost(float hmotnost) {
        this.hmotnost = hmotnost;
    }

    @Override
    public String toString() {
        return String.format("#%04d %s- cisloLetu: %s, hmotnost: %.2f kg ", id, typ, cisloLetu, hmotnost);
    }

    private int createId() {
        int id = rng.nextInt(9999) + 1;
        return id;
    }

}
