package pp2;

import java.awt.*;
import javax.swing.*;

/**
 * Ventana "Mi Cuenta".
 * Modificada para usar StorageManager.
 */
public class MiCuenta extends JFrame {

    private JFrame menuPrincipalFrame;

    public MiCuenta(JFrame menuPrincipalFrame) {
        this.menuPrincipalFrame = menuPrincipalFrame;
        
        // Obtenemos al usuario logueado desde el StorageManager
        Player user = StorageManager.loggedInUser;
        
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Error: No hay usuario logueado.");
            menuPrincipalFrame.setVisible(true);
            dispose();
            return;
        }

        // ... (El resto de la UI: paneles, botones, etc. sigue igual) ...
        setSize(400, 450);
        setTitle("Mi Perfil - " + user.getUsername());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(40, 40, 40));
        JLabel titulo = new JLabel("MI INFORMACIÓN");
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.NORTH);
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.add(crearInfoLabel("Usuario: " + user.getUsername()));
        infoPanel.add(crearInfoLabel("Puntos Acumulados: " + user.getPuntos()));
        infoPanel.add(crearInfoLabel("Partidas Jugadas: " + user.getGameLogs().size()));
        panel.add(infoPanel, BorderLayout.CENTER);
        JPanel botonesPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        botonesPanel.setOpaque(false);
        JButton btnCambiarPass = new JButton("a. Cambiar Password");
        JButton btnEliminar = new JButton("b. Eliminar mi Cuenta");
        JButton btnVolver = new JButton("Volver al Menú");
        btnCambiarPass.setBackground(new Color(50, 50, 150));
        btnEliminar.setBackground(new Color(180, 0, 0));
        btnVolver.setBackground(new Color(80, 80, 80));
        JButton[] botones = {btnCambiarPass, btnEliminar, btnVolver};
        for (JButton b : botones) {
            b.setForeground(Color.WHITE);
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
            botonesPanel.add(b);
        }
        panel.add(botonesPanel, BorderLayout.SOUTH);
        add(panel);

        // --- ACCIONES DE BOTONES (MODIFICADAS) ---

        btnVolver.addActionListener(e -> {
            menuPrincipalFrame.setVisible(true);
            dispose();
        });
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                menuPrincipalFrame.setVisible(true);
            }
        });

        btnCambiarPass.addActionListener(e -> {
            cambiarPassword(user);
        });

        btnEliminar.addActionListener(e -> {
            eliminarCuenta(user);
        });

        setVisible(true);
    }
    
    private JLabel crearInfoLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setForeground(Color.LIGHT_GRAY);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }

    // --- MÉTODO MODIFICADO ---
    private void cambiarPassword(Player user) {
        JPasswordField passField = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(this, passField, 
                                "Confirma tu contraseña actual:", 
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (ok == JOptionPane.OK_OPTION) {
            String passActual = new String(passField.getPassword());
            
            if (passActual.equals(user.getPassword())) {
                JTextField newPassField = new JTextField();
                int ok2 = JOptionPane.showConfirmDialog(this, newPassField,
                                "Ingresa tu NUEVA contraseña (5 caracteres):",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
                if (ok2 == JOptionPane.OK_OPTION) {
                    String newPass = newPassField.getText();
                    
                    if (newPass.length() == 5) {
                        // Usamos el StorageManager para actualizar
                        boolean success = StorageManager.storage.updatePassword(user.getUsername(), newPass);
                        if (success) {
                            JOptionPane.showMessageDialog(this, "¡Contraseña actualizada exitosamente!",
                                                          "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                             JOptionPane.showMessageDialog(this, "Error: No se pudo actualizar.",
                                                          "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "La nueva contraseña debe tener EXACTAMENTE 5 caracteres.",
                                                      "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña actual incorrecta.",
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- MÉTODO MODIFICADO ---
    private void eliminarCuenta(Player user) {
        JPasswordField passField = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(this, passField, 
                                "CONFIRMA tu contraseña para ELIMINAR:", 
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (ok == JOptionPane.OK_OPTION) {
            String passActual = new String(passField.getPassword());

            if (passActual.equals(user.getPassword())) {
                int resp = JOptionPane.showConfirmDialog(this,
                        "¿Estás SEGURO? Esta acción es irreversible.",
                        "Confirmación Final", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                
                if (resp == JOptionPane.YES_OPTION) {
                    // Usamos el StorageManager para eliminar
                    StorageManager.storage.deletePlayer(user.getUsername());
                    StorageManager.loggedInUser = null;  // Cerramos la sesión
                    
                    JOptionPane.showMessageDialog(this, "Tu cuenta ha sido eliminada.");
                    
                    // Cerramos TODO y volvemos al inicio
                    dispose(); 
                    menuPrincipalFrame.dispose(); 
                    new inicio();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta. Tu cuenta NO ha sido eliminada.",
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}