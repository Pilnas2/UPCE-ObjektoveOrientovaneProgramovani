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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author marti
 */
public class PerzistenceTxt {

    public PerzistenceTxt() {
    }

    public static <T> Stream<T> readStream(String nameFile, Function<String, T> mapper)
            throws IOException {
        return Files.lines(Paths.get(nameFile), StandardCharsets.UTF_8)
                .filter(t -> t != null)
                .map(mapper);
    }

    public static <T> void writeStream(Stream<T> stream, String nameFile, Function<T, String> mapper) throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter pw = new PrintWriter(nameFile, "UTF-8")) {
            stream
                    .map(mapper)
                    .forEachOrdered(pw::println);
        }
    }

    public static Function<String, Letadla> mapperInput = (line) -> {
        Letadla letadla = null;
        String[] hodnoty = line.split(";");
        int id = Integer.parseInt(hodnoty[1]);
        String cisloLetu = hodnoty[2];
        float hmotnost = Float.parseFloat(hodnoty[3]);
        switch (hodnoty[0]) {
            case "DOPRAVNI_LETADLO" -> {
                int kapacita = Integer.parseInt(hodnoty[4]);
                letadla = new DopravniLetadlo(kapacita, cisloLetu, hmotnost);
            }
            case "NAKLADNI_LETADLO" -> {
                double hmotnostNakladu = Double.parseDouble(hodnoty[4]);
                letadla = new NakladniLetadlo(hmotnostNakladu, cisloLetu, hmotnost);
            }
            case "VOJENSKE_LETADLO" -> {
                int pocetZbrani = Integer.parseInt(hodnoty[4]);
                letadla = new VojenskeLetadlo(pocetZbrani, cisloLetu, hmotnost);
            }
        }
        letadla.setId(id);
        return letadla;
    };

    public static Function<Letadla, String> mapperOutput = (letadlo) -> {
        TypLetadla typ = letadlo.getTyp();
        int id = letadlo.getId();
        String cisloLetu = letadlo.getCisloLetu();
        double hmotnost = letadlo.getHmotnost();
        String line = "";
        switch (typ) {
            case DOPRAVNI_LETADLO -> {
                DopravniLetadlo dopravniLetadlo = (DopravniLetadlo) letadlo;
                int kapacita = dopravniLetadlo.getKapacitaLetadla();
                line = (typ.nazev() + ";" + id + ";" + cisloLetu + ";" + hmotnost + ";" + kapacita);
            }
            case NAKLADNI_LETADLO -> {
                NakladniLetadlo nakladniLetadlo = (NakladniLetadlo) letadlo;
                double hmotnostNakladu = nakladniLetadlo.getHmotmostNakladu();
                line = (typ.nazev() + ";" + id + ";" + cisloLetu + ";" + hmotnost + ";" + hmotnostNakladu);
            }
            case VOJENSKE_LETADLO -> {
                VojenskeLetadlo vojenskeLetadlo = (VojenskeLetadlo) letadlo;
                int pocetZbrani = vojenskeLetadlo.getPocetZbrani();
                line = (typ.nazev() + ";" + id + ";" + cisloLetu + ";" + hmotnost + ";" + pocetZbrani);

            }
        }
        return line;
    };
}
