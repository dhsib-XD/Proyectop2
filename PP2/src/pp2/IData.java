package pp2;

import java.util.ArrayList;


public interface IData {

    
    Player getPlayer(String username, String password);
    
    
    Player getPlayerByUsername(String username);
    
    
    boolean addPlayer(Player newPlayer);
    
    
    boolean deletePlayer(String username);
    
    
    boolean updatePassword(String username, String newPassword);
    
   
    void addLog(String username, String logEntry);
    
    
    ArrayList<Player> getAllPlayers();
}