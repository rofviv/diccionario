package presentacion;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import negocio.NPalabra;

@SuppressWarnings("serial")
public class frmAñadirPalabra extends javax.swing.JFrame {

    frmAñadirVocablo añadir = new frmAñadirVocablo();
    NPalabra np = new NPalabra();
    ResultSet rs;

    @SuppressWarnings("unchecked")
    public void llenarTipos() {
        rs = np.mostrarTipo();
        try {
            while (rs.next()) {
                cboTipo.addItem(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }

    public void guardarPalabra() {
        np.insertarPalabra(txtPalabra.getText().trim());
        rs = np.obtenerID_palabra(txtPalabra.getText());
        int id_p = 0;
        try {
            rs.next();
            id_p = Integer.parseInt(rs.getString(1));
        } catch (SQLException | NumberFormatException e) {
        }
        np.insertarSignificado(id_p, cboTipo.getSelectedIndex(), txtSignificado.getText());
    }

    public String validarCampos() {
        if ("".equals(txtPalabra.getText())) {
            return "Debe escribir el nombre de la palabra";
        } else if (cboTipo.getSelectedIndex() == -1 || cboTipo.getSelectedIndex() == 0) {
            return "Debe seleccionar una categoria.";
        } else if ("".equals(txtSignificado.getText())) {
            return "Debe insertar como mínimo un significado.";
        }
        return "";
    }

    public void habilitar() {
        txtPalabra.requestFocus();
        txtPalabra.setText("");
    }

    public void limpiar() {
        cboTipo.setSelectedIndex(0);
        txtSignificado.setText("");
        lblContador.setText("0/150");
    }

    private String primeraMayuscula(String txt) {
        return txt.substring(0, 1).toUpperCase() + txt.substring(1);
    }

    public frmAñadirPalabra() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/icono.png")).getImage());
        this.setLocationRelativeTo(null);
        txtSignificado.setLineWrap(true);
        txtSignificado.setWrapStyleWord(true);
        llenarTipos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFondo1 = new clases.pnlFondo();
        btnGuardar = new javax.swing.JButton();
        btnAñadirVocablo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSignificado = new javax.swing.JTextArea();
        btnCancelar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cboTipo = new javax.swing.JComboBox();
        txtPalabra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        lblContador = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Diccionario");

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnAñadirVocablo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/vocablo.png"))); // NOI18N
        btnAñadirVocablo.setToolTipText("Añadir referencias");
        btnAñadirVocablo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirVocabloActionPerformed(evt);
            }
        });

        txtSignificado.setColumns(20);
        txtSignificado.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        txtSignificado.setRows(5);
        txtSignificado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtSignificado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSignificadoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSignificadoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtSignificado);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/volver.png"))); // NOI18N
        btnCancelar.setToolTipText("Volver atras");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/limpSmall.png"))); // NOI18N
        jButton2.setToolTipText("Limpiar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tekton Pro", 0, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(246, 243, 243));
        jLabel2.setText("Añadir una nueva palabra");

        cboTipo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-----Seleccione una categoria-----" }));
        cboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoActionPerformed(evt);
            }
        });

        txtPalabra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPalabra.setToolTipText("");
        txtPalabra.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPalabra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPalabraMouseClicked(evt);
            }
        });
        txtPalabra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPalabraActionPerformed(evt);
            }
        });
        txtPalabra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPalabraKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 102));
        jLabel3.setText("Categoria gramatical: ");

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 102));
        jLabel1.setText("Nombre de la palabra:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 102));
        jLabel4.setText("Definición:");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadir.png"))); // NOI18N

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        jButton3.setToolTipText("Añadir una nueva palabra");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lblContador.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblContador.setForeground(new java.awt.Color(255, 255, 102));
        lblContador.setText("0/150");

        javax.swing.GroupLayout pnlFondo1Layout = new javax.swing.GroupLayout(pnlFondo1);
        pnlFondo1.setLayout(pnlFondo1Layout);
        pnlFondo1Layout.setHorizontalGroup(
            pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondo1Layout.createSequentialGroup()
                .addGroup(pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFondo1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFondo1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFondo1Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(txtPalabra, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFondo1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel3))
                    .addGroup(pnlFondo1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFondo1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFondo1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFondo1Layout.createSequentialGroup()
                                .addComponent(lblContador, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(156, 156, 156)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnAñadirVocablo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFondo1Layout.setVerticalGroup(
            pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondo1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFondo1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtPalabra, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAñadirVocablo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContador, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );

        jMenu1.setText("Archivo");

        jMenuItem1.setText("Añadir una nueva palabra");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edicion");

        jMenuItem2.setText("Limpiar todo");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 550, 542);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validarCampos().equals("")) {
            guardarPalabra();
            JOptionPane.showMessageDialog(this, "Guardado.");
            limpiar();
        } else {
            getToolkit().beep();
            JOptionPane.showMessageDialog(this, validarCampos());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoActionPerformed
    }//GEN-LAST:event_cboTipoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAñadirVocabloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirVocabloActionPerformed
        if (!(validarCampos().equals("")) && !(txtPalabra.getText().equals(""))) {
            añadir.asignarNombrePalabra(txtPalabra.getText());
            añadir.setVisible(true);
        } else {
            getToolkit().beep();
            JOptionPane.showMessageDialog(this, "Primero debe guardar la nueva palabra");
        }
    }//GEN-LAST:event_btnAñadirVocabloActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        limpiar();
        habilitar();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        habilitar();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jMenuItem1ActionPerformed(evt);
    }//GEN-LAST:event_jButton3ActionPerformed

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

    private void txtPalabraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPalabraMouseClicked
        txtPalabra.selectAll();
    }//GEN-LAST:event_txtPalabraMouseClicked

    private void txtSignificadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSignificadoKeyTyped
    }//GEN-LAST:event_txtSignificadoKeyTyped

    private void txtSignificadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSignificadoKeyPressed
        if (txtSignificado.getText().length() < 150) {
            if (evt.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
                lblContador.setText((txtSignificado.getText().length() + 1) + "/150");
            } else {
                lblContador.setText((txtSignificado.getText().length() - 1) + "/150");
            }
        } else {
            getToolkit().beep();
            evt.consume();
            try {
                txtSignificado.setText(txtSignificado.getText(0, 149));
            } catch (BadLocationException ex) {
            }
            JOptionPane.showMessageDialog(this, "No puede exceder los 150 caracteres.");

        }
    }//GEN-LAST:event_txtSignificadoKeyPressed

    private void txtPalabraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPalabraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPalabraActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new frmAñadirPalabra().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadirVocablo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cboTipo;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblContador;
    private clases.pnlFondo pnlFondo1;
    private javax.swing.JTextField txtPalabra;
    private javax.swing.JTextArea txtSignificado;
    // End of variables declaration//GEN-END:variables
}
