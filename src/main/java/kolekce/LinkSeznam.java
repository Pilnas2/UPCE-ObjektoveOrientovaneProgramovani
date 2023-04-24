/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kolekce;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author st64167
 * @param <E>
 */
public class LinkSeznam<E> implements SpojovySeznam<E> {

    private Prvek prvni;
    private Prvek posledni;
    private Prvek aktualni;
    private int pocet;

    private class Prvek {

        private final E data;
        private Prvek dalsi;

        public Prvek(E data, Prvek dalsi) {
            this.data = data;
            this.dalsi = dalsi;
        }
    }

    @Override
    public void nastavPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        aktualni = prvni;

    }

    @Override
    public void nastavPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        aktualni = posledni;
    }

    @Override
    public void dalsi() throws KolekceException {
        if (aktualni == null || !jeDalsi()) {
            throw new KolekceException();
        }
        aktualni = aktualni.dalsi;
    }

    @Override
    public boolean jeDalsi() {
        if (aktualni != null) {
            return aktualni.dalsi != null;
        } else {
            return false;
        }
        
    }

    @Override
    public void vlozPrvni(E data) {
        if (data == null) {
            throw new NullPointerException();
        }
        
        if (jePrazdny()) {
            Prvek novy = new Prvek(data, null);
            prvni = novy;
            posledni = prvni;
            pocet = 1;
        } else {
            prvni = new Prvek(data, prvni);
            pocet++;
        }

    }

    @Override
    public void vlozPosledni(E data) {
        if (data == null) {
            throw new NullPointerException();
        }
        
        if (jePrazdny()) {
            vlozPrvni(data);
        } else {
            Prvek novy = new Prvek(data, null);
            posledni.dalsi = novy;
            posledni = novy;
            pocet++;
        }

    }

    @Override
    public void vlozZaAktualni(E data) throws KolekceException {
        if (data == null) {
            throw new NullPointerException();
        }
        kontrolaAktualni();
        if (aktualni == posledni) {
            aktualni.dalsi = new Prvek(data, null);
            posledni = aktualni.dalsi;
        } else {
            Prvek odpojeny = aktualni.dalsi;
            aktualni.dalsi = new Prvek(data, odpojeny);
        }
        pocet++;
    }

    @Override
    public boolean jePrazdny() {
        return prvni == null;
    }

    @Override
    public E dejPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        return prvni.data;
    }

    @Override
    public E dejPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        return posledni.data;
    }

    private void kontrolaAktualni() throws KolekceException {
        if (aktualni == null) {
            throw new KolekceException();
        }
    }

    @Override
    public E dejAktualni() throws KolekceException {
        kontrolaAktualni();
        return aktualni.data;

    }

    @Override
    public E dejZaAktualnim() throws KolekceException {
        kontrolaAktualni();
        if (aktualni.dalsi == null) {
            throw new KolekceException();
        }
        return aktualni.dalsi.data;
    }

    @Override
    public E odeberPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        E data = prvni.data;
        if (pocet == 1) {
            zrus();
        } else {
            if (aktualni == prvni) {
                aktualni = null;
            }
            prvni = prvni.dalsi;
            pocet--;
        }
        
        return data;
    }

    @Override
    public E odeberPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        E data = posledni.data;
        if (pocet == 1) {
            zrus();
        } else {
            if (aktualni == posledni) {
                aktualni = null;
            }
            posledni = predchoziPrvek(posledni);
            posledni.dalsi = null;
            pocet--;
        }
        
        return data;
    }

    @Override
    public E odeberAktualni() throws KolekceException {
        kontrolaAktualni();
        E data = aktualni.data;
        if (aktualni == prvni) {
            odeberPrvni();
        } else if (aktualni == posledni) {
            odeberPosledni();
        } else {
            predchoziPrvek(aktualni).dalsi = aktualni.dalsi;
            aktualni = null;
            pocet--;
        }
        
        return data;
    }

    @Override
    public E odeberZaAktualnim() throws KolekceException {
        kontrolaAktualni();
        if (aktualni == posledni) {
            throw new KolekceException();
        }
        E data = aktualni.dalsi.data;
        aktualni.dalsi = aktualni.dalsi.dalsi;
        pocet--;
        return data;
    }

    @Override
    public int size() {
        return pocet;
    }

    @Override
    public void zrus() {
        prvni = null;
        posledni = prvni;
        aktualni = null;
        pocet = 0;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> iterator = new Iterator<E>() {
            
            Prvek prvek = prvni;
            
            @Override
            public boolean hasNext() {
                return prvek != null;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    E data = prvek.data;
                    prvek = prvek.dalsi;
                    return data;
                }
                throw new NoSuchElementException();
            }
            
        };
        return iterator;
        
    }

    private Prvek predchoziPrvek(Prvek posledni) {
        Prvek prvek;
        if (pocet == 2) {
            prvek = prvni;
        } else {
            prvek = prvni;
            while (prvek.dalsi != posledni) {
                prvek = prvek.dalsi;
            }
        }
        return prvek;
    }
}
