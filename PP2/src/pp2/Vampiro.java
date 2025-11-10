package pp2;

public class Vampiro extends Pieza {
    
    public Vampiro() {
       
        super("Vampiro", 4, 3, 5); 
    }

    @Override
    public void atacar(Pieza objetivo) {
       
        objetivo.recibirDano(this.dano, false);
    }
    
    
    public void chuparSangre(Pieza objetivo) {
        final int dano = 1;
        
        objetivo.recibirDano(dano, true); 
        
        
        this.setVida(this.getVida() + dano);
    }
}