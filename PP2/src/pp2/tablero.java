package pp2;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class tablero extends JFrame {
    
    // --- CLASE FINAL REQUERIDA ---
    final class GameUtils {
        public static final int BOARD_SIZE = 6;
    }

    private JButton[][] b = new JButton[GameUtils.BOARD_SIZE][GameUtils.BOARD_SIZE];
    private Pieza[][] piezas = new Pieza[GameUtils.BOARD_SIZE][GameUtils.BOARD_SIZE];
    private int selectedRow = -1;
    private int selectedCol = -1;

    private ArrayList<Pieza> t1 = new ArrayList<>();
    private ArrayList<Pieza> t2 = new ArrayList<>();
    
    private int currentPlayer = 1;
    private final int initialPieces = 6; 
    
    private ArrayList<String> piezasPermitidas = new ArrayList<>();
    
   
    private Player player1;
    private Player player2;

    public tablero() {
        
        this.player1 = StorageManager.loggedInUser; // Asume J1
        this.player2 = StorageManager.player2; // Asume J2

        if (player1 == null || player2 == null) {
            JOptionPane.showMessageDialog(null, "Error: No se han cargado los jugadores.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
            new MainMenu().setVisible(true);
            dispose();
            return;
        }

        setTitle("Vampire Wargame: " + player1.getUsername() + " vs " + player2.getUsername());
        setSize(800, 700); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        setLayout(new BorderLayout());

        
        JPanel panelTablero = new JPanel(new GridLayout(GameUtils.BOARD_SIZE, GameUtils.BOARD_SIZE));
        
        for (int row = 0; row < GameUtils.BOARD_SIZE; row++) {
            for (int col = 0; col < GameUtils.BOARD_SIZE; col++) {
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
                panelTablero.add(cell); 
            }
        }
        
      
        add(panelTablero, BorderLayout.CENTER);

        
        JButton btnRendirse = new JButton("Rendirme");
        btnRendirse.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnRendirse.setBackground(new Color(150, 0, 0));
        btnRendirse.setForeground(Color.WHITE);
        btnRendirse.setFocusPainted(false);
        btnRendirse.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro que quieres rendirte?\nPerderás la partida.",
                    "Confirmar Rendición", JOptionPane.YES_NO_OPTION);
            
            if (resp == JOptionPane.YES_OPTION) {
                if (currentPlayer == 1) {
                   
                    finalizarJuego(player2, player1, "Rendición");
                } else {
                   
                    finalizarJuego(player1, player2, "Rendición");
                }
            }
        });
        
       
        add(btnRendirse, BorderLayout.SOUTH);
        
        
        SwingUtilities.invokeLater(() -> iniciarTurno());
    }
    
    // 

    private void iniciarTurno() {
        piezasPermitidas.clear(); 
        resetSelection();
        
        ArrayList<Pieza> equipoActual = (currentPlayer == 1) ? t1 : t2;
        int piezasPerdidas = initialPieces - equipoActual.size();
        
        int numGiros = 1;
        if (piezasPerdidas >= 4) numGiros = 3;
        else if (piezasPerdidas >= 2) numGiros = 2;
        
        String mensajeGiros = "Turno del Jugador " + currentPlayer + " (" + ((currentPlayer == 1) ? player1.getUsername() : player2.getUsername()) + ")\n"
                + "Tienes " + numGiros + " giro(s).\n";
        
        boolean puedeMoverZombie = false;

        for (int i = 0; i < numGiros; i++) {
            String pieza = girarRuletaUnaVez();
            if (pieza != null) {
                if (!piezasPermitidas.contains(pieza)) {
                    piezasPermitidas.add(pieza);
                }
                mensajeGiros += "Giro " + (i+1) + ": " + pieza + "\n";
                
                if (pieza.equals("Necromancer")) {
                    if (!piezasPermitidas.contains("Zombie")) {
                        piezasPermitidas.add("Zombie");
                    }
                    puedeMoverZombie = true;
                }
            }
        }
        
        if (puedeMoverZombie) {
            mensajeGiros += "¡También puedes atacar con tus Zombies!\n";
        }
        
        if (!jugadorTienePiezasPermitidas(equipoActual, piezasPermitidas)) {
            JOptionPane.showMessageDialog(this, mensajeGiros + "\nNo tienes ninguna de las piezas seleccionadas. Pierdes el turno.");
            cambiarTurno();
        } else {
             JOptionPane.showMessageDialog(this, mensajeGiros + "\n¡Mueve tu pieza!");
        }
    }

    private String girarRuletaUnaVez() {
        JDialog dialog = new JDialog(this, "Gira la Ruleta", true); 
        Ruleta ruletaPanel = new Ruleta();
        
        dialog.setLayout(new BorderLayout());
        dialog.add(ruletaPanel, BorderLayout.CENTER);
        
        JLabel infoLabel = new JLabel("Haz clic en la ruleta para detener", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dialog.add(infoLabel, BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this); 
        
        ruletaPanel.iniciarRuleta(); 
        dialog.setVisible(true); 
        
        return ruletaPanel.getPiezaSeleccionada();
    }
    
    private void cambiarTurno() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        iniciarTurno();
    }
    
    private void celdas(int row, int col) {
        JButton cell = b[row][col];

        if (selectedRow == -1 && selectedCol == -1) {
            Pieza piezaSeleccionada = piezas[row][col];
            
            if (piezaSeleccionada == null) return; 

            if (!esDelJugadorActual(piezaSeleccionada)) {
                JOptionPane.showMessageDialog(this, "No es tu pieza.");
                return;
            }
            
            if (!piezasPermitidas.contains(piezaSeleccionada.getTipo())) {
                JOptionPane.showMessageDialog(this, 
                    "No puedes mover esta pieza.\nPiezas permitidas este turno: " + piezasPermitidas.toString());
                return;
            }
            
            selectedRow = row;
            selectedCol = col;
            cell.setBackground(Color.GREEN);
            
        } else {
            Pieza seleccionada = piezas[selectedRow][selectedCol];

            if (row == selectedRow && col == selectedCol) {
                resetSelection();
                return;
            }
            
            if (seleccionada instanceof NecroMancer || seleccionada instanceof Vampiro) {
                
                if (seleccionada instanceof NecroMancer) {
                     String[] opciones = {"Ataque cercano/Moverse", "Ataque lejano", "Invocar Zombie", "Cancelar"};
                     int eleccion = JOptionPane.showOptionDialog(
                            this, "¿Qué desea hacer el Necromancer?", "Acción del Necromancer",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]
                     );

                     if (eleccion == 0) {
                         if (mvalido(selectedRow, selectedCol, row, col)) {
                             moverOAtacar(selectedRow, selectedCol, row, col);
                             cambiarTurno(); 
                         } else {
                             JOptionPane.showMessageDialog(this, "Movimiento/Ataque Cercano (Rango 1) no permitido.");
                             resetSelection();
                         }
                     } else if (eleccion == 1) {
                         int diffRow = Math.abs(row - selectedRow);
                         int diffCol = Math.abs(col - selectedCol);
                         
                         if (piezas[row][col] != null && !equipos(seleccionada, piezas[row][col]) && 
                            (diffRow == 2 && diffCol == 0 || diffRow == 0 && diffCol == 2 || diffRow == 2 && diffCol == 2)) {
                             atacarLejano(seleccionada, row, col);
                             cambiarTurno(); 
                         } else {
                              JOptionPane.showMessageDialog(this, "Ataque lejano: No hay objetivo válido a 2 casillas H/V/D exactas.");
                              resetSelection();
                         }
                     } else if (eleccion == 2) {
                         if (piezas[row][col] == null) { 
                              invocarZombie(seleccionada, row, col);
                              cambiarTurno(); 
                         } else {
                              JOptionPane.showMessageDialog(this, "La invocación debe ser en una casilla vacía.");
                              resetSelection();
                         }
                     } else {
                         resetSelection(); 
                     }

                } else if (seleccionada instanceof Vampiro) {
                    String[] opciones = {"Ataque normal/Moverse", "Chupar sangre", "Cancelar"};
                    int eleccion = JOptionPane.showOptionDialog(
                            this, "¿Qué desea hacer el Vampiro?", "Acción del Vampiro",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]
                    );

                    if (eleccion == 0) {
                        if (mvalido(selectedRow, selectedCol, row, col)) {
                            moverOAtacar(selectedRow, selectedCol, row, col);
                            cambiarTurno(); 
                        } else {
                            JOptionPane.showMessageDialog(this, "Movimiento o ataque no permitido (máximo rango 1).");
                            resetSelection();
                        }
                    } else if (eleccion == 1) {
                        int diffRow = Math.abs(row - selectedRow);
                        int diffCol = Math.abs(col - selectedCol);
                        if (diffRow <= 1 && diffCol <= 1 && diffRow + diffCol > 0 && piezas[row][col] != null && !equipos(seleccionada, piezas[row][col])) {
                            chuparSangre(selectedRow, selectedCol, row, col);
                            cambiarTurno(); 
                        } else {
                            JOptionPane.showMessageDialog(this, "Chupar sangre solo es válido contra enemigos adyacentes (rango 1).");
                            resetSelection();
                        }
                    } else {
                        resetSelection(); 
                    }
                }
                
                return; 
            }

            if (mvalido(selectedRow, selectedCol, row, col)) {
                moverOAtacar(selectedRow, selectedCol, row, col);
                cambiarTurno(); 
            } else {
                JOptionPane.showMessageDialog(this, "Movimiento o ataque no permitido.");
                resetSelection();
            }
        }
    }

    

    private void atacarLejano(Pieza necro, int row, int col) {
        Pieza objetivo = piezas[row][col];
        ((NecroMancer)necro).ataqueLanza(objetivo);
        
        JOptionPane.showMessageDialog(this, "Ataque lejano: " + objetivo.getTipo() + 
                " ahora tiene " + objetivo.getVida() + " de vida y " + objetivo.getEscudo() + " de escudo.");
        
        if (objetivo.getVida() <= 0) {
            eliminarPieza(objetivo, row, col);
        }
    }

    private void invocarZombie(Pieza necro, int row, int col) {
        Zombie zombie = ((NecroMancer)necro).invocar();
        piezas[row][col] = zombie;
        setImage(b[row][col], "/pp2/Zombie.jpeg");
        
        if (t1.contains(necro)) t1.add(zombie);
        else if (t2.contains(necro)) t2.add(zombie);
        
        JOptionPane.showMessageDialog(this, "Zombie invocado");
    }

    private void chuparSangre(int fromRow, int fromCol, int toRow, int toCol) {
        Pieza vampiro = piezas[fromRow][fromCol];
        Pieza objetivo = piezas[toRow][toCol];
        
        ((Vampiro)vampiro).chuparSangre(objetivo);
        
        JOptionPane.showMessageDialog(this, "Vampiro chupó sangre. Objetivo: Vida " + objetivo.getVida() + 
            ", Escudo " + objetivo.getEscudo() + ". Vampiro: Vida " + vampiro.getVida());
        
        if (objetivo.getVida() <= 0) {
            eliminarPieza(objetivo, toRow, toCol);
        }
    }

    private void moverOAtacar(int fromRow, int fromCol, int toRow, int toCol) {
        Pieza atacante = piezas[fromRow][fromCol];
        Pieza objetivo = piezas[toRow][toCol];

        if (objetivo == null) {
            b[toRow][toCol].setIcon(b[fromRow][fromCol].getIcon());
            piezas[toRow][toCol] = atacante;
            piezas[fromRow][fromCol] = null;
            b[fromRow][fromCol].setIcon(null);
            return;
        }

        if (equipos(atacante, objetivo)) {
            JOptionPane.showMessageDialog(this, "No puedes atacar a tu mismo equipo");
            return;
        }

        atacante.atacar(objetivo);
        
        JOptionPane.showMessageDialog(this,
                atacante.getTipo() + " ataca a " + objetivo.getTipo() +
                        "\nVida restante: " + objetivo.getVida() +
                        "\nEscudo restante: " + objetivo.getEscudo());

        if (objetivo.getVida() <= 0) {
            eliminarPieza(objetivo, toRow, toCol);
        }
    }
    
    // --- MÉTODOS HELPER (MODIFICADOS Y NUEVOS) ---

    private void eliminarPieza(Pieza pieza, int row, int col) {
        piezas[row][col] = null;
        b[row][col].setIcon(null);
        
        if (t1.contains(pieza)) {
            t1.remove(pieza);
        } else if (t2.contains(pieza)) {
            t2.remove(pieza);
        }
        
        if (t1.isEmpty()) {
            finalizarJuego(player2, player1, "Victoria");
        }
        if (t2.isEmpty()) {
            finalizarJuego(player1, player2, "Victoria");
        }
    }
    
    private void finalizarJuego(Player ganador, Player perdedor, String tipoResultado) {
        ganador.addPuntos(3);
        
        String mensajePopup = "";
        String logGanador = "";
        String logPerdedor = "";

        if (tipoResultado.equals("Victoria")) {
            mensajePopup = "JUGADOR " + ganador.getUsername().toUpperCase() + " VENCIO A JUGADOR " + perdedor.getUsername().toUpperCase() +
                           ", FELICIDADES HAS GANADO 3 PUNTOS";
            logGanador = mensajePopup;
            logPerdedor = "PERDISTE CONTRA JUGADOR " + ganador.getUsername().toUpperCase() + " (Eliminación)";

        } else if (tipoResultado.equals("Rendición")) {
            mensajePopup = "JUGADOR " + perdedor.getUsername().toUpperCase() + " SE HA RETIRADO, FELICIDADES JUGADOR " +
                           ganador.getUsername().toUpperCase() + ", HAS GANADO 3 PUNTOS";
            logGanador = mensajePopup;
            logPerdedor = "TE RETIRASTE CONTRA JUGADOR " + ganador.getUsername().toUpperCase();
        }
        
        ganador.addGameLog(logGanador);
        perdedor.addGameLog(logPerdedor);
        
        

        JOptionPane.showMessageDialog(this, mensajePopup, "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
        new MainMenu().setVisible(true);
    }
    
    private boolean esDelJugadorActual(Pieza p) {
        if (p == null) return false;
        if (currentPlayer == 1 && t1.contains(p)) return true;
        if (currentPlayer == 2 && t2.contains(p)) return true;
        return false;
    }
    
    private boolean jugadorTienePiezasPermitidas(ArrayList<Pieza> equipo, ArrayList<String> permitidas) {
        for (Pieza p : equipo) {
            if (permitidas.contains(p.getTipo())) {
                return true; 
            }
        }
        return false; 
    }
    
    

    private void colocarPiezas(int row, int col, JButton cell) {
        Pieza pieza = null;

        if (row == 0) { 
            if (col == 0 || col == 5) pieza = new HombreLobo();
            else if (col == 1 || col == 4) pieza = new Vampiro();
            else if (col == 2 || col == 3) pieza = new NecroMancer();
            
            if (pieza != null) {
                t1.add(pieza);
                
                if (pieza instanceof HombreLobo) setImage(cell, "/pp2/HL.jpg");
                else if (pieza instanceof Vampiro) setImage(cell, "/pp2/Vampiro.jpg");
                else if (pieza instanceof NecroMancer) setImage(cell, "/pp2/Necromancer.jpg");
            }
        }

        if (row == 5) { 
            if (col == 0 || col == 5) pieza = new HombreLobo();
            else if (col == 1 || col == 4) pieza = new Vampiro();
            else if (col == 2 || col == 3) pieza = new NecroMancer();
            
            if (pieza != null) {
                t2.add(pieza);
                
                if (pieza instanceof HombreLobo) setImage(cell, "/pp2/HL2.jpg");
                else if (pieza instanceof Vampiro) setImage(cell, "/pp2/Vampiro2.jpg");
                else if (pieza instanceof NecroMancer) setImage(cell, "/pp2/Necromancer2.jpg");
            }
        }

        piezas[row][col] = pieza;
    }

    private void setImage(JButton boton, String ruta) {
        try {
            ImageIcon img = new ImageIcon(getClass().getResource(ruta));
            Image scaled = img.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + ruta);
            if (ruta != null && ruta.length() > 6) {
                // Muestra las iniciales si la imagen falla
                boton.setText(ruta.substring(4, 6)); 
            }
        }
    }

    private boolean equipos(Pieza a, Pieza b) {
        if (a == null || b == null) return false;
        if (t1.contains(a) && t1.contains(b)) return true;
        if (t2.contains(a) && t2.contains(b)) return true;
        return false;
    }

    private boolean mvalido(int fromRow, int fromCol, int toRow, int toCol) {
        Pieza pieza = piezas[fromRow][fromCol];
        
        int diffRow = Math.abs(toRow - fromRow);
        int diffCol = Math.abs(toCol - fromCol);
        
        if (diffRow == 0 && diffCol == 0) return false; 
        
        if (pieza instanceof HombreLobo) {
            boolean esMovimientoTipoCaballo = 
                (diffRow == 1 && diffCol == 2) || 
                (diffRow == 2 && diffCol == 1);
            
            if (esMovimientoTipoCaballo) return false; 
            
            if (piezas[toRow][toCol] == null) {
                return diffRow <= 2 && diffCol <= 2;
            }
            
            return diffRow <= 1 && diffCol <= 1; 
        }

        if (pieza instanceof Zombie) {
            if (piezas[toRow][toCol] == null) {
                return false; 
            }
            return diffRow <= 1 && diffCol <= 1;
        }
        
        int rangoMovimiento = 1; 
        
        if (piezas[toRow][toCol] == null) {
            return diffRow <= rangoMovimiento && diffCol <= rangoMovimiento;
        } 
        
        return diffRow <= 1 && diffCol <= 1;
    }

    private final void resetSelection() {
        for (int r = 0; r < GameUtils.BOARD_SIZE; r++)
            for (int c = 0; c < GameUtils.BOARD_SIZE; c++)
                b[r][c].setBackground((r+c)%2==0? Color.GRAY: Color.WHITE);
        selectedRow = -1; selectedCol = -1;
    }
}