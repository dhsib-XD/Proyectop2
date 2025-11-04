package pp2;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class tablero extends JFrame {
    private JButton[][] b = new JButton[6][6];
    private Pieza[][] piezas = new Pieza[6][6];
    private int selectedRow = -1;
    private int selectedCol = -1;

    private ArrayList<Pieza> t1 = new ArrayList<>();
    private ArrayList<Pieza> t2 = new ArrayList<>();

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
                cell.addActionListener(e -> celdas(r, c));
                add(cell);
            }
        }
    }

    private void colocarPiezas(int row, int col, JButton cell) {
        Pieza pieza = null;

        if (row == 0) {
            if (col == 0 || col == 5) {
                pieza = new HombreLobo();
                setImage(cell, "/pp2/HL.jpeg");
            } else if (col == 1 || col == 4) {
                pieza = new Vampiro();
                setImage(cell, "/pp2/Vampiro.jpeg");
            } else if (col == 2 || col == 3) {
                pieza = new NecroMancer();
                setImage(cell, "/pp2/Necromancer.jpeg");
            }
            if (pieza != null) t1.add(pieza);
        }

        if (row == 5) {
            if (col == 0 || col == 5) {
                pieza = new HombreLobo();
                setImage(cell, "/pp2/HL.jpeg");
            } else if (col == 1 || col == 4) {
                pieza = new Vampiro();
                setImage(cell, "/pp2/Vampiro.jpeg");
            } else if (col == 2 || col == 3) {
                pieza = new NecroMancer();
                setImage(cell, "/pp2/Necromancer.jpeg");
            }
            if (pieza != null) t2.add(pieza);
        }

        piezas[row][col] = pieza;
    }

    private void setImage(JButton boton, String ruta) {
        ImageIcon img = new ImageIcon(getClass().getResource(ruta));
        Image scaled = img.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(scaled));
    }

    private void celdas(int row, int col) {
    JButton cell = b[row][col];

    // Seleccionar pieza
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

        Pieza seleccionada = piezas[selectedRow][selectedCol];

        // Si la pieza seleccionada es un Necromancer y la celda destino está vacía
        if (seleccionada instanceof NecroMancer && piezas[row][col] == null) {
            String[] opciones = {"Mover", "Invocar Zombie"};
            int decision = JOptionPane.showOptionDialog(this,
                    "¿Qué quiere hacer el Necromancer?",
                    "Acción Necromancer",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (decision == 0) {
                // Mover
                if (mvalido(selectedRow, selectedCol, row, col)) {
                    moverOAtacar(selectedRow, selectedCol, row, col);
                } else {
                    JOptionPane.showMessageDialog(this, "Movimiento no permitido.");
                }
            } else if (decision == 1) {
                // Invocar Zombie
                moverOAtacar(selectedRow, selectedCol, row, col); // moverOAtacar detecta que es Necro y hace invocación
            }

            resetSelection();
            return;
        }

        // Para otras piezas o ataque normal
        if (mvalido(selectedRow, selectedCol, row, col)) {
            moverOAtacar(selectedRow, selectedCol, row, col);
        } else {
            JOptionPane.showMessageDialog(this, "Movimiento o ataque no permitido.");
        }

        resetSelection();
    }
}

   private void moverOAtacar(int fromRow, int fromCol, int toRow, int toCol) {
    Pieza atacante = piezas[fromRow][fromCol];
    Pieza objetivo = piezas[toRow][toCol];

    // 🔹 Si la celda destino está vacía
    if (objetivo == null) {

        // Si el atacante es un Necromancer → puede invocar un Zombie
        if (atacante instanceof NecroMancer) {
            NecroMancer necro = (NecroMancer) atacante;
            Zombie zombie = necro.invocar();

            piezas[toRow][toCol] = zombie;
            setImage(b[toRow][toCol], "/pp2/Zombie.jpeg"); // imagen para el zombie

            // Asociar Zombie al equipo del Necromancer
            if (t1.contains(atacante)) t1.add(zombie);
            else if (t2.contains(atacante)) t2.add(zombie);

            System.out.println("Zombie invocado en (" + toRow + "," + toCol + ")");
            return;
        }

       
        b[toRow][toCol].setIcon(b[fromRow][fromCol].getIcon());
        piezas[toRow][toCol] = atacante;
        piezas[fromRow][fromCol] = null;
        b[fromRow][fromCol].setIcon(null);
        System.out.println("Pieza movida de (" + fromRow + "," + fromCol + ") a (" + toRow + "," + toCol + ")");
        return;
    }

    // 🔹 Si es del mismo equipo, no atacar
    if (equipos(atacante, objetivo)) {
        JOptionPane.showMessageDialog(this, "No puedes atacar a una pieza de tu mismo equipo.");
        return;
    }

    // 🔹 Atacar
    atacante.atacar(objetivo);

    JOptionPane.showMessageDialog(this,
            atacante.getTipo() + " ataca a " + objetivo.getTipo() + "\n" +
            "Vida restante: " + objetivo.getVida() + "\n" +
            "Escudo restante: " + objetivo.getEscudo());

    // 🔹 Si el objetivo fue derrotado → eliminarlo, pero NO moverse
    if (objetivo.getVida() <= 0) {
        JOptionPane.showMessageDialog(this, objetivo.getTipo() + " ha sido derrotado!");
        b[toRow][toCol].setIcon(null);
        piezas[toRow][toCol] = null;
        System.out.println(atacante.getTipo() + " derrotó a " + objetivo.getTipo() + " y se queda en su lugar.");
    }
}


    private boolean equipos(Pieza a, Pieza b) {
        if (a == null || b == null) return false;
        if (t1.contains(a) && t1.contains(b)) {
            return true;
        }
        if (t2.contains(a) && t2.contains(b)){
            return true;
        }
        return false;
    }

   
    private boolean mvalido(int fromRow, int fromCol, int toRow, int toCol) {
        Pieza pieza = piezas[fromRow][fromCol];
        int diffRow = Math.abs(toRow - fromRow);
        int diffCol = Math.abs(toCol - fromCol);

        int rangoMovimiento = 1;
        int rangoAtaque = 1;

        if (pieza instanceof HombreLobo) {
            rangoMovimiento = 2;
        } else if (pieza instanceof NecroMancer) {
            rangoAtaque = 2;
        }

        // Movimiento normal
        if (piezas[toRow][toCol] == null) {
            return diffRow <= rangoMovimiento && diffCol <= rangoMovimiento;
        }

        // Ataque
        return diffRow <= rangoAtaque && diffCol <= rangoAtaque;
    }

    private void resetSelection() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if ((r + c) % 2 == 0)
                    b[r][c].setBackground(Color.gray);
                else
                    b[r][c].setBackground(Color.white);
            }
        }
        selectedRow = -1;
        selectedCol = -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new tablero().setVisible(true));
    }
}
