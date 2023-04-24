/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perzistence;

import data.DopravniLetadlo;
import data.Letadla;
import data.NakladniLetadlo;
import data.TypLetadla;
import data.VojenskeLetadlo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import kolekce.SpojovySeznam;

/**
 *
 * @author marti
 */
public class Persistence {

    public static <T> void uloz(String jmenoSouboru, SpojovySeznam<T> seznam) throws IOException {
        try {
            Objects.requireNonNull(seznam);
            ObjectOutputStream vystup = new ObjectOutputStream(new FileOutputStream(jmenoSouboru));
            vystup.writeInt(seznam.size());
            Iterator<T> it = seznam.iterator();
            while (it.hasNext()) {
                vystup.writeObject(it.next());
            }
            vystup.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static <T> SpojovySeznam<T> nacti(String jmenoSouboru, SpojovySeznam seznam) throws IOException {
        try {
            Objects.requireNonNull(seznam);
            ObjectInputStream vstup = new ObjectInputStream(new FileInputStream(jmenoSouboru));
            seznam.zrus();

            int pocet = vstup.readInt();
            for (int i = 0; i < pocet; i++) {
                seznam.vlozPosledni((T) vstup.readObject());
            }
            vstup.close();
        } catch (IOException | ClassNotFoundException ex) {
            throw new IOException(ex);
        } finally {
        }
        return seznam;
    }
}
