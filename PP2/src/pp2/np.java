package pp2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;

/**
 * 
 * 
 * @author CarlosXl 
 */
public class np extends JFrame {

    private JTextField nom = new JTextField();
    private JPasswordField contra = new JPasswordField(); 

    public np() {
        setSize(500, 500);
        setTitle("NUEVO Jugador");
        setLayout(null);

        ImageIcon img = new ImageIcon(getClass().getResource("/pp2/fondo_warframe.jpg"));
        Image imagenEscalada = img.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        JLabel fondo = new JLabel(new ImageIcon(imagenEscalada));
        fondo.setLayout(null);
        
        setContentPane(fondo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(25, 25, 25));

        
        JLabel p = new JLabel("Crea una cuenta de Vampire Wargame");
        p.setBounds(50, 40, 400, 40);
        p.setForeground(Color.BLACK);
        p.setFont(new Font("Serif", Font.BOLD, 18));
        p.setHorizontalAlignment(SwingConstants.CENTER);
        add(p);

        // nom
        JLabel TN = new JLabel("Nombre:");
        TN.setBounds(100, 140, 100, 30);
        TN.setForeground(Color.black);
        TN.setFont(new Font("SansSerif", Font.PLAIN, 15));
        add(TN);

        nom.setBounds(200, 140, 200, 30);
        nom.setBackground(new Color(50, 50, 50));
        nom.setForeground(Color.WHITE);
        nom.setCaretColor(Color.WHITE);
        nom.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(nom);

        // contra
        JLabel TC = new JLabel("Contraseña (5 caracteres, 1 especial):"); // Texto actualizado
        TC.setBounds(40, 200, 200, 30); // Ancho aumentado
        TC.setForeground(Color.LIGHT_GRAY);
        TC.setFont(new Font("SansSerif", Font.PLAIN, 15));
        add(TC);

        contra.setBounds(240, 200, 160, 30); // Posición X actualizada
        contra.setBackground(new Color(50, 50, 50));
        contra.setForeground(Color.WHITE);
        contra.setCaretColor(Color.WHITE);
        contra.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(contra);

        
        JButton addBtn = new JButton("Agregar usuario");
        addBtn.setBounds(150, 300, 200, 40);
        addBtn.setBackground(new Color(150, 0, 0));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        addBtn.setFocusPainted(false);
        add(addBtn);

        
        JButton v = new JButton("Volver al menú principal");
        v.setBounds(150, 360, 200, 40);
        v.setBackground(new Color(60, 60, 60));
        v.setForeground(Color.WHITE);
        v.setFont(new Font("SansSerif", Font.BOLD, 13));
        v.setFocusPainted(false);
        add(v);

        
        addBtn.addActionListener(e -> {
            String nombre = nom.getText().trim();
            String contraseña = new String(contra.getPassword());

            
            if (contraseña.length() != 5) {
                JOptionPane.showMessageDialog(this,
                        "La contraseña debe tener exactamente 5 caracteres.",
                        "Error de Contraseña", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            
            boolean caracateresE = false;
            for (char c : contraseña.toCharArray()) {
                // Si el carácter NO es una letra Y NO es un dígito, es especial
                if (!Character.isLetterOrDigit(c)) {
                    caracateresE = true;
                    break; // Encontramos uno, no es necesario seguir
                }
            }

            if (!caracateresE) {
                JOptionPane.showMessageDialog(this,
                        "La contraseña debe incluir al menos un carácter especial (ej: @, #, !, $).",
                        "Error de Contraseña", JOptionPane.WARNING_MESSAGE);
                return;
            }
            

            
            
            Player newPlayer = new Player(nombre, contraseña);
            boolean success = StorageManager.storage.addPlayer(newPlayer);

            if (success) {
                
                StorageManager.loggedInUser = newPlayer;
                
                JOptionPane.showMessageDialog(this, "¡Usuario " + nombre + " creado exitosamente!\nBienvenido al Menú Principal.");
                
                
                new MainMenu().setVisible(true);

                
                dispose();
                
            } else {
                JOptionPane.showMessageDialog(this,
                        "El nombre no puede estar vacío o ya existe",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        v.addActionListener(e -> {
            dispose();
            new inicio();
        });

        setVisible(true);
    }
}