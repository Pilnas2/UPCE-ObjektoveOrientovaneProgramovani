/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package spravce;

import data.Letadla;
import java.util.Comparator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import kolekce.SpojovySeznam;

/**
 *
 * @author marti
 */
public interface Ovladani<E> extends Iterable<E>{
    
    /**
     * Metoda nastavErrorLog() nastaví chybový výpis
     *
     * @param errorlog Reference na objekt s výpisem chybového hlášení
     */
    void nastavErrorLog(Consumer<String> errorlog);
    
    /**
     * Metoda nastavKomparator nastaví jakým způsobem se budou porovnávat datové
     * položky seznamu.
     *
     * @param comparator reference na komparátor
     */
    void nastavKomparator(Comparator<? super E> comparator);

        /**
     * Metoda vloží novou datovou položku do seznamu.
     *
     * @param data datová položka, která se má vložit do seznamu.
     */
    void vloz(E data);
    
    default void vloz(E... data) {
        for (E d : data) {
            vloz(d);
        }
    }
    
    /**
     * Metoda vrátí referenci na vyhledanou datovou položku v seznamu podle
     * hodnoty klíče.
     * @param klic reference na objekt hodnotou klíče
     *
     * @return reference na nalezenou datovou položku při shodě s klíčem, v
     * případě, že nedošlo ke shodě podle klíče vrací se <code>null</code>
     */
    E najdi(E klic);
    
    /**
     * Metoda odebere vyhledanou datovou položku v seznamu podle hodnoty klíče.
     *
     * @param klic reference na objekt daný hodnotou klíče
     * @return vrací odebranou datovou položku pokud se shoduje s klíčem, pokud
     * se neshoduje tak se vrací null.
     */
    E odeber(E klic);
    
     /**
     * Metoda odeber() odebere aktuální prvek ze seznamu a zajistí souvislé
     * propojení zbylých prvků seznamu.
     *
     * Metoda mění aktuální počet objektů v seznamu.
     *
     * Po odebrání aktuálního prvku bude vnitřní ukazatel na aktuální
     * nedefinován.
     *
     * @return Vrací odkaz na odebíraný objekt, datovou entitu typu E
     */
    E odeber();
    
    /**
     * Metoda nastaví aktuální prvek seznamu na první prvek v seznamu.
     */
    void prvni();
    
    /**
     * Metoda nastaví aktuální prvek seznamu na poslední prvek v seznamu.
     */
    void posledni();
    
    /**
     * Metoda nastaví aktualní prvek jako další prvek v seznamu. Předpokladem
     * této metody je, že je vždy nastaven aktuální prvek.
     *
     */
    void dalsi();
    
    /**
     * Metoda nastaví aktuální prvek na prvek před původním aktuálním prvkem.
     * Předpokladem této metody je, že je vždy nastaven aktuální prvek.
     *
     */
    
    E dej();
    
    
    /**
     * Metoda generuje náhodně položky do seznamu podle zadaného počtu.
     *
     * @param pocet počet generovaných prvků
     */
    void generuj(int pocet);
    
        
    /**
     * Metoda zajišťuje editaci všech parametrů aktuálně nastaveného prvku.
     * Předpokladem je, že je vždy nastaven aktuální prvek.
     *
     * @param editor odkaz na objekt editor.
     */
    void edituj(Function<E, E> editor);
    
    /**
     * Metoda načte data z textového souboru a pomocí mapperu převede tyto data
     * na objekt.
     *
     * @param soubor název cílového souboru.
     * @param mapper odkaz na mapper
     */
    void nactiTextSoubor(String soubor, Function<String, E> mapper);
    
    /**
     * Metoda uloží do textového souboru data, která pomocí mapperu převede z
     * objektu na String.
     *
     * @param soubor název cíleného souboru
     * @param mapper odkaz na mapper
     */
    void ulozTextSoubor(String soubor, Function<E, String> mapper);
    
    /**
     * Metoda provede perzistenci seznamu do souboru, který je dán parametrem
     * soubor.
     *
     * Požadavek je, že se serializují jednotlivé datové entity seznamu.
     *
     * @param soubor název cíleného souboru
     */
    void zalohuj(String soubor);
    
    /**
     * Metoda obnoví seznam položek ze souboru, který je dán parametrem soubor.
     *
     * Požadavek je, že soubor obsahuje serializované jednotlivé data.
     *
     * @param soubor
     */
    void obnov(String soubor);
    
    
    /**
     * Metoda vrací aktuální počet položek v seznamu.
     *
     * @return
     */
    int dejPocet();
    
    /**
     * Metoda smaže všechny položky v seznamu(zruší).
     */
    void zrus();
    
    /**
     * Stream převádí obsah seznamu na datový proud.
     *
     * @return datový proud
     */
    void predchozi();
    
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
    
}
