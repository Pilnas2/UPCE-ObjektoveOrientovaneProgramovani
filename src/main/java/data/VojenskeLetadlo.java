/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author marti
 */
public final class VojenskeLetadlo extends Letadla{
    
    private int pocetZbrani;

    public VojenskeLetadlo(int pocetZbrani, int id, TypLetadla typ, String cisloLetu, float hmotnost) {
        super(typ, cisloLetu, hmotnost);
        this.pocetZbrani = pocetZbrani;
    }
    

    public VojenskeLetadlo(int pocetZbrani, String cisloLetu, float hmotnost) {
        super(TypLetadla.VOJENSKE_LETADLO, cisloLetu, hmotnost);
        this.pocetZbrani = pocetZbrani;
    }

    public int getPocetZbrani() {
        return pocetZbrani;
    }

    public void setPocetZbrani(int pocetZbrani) {
        this.pocetZbrani = pocetZbrani;
    }

    @Override
    public String toString() {
        return String.format("%s, pocetZbrani: %s", super.toString(), pocetZbrani);
    }
    
    

}
