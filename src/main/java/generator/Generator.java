/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generator;

import data.DopravniLetadlo;
import data.Letadla;
import data.TypLetadla;
import java.util.Arrays;
import java.util.Random;
import data.TypLetadla;
import data.DopravniLetadlo;
import data.NakladniLetadlo;
import static data.TypLetadla.randomTyp;
import data.VojenskeLetadlo;
import kolekce.LinkSeznam;
import kolekce.SpojovySeznam;

/**
 *
 * @author marti
 */
public class Generator {
    private static final Random rng = new Random();

    
    public static Letadla[] vygeneruj(int pocet) {
        Letadla[] letadla = new Letadla[pocet];
        Random random = new Random();
        for (int i = 0; i < pocet; i++) {
            TypLetadla typ = randomTyp();
            switch (typ) {
            case DOPRAVNI_LETADLO -> {
                int kapacitaLt = random.nextInt(1000) + 1;
                String cislo = genCisloLetu();
                float hm = random.nextFloat(4000) + 1000;
                letadla[i] = new DopravniLetadlo(kapacitaLt, cislo, hm);
            }
            case NAKLADNI_LETADLO -> {
                double naklad = random.nextDouble(10000) + 1000;
                String cislo = genCisloLetu();
                float hm = random.nextFloat(5000) + 1000;
                letadla[i] = new NakladniLetadlo(naklad, cislo, hm);
            }
            case VOJENSKE_LETADLO -> {
                int zbrane = random.nextInt(10) + 1;
                String cislo = genCisloLetu();
                float hm = random.nextFloat(3000) + 1000;
                letadla[i] = new VojenskeLetadlo(zbrane, cislo, hm);
            }
        }
        }
        return letadla;
    }
    public static double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
    
    public static String genCisloLetu(){
        return String.format("%d%c%d%04d", rng.nextInt(9) + 1, (char) ('A' + rng.nextInt('Z' - 'A') + 1), rng.nextInt(9) + 1, rng.nextInt(10000));
    }
    
}
