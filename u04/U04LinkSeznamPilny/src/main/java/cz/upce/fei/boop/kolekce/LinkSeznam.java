/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.upce.fei.boop.kolekce;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author st64167
 */
class LinkSeznam<E> implements SpojovySeznam<E> {

    private Prvek<E> prvni, posledni, aktualni;
    private int pocet;

    public LinkSeznam(E... hodnoty) {
        Arrays.stream(hodnoty).forEach(hodnota -> vlozNaKonec(hodnota));
    }

    public void vlozNaKonec(E hodnota) {
        Objects.requireNonNull(hodnota);

        Prvek<E> p = new Prvek<>(hodnota);

        if (jePrazdny()) {
            prvni = p;
        } else {
            posledni.dalsi = p;
        }

        posledni = p;
        pocet++;
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
        if (aktualni!= null) {
           return aktualni.dalsi != null;
        } else{
            return false;
        }
    }

    @Override
    public void vlozPrvni(E data) {
        Objects.requireNonNull(data);

        Prvek<E> p = new Prvek<>(data);

        if (jePrazdny()) {
            posledni = p;
        } else {
            p.dalsi = prvni;
        }

        prvni = p;
        pocet++;
    }

    @Override
    public void vlozPosledni(E data) {
        Objects.requireNonNull(data);

        Prvek<E> p = new Prvek<>(data);

        if (jePrazdny()) {
            prvni = p;
        } else {
            posledni.dalsi = p;
        }

        posledni = p;
        pocet++;
    }

    @Override
    public void vlozZaAktualni(E data) throws KolekceException {
        if (Objects.isNull(aktualni)) {
            throw new NullPointerException();
        }

        Objects.requireNonNull(data);

        Prvek<E> p = new Prvek<>(data);
        p.dalsi = aktualni.dalsi;
        aktualni.dalsi = p;

        if (Objects.isNull(p.dalsi)) {
            posledni = p;
        }

        pocet++;
    }

    @Override
    public boolean jePrazdny() {
        return (size() == 0);
    }

    @Override
    public E dejPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }

        return prvni.hodnota;
    }

    @Override
    public E dejPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }

        return posledni.hodnota;
    }

    @Override
    public E dejAktualni() throws KolekceException {
        if (Objects.isNull(aktualni)) {
            throw new KolekceException();
        }

        return aktualni.hodnota;
    }

    @Override
    public E dejZaAktualnim() throws KolekceException {
        if (aktualni == null || aktualni.dalsi == null ) {
            throw new KolekceException();
        }

        return aktualni.dalsi.hodnota;
    }

    @Override
    public E odeberPrvni() throws KolekceException {
        if(jePrazdny()){
            throw new KolekceException();
        }
        E data = prvni.hodnota;
        if(pocet == 1){
            zrus();
        } else {
            if(aktualni == prvni){
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
        E data = posledni.hodnota;
        if(pocet == 1){
            zrus();
        } else{
            if(aktualni == posledni){
                aktualni = null;
            }
            posledni = predchoziPrvek(posledni);
            posledni.dalsi = null;
            pocet--;
        }
        return data;
    }
    
    private Prvek predchoziPrvek (Prvek posledni) {
        Prvek prvek;
        if(pocet == 2 ){
            prvek = prvni;
        } else {
            prvek = prvni;
            while (prvek.dalsi != posledni) {
                prvek = prvek.dalsi;
            }
        }
        return prvek;
    }

    @Override
    public E odeberAktualni() throws KolekceException {
        if (Objects.isNull(aktualni)) {
            throw new KolekceException();
        }

        E h = aktualni.hodnota;
        Prvek<E> dalsi = aktualni.dalsi;
        Prvek<E> predchozi;
        for (predchozi = prvni;
                predchozi != aktualni && predchozi.dalsi != aktualni;
                predchozi = predchozi.dalsi) {
        }

        if (aktualni == prvni) {
            prvni = dalsi;
        } else if (aktualni == posledni) {
            posledni = predchozi;
            predchozi.dalsi = null;
        } else {
            predchozi.dalsi = dalsi;
        }

        aktualni = null;
        pocet--;
        return h;
    }

    @Override
    public E odeberZaAktualnim() throws KolekceException {
        if (Objects.isNull(aktualni)) {
            throw new KolekceException();
        }

        Prvek<E> p = aktualni.dalsi;
        if (Objects.isNull(p)) {
            throw new KolekceException();
        }

        if (p == posledni) {
            posledni = aktualni;
        }

        aktualni.dalsi = p.dalsi;
        pocet--;

        return p.hodnota;
    }

    @Override
    public int size() {
        return pocet;
    }

    @Override
    public void zrus() {
        prvni = null;
        posledni = null;
        aktualni = null;
        pocet = 0;
    }

    @Override
    public Iterator<E> iterator() {
    Iterator<E> iterator = new Iterator<E>(){
    
        Prvek prvek = prvni;

        @Override
        public boolean hasNext() {
            return prvek != null;
        }

        @Override
        public E next() {
            if(hasNext()){
                E data = (E) prvek.hodnota;
                prvek = prvek.dalsi;
                return data;
            }
            throw new NoSuchElementException();
        }
    };
    return iterator;
    }

    private static class Prvek<E> {

        Prvek<E> dalsi;
        E hodnota;

        Prvek(E hodnota) {
            this.hodnota = hodnota;
        }
    }

    private class LinkSeznamIterator<E> implements Iterator<E> {

        Prvek<E> aktualni;

        LinkSeznamIterator(Prvek<E> prvni) {
            this.aktualni = prvni;
        }

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public E next() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }

}
