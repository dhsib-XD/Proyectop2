package pp2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Ventana de inicio de sesión
 * @author CarlosXl
 */
public class log extends JFrame {
    
    private JTextField nom = new JTextField();
    private JPasswordField contra = new JPasswordField();

    public log() {
        setSize(500, 500);
        setTitle("Iniciar Sesión - Vampire Wargame");
        setLayout(null);
        
         ImageIcon img = new ImageIcon(getClass().getResource("/pp2/fondo_warframe.jpg"));
        Image imagenEscalada = img.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        JLabel fondo = new JLabel(new ImageIcon(imagenEscalada));
        fondo.setLayout(null); 
        setContentPane(fondo); 

        
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        panel.setBounds(0, 0, 500, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(25, 25, 25));

        // titulo
        JLabel titulo = new JLabel("Inicia sesión en Vampire Wargame");
        titulo.setBounds(50, 40, 400, 40);
        titulo.setForeground(Color.black);
        titulo.setFont(new Font("Serif", Font.BOLD, 18));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo);

        //usuario
        JLabel lnom = new JLabel("Nombre:");
        lnom.setBounds(100, 140, 100, 30);
        lnom.setForeground(Color.black);
        lnom.setFont(new Font("SansSerif", Font.PLAIN, 15));
        add(lnom);

        nom.setBounds(200, 140, 200, 30);
        nom.setBackground(new Color(50, 50, 50));
        nom.setForeground(Color.WHITE);
        nom.setCaretColor(Color.WHITE);
        nom.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(nom);

        // contraseña
        JLabel lcontra = new JLabel("Contraseña:");
        lcontra.setBounds(100, 200, 100, 30);
        lcontra.setForeground(Color.black);
        lcontra.setFont(new Font("SansSerif", Font.PLAIN, 15));
        add(lcontra);

        contra.setBounds(200, 200, 200, 30);
        contra.setBackground(new Color(50, 50, 50));
        contra.setForeground(Color.WHITE);
        contra.setCaretColor(Color.WHITE);
        contra.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(contra);

        //boton inicio
        JButton iniciar = new JButton("Iniciar sesión");
        iniciar.setBounds(150, 280, 200, 40);
        iniciar.setBackground(new Color(150, 0, 0));
        iniciar.setForeground(Color.WHITE);
        iniciar.setFont(new Font("SansSerif", Font.BOLD, 14));
        iniciar.setFocusPainted(false);
        add(iniciar);

        /// boton volver
        JButton volver = new JButton("Volver al menú principal");
        volver.setBounds(150, 340, 200, 40);
        volver.setBackground(new Color(60, 60, 60));
        volver.setForeground(Color.WHITE);
        volver.setFont(new Font("SansSerif", Font.BOLD, 13));
        volver.setFocusPainted(false);
        add(volver);

        // --- Acción del botón Iniciar sesión ---
        iniciar.addActionListener(e -> {
            String nombre = nom.getText();
            String contraseña = new String(contra.getPassword());

            if (verificar(nombre, contraseña)) {
               JOptionPane.showMessageDialog(this, "Entrando ", "a", JOptionPane.OK_OPTION);
                dispose();
                
               
            } else {
                JOptionPane.showMessageDialog(this, "Nombre o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

       //volver
        volver.addActionListener(e -> {
            dispose();
            new inicio();
        });
       fondo.add(panel);   
        setContentPane(fondo); 
        setVisible(true);
    }

    
    public boolean verificar(String nombre, String contraseña) {
        ArrayList<String> nombres = np.ncuenta;
        ArrayList<String> contraseñas = np.ccuenta;

        for (int i = 0; i < nombres.size(); i++) {
            if (nombres.get(i).equals(nombre) && contraseñas.get(i).equals(contraseña)) {
                return true;
            }
        }
        return false;
    }
}
