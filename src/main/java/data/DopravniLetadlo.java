/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author marti
 */
public final class DopravniLetadlo extends Letadla{
    
    private int kapacitaLetadla;

    public DopravniLetadlo(int kapacitaLetadla, int id, TypLetadla typ, String cisloLetu, float hmotnost) {
        super(typ, cisloLetu, hmotnost);
        this.kapacitaLetadla = kapacitaLetadla;
    }

    public DopravniLetadlo(int kapacitaLetadla, String cisloLetu, float hmotnost) {
        super(TypLetadla.DOPRAVNI_LETADLO, cisloLetu, hmotnost);
        this.kapacitaLetadla = kapacitaLetadla;
    }

    public int getKapacitaLetadla() {
        return kapacitaLetadla;
    }

    public void setKapacitaLetadla(int kapacitaLetadla) {
        this.kapacitaLetadla = kapacitaLetadla;
    }

    @Override
    public String toString() {
        return String.format("%s, kapacitaLetadla: %s", super.toString(), kapacitaLetadla);
    }
    
    
    
}
