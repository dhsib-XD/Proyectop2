package pp2;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Reportes extends JFrame {

    private JFrame menuPrincipalFrame;

    public Reportes(JFrame menuPrincipalFrame) {
        // ... (Constructor sin cambios, usando el diseño de fondo negro) ...
        this.menuPrincipalFrame = menuPrincipalFrame;
        
        setSize(600, 500);
        setTitle("Reportes - Vampire Wargame");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        this.getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout(10, 10));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(true); 
        tabbedPane.setBackground(Color.BLACK); 
        tabbedPane.setForeground(Color.WHITE); 

        // --- Pestaña 1: Ranking ---
        JPanel rankingPanel = new JPanel(new BorderLayout());
        rankingPanel.setBackground(Color.BLACK); 
        
        JTextArea rankingArea = new JTextArea();
        rankingArea.setEditable(false);
        rankingArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rankingArea.setOpaque(true);
        rankingArea.setForeground(Color.WHITE); 
        rankingArea.setBackground(Color.BLACK); 
        rankingArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rankingArea.setText(getRankingData());
        
        JScrollPane scrollRanking = new JScrollPane(rankingArea);
        scrollRanking.setBorder(BorderFactory.createEmptyBorder()); 

        rankingPanel.add(scrollRanking, BorderLayout.CENTER);
        tabbedPane.addTab("1. Ranking de Jugadores", rankingPanel);
        
        // --- Pestaña 2: Logs ---
        JPanel logsPanel = new JPanel(new BorderLayout());
        logsPanel.setBackground(Color.BLACK); 
        
        JTextArea logsArea = new JTextArea();
        logsArea.setEditable(false);
        logsArea.setFont(new Font("SansSerif", Font.PLAIN, 14)); 
        logsArea.setOpaque(true);
        logsArea.setForeground(Color.WHITE); 
        logsArea.setBackground(Color.BLACK); 
        logsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logsArea.setText(getMisLogsData()); // <- Llama al método corregido
        
        JScrollPane scrollLogs = new JScrollPane(logsArea);
        scrollLogs.setBorder(BorderFactory.createEmptyBorder()); 

        logsPanel.add(scrollLogs, BorderLayout.CENTER);
        tabbedPane.addTab("2. Log de Mis Últimos Partidos", logsPanel);
        
        // --- 3. BOTÓN VOLVER ---
        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnVolver.setBackground(new Color(60, 60, 60));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);

        btnVolver.addActionListener(e -> {
            menuPrincipalFrame.setVisible(true);
            dispose();
        });
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(btnVolver, BorderLayout.SOUTH);
        
        this.add(mainPanel);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                menuPrincipalFrame.setVisible(true);
            }
        });

        setVisible(true);
    }

    private String getRankingData() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- RANKING DE JUGADORES ---\n\n");
        sb.append(String.format("%-10s %-20s %-10s\n", "POSICIÓN", "USERNAME", "PUNTOS"));
        sb.append("-------------------------------------------\n");

        ArrayList<Player> jugadoresOrdenados = new ArrayList<>(StorageManager.storage.getAllPlayers());
        
        // Asumiendo que GameUtils existe y tiene este método
        GameUtils.mergeSortPlayersByPoints(jugadoresOrdenados);
        
        int posicion = 1;
        for (Player p : jugadoresOrdenados) {
            sb.append(String.format("%-10s %-20s %-10d\n", 
                    posicion + ".", 
                    p.getUsername(), 
                    p.getPuntos()));
            posicion++;
        }
        
        sb.append("\n\n* Cada gane otorga 3 puntos.");
        return sb.toString();
    }

    /**
     * Genera el String para el reporte de Logs del usuario.
     * ¡BUCLE CORREGIDO!
     */
    private String getMisLogsData() {
        StringBuilder sb = new StringBuilder();
        
        Player user = StorageManager.loggedInUser;
        
        if (user == null) {
            return "ERROR: No hay usuario logueado.";
        }
        
        sb.append("--- LOGS DE: " + user.getUsername() + " ---\n");
        sb.append("--- (Del más reciente al más viejo) ---\n\n");

        ArrayList<String> logs = user.getGameLogs();
        
        if (logs.isEmpty()) {
            sb.append("No has finalizado ningún partido todavía.");
            return sb.toString();
        }

        // --- BUCLE CORREGIDO ---
        // Itera desde el inicio (índice 0, que es el más reciente)
        for (String log : logs) {
            sb.append("• " + log + "\n");
        }
        // -------------------------
        
        return sb.toString();
    }
}