/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pp2;

/**
 *
 * @author CarlosXl
 */
public class Vampiro extends Pieza {
    
    public Vampiro() {
        super("Vampiro", 4, 3, 5); 
    }

    @Override
    public void atacar(Pieza objetivo) {
        // Aplica el ataque normal
        super.atacar(objetivo);

        // Luego absorbe 1 punto de vida si sigue vivo
        if (this.sinHP()) {
            this.vida += 1;
            System.out.println("El vampiro roba 1 punto de vida. Vida actual: " + this.vida);
        }
    }
}