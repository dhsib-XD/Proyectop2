package pp2;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * 
 */
public class MainMenu extends JFrame {

    public MainMenu() {
        String username = (StorageManager.loggedInUser != null) ? StorageManager.loggedInUser.getUsername() : "Invitado";
        
        setSize(500, 500);
        setTitle("Menu Principal - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(30, 30, 30));
        JLabel titulo = new JLabel("VAMPIRE WARGAME");
        titulo.setFont(new Font("Serif", Font.BOLD, 30));
        titulo.setForeground(new Color(180, 0, 0));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.NORTH);
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setOpaque(false);
        JButton btnJugar = new JButton("1. JUGAR VAMPIRE WARGAME");
        JButton btnCuenta = new JButton("2. MI CUENTA");
        JButton btnReportes = new JButton("3. REPORTES");
        JButton btnLogout = new JButton("4. LOG OUT");
        JButton[] botones = {btnJugar, btnCuenta, btnReportes, btnLogout};
        for (JButton b : botones) {
            b.setFont(new Font("SansSerif", Font.BOLD, 16));
            b.setBackground(new Color(100, 0, 0));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            panelBotones.add(b);
        }
        panel.add(panelBotones, BorderLayout.CENTER);
        add(panel);


        
        btnJugar.addActionListener(e -> {
            
            int totalPlayers = StorageManager.storage.getAllPlayers().size();
            
            if (totalPlayers < 2) {
                JOptionPane.showMessageDialog(this,
                        "Se necesitan al menos 2 jugadores registrados para jugar.\n" +
                        "Actualmente solo hay " + totalPlayers + " jugador(es).",
                        "No hay suficientes jugadores",
                        JOptionPane.WARNING_MESSAGE);
                return; // Detiene la acción
            }

            
            Player player1 = StorageManager.loggedInUser;
            Player player2 = loginJugador2(); // Llamamos a la función helper

            if (player2 != null) {
                
                StorageManager.player2 = player2; 
                
                JOptionPane.showMessageDialog(this,
                        "¡Listos para la batalla!\n\n" +
                        "Jugador 1: " + player1.getUsername() + "\n" +
                        "Jugador 2: " + player2.getUsername(),
                        "¡A Jugar!",
                        JOptionPane.INFORMATION_MESSAGE);
                
                
                new tablero().setVisible(true);
               
                dispose();
                
            }
        });

        
        btnCuenta.addActionListener(e -> {
            new MiCuenta(this); 
            this.setVisible(false);
        });

        btnReportes.addActionListener(e -> {
            new Reportes(this); 
            this.setVisible(false);
        });

        
        btnLogout.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro que quieres cerrar sesión?", "Log Out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (resp == JOptionPane.YES_OPTION) {
                StorageManager.logOut(); 
                dispose();
                new inicio(); 
            }
        });

        setVisible(true);
    }

    
    private Player loginJugador2() {
        // Creamos un panel personalizado para el diálogo
        JPanel panelLoginP2 = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel userLabel = new JLabel("Usuario (Jugador 2):");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Contraseña (Jugador 2):");
        JPasswordField passField = new JPasswordField();
        panelLoginP2.add(userLabel);
        panelLoginP2.add(userField);
        panelLoginP2.add(passLabel);
        panelLoginP2.add(passField);

        int result = JOptionPane.showConfirmDialog(this, panelLoginP2,
                "Inicio de Sesión - Jugador 2",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String usernameP2 = userField.getText();
            String passwordP2 = new String(passField.getPassword());

            
            if (usernameP2.isBlank() || passwordP2.isBlank()) {
                JOptionPane.showMessageDialog(this, "Usuario y Contraseña no pueden estar vacíos.",
                        "Error Jugador 2", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            
            if (usernameP2.equals(StorageManager.loggedInUser.getUsername())) {
                JOptionPane.showMessageDialog(this, "El Jugador 2 no puede ser el mismo que el Jugador 1.",
                        "Error Jugador 2", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            
            Player p2 = StorageManager.storage.getPlayer(usernameP2, passwordP2);
            if (p2 == null) {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas para el Jugador 2.",
                        "Error Jugador 2", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            
            return p2;
        }

        return null; 
    }
}