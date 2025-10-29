package pp2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Ventana para crear nuevos jugadores con límite de 5 usuarios
 * y contraseñas de máximo 5 caracteres.
 * @author CarlosXl
 */
public class np extends JFrame {

    static ArrayList<String> ncuenta = new ArrayList<>();
    static ArrayList<String> ccuenta = new ArrayList<>();

    private JTextField nom = new JTextField();
    private JTextField contra = new JTextField(); // visible
    private JLabel contador = new JLabel();

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

        //titulos
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
        JLabel TC = new JLabel("Contraseña (máx. 5):");
        TC.setBounds(60, 200, 150, 30);
        TC.setForeground(Color.LIGHT_GRAY);
        TC.setFont(new Font("SansSerif", Font.PLAIN, 15));
        add(TC);

        contra.setBounds(200, 200, 200, 30);
        contra.setBackground(new Color(50, 50, 50));
        contra.setForeground(Color.WHITE);
        contra.setCaretColor(Color.WHITE);
        contra.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(contra);

        

        // agregar
        JButton addBtn = new JButton("Agregar usuario");
        addBtn.setBounds(150, 300, 200, 40);
        addBtn.setBackground(new Color(150, 0, 0));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        addBtn.setFocusPainted(false);
        add(addBtn);

        // volver
        JButton v = new JButton("Volver al menú principal");
        v.setBounds(150, 360, 200, 40);
        v.setBackground(new Color(60, 60, 60));
        v.setForeground(Color.WHITE);
        v.setFont(new Font("SansSerif", Font.BOLD, 13));
        v.setFocusPainted(false);
        add(v);

        // nuevo usuario
        addBtn.addActionListener(e -> {
            String nombre = nom.getText().trim();
            String contraseña = contra.getText().trim();

            

            // Límite de longitud en contraseña
            if (contraseña.length() > 5) {
                JOptionPane.showMessageDialog(this,
                        "La contraseña no puede tener más de 5 caracteres.",
                        "Contraseña demasiado larga", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (verif(nombre, contraseña)) {
                ncuenta.add(nombre);
                ccuenta.add(contraseña);
                JOptionPane.showMessageDialog(this, "Usuario creado exitosamente");
                nom.setText("");
                contra.setText("");
                 
            } else {
                JOptionPane.showMessageDialog(this,
                        "El nombre o la contraseña son inválidos o ya existen",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        // volver
        v.addActionListener(e -> {
            dispose();
            new inicio();
        });

        setVisible(true);
    }

    //
    public boolean verif(String nom, String contra) {
        if (nom.isBlank() || contra.isBlank()) {
            return false;
        }
        return !ncuenta.contains(nom);
    }

    public ArrayList<String> getNcuenta() {
        return ncuenta;
    }

    public ArrayList<String> getCcuenta() {
        return ccuenta;
    }
}
