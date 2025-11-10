package pp2;

import java.util.ArrayList;

/**
 * Implementación de IDataStorage que guarda los datos en un ArrayList en memoria.
 * ¡MODIFICADO! Se eliminaron los usuarios de prueba.
 */
public class MemoryStorage implements IData {

    private ArrayList<Player> players = new ArrayList<>();

    public MemoryStorage() {
        // --- Bloque de datos de prueba ---
        // ¡ELIMINADO!
        // La lista de jugadores ahora comienza 100% vacía.
        // Los usuarios DEBEN registrarse desde la app.
        // --- Fin de datos de prueba ---
    }

    @Override
    public Player getPlayer(String username, String password) {
        Player user = getPlayerByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public Player getPlayerByUsername(String username) {
        // ¡Llamada a la Función Recursiva 1!
        return findUserRecursive(this.players, username, 0);
    }

    @Override
    public boolean addPlayer(Player newPlayer) {
        if (newPlayer.getUsername().isBlank() || getPlayerByUsername(newPlayer.getUsername()) != null) {
            return false;
        }
        players.add(newPlayer);
        return true;
    }

    @Override
    public boolean deletePlayer(String username) {
        Player user = getPlayerByUsername(username);
        if (user != null) {
            players.remove(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePassword(String username, String newPassword) {
        Player user = getPlayerByUsername(username);
        if (user != null) {
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }

    @Override
    public void addLog(String username, String logEntry) {
        Player user = getPlayerByUsername(username);
        if (user != null) {
            // Añade al principio (Modificado en la respuesta anterior)
            user.addGameLog(logEntry); 
        }
    }

    @Override
    public ArrayList<Player> getAllPlayers() {
        return players;
    }

    // --- FUNCIÓN RECURSIVA 1 ---
    private Player findUserRecursive(ArrayList<Player> list, String username, int index) {
        if (index >= list.size()) {
            return null;
        }
        if (list.get(index).getUsername().equalsIgnoreCase(username)) {
            return list.get(index);
        }
        return findUserRecursive(list, username, index + 1);
    }
}