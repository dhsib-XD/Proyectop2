/*
 * Click nbfs://nombhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pp2;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author CarlosXl
 */
public class np extends JFrame {
    
    public np (){
        ArrayList<JTextField> ncuenta= new ArrayList<>();
        ArrayList<JTextField> ccuenta= new ArrayList<>();
        
    setSize(500, 500);
    
        setTitle("NUEVO Jugador");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana
        getContentPane().setBackground(new Color(25, 25, 25)); // Fondo oscuro elegante

        // --- Etiqueta principal ---
        JLabel p = new JLabel("Crea una cuenta de Vampire Wargame");
        p.setBounds(50, 40, 400, 40);
        p.setForeground(Color.WHITE);
        p.setFont(new Font("Serif", Font.BOLD, 18));
        p.setHorizontalAlignment(SwingConstants.CENTER);
        add(p);

        // Etiqueta "Nombre" 
        JLabel TN = new JLabel("Nombre:");
        TN.setBounds(100, 140, 100, 30);
        TN.setForeground(Color.LIGHT_GRAY);
        TN.setFont(new Font("SansSerif", Font.PLAIN, 15));
        add(TN);

        //  Campo de texto para el nombre 
        JTextField nom = new JTextField();
        nom.setBounds(200, 140, 200, 30);
        nom.setBackground(new Color(50, 50, 50));
        nom.setForeground(Color.WHITE);
        nom.setCaretColor(Color.WHITE);
        nom.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(nom);

        // --- Etiqueta "Contraseña" ---
        JLabel TC = new JLabel("Contraseña:");
        TC.setBounds(100, 200, 100, 30);
        TC.setForeground(Color.LIGHT_GRAY);
        TC.setFont(new Font("SansSerif", Font.PLAIN, 15));
        add(TC);

         //Contraseña 
        JPasswordField contra = new JPasswordField();
        contra.setBounds(200, 200, 200, 30);
        contra.setBackground(new Color(50, 50, 50));
        contra.setForeground(Color.WHITE);
        contra.setCaretColor(Color.WHITE);
        contra.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(contra);

       
        JButton add = new JButton("Agregar usuario");
        add.setBounds(150, 280, 200, 40);
        add.setBackground(new Color(150, 0, 0));
        add.setForeground(Color.WHITE);
        add.setFont(new Font("SansSerif", Font.BOLD, 14));
        add.setFocusPainted(false);
        add(add);

       
        JButton v = new JButton("Volver al menú principal");
        v.setBounds(150, 340, 200, 40);
        v.setBackground(new Color(60, 60, 60));
        v.setForeground(Color.WHITE);
        v.setFont(new Font("SansSerif", Font.BOLD, 13));
        v.setFocusPainted(false);
        add(v);

        add.addActionListener(e ->{
            if (verif(nom,contra,ccuenta,ncuenta)) {
                 
                ccuenta.add(contra);
                ncuenta.add(nom);
                JOptionPane.showMessageDialog(this, "Usuario Creado exitosamente");
                
            }else{
                JOptionPane.showMessageDialog(this, "El Nombre o Contrasena son invalidos", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
 
               
            
        });
        v.addActionListener(e -> {
        dispose();
        new inicio();
        
        });
        
        setVisible(true);
    
    }
    public boolean verif(JTextField nom,JTextField contra,ArrayList ccuenta, ArrayList ncuenta){
        if (nom == null || contra == null || nom.getText().isBlank()||contra.getText().isBlank()) {
           
            return false;
            
        }
        if (ncuenta.contains(nom)) {
            return false;
        }
        
        
        return true;
    }
    
}
