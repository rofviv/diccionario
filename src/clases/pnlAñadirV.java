package clases;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class pnlAñadirV extends javax.swing.JPanel {

    ImageIcon marco;

    public pnlAñadirV() {
        initComponents();
    }

    @Override
    public void paintComponent(Graphics g) {
        Dimension dim = getSize();
        if (marco == null) {
            marco = new ImageIcon(getClass().getResource("/imagenes/fondoV.jpg"));
        }
        g.drawImage(marco.getImage(), 0, 0, dim.width, dim.height, null);
        setOpaque(false);
        super.paintComponent(g);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 146, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 337, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
