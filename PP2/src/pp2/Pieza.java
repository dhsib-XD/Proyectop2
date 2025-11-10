package pp2;

public abstract class Pieza {
    protected String tipo;
    protected int vida;
    protected int dano;
    protected int escudo;

    public Pieza(String tp, int vida, int dano, int escudo) {
        this.tipo = tp;
        this.vida = vida;
        this.dano = dano;
        this.escudo = escudo;
    }

    public String getTipo() {
        return tipo;
    }

    public int getVida() {
        return vida;
    }

    public int getEscudo() {
        return escudo;
    }

    public boolean sinHP() {
        return vida <= 0;
    }

    
    public abstract void atacar(Pieza objetivo);
    
    
    public void recibirDano(int cantidadDano, boolean ignoraEscudo) {
        
        if (ignoraEscudo) {
           
            this.vida -= cantidadDano;
            
        } else {
            
            if (this.escudo > 0) {
                
                this.escudo = Math.max(0, this.escudo - cantidadDano);

            } else {
               
                this.vida -= cantidadDano;
            }
        }
        
       
        if (this.vida < 0) {
            this.vida = 0;
        }
        if (this.escudo < 0) {
            this.escudo = 0;
        }
    }

    public void setVida(int vida) {
        this.vida = Math.max(0, vida);
    }
    
    public void setEscudo(int escudo) {
        this.escudo = Math.max(0, escudo);
    }
}