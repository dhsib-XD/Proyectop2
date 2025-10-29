package pp2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class tablero extends JFrame {
    private JButton[][] b = new JButton[6][6]; 
    private int selectedRow = -1;
    private int selectedCol = -1;

    public tablero() {
        setTitle("Vampire Wargame");
        setSize(800, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 6)); 

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                JButton cell = new JButton();
                cell.setFocusPainted(false);
                cell.setOpaque(true);
                cell.setBorderPainted(false);

                // Color de tablero tipo ajedrez
                if ((row + col) % 2 == 0) {
                    cell.setBackground(Color.gray);
                } else {
                    cell.setBackground(Color.white);
                }

                b[row][col] = cell;

                // --- COLOCAR PIEZAS ---
                colocarPiezas(row, col, cell);

                // --- ESCUCHAR CLICS ---
                final int r = row, c = col;
                cell.addActionListener(e -> manejarClick(r, c));

                add(cell);
            }
        }
    }

    // =====================
    // MÉTODOS DEL TABLERO
    // =====================

    private void colocarPiezas(int row, int col, JButton cell) {
        if ((row == 0 && (col == 0 || col == 5)) || (row == 5 && (col == 0 || col == 5))) {
            setImage(cell, "/pp2/HL.jpeg");
            
        } else if ((row == 0 && (col == 1 || col == 4)) || (row == 5 && (col == 1 || col == 4))) {
            setImage(cell, "/pp2/Vampiro.jpeg");
        } else if ((row == 0 && (col == 2 || col == 3)) || (row == 5 && (col == 2 || col == 3))) {
            setImage(cell, "/pp2/Necromancer.jpeg");
        }
    }

    private void setImage(JButton boton, String ruta) {
        ImageIcon img = new ImageIcon(getClass().getResource(ruta));
        Image scaled = img.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(scaled));
    }

    // =====================
    // LÓGICA DE MOVIMIENTO
    // =====================

    private void manejarClick(int row, int col) {
        JButton cell = b[row][col];

        
        if (selectedRow == -1 && selectedCol == -1) {
            if (cell.getIcon() != null) { 
                selectedRow = row;
                selectedCol = col;
                cell.setBackground(Color.green); 
            }

         
        } else {
            JButton selectedCell = b[selectedRow][selectedCol];

            
            if (row == selectedRow && col == selectedCol) {
                resetSelection();
                return;
            }

            // Mover a cualquier dirección
            moverPieza(selectedRow, selectedCol, row, col);
            resetSelection();
        }
    }

    private void moverPieza(int fromRow, int fromCol, int toRow, int toCol) {
        JButton origen = b[fromRow][fromCol];
        JButton destino = b[toRow][toCol];

        if (destino.getIcon() == null) { // Solo mover si está vacía
            destino.setIcon(origen.getIcon());
            origen.setIcon(null);
            System.out.println("Pieza movida de (" + fromRow + "," + fromCol + ") a (" + toRow + "," + toCol + ")");
        } else {
            System.out.println("Esa casilla ya está ocupada.");
        }
    }

    private void resetSelection() {
        // restaurar colores de fondo
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if ((r + c) % 2 == 0) {
                    b[r][c].setBackground(Color.gray);
                } else {
                    b[r][c].setBackground(Color.white);
                }
            }
        }
        selectedRow = -1;
        selectedCol = -1;
    }

    // =====================
    // MAIN
    // =====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new tablero().setVisible(true));
    }
}
