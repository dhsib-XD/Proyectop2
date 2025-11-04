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

    // 🔹 Ahora devuelve true si la pieza está sin vida
    public boolean sinHP() {
        return vida <= 0;
    }

    // ⚔️ Ataque genérico (afecta primero el escudo)
    public void atacar(Pieza objetivo) {
        int danoRestante = dano;

        if (objetivo.escudo > 0) {
            int danoEscudo = Math.min(danoRestante, objetivo.escudo);
            objetivo.escudo -= danoEscudo;
            danoRestante -= danoEscudo;
        }

        if (danoRestante > 0) {
            objetivo.vida -= danoRestante;
        }

        if (objetivo.vida < 0)
            objetivo.vida = 0;
    }
}
