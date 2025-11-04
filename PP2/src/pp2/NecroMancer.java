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
    
  public NecroMancer() {
        super("Necromancer", 4, 3, 1);
    }

    @Override
    public void atacar(Pieza objetivo) {
        super.atacar(objetivo);
        System.out.println(" El necromancer lanza un ataque oscuro.");
    }
    
    public Zombie invocar() {
        System.out.println("️ El necromancer invoca un zombie!");
        return new Zombie();
    }
}
    

