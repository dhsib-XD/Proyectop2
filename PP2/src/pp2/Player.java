package pp2;

import java.util.ArrayList;

public class Player {

    private String username;
    private String password;
    private int puntos;
    private ArrayList<String> gameLogs;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.puntos = 0; 
        this.gameLogs = new ArrayList<>();
    }

    // --- Getters y Setters ---
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntos() {
        return puntos;
    }
    
    // --- MÉTODO REQUERIDO ---
    public void addPuntos(int puntos) {
        this.puntos += puntos;
    }

    public ArrayList<String> getGameLogs() {
        return gameLogs;
    }

    // --- MÉTODO REQUERIDO ---
    public void addGameLog(String logEntry) {
        // Añade al principio para que el más reciente esté en el índice 0
        this.gameLogs.add(0, logEntry); 
    }
}