package presentacion;

import javax.swing.table.DefaultTableModel;
import negocio.NPalabra;
import java.sql.ResultSet;
import clases.AutoCompletar;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class frmAñadirVocablo extends javax.swing.JFrame {

    DefaultTableModel model = new DefaultTableModel();
    NPalabra np = new NPalabra();
    ResultSet rs;
    AutoCompletar completer;

    public void cargarTitulos() {
        String[] titulos = {"Nombre"};
        for (short i = 0; i < titulos.length; i++) {
            model.addColumn(titulos[i]);
        }
        tblPalabras.setModel(model);
    }

    public void cargarTabla() {
        cargarTitulos();
        rs = np.mostrar_palabra();
        llenarTabla(rs);
    }

    private void llenarTabla(ResultSet rs) {
        Object[] mostrar = new Object[1];
        try {
            while (rs.next()) {
                mostrar[0] = rs.getString(2);
                model.addRow(mostrar);
            }
            tblPalabras.setModel(model);
        } catch (Exception e) {
            System.err.println("Error al cargar la tabla " + e.getMessage());
        }
    }

    public void asignarNombrePalabra(String nombre) {
        lblTitulo.setText(nombre);
    }

    private int palabraSeleccionada() {
        rs = np.obtenerID_palabra(txtBuscar.getText());
        int y = 0;
        try {
            while (rs.next()) {
                y = Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException | NumberFormatException e) {
        }
        return y;
    }

    private void guardarPalabra(String palabra) {
        String msj = "";
        if (rbSinonimos.isSelected() == true) {
            guardarSinonimo(palabra, palabraSeleccionada());
            msj = "Se añadio un sinonimo";
        } else if (rbFamilia.isSelected() == true) {
            guardarFamilia(palabra, palabraSeleccionada());
            msj = "Se añadio una familia";
        } else if (rbParonimos.isSelected() == true) {
            guardarParonimo(palabra, palabraSeleccionada());
            msj = "Se añadio un paronimo";
        } else if (rbAntonimos.isSelected() == true) {
            guardarAntonimo(palabra, palabraSeleccionada());
            msj = "Se añadio un antonimo";
        }
        JOptionPane.showMessageDialog(this, msj);
    }

    private int id_palabra(String palabra) {
        rs = np.obtenerID_palabra(palabra);
        int id_p = 0;
        try {
            rs.next();
            id_p = Integer.parseInt(rs.getString(1));
        } catch (SQLException | NumberFormatException e) {
        }
        return id_p;
    }

    public void guardarSinonimo(String palabra, int id_s) {
        np.insertarSinonimo(id_palabra(palabra), id_s);
    }

    public void guardarFamilia(String palabra, int id_f) {
        np.insertarFamilia(id_palabra(palabra), id_f);
    }

    public void guardarParonimo(String palabra, int id_paro) {
        np.insertarParonimo(id_palabra(palabra), id_paro);
    }

    public void guardarAntonimo(String palabra, int id_a) {
        np.insertarAntonimo(id_palabra(palabra), id_a);
    }

    public frmAñadirVocablo() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/icono.png")).getImage());
        completer = new AutoCompletar(txtBuscar);
        cargarTabla();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlAñadirV1 = new clases.pnlAñadirV();
        jPanel2 = new javax.swing.JPanel();
        rbSinonimos = new javax.swing.JRadioButton();
        rbAntonimos = new javax.swing.JRadioButton();
        rbParonimos = new javax.swing.JRadioButton();
        rbFamilia = new javax.swing.JRadioButton();
        btnAñadir = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPalabras = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Añadir", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Iskoola Pota", 1, 20))); // NOI18N
        jPanel2.setOpaque(false);

        buttonGroup1.add(rbSinonimos);
        rbSinonimos.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        rbSinonimos.setText("Sinonimos");
        rbSinonimos.setOpaque(false);

        buttonGroup1.add(rbAntonimos);
        rbAntonimos.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        rbAntonimos.setText("Antonimos");
        rbAntonimos.setOpaque(false);

        buttonGroup1.add(rbParonimos);
        rbParonimos.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        rbParonimos.setText("Paronimos");
        rbParonimos.setOpaque(false);

        buttonGroup1.add(rbFamilia);
        rbFamilia.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        rbFamilia.setText("Familia");
        rbFamilia.setOpaque(false);

        btnAñadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add2.png"))); // NOI18N
        btnAñadir.setToolTipText("Añadir");
        btnAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbSinonimos)
                    .addComponent(rbAntonimos)
                    .addComponent(rbParonimos)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnAñadir)
                        .addComponent(rbFamilia)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(rbSinonimos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbAntonimos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbParonimos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbFamilia)
                .addGap(26, 26, 26)
                .addComponent(btnAñadir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/volver.png"))); // NOI18N
        btnCerrar.setToolTipText("Volver");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Iskoola Pota", 1, 20))); // NOI18N
        jPanel1.setOpaque(false);

        tblPalabras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblPalabras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPalabrasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPalabras);

        txtBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Nombre de la palabra");

        javax.swing.GroupLayout pnlAñadirV1Layout = new javax.swing.GroupLayout(pnlAñadirV1);
        pnlAñadirV1.setLayout(pnlAñadirV1Layout);
        pnlAñadirV1Layout.setHorizontalGroup(
            pnlAñadirV1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAñadirV1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(pnlAñadirV1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAñadirV1Layout.createSequentialGroup()
                        .addComponent(btnCerrar)
                        .addGap(43, 43, 43)))
                .addGap(20, 20, 20))
            .addGroup(pnlAñadirV1Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlAñadirV1Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAñadirV1Layout.setVerticalGroup(
            pnlAñadirV1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAñadirV1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(pnlAñadirV1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAñadirV1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCerrar)))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlAñadirV1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlAñadirV1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(509, 549));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirActionPerformed
        guardarPalabra(lblTitulo.getText());
    }//GEN-LAST:event_btnAñadirActionPerformed

    private void tblPalabrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPalabrasMouseClicked
        txtBuscar.setText((tblPalabras.getValueAt(tblPalabras.getSelectedRow(), 0)).toString());
    }//GEN-LAST:event_tblPalabrasMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        completer.txtReiniciar();
        completer.txtPredictivo(true);
        txtBuscar.setText("");
    }//GEN-LAST:event_formWindowActivated

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new frmAñadirVocablo().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadir;
    private javax.swing.JButton btnCerrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTitulo;
    private clases.pnlAñadirV pnlAñadirV1;
    private javax.swing.JRadioButton rbAntonimos;
    private javax.swing.JRadioButton rbFamilia;
    private javax.swing.JRadioButton rbParonimos;
    private javax.swing.JRadioButton rbSinonimos;
    private javax.swing.JTable tblPalabras;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
