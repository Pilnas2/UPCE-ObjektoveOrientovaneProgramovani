/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spravce;

import data.Letadla;
import generator.Generator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import kolekce.KolekceException;
import kolekce.LinkSeznam;
import kolekce.SpojovySeznam;
import perzistence.Persistence;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import perzistence.PerzistenceTxt;

/**
 *
 * @author marti
 * @param <E>
 */
public class SpravceLetadel<E> implements Ovladani<E> {

    private SpojovySeznam<E> letadla;
    private Comparator<? super E> komparator;
    private Consumer<String> errorLog;

    public SpravceLetadel(SpojovySeznam<E> letadla) {
        this.letadla = letadla;
    }

    @Override
    public void nastavKomparator(Comparator<? super E> comparator) {
        this.komparator = comparator;
    }

    public void nastavErrorLog(Consumer<String> errorLog) {
        this.errorLog = errorLog;
    }

    public void vloz(E data) {
        letadla.vlozPosledni(data);
    }

    public E najdi(E data) {
        if (komparator == null) {
            errorLog.accept("Není nastaven komparátor");
            return null;
        } else {
            return letadla.stream()
                    .filter(E -> komparator.compare(E, data) == 0)
                    .findAny()
                    .orElse(null);
        }
    }

    public void prvni() {
        try {
            letadla.nastavPrvni();
        } catch (KolekceException ex) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());
        }
    }

    public void posledni() {
        try {
            letadla.nastavPosledni();
        } catch (KolekceException ex) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());
        }
    }

    public void dalsi() {
        try {
            letadla.dalsi();
        } catch (KolekceException ex) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());
        }
    }

    public void predchozi() {
        try {
            Iterator<E> it = iterator();
            E letadlo = dej();
            if (letadlo != null) {
                prvni();
                if (letadlo == dej()) {
                    errorLog.accept("Nelze nastavit předchozí!");
                } else {
                    it.next();
                    while (it.hasNext() && it.next() != letadlo) {
                        dalsi();
                    }
                }
            }

        } catch (Exception e) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + e.toString());
        }

    }

    public E dej() {
        try {
            return letadla.dejAktualni();
        } catch (KolekceException ex) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());
        }
        return null;
    }

    public Iterator<E> iterator() {
        return letadla.iterator();
    }

    public E odeber(E klic) {
        if (komparator == null) {
            errorLog.accept("Není nastaven komparátor");
            return null;
        } else {
            E data = null;

            try {
                letadla.nastavPrvni();
                for (E i : letadla) {
                    if (komparator.compare(i, klic) == 0) {
                        data = i;
                        letadla.odeberAktualni();
                    }
                    if (letadla.jeDalsi()) {
                        letadla.dalsi();
                    }
                }
            } catch (KolekceException ex) {
                errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());

            }
            return data;

        }
    }
    @Override
    public E odeber() {
        try {
            return letadla.odeberAktualni();
        } catch (KolekceException ex) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());
        }
        return null;
    }

    public void generuj(int pocet) {
        for (Letadla letadlo : generator.Generator.vygeneruj(pocet)) {
            letadla.vlozPosledni((E) letadlo);
        }
    }

    public void edituj(Function<E, E> editor) {
        try {
            E data = dej();
            if (data != null) {
                data = editor.apply(data);
                letadla.vlozZaAktualni(data);
                letadla.odeberAktualni();
            }
        } catch (KolekceException ex) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());
        }
    }

    public void nactiTextSoubor(String soubor, Function<String, E> mapper) {
        Stream stream = null;
        try {
            stream = PerzistenceTxt.readStream(soubor, mapper);
        } catch (IOException ex) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());
        }
        stream.forEach(x -> {
            letadla.vlozPosledni((E) x);
        });
    }

    public void ulozTextSoubor(String soubor, Function<E, String> mapper) {
        try {
            PerzistenceTxt.writeStream(letadla.stream(), soubor, mapper);
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());
        }
    }

    @Override
    public void zalohuj(String soubor) {
        try {
            Persistence.uloz(soubor, letadla);
        } catch (IOException ex) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());
        }
    }

    @Override
    public void obnov(String soubor) {
         try {
            Persistence.nacti(soubor, letadla);
        } catch (IOException ex) {
            errorLog.accept(SpravceLetadel.class.getName() + " " + ex.toString());
        }
    }

    public int dejPocet() {
        return letadla.size();
    }

    public void zrus() {
        letadla.zrus();
    }

}
