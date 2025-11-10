package pp2;

/**
 * Clase estática que gestiona la instancia de almacenamiento
 * y mantiene a los usuarios que han iniciado sesión.
 * ¡MODIFICADO! Ahora guarda al Jugador 1 y Jugador 2.
 */
public class StorageManager {

    public static IData storage = new MemoryStorage();
    
    // Almacena al usuario que ha iniciado sesión.
    public static Player loggedInUser = null; // Jugador 1
    public static Player player2 = null;       // Jugador 2
    
    // Constructor privado para que no se pueda instanciar
    private StorageManager() {} 
    
    /**
     * Cierra la sesión de AMBOS jugadores.
     */
    public static void logOut() {
        loggedInUser = null;
        player2 = null;
    }
}