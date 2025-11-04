package pp2;

public class Zombie extends Pieza {

    public Zombie() {
        super("Zombie", 1, 1, 0); 
    }

    @Override
    public void atacar(Pieza objetivo) {
        super.atacar(objetivo);
        System.out.println("💀 El zombie muerde a " + objetivo.getTipo());
    }
}
