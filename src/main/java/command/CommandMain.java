/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package command;

import data.DopravniLetadlo;
import kolekce.KolekceException;
import kolekce.LinkSeznam;
import kolekce.SpojovySeznam;
import data.Letadla;
import data.NakladniLetadlo;
import data.VojenskeLetadlo;
import generator.Generator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import kolekce.SpojovySeznam;
import perzistence.Persistence;

/**
 *
 * @author marti
 */
//public class CommandMain {
//    
//    private static final String ZALOHA = "zaloha.bin";
//    private static SpojovySeznam<Letadla> seznam;
//    private static final Scanner scanner = new Scanner(System.in);
//
//    
//    public static void main(String[] args) throws KolekceException, IOException, ClassNotFoundException{
//           seznam = new LinkSeznam();
//           run();
//    }
//    
//    public static void run() throws KolekceException, IOException, ClassNotFoundException{
//        boolean exit = false;
//        do{
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("Zadej prikaz: ");
//            String cmd = scanner.nextLine();
//            switch(cmd){
//                case "help", "h" -> 
//                    vypisHelp();
//                case "novy", "no" -> {
//                    Letadla letadlo = null;
//                    System.out.println("Typ: dopravni(press d), nakladni(press n), vojenske(press v)");
//                    char typ = typValid(scanner.nextLine().charAt(0));
//                    System.out.println("Zadejte cislo letu - napr: 4F12421");
//                    String cisloLetu = scanner.nextLine();
//                    System.out.println("Zadej hmotnost:");
//                    double hmotnost = scanner.nextDouble();
//                    switch (typ) {
//                        case 'd' -> {
//                            System.out.println("Zadej kapacitu letadla:");
//                            int kapacitaLetadla = scanner.nextInt();
//                            letadlo = new DopravniLetadlo(kapacitaLetadla, cisloLetu, (int) hmotnost);
//                        }
//                        case 'n' -> {
//                            System.out.println("Zadej hmotnost nakladu: ");
//                            int hmotnostNakladu = scanner.nextInt();
//                            letadlo = new NakladniLetadlo(hmotnostNakladu, cisloLetu, (int) hmotnost);
//                        }
//                        case 'v' -> {
//                            System.out.println("Zadej pocet zbrani:");
//                            int pocetZbrani = scanner.nextInt();
//                            letadlo = new VojenskeLetadlo(pocetZbrani, cisloLetu, (int) hmotnost);
//                        }
//                    }
//                    if(letadlo != null){
//                        seznam.vlozPosledni(letadlo);
//                        System.out.println("letadlo pridano");
//                    }
//                }
//                case "najdi", "na", "n" -> {
//                    System.out.println("Zadej hmotnost: ");
//                    int zadanaHmotnost = scanner.nextInt();
//                    for (Letadla i : seznam) {
//                        if(i.getHmotnost() == zadanaHmotnost){
//                            System.out.println(i);
//                        }
//                        
//                    }
//                }
//                case "odeber", "od" ->{
//                    System.out.println("Zadej hmotnost: ");
//                    int odebranaHmotnost = scanner.nextInt();
//                    seznam.nastavPrvni();
//                    for(Letadla i : seznam) {
//                        if(i.getHmotnost() == odebranaHmotnost){
//                            System.out.println("Odebrano: " + i);
//                            seznam.odeberAktualni();
//                        }
//                        if(seznam.jeDalsi()){
//                            seznam.dalsi();
//                        }
//                    }
//                }
//                case "dej" ->{
//                    try{
//                        Letadla dej = seznam.dejAktualni();
//                        System.out.println(dej);
//                        
//                    }catch (KolekceException e){
//                        System.out.println("Neni nastaven");
//                    }
//                }
//                case "edituj", "edit" -> {
//                    var letadlo = seznam.dejAktualni();
//                    System.out.println("Zadejte cislo letadla: ");
//                    String cisloLeadla = scanner.nextLine();
//                    System.out.println("Hmotnost: ");
//                    float hmotnost = scanner.nextFloat();
//                    
//                    switch(letadlo.getTyp()) {
//                        case DOPRAVNI_LETADLO -> {
//                            System.out.println("Zadejte novou kapacitu letadla:");
//                            int kapacitaLetadla = scanner.nextInt();
//                            DopravniLetadlo dLetadlo = (DopravniLetadlo) seznam.dejAktualni();
//                            dLetadlo.setCisloLetu(cisloLeadla);
//                            dLetadlo.setHmotnost(hmotnost);
//                            dLetadlo.setKapacitaLetadla(kapacitaLetadla);
//                            seznam.vlozZaAktualni(dLetadlo);
//                            seznam.odeberAktualni();
//                        }
//                        case NAKLADNI_LETADLO -> {
//                            System.out.println("Zadejte novou hmotnost nakladu: ");
//                            int hmotnostNakl = scanner.nextInt();
//                            NakladniLetadlo nLetadlo = (NakladniLetadlo) seznam.dejAktualni();
//                            nLetadlo.setCisloLetu(cisloLeadla);
//                            nLetadlo.setHmotnost(hmotnost);
//                            nLetadlo.setHmotmostNakladu(hmotnostNakl);
//                            seznam.vlozZaAktualni(nLetadlo);
//                            seznam.odeberAktualni();
//                        }
//                        case VOJENSKE_LETADLO -> {
//                            System.out.println("Zadejte novy pocet zbrani: ");
//                            int zbrane = scanner.nextInt();
//                            VojenskeLetadlo vLetadlo = (VojenskeLetadlo) seznam.dejAktualni();
//                            vLetadlo.setCisloLetu(cisloLeadla);
//                            vLetadlo.setHmotnost(hmotnost);
//                            vLetadlo.setPocetZbrani(zbrane);
//                            seznam.vlozZaAktualni(vLetadlo);
//                            seznam.odeberAktualni();
//                        }
//                    }
//                    
//                }
//                case "vyjmi" -> {
//                    Letadla letadlo = seznam.odeberAktualni();
//                    System.out.println("Prvek byl vyjmut: " + letadlo);
//                }
//                case "prvni", "pr" -> {
//                    seznam.nastavPrvni();
//                }
//                case "dalsi", "da" -> {
//                    seznam.dalsi();
//                }
//                case "posledni", "po" -> {
//                    seznam.nastavPosledni();
//                }
//                case "pocet" -> {
//                    System.out.println("Pocet je: " + seznam.size() );
//                }
//                case "obnov" -> {
//                   perzistence.Persistence.nactiBin(seznam, ZALOHA);
//                }
//                case "zalohuj" -> {
//                    perzistence.Persistence.ulozBin(seznam, ZALOHA);
//                }
//                case "vypis" -> {
//                    Iterator iterator = seznam.iterator();
//                    while (iterator.hasNext()) {
//                        System.out.println(iterator.next());
//                    }
//                }
//                case "nactitext", "nt" -> {
//                    perzistence.Persistence.nacti("zaloha.txt", seznam);
//                }
//                case "ulozText", "ut" -> {
//                    perzistence.Persistence.uloz("zaloha.txt", seznam);
//                }
//                case "generuj", "g" -> {
//                    System.out.println("Zadej počet: ");
//                    int pocet = scanner.nextInt();
//                    for (Letadla letadlo : Generator.vygeneruj(pocet)) {
//                        seznam.vlozPosledni(letadlo);
//                    }
//
//                }
//
//                case "zrus" -> {
//                    seznam.zrus();
//                }
//
//                case "exit" -> {
//                    exit = true;
//                }
//            }
//        } while (exit == false);
//    }
//    
//    private static void vypisHelp() {
//        System.out.println("""
//                           help, h     - výpis příkazů
//                           novy,no     - vytvoř novou instanci a vlož data za aktuální prvek
//                           najdi,na,n  - najdi v seznamu data podle hodnoty nějakém atributu
//                           odeber,od   - odeber data ze seznamu podle nějaké hodnoty atributu 
//                           dej         - zobraz aktuální data v seznamu
//                           edituj,edit - edituj aktuální data v seznamu
//                           vyjmi       - vyjmi aktuální data ze seznamu
//                           prvni,pr    - nastav jako aktuální první data v seznamu
//                           dalsi,da    - přejdi na další data
//                           posledni,po - přejdi na poslední data
//                           pocet       - zobraz počet položek v seznamu
//                           obnov       - obnov seznam data z binárního souboru
//                           zalohuj     - zálohuj seznam dat do binárního souboru
//                           vypis       - zobraz seznam dat
//                           nactitext,nt- načti seznam data z textového souboru
//                           uloztext,ut - ulož seznam data do textového souboru
//                           generuj,g   - generuj náhodně data pro testování
//                           zrus        - zruš všechny data v seznamu
//                           exit        - ukončení programu""");
//    }
//    private static char typValid(char character) {
//        boolean opakuj = true;
//        while (opakuj == true) {
//            if (character == 'd' || character == 'n' || character == 'v') {
//                opakuj = false;
//            } else {
//                System.out.println("Chybný vstup, zadej znovu");
//                character = scanner.nextLine().charAt(0);
//                opakuj = true;
//            }
//        }
//        return character;
//    }
//
//}
