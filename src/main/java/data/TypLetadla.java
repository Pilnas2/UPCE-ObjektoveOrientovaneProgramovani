/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author marti
 */
public enum TypLetadla {
    DOPRAVNI_LETADLO("DOPRAVNI_LETADLO"),
    NAKLADNI_LETADLO("NAKLADNI_LETADLO"),
    VOJENSKE_LETADLO("VOJENSKE_LETADLO"),
    KLIC("KLIC");

    private final String nazev;
    private static final List<TypLetadla> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    TypLetadla(String nazev) {
        this.nazev = nazev;
    }

    public String nazev() {
        return this.nazev;
    }

    public static TypLetadla randomTyp() {
        return VALUES.get(RANDOM.nextInt(SIZE - 1));
    }

    public static TypLetadla[] valuesBezKlice() {
        return Arrays.copyOf(TypLetadla.values(), SIZE - 1);
    }

}
