package pp2;

public class Zombie extends Pieza {

    public Zombie() {
        super("Zombie", 1, 1, 0); 
    }

    @Override
    public void atacar(Pieza objetivo) {
         objetivo.recibirDano(this.dano, false);
        System.out.println(" El zombie muerde a " + objetivo.getTipo());
    }
}
