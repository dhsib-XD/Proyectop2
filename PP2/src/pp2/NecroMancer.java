package pp2;

public class NecroMancer extends Pieza {

    public NecroMancer() {
        
        super("Necromancer", 3, 4, 1); 
    }

    @Override
    public void atacar(Pieza objetivo) {
        
        objetivo.recibirDano(this.dano, false);
    }
    
    public Zombie invocar() {
        
        return new Zombie(); 
    }
    
    
    public void ataqueLanza(Pieza objetivo) {
       final int dano = 2;
        
        objetivo.recibirDano(dano, true); 
    }
}