/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        super.atacar(objetivo);
        System.out.println(" El hombre lobo ataca con furia.");
    }
}