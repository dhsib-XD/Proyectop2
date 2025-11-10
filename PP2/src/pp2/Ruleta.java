package pp2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ruleta extends JPanel {
    private String[] piezas = {"Hombre Lobo", "Vampiro", "Necromancer",
                             "Hombre Lobo", "Vampiro", "Necromancer"};
    private Color[] colores = {Color.DARK_GRAY, new Color(139,0,0), new Color(50,50,50)};
    private int index = 0;
    private Timer timer;
    private String piezaSeleccionada = null;
    
    // No usamos un JFrame estático. El tablero creará un JDialog.

    public Ruleta() {
        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Detener solo si el timer está corriendo
                if (timer != null && timer.isRunning()) {
                    detenerRuleta();
                }
            }
        });
    }

    public void iniciarRuleta() {
        if (timer != null && timer.isRunning()) return;

        timer = new Timer(50, e -> {
            index = (index + 1) % piezas.length;
            repaint();
        });
        timer.start();
    }

    private void detenerRuleta() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            piezaSeleccionada = piezas[index];
            
            // Cierra la ventana (JDialog) que contiene este panel
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w != null) {
                w.dispose(); // Cierra el JDialog
            }
        }
    }

    public String getPiezaSeleccionada() {
        return piezaSeleccionada;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) - 20;
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = size / 2;
        
        int x = centerX - radius;
        int y = centerY - radius;
        
        int total = piezas.length;
        int arc = 360 / total;

        int rotacion = 90 - (index * arc + arc / 2);
        
        for (int i = 0; i < total; i++) {
            Color colorRelleno;
            if (piezas[i].equals("Hombre Lobo")) colorRelleno = colores[0];
            else if (piezas[i].equals("Vampiro")) colorRelleno = colores[1];
            else colorRelleno = colores[2];
            
            g2d.setColor(colorRelleno);
            
            int startAngle = i * arc + rotacion;
            g2d.fillArc(x, y, size, size, startAngle, arc);
        }

        int fixedSegmentIndex = 0; 
        int borderStartAngle = fixedSegmentIndex * arc + rotacion;

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawArc(x, y, size, size, borderStartAngle, arc);
        g2d.setStroke(new BasicStroke(1)); 

        g2d.setColor(Color.YELLOW);
        
        int indicatorHeight = 25;
        int indicatorWidth = 15;
        
        int[] xPoints = {centerX - indicatorWidth, centerX, centerX + indicatorWidth};
        int[] yPoints = {0, indicatorHeight, 0};
        
        g2d.fillPolygon(xPoints, yPoints, 3);
        
        g2d.setColor(Color.BLACK);
        g2d.fillOval(centerX - 5, centerY - 5, 10, 10);
    }
}