package pp2;

import java.awt.*;
import javax.swing.*;

public class inicio extends JFrame {

    public inicio() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Menu Inicio");
        setLocationRelativeTo(null);
        setLayout(null);

        
        ImageIcon img = new ImageIcon(getClass().getResource("/pp2/fondo_warframe.jpg"));
        Image imagenEscalada = img.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        JLabel fondo = new JLabel(new ImageIcon(imagenEscalada));
        fondo.setBounds(0, 0, 500, 500);

        
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        panel.setBounds(0, 0, 500, 500);

      
        JLabel jp = new JLabel("Vampire Wargame");
        jp.setBounds(50, 60, 400, 100);
        jp.setOpaque(true);
        jp.setHorizontalAlignment(SwingConstants.CENTER);
        jp.setBackground(new Color(60, 0, 0, 200)); // rojo oscuro con transparencia
        jp.setForeground(Color.WHITE);
        jp.setFont(new Font("Serif", Font.BOLD, 22));
        jp.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5, true));
        panel.add(jp);

        // botones
        JButton log = new JButton("Log In");
        JButton np = new JButton("Crear Player");
        JButton exit = new JButton("Salir");

        log.setBounds(340, 300, 100, 100);
        np.setBounds(200, 300, 100, 100);
        exit.setBounds(60, 300, 100, 100);

        JButton[] botones = {log, np, exit};
        for (JButton b : botones) {
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
            b.setFocusPainted(false);
            b.setBackground(new Color(100, 0, 0));
            b.setForeground(Color.WHITE);
            b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

            b.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b.setBackground(new Color(180, 0, 0));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b.setBackground(new Color(100, 0, 0));
                }
            });
            panel.add(b);
        }

        // 
        log.addActionListener(e -> {
            dispose();
            new log();
        });

        np.addActionListener(e -> {
            dispose();
            new np();
        });

        exit.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // 
        fondo.add(panel);   
        setContentPane(fondo); 

        setVisible(true);
    }
}
