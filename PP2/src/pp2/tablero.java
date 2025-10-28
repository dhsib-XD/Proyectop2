/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pp2;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
    
/**
 *
 * @author CarlosXl
 */
public class tablero extends JFrame {
    private JButton[][] b = new JButton[6][6]; 

    public tablero() {
        setTitle("Vampire Wargame");
        setSize(800, 690);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 6)); 

       
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                JButton cell = new JButton();

                
                if ((row + col) % 2 == 0) {
                    cell.setBackground(new Color (210,4,45));
                } else {
                    cell.setBackground(Color.BLACK);
                }

                
                b[row][col] = cell;

                
                cell.setText("(" + row + "," + col + ")");

                
                add(cell);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new tablero().setVisible(true);
        });
    }
}
