/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author marti
 */
public final class NakladniLetadlo extends Letadla{
    
    private double hmotmostNakladu;

    public NakladniLetadlo(double hmotmostNakladu, int id, TypLetadla typ, String cisloLetu, float hmotnost) {
        super(typ, cisloLetu, hmotnost);
        this.hmotmostNakladu = hmotmostNakladu;
    }

    public NakladniLetadlo(double hmotmostNakladu, String cisloLetu, float hmotnost) {
        super(TypLetadla.NAKLADNI_LETADLO, cisloLetu, hmotnost);
        this.hmotmostNakladu = hmotmostNakladu;
    }

    public double getHmotmostNakladu() {
        return hmotmostNakladu;
    }

    public void setHmotmostNakladu(double hmotmostNakladu) {
        this.hmotmostNakladu = hmotmostNakladu;
    }

    @Override
    public String toString() {
        return String.format("%s, hmotnostNakladu: %s kg", super.toString(), hmotmostNakladu);
    }
    
    

    
    
}
