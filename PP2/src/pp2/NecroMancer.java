/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pp2;

/**
 *
 * @author CarlosXl
 */
public class NecroMancer extends Pieza {
    
    public NecroMancer(String tp, int vida, int dano,int escudo) {
        super("NecroMancer", 3, 4,1);
    }
    
    @Override
    public void atacar(Pieza objetivo) {
        super.atacar(objetivo);
        System.out.println("El necromancer lanza un hechizo oscuro contra " + objetivo.getTipo());
    }
    
}
