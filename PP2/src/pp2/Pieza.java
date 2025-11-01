/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pp2;

/**
 *
 * @author CarlosXl
 */
public abstract class Pieza  {
    String tp;
    int vida;
    int dano;
    
public Pieza(String tp,int vida,int dano){

    
}

    public String getTp() {
        return tp;
    }

    public int getVida() {
        return vida;
    }

    public int getDano() {
        return dano;
    }

public void recibirD(int cantidad){
    vida -=cantidad;
     if (vida < 0){
         vida = 0;
     }
}

public boolean sinHP(){
    return vida<=0;
}

public void atacar(Pieza objetivo){
    objetivo.recibirD(this.dano);
}

    public String toString() {
        return tp + " (vida: " + vida + ")";
    }
}

