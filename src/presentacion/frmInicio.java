package presentacion;

import com.sun.awt.AWTUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class frmInicio extends javax.swing.JFrame {

    Timer timer;

    public frmInicio() {
        this.setUndecorated(true);
        initComponents();
//        this.setSize(360, 600);
        AWTUtilities.setWindowOpaque(this, false);
        this.setIconImage(new ImageIcon(getClass().getResource("/imagenes/icono.png")).getImage());
        timer = new Timer(50, new Progreso());
        timer.start();
    }
    
    public class Progreso implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int n = barraProgreso.getValue();
            barraProgreso.setMaximum(200);
            if (n < 200) {
                carga(n++);
                barraProgreso.setValue(n);
            } else {
                timer.stop();
                new frmDiccionario().setVisible(true);
                dispose();
            }
        }

        private void carga(int n) {
//            Thread.sleep(90L);
            if (n == 00) {
                lblCarga.setText("Cargando Componentes...");
            }
            if (n == 40) {
                lblCarga.setText("Iniciando Software...");
            }
            if (n == 60) {
                lblCarga.setText("Conectando con la Base de Datos...");
            }
            if (n == 80) {
                lblCarga.setText("Cargando Interfaz de Usuario...");
            }
            if (n == 90) {
                lblCarga.setText("Cargando Reportes...");
            }
            if (n == 110) {
                lblCarga.setText("Compilando...");
            }
            if (n == 130) {
                lblCarga.setText("Verificando Administrador...");
            }
            if (n == 140) {
                lblCarga.setText("Verificando Usuarios...");
            }
            if (n == 145) {
                lblCarga.setText("Cargando Listas...");
            }
            if (n == 150) {
                lblCarga.setText("Cargando Módulos...");
            }
            if (n == 170) {
                lblCarga.setText("Carga de Módulos Terminada...");
            }
            if (n == 180) {
                lblCarga.setText("Iniciando Módulos...");
            }
            if (n == 190) {
                lblCarga.setText("Bienvenido al Sistema Punto de Ventas");
            }
        }
    }
    
    public void setProgresoMax(int maxProgress) {
        barraProgreso.setMaximum(maxProgress);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barraProgreso = new javax.swing.JProgressBar();
        lblCarga = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        getContentPane().setLayout(null);
        getContentPane().add(barraProgreso);
        barraProgreso.setBounds(40, 240, 470, 20);

        lblCarga.setText("Cargando..");
        getContentPane().add(lblCarga);
        lblCarga.setBounds(50, 200, 100, 20);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/titulo.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 0, 750, 290);

        setSize(new java.awt.Dimension(625, 331));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new frmInicio().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgreso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCarga;
    // End of variables declaration//GEN-END:variables
}
