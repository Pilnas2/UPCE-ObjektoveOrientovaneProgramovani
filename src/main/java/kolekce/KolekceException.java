package kolekce;

/**

 * @author karel@simerda.cz
 */
public class KolekceException extends Exception {
    
    public KolekceException(String zpráva) {
        super(zpráva);
    }

    public KolekceException() {
        super();
    }

}
