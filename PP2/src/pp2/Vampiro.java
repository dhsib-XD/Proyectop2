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
    
    public Vampiro(String tp, int vida, int dano,int escudo) {
        super("Vampiro", 4, 3,5);
    }
    
    @Override
    public void atacar(Pieza objetivo){
        this.vida += 1;
        System.out.println("El vampiro roba 1 punto de vida. Vida actual: " + this.vida);
    }
}
