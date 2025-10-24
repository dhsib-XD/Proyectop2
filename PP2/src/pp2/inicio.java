/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pp2;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author CarlosXl
 */
public class inicio extends JFrame {
    
    public inicio(){
     setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Menu Inicio");
        setLocationRelativeTo(null); // Centrar ventana
        getContentPane().setBackground(new Color(20, 20, 20)); 

        // titulo
        JLabel jp = new JLabel(" Vampire Wargame ");
        jp.setBounds(50, 60, 400, 100);
        jp.setOpaque(true);
        jp.setHorizontalAlignment(SwingConstants.CENTER);
        jp.setVerticalAlignment(SwingConstants.CENTER);
        jp.setBackground(new Color(60, 0, 0)); // rojo oscuro
        jp.setForeground(Color.WHITE);
        jp.setFont(new Font("Serif", Font.BOLD, 22));
        
        jp.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,5,true));
        add(jp);

        // botones
        JButton log = new JButton("Log In");
        JButton np = new JButton("Crear Player");
        JButton exit = new JButton("Salir");

        log.setBounds(340, 300, 100, 100);
        np.setBounds(200, 300, 100, 100);
        exit.setBounds(60, 300, 100, 100);

        
        final JButton[] botones = { log, np, exit };
        for (JButton b : botones) {
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
            b.setFocusPainted(false);
            b.setBackground(new Color(100, 0, 0)); // Rojo oscuro
            b.setForeground(Color.WHITE);
            b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

            
            b.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b.setBackground(new Color(180, 0, 0)); // Más brillante
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b.setBackground(new Color(100, 0, 0)); // Regresa al color original
                }
            });
            add(b);
        }

        
        log.addActionListener(e -> {
            dispose();
            new log();
        });

        
        np.addActionListener(e -> {
            dispose();
            np NP = new np();
        });

        // --- Acción del botón Salir ---
        exit.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setVisible(true);
    }
    
}
