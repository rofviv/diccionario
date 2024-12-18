package presentacion;

import negocio.NPalabra;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class frmEditarPalabra extends javax.swing.JFrame {

    NPalabra np = new NPalabra();
    ResultSet rs;
    String palabra;

    public void asignarPalabra(String p) {
        txtPalabra.setText(p);
        this.palabra = p;
    }

    public int obtenerID_P() {
        rs = np.obtenerID_palabra(palabra);
        int id_p = 0;
        try {
            rs.next();
            id_p = Integer.parseInt(rs.getString(1));
        } catch (SQLException | NumberFormatException e) {
        }
        return id_p;
    }

    public void editarPalabra(String p) {
        np.editarPalabra(obtenerID_P(), p);
    }

    private String primeraMayuscula(String txt) {
        return txt.substring(0, 1).toUpperCase() + txt.substring(1);
    }

    public frmEditarPalabra() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtPalabra = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Editar una palabra");

        txtPalabra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPalabraKeyTyped(evt);
            }
        });

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(txtPalabra, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton1)))
                .addContainerGap(246, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPalabra, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(188, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        editarPalabra(txtPalabra.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPalabraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPalabraKeyTyped
        if (txtPalabra.getText().length() > 0) {
            if (!(evt.getKeyChar() < 'a' || evt.getKeyChar() > 'z')) {
                txtPalabra.setText(primeraMayuscula(txtPalabra.getText()));
            } else {
                evt.consume();
                getToolkit().beep();
            }
        }
    }//GEN-LAST:event_txtPalabraKeyTyped

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new frmEditarPalabra().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtPalabra;
    // End of variables declaration//GEN-END:variables
}
