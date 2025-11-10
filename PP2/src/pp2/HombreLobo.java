
package pp2;

/**
 *
 * @author CarlosXl
 */
public class HombreLobo extends Pieza {
     
    public HombreLobo() {
        
        super("Hombre Lobo", 5, 5, 2); 
    }

    @Override
    public void atacar(Pieza objetivo) {
        objetivo.recibirDano(this.dano, false);
    }
}