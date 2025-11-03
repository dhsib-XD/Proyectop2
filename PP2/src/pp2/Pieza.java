package pp2;

public abstract class Pieza {
    protected String tipo;
    protected int vida;
    protected int daño;
    protected int escudo; // 🛡️ Nuevo atributo

    public Pieza(String tipo, int vida, int daño, int escudo) {
        this.tipo = tipo;
        this.vida = vida;
        this.daño = daño;
        this.escudo = escudo;
    }

    public String getTipo() {
        return tipo;
    }

    public int getVida() {
        return vida;
    }

    public int getDaño() {
        return daño;
    }

    public int getEscudo() {
        return escudo;
    }

    public void recibirDaño(int cantidad) {
        System.out.println(tipo + " recibe un ataque de " + cantidad + " puntos.");

        if (escudo > 0) {
            int dañoAlEscudo = Math.min(cantidad, escudo);
            escudo -= dañoAlEscudo;
            cantidad -= dañoAlEscudo;
            System.out.println(tipo + " pierde " + dañoAlEscudo + " puntos de escudo. Escudo restante: " + escudo);
        }

        if (cantidad > 0) {
            vida -= cantidad;
            if (vida < 0) vida = 0;
            System.out.println(tipo + " pierde " + cantidad + " puntos de vida. Vida restante: " + vida);
        }
    }

    public boolean sinHP() {
        return vida > 0;
    }

    public void atacar(Pieza objetivo) {
        objetivo.recibirDaño(this.daño);
    }

    @Override
    public String toString() {
        return tipo + " (vida: " + vida + ", escudo: " + escudo + ")";
    }
}
