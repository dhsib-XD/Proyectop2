package pp2;

import java.awt.*;
import javax.swing.*;

public class tablero extends JFrame {
    private JButton[][] b = new JButton[6][6]; 
    private Pieza[][] piezas = new Pieza[6][6]; // Nueva matriz lógica
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

                if ((row + col) % 2 == 0)
                    cell.setBackground(Color.gray);
                else
                    cell.setBackground(Color.white);

                b[row][col] = cell;
                colocarPiezas(row, col, cell);

                final int r = row, c = col;
                cell.addActionListener(e -> manejarClick(r, c));

                add(cell);
            }
        }
    }

    private void colocarPiezas(int row, int col, JButton cell) {
        if ((row == 0 && (col == 0 || col == 5)) || (row == 5 && (col == 0 || col == 5))) {
            setImage(cell, "/pp2/HL.jpeg");
            piezas[row][col] = new Pieza("Humano Lobo", 5, 5,2) {};
        } else if ((row == 0 && (col == 1 || col == 4)) || (row == 5 && (col == 1 || col == 4))) {
            setImage(cell, "/pp2/Vampiro.jpeg");
            piezas[row][col] = new Pieza("Vampiro", 4, 3,5) {};
        } else if ((row == 0 && (col == 2 || col == 3)) || (row == 5 && (col == 2 || col == 3))) {
            setImage(cell, "/pp2/Necromancer.jpeg");
            piezas[row][col] = new Pieza("Necromancer", 3, 4,1) {};
        }
    }

    private void setImage(JButton boton, String ruta) {
        ImageIcon img = new ImageIcon(getClass().getResource(ruta));
        Image scaled = img.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(scaled));
    }

    private void manejarClick(int row, int col) {
        JButton cell = b[row][col];

        if (selectedRow == -1 && selectedCol == -1) {                               
            if (piezas[row][col] != null) { 
                selectedRow = row;
                selectedCol = col;
                cell.setBackground(Color.GREEN); 
            }
        } else {
            JButton selectedCell = b[selectedRow][selectedCol];

            if (row == selectedRow && col == selectedCol) {
                resetSelection();
                return;
            }

            if (esMovimientoValido(selectedRow, selectedCol, row, col)) {
                moverOAtacar(selectedRow, selectedCol, row, col);
            } else {
                JOptionPane.showMessageDialog(this, "Movimiento no permitido. Solo se puede mover una casilla por turno.");
            }

            resetSelection();
        }
    }

    private boolean esMovimientoValido(int fromRow, int fromCol, int toRow, int toCol) {
        int diffRow = Math.abs(toRow - fromRow);
        int diffCol = Math.abs(toCol - fromCol);
        return diffRow <= 1 && diffCol <= 1;
    }

    private void moverOAtacar(int fromRow, int fromCol, int toRow, int toCol) {
        Pieza atacante = piezas[fromRow][fromCol];
        Pieza objetivo = piezas[toRow][toCol];
        JButton origen = b[fromRow][fromCol];
        JButton destino = b[toRow][toCol];

        if (objetivo == null) {
            
            destino.setIcon(origen.getIcon());
            piezas[toRow][toCol] = atacante;
            piezas[fromRow][fromCol] = null;
            origen.setIcon(null);
        } else {
            // Combate
            atacante.atacar(objetivo);
            JOptionPane.showMessageDialog(this, atacante.getTipo() + " ataca a " + objetivo.getTipo() +
                    " (vida restante: " + objetivo.getVida() + ")");

            if (!objetivo.sinHP()) {
                JOptionPane.showMessageDialog(this, objetivo.getTipo() + " ha muerto!");
                destino.setIcon(origen.getIcon());
                piezas[toRow][toCol] = atacante;
                piezas[fromRow][fromCol] = null;
                origen.setIcon(null);
            }
        }
    }

    private void resetSelection() {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new tablero().setVisible(true));
    }
}
