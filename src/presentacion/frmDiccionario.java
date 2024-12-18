package presentacion;

import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import negocio.NPalabra;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import clases.AutoCompletar;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class frmDiccionario extends javax.swing.JFrame {

    AutoCompletar completer;
    JLabel lblSigNombre;
    JLabel lblCont;
    JLabel lblClasificacion;
    JLabel lblSignificado;
    frmAñadirPalabra insertarPalabra;
    frmEditarPalabra editarPalabra;
    DefaultTableModel model;
    DefaultListModel listModel = new DefaultListModel();
    NPalabra np = new NPalabra();
    ResultSet rs;

    public void cargarTitulos(String titulo) {
        model = new DefaultTableModel();
        String[] titulos = {titulo};
        for (short i = 0; i < titulos.length; i++) {
            model.addColumn(titulos[i]);
        }
        tblMiTabla.setModel(model);
    }

    public void cargarTabla(ResultSet rs, byte n) {
        Object[] mostrar = new Object[1];
        try {
            while (rs.next()) {
                mostrar[0] = rs.getString(n);
                model.addRow(mostrar);
            }
            tblMiTabla.setModel(model);

        } catch (Exception e) {
            System.err.println("Error al cargar la tabla " + e.getMessage());
        }
    }

    public void mostrarTabla() {
        rs = np.mostrar_palabra();
        cargarTitulos("Todas las palabras");
        limpiarTabla();
        cargarTabla(rs, (byte) 2);
    }

    public void cargarIndice(char c) {
        rs = np.buscarPorIndice(c);
        cargarTitulos("Indice \"" + c + "\"");
        limpiarTabla();
        cargarTabla(rs, (byte) 1);
    }

    public void limpiarTabla() {
        short x = (short) model.getRowCount();
        for (int i = x - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    public void noCoincidencias(String nombre) {
        lblSigNombre = new JLabel("No se encontró una definición para \"" + nombre + "\"");
        JLabel lblResultVacio = new JLabel("<html><br>No se encontraron coincidencias. </br>"
                + "<br>NOTA: Si usted tecleó la palabra manualmente, por favor...</br>"
                + "<br>revise su ortografía y vuélvalo a intentar.</br></html>");
        lblSigNombre.setVisible(true);
        lblResultVacio.setVisible(true);
        lblSigNombre.setFont(new Font("Comic Sans MS", 4, 18));
        lblResultVacio.setFont(new Font("High Tower Text", 4, 20));
        lblSigNombre.setForeground(new Color(0, 0, 0));
        lblResultVacio.setForeground(new Color(255, 204, 0));
        lblSigNombre.setBounds(30, 20, 400, 50);
        lblResultVacio.setBounds(85, 50, 1000, 100);
        pnlResultado.add(lblSigNombre);
        pnlResultado.add(lblResultVacio);
        pnlResultado.repaint();
    }

    public void validarCampos() {
        if (!"".equals(txtBuscar.getText())) {
            busqueda(txtBuscar.getText());
        } else {
            getToolkit().beep();
            JOptionPane.showMessageDialog(this, "El campo de busqueda está vacio.", "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void busqueda(String nombre) {
        limpiarPanelResultado();
        listModel.removeAllElements();
        rs = np.buscarPalabra(nombre);
        cargarPalabra(rs);
        int i = pnlResultado.getComponentCount();
        if (i != 0) {
            rs = np.buscarSinonimosNombre(nombre);
            cargarSinonimo(rs);
            rs = np.buscarFamiliaNombre(nombre);
            cargarFamilia(rs);
            rs = np.buscarParonimoNombre(nombre);
            cargarParonimos(rs);
        } else {
            noCoincidencias(nombre);
        }
    }

    public void eliminarPalabra(String nombre) {
        np.eliminarSignificado(nombre);
        rs = np.obtenerID_palabra(nombre);
        int i = 0;
        try {
            rs.next();
            i = Integer.parseInt(rs.getString(1));
        } catch (SQLException | NumberFormatException e) {
        }
        np.eliminarAntonimo(i);
        np.eliminarSinonimo(i);
        np.eliminarParonimo(i);
        np.eliminarFamilia(i);
        np.eliminarPalabra(nombre);
    }

    public void crearLabel(String nombre, byte i, byte x) {
        lblSigNombre = new JLabel("Definición de \"" + nombre + "\"");
        lblCont = new JLabel();
        lblClasificacion = new JLabel();
        lblSignificado = new JLabel();
        lblSigNombre.setFont(new Font("Comic Sans MS", 4, 20));
        lblCont.setFont(new Font("Tahoma", 4, 14));
        lblClasificacion.setFont(new Font("Vijaya", 0, 18));
        lblSignificado.setFont(new Font("Baskerville Old Face", 0, 20));
        lblSigNombre.setForeground(new Color(0, 0, 0));
        lblCont.setForeground(new Color(255, 204, 0));
        lblClasificacion.setForeground(new Color(255, 255, 102));
        lblSignificado.setForeground(new Color(255, 204, 0));
        lblSigNombre.setVisible(true);
        lblCont.setVisible(true);
        lblClasificacion.setVisible(true);
        lblSignificado.setVisible(true);
        lblSigNombre.setBounds(30, 20, 360, 35);
        lblCont.setBounds(85, (70 * i), 20, 30);
        lblClasificacion.setBounds(125, (70 * i), 100, 30);
        lblSignificado.setBounds(238, ((61 * i) + x), 700, 50);
        pnlResultado.add(lblSigNombre);
        pnlResultado.add(lblCont);
        pnlResultado.add(lblClasificacion);
        pnlResultado.add(lblSignificado);
        pnlResultado.validate();
        pnlResultado.repaint();

    }

    public void cargarPalabra(ResultSet rs) {
        byte i = 1, x = 0;
        try {
            while (rs.next()) {
                String nombre = rs.getString(1);
                crearLabel(nombre, i, x);
                lblCont.setText(String.valueOf(i) + "°");
                lblClasificacion.setText("[" + rs.getString(3) + "]");
                String msj = rs.getString(2);
                lblSignificado.setText("<html>" + msj + "</html>");
                i++;
                x += 9;
            }
        } catch (Exception e) {
        }
    }

    public String palabraSeleccionada() {
        if (!(txtBuscar.getText().equals(""))) {
            return txtBuscar.getText();
        } else {
            return String.valueOf(tblMiTabla.getValueAt(tblMiTabla.getSelectedRow(), 0));
        }
    }

    public void limpiarTodo() {
        listModel.removeAllElements();
        txtBuscar.setText("");
        limpiarPanelResultado();
    }

    public void limpiarPanelResultado() {
        pnlResultado.removeAll();
        pnlResultado.repaint();
    }

    @SuppressWarnings("unchecked")
    private void cargarLista(ResultSet rs) {
        try {
            while (rs.next()) {
                listModel.addElement(rs.getString(1));
            }
        } catch (Exception e) {
        }
        lstSugerencias.setModel(listModel);
    }

    public void cargarSinonimo(ResultSet rs) {
        cargarLista(rs);
    }

    public void cargarFamilia(ResultSet rs) {
        cargarLista(rs);
    }

    public void cargarParonimos(ResultSet rs) {
        cargarLista(rs);
    }

    public void cargarAntonimos(ResultSet rs) {
        //
    }

    public frmDiccionario() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setIconImage(new ImageIcon(getClass().getResource("/imagenes/icono.png")).getImage());
        completer = new AutoCompletar(txtBuscar);
        mostrarTabla();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuTabla = new javax.swing.JPopupMenu();
        Buscar = new javax.swing.JMenuItem();
        btnEditarPalabra = new javax.swing.JButton();
        pnlFondo1 = new clases.pnlFondo();
        pnlTitulo = new clases.pnlTitulo();
        txtBuscar = new javax.swing.JTextField();
        tbOpciones = new javax.swing.JToolBar();
        btnAñadirPalabra = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        pnlIndice = new javax.swing.JPanel();
        lblA = new javax.swing.JLabel();
        lblB = new javax.swing.JLabel();
        lblC = new javax.swing.JLabel();
        lblD = new javax.swing.JLabel();
        lblE = new javax.swing.JLabel();
        lblF = new javax.swing.JLabel();
        lblG = new javax.swing.JLabel();
        lblH = new javax.swing.JLabel();
        lblI = new javax.swing.JLabel();
        lblJ = new javax.swing.JLabel();
        lblK = new javax.swing.JLabel();
        lblL = new javax.swing.JLabel();
        lblM = new javax.swing.JLabel();
        lblN = new javax.swing.JLabel();
        lblO = new javax.swing.JLabel();
        lblÑ = new javax.swing.JLabel();
        lblP = new javax.swing.JLabel();
        lblQ = new javax.swing.JLabel();
        lblR = new javax.swing.JLabel();
        lblS = new javax.swing.JLabel();
        lblT = new javax.swing.JLabel();
        lblU = new javax.swing.JLabel();
        lblV = new javax.swing.JLabel();
        lblW = new javax.swing.JLabel();
        lblX = new javax.swing.JLabel();
        lblY = new javax.swing.JLabel();
        lblZ = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pnlResultado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlReferencias = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstSugerencias = new javax.swing.JList();
        pnlReferencias1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMiTabla = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        Archivo = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        Buscar.setText("Buscar");
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });
        menuTabla.add(Buscar);

        btnEditarPalabra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar.png"))); // NOI18N
        btnEditarPalabra.setToolTipText("Editar");
        btnEditarPalabra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarPalabraActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        pnlTitulo.setOpaque(false);

        txtBuscar.setFont(new java.awt.Font("Adobe Hebrew", 0, 18)); // NOI18N
        txtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarMouseClicked(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        tbOpciones.setRollover(true);

        btnAñadirPalabra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadir.png"))); // NOI18N
        btnAñadirPalabra.setToolTipText("Añadir palabra");
        btnAñadirPalabra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirPalabraActionPerformed(evt);
            }
        });
        tbOpciones.add(btnAñadirPalabra);

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/limpiar.png"))); // NOI18N
        btnLimpiar.setToolTipText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        tbOpciones.add(btnLimpiar);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar palabra");
        btnEliminar.setFocusable(false);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        tbOpciones.add(btnEliminar);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir.png"))); // NOI18N
        btnSalir.setToolTipText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        tbOpciones.add(btnSalir);

        lblTitulo.setFont(new java.awt.Font("Tekton Pro Ext", 0, 36)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(102, 0, 0));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("DICCIONARIO");

        btnBuscar.setFont(new java.awt.Font("SketchFlow Print", 0, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        pnlIndice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar por índice", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Traditional Arabic", 1, 14))); // NOI18N
        pnlIndice.setOpaque(false);

        lblA.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblA.setText("[A]");
        lblA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAMouseExited(evt);
            }
        });

        lblB.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblB.setText("[B]");
        lblB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBMouseEntered(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBMouseExited(evt);
            }
        });

        lblC.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblC.setText("[C]");
        lblC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCMouseEntered(evt);
            }
        });

        lblD.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblD.setText("[D]");
        lblD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDMouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDMouseEntered(evt);
            }
        });

        lblE.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblE.setText("[E]");
        lblE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEMouseEntered(evt);
            }
        });

        lblF.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblF.setText("[F]");
        lblF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblFMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblFMouseEntered(evt);
            }
        });

        lblG.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblG.setText("[G]");
        lblG.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblGMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblGMouseExited(evt);
            }
        });

        lblH.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblH.setText("[H]");
        lblH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHMouseEntered(evt);
            }
        });

        lblI.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblI.setText("[I]");
        lblI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblIMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblIMouseEntered(evt);
            }
        });

        lblJ.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblJ.setText("[J]");
        lblJ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblJ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblJMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblJMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblJMouseEntered(evt);
            }
        });

        lblK.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblK.setText("[K]");
        lblK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblKMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblKMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblKMouseEntered(evt);
            }
        });

        lblL.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblL.setText("[L]");
        lblL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLMouseEntered(evt);
            }
        });

        lblM.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblM.setText("[M]");
        lblM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMMouseEntered(evt);
            }
        });

        lblN.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblN.setText("[N]");
        lblN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNMouseEntered(evt);
            }
        });

        lblO.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblO.setText("[O]");
        lblO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblOMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblOMouseEntered(evt);
            }
        });

        lblÑ.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblÑ.setText("[Ñ]");
        lblÑ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblÑ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblÑMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblÑMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblÑMouseEntered(evt);
            }
        });

        lblP.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblP.setText("[P]");
        lblP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblPMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPMouseEntered(evt);
            }
        });

        lblQ.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblQ.setText("[Q]");
        lblQ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblQ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQMouseEntered(evt);
            }
        });

        lblR.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblR.setText("[R]");
        lblR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRMouseEntered(evt);
            }
        });

        lblS.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblS.setText("[S]");
        lblS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSMouseEntered(evt);
            }
        });

        lblT.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblT.setText("[T]");
        lblT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTMouseEntered(evt);
            }
        });

        lblU.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblU.setText("[U]");
        lblU.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUMouseEntered(evt);
            }
        });

        lblV.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblV.setText("[V]");
        lblV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblVMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblVMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblVMouseEntered(evt);
            }
        });

        lblW.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblW.setText("[W]");
        lblW.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblW.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblWMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblWMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblWMouseEntered(evt);
            }
        });

        lblX.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblX.setText("[X]");
        lblX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblXMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblXMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblXMouseEntered(evt);
            }
        });

        lblY.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblY.setText("[Y]");
        lblY.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblYMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblYMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblYMouseEntered(evt);
            }
        });

        lblZ.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblZ.setText("[Z]");
        lblZ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblZ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblZMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblZMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblZMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlIndiceLayout = new javax.swing.GroupLayout(pnlIndice);
        pnlIndice.setLayout(pnlIndiceLayout);
        pnlIndiceLayout.setHorizontalGroup(
            pnlIndiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIndiceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblJ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblÑ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblQ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblW)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblZ)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlIndiceLayout.setVerticalGroup(
            pnlIndiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlIndiceLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlIndiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblA)
                    .addComponent(lblB)
                    .addComponent(lblC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblE)
                    .addComponent(lblF)
                    .addComponent(lblG)
                    .addComponent(lblH)
                    .addComponent(lblI)
                    .addComponent(lblJ)
                    .addComponent(lblK)
                    .addComponent(lblL)
                    .addComponent(lblM)
                    .addComponent(lblN)
                    .addComponent(lblO)
                    .addComponent(lblÑ)
                    .addComponent(lblP)
                    .addComponent(lblQ)
                    .addComponent(lblR)
                    .addComponent(lblS)
                    .addComponent(lblT)
                    .addComponent(lblU)
                    .addComponent(lblV)
                    .addComponent(lblW)
                    .addComponent(lblX)
                    .addComponent(lblY)
                    .addComponent(lblZ)))
        );

        javax.swing.GroupLayout pnlTituloLayout = new javax.swing.GroupLayout(pnlTitulo);
        pnlTitulo.setLayout(pnlTituloLayout);
        pnlTituloLayout.setHorizontalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTituloLayout.createSequentialGroup()
                        .addComponent(pnlIndice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlTituloLayout.createSequentialGroup()
                        .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitulo)
                            .addGroup(pnlTituloLayout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tbOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))))
        );
        pnlTituloLayout.setVerticalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitulo)
                    .addComponent(tbOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnlIndice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        pnlResultado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlResultado.setMaximumSize(new java.awt.Dimension(1000, 1000));
        pnlResultado.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Baskerville Old Face", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 204, 0));
        jLabel1.setText("-El que escucha entiende, el que observa comprende, pero solo el que hace Aprende...");

        javax.swing.GroupLayout pnlResultadoLayout = new javax.swing.GroupLayout(pnlResultado);
        pnlResultado.setLayout(pnlResultadoLayout);
        pnlResultadoLayout.setHorizontalGroup(
            pnlResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResultadoLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlResultadoLayout.setVerticalGroup(
            pnlResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlResultadoLayout.createSequentialGroup()
                .addContainerGap(301, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlReferencias.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 2, 12), new java.awt.Color(102, 153, 255)), "Ver también", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Baskerville Old Face", 2, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlReferencias.setOpaque(false);

        lstSugerencias.setBorder(new javax.swing.border.MatteBorder(null));
        lstSugerencias.setFont(new java.awt.Font("Kalinga", 3, 14)); // NOI18N
        lstSugerencias.setForeground(new java.awt.Color(51, 64, 204));
        lstSugerencias.setToolTipText("");
        lstSugerencias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lstSugerencias.setOpaque(false);
        lstSugerencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstSugerenciasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(lstSugerencias);

        javax.swing.GroupLayout pnlReferenciasLayout = new javax.swing.GroupLayout(pnlReferencias);
        pnlReferencias.setLayout(pnlReferenciasLayout);
        pnlReferenciasLayout.setHorizontalGroup(
            pnlReferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReferenciasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlReferenciasLayout.setVerticalGroup(
            pnlReferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReferenciasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlReferencias1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 2, 12), new java.awt.Color(102, 153, 255)), "----------------------------", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Baskerville Old Face", 2, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlReferencias1.setOpaque(false);

        tblMiTabla.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblMiTabla.setFont(new java.awt.Font("Sitka Small", 3, 14)); // NOI18N
        tblMiTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblMiTabla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblMiTabla.setOpaque(false);
        tblMiTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMiTablaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblMiTablaMouseEntered(evt);
            }
        });
        tblMiTabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblMiTablaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblMiTabla);

        javax.swing.GroupLayout pnlReferencias1Layout = new javax.swing.GroupLayout(pnlReferencias1);
        pnlReferencias1.setLayout(pnlReferencias1Layout);
        pnlReferencias1Layout.setHorizontalGroup(
            pnlReferencias1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlReferencias1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlReferencias1Layout.setVerticalGroup(
            pnlReferencias1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReferencias1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlFondo1Layout = new javax.swing.GroupLayout(pnlFondo1);
        pnlFondo1.setLayout(pnlFondo1Layout);
        pnlFondo1Layout.setHorizontalGroup(
            pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondo1Layout.createSequentialGroup()
                .addGroup(pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlFondo1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFondo1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pnlReferencias1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlResultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlReferencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlFondo1Layout.setVerticalGroup(
            pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondo1Layout.createSequentialGroup()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(pnlFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlReferencias1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlReferencias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlResultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        Archivo.setText("Archivo");

        jMenuItem1.setText("Cargar Palabras");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        Archivo.add(jMenuItem1);

        jMenuBar1.add(Archivo);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemCargarTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCargarTituloActionPerformed
    }//GEN-LAST:event_menuItemCargarTituloActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        mostrarTabla();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (!"".equals(palabraSeleccionada())) {
            int delete = JOptionPane.showOptionDialog(this, "¿Eliminar "
                    + "la palabra \"" + palabraSeleccionada() + "\" del diccionario?", "Eliminar",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si, seguro", "No, cancelar"}, 1);
            if (delete == 0) {
                eliminarPalabra(palabraSeleccionada());
                limpiarTodo();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Para eliminar primero debe seleccionar una palabra.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarTodo();
        cargarTitulos("Todas las palabras");
        mostrarTabla();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnAñadirPalabraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirPalabraActionPerformed
        insertarPalabra.setVisible(true);
    }//GEN-LAST:event_btnAñadirPalabraActionPerformed

    private void lblZMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZMouseEntered
        this.lblZ.setForeground(Color.yellow);
    }//GEN-LAST:event_lblZMouseEntered

    private void lblZMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZMouseClicked
        cargarIndice('Z');
    }//GEN-LAST:event_lblZMouseClicked

    private void lblZMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZMouseExited
        this.lblZ.setForeground(Color.black);
    }//GEN-LAST:event_lblZMouseExited

    private void lblYMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblYMouseEntered
        this.lblY.setForeground(Color.yellow);
    }//GEN-LAST:event_lblYMouseEntered

    private void lblYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblYMouseClicked
        cargarIndice('Y');
    }//GEN-LAST:event_lblYMouseClicked

    private void lblYMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblYMouseExited
        this.lblY.setForeground(Color.black);
    }//GEN-LAST:event_lblYMouseExited

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        this.lblX.setForeground(Color.yellow);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        cargarIndice('X');
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        this.lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void lblWMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWMouseEntered
        this.lblW.setForeground(Color.yellow);
    }//GEN-LAST:event_lblWMouseEntered

    private void lblWMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWMouseClicked
        cargarIndice('W');
    }//GEN-LAST:event_lblWMouseClicked

    private void lblWMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWMouseExited
        this.lblW.setForeground(Color.black);
    }//GEN-LAST:event_lblWMouseExited

    private void lblVMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVMouseEntered
        this.lblV.setForeground(Color.yellow);
    }//GEN-LAST:event_lblVMouseEntered

    private void lblVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVMouseClicked
        cargarIndice('V');
    }//GEN-LAST:event_lblVMouseClicked

    private void lblVMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVMouseExited
        this.lblV.setForeground(Color.black);
    }//GEN-LAST:event_lblVMouseExited

    private void lblUMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUMouseEntered
        this.lblU.setForeground(Color.yellow);
    }//GEN-LAST:event_lblUMouseEntered

    private void lblUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUMouseClicked
        cargarIndice('U');
    }//GEN-LAST:event_lblUMouseClicked

    private void lblUMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUMouseExited
        this.lblU.setForeground(Color.black);
    }//GEN-LAST:event_lblUMouseExited

    private void lblTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTMouseEntered
        this.lblT.setForeground(Color.yellow);
    }//GEN-LAST:event_lblTMouseEntered

    private void lblTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTMouseClicked
        cargarIndice('T');
    }//GEN-LAST:event_lblTMouseClicked

    private void lblTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTMouseExited
        this.lblT.setForeground(Color.black);
    }//GEN-LAST:event_lblTMouseExited

    private void lblSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSMouseEntered
        this.lblS.setForeground(Color.yellow);
    }//GEN-LAST:event_lblSMouseEntered

    private void lblSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSMouseClicked
        cargarIndice('S');
    }//GEN-LAST:event_lblSMouseClicked

    private void lblSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSMouseExited
        this.lblS.setForeground(Color.black);
    }//GEN-LAST:event_lblSMouseExited

    private void lblRMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRMouseEntered
        this.lblR.setForeground(Color.yellow);
    }//GEN-LAST:event_lblRMouseEntered

    private void lblRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRMouseClicked
        cargarIndice('R');
    }//GEN-LAST:event_lblRMouseClicked

    private void lblRMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRMouseExited
        this.lblR.setForeground(Color.black);
    }//GEN-LAST:event_lblRMouseExited

    private void lblQMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQMouseEntered
        this.lblQ.setForeground(Color.yellow);
    }//GEN-LAST:event_lblQMouseEntered

    private void lblQMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQMouseClicked
        cargarIndice('Q');
    }//GEN-LAST:event_lblQMouseClicked

    private void lblQMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQMouseExited
        this.lblQ.setForeground(Color.black);
    }//GEN-LAST:event_lblQMouseExited

    private void lblPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPMouseEntered
        this.lblP.setForeground(Color.yellow);
    }//GEN-LAST:event_lblPMouseEntered

    private void lblPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPMouseClicked
        cargarIndice('P');
    }//GEN-LAST:event_lblPMouseClicked

    private void lblPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPMouseExited
        this.lblP.setForeground(Color.black);
    }//GEN-LAST:event_lblPMouseExited

    private void lblÑMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblÑMouseEntered
        this.lblÑ.setForeground(Color.yellow);
    }//GEN-LAST:event_lblÑMouseEntered

    private void lblÑMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblÑMouseClicked
        cargarIndice('Ñ');
    }//GEN-LAST:event_lblÑMouseClicked

    private void lblÑMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblÑMouseExited
        this.lblÑ.setForeground(Color.black);
    }//GEN-LAST:event_lblÑMouseExited

    private void lblOMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOMouseEntered
        this.lblO.setForeground(Color.yellow);
    }//GEN-LAST:event_lblOMouseEntered

    private void lblOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOMouseClicked
        cargarIndice('O');
    }//GEN-LAST:event_lblOMouseClicked

    private void lblOMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOMouseExited
        this.lblO.setForeground(Color.black);
    }//GEN-LAST:event_lblOMouseExited

    private void lblNMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNMouseEntered
        this.lblN.setForeground(Color.yellow);
    }//GEN-LAST:event_lblNMouseEntered

    private void lblNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNMouseClicked
        cargarIndice('N');
    }//GEN-LAST:event_lblNMouseClicked

    private void lblNMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNMouseExited
        this.lblN.setForeground(Color.black);
    }//GEN-LAST:event_lblNMouseExited

    private void lblMMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMMouseEntered
        this.lblM.setForeground(Color.yellow);
    }//GEN-LAST:event_lblMMouseEntered

    private void lblMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMMouseClicked
        cargarIndice('M');
    }//GEN-LAST:event_lblMMouseClicked

    private void lblMMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMMouseExited
        this.lblM.setForeground(Color.black);
    }//GEN-LAST:event_lblMMouseExited

    private void lblLMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLMouseEntered
        this.lblL.setForeground(Color.yellow);
    }//GEN-LAST:event_lblLMouseEntered

    private void lblLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLMouseClicked
        cargarIndice('L');
    }//GEN-LAST:event_lblLMouseClicked

    private void lblLMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLMouseExited
        this.lblL.setForeground(Color.black);
    }//GEN-LAST:event_lblLMouseExited

    private void lblKMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKMouseEntered
        this.lblK.setForeground(Color.yellow);
    }//GEN-LAST:event_lblKMouseEntered

    private void lblKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKMouseClicked
        cargarIndice('K');
    }//GEN-LAST:event_lblKMouseClicked

    private void lblKMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKMouseExited
        this.lblK.setForeground(Color.black);
    }//GEN-LAST:event_lblKMouseExited

    private void lblJMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblJMouseEntered
        this.lblJ.setForeground(Color.yellow);
    }//GEN-LAST:event_lblJMouseEntered

    private void lblJMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblJMouseClicked
        cargarIndice('J');
    }//GEN-LAST:event_lblJMouseClicked

    private void lblJMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblJMouseExited
        this.lblJ.setForeground(Color.black);
    }//GEN-LAST:event_lblJMouseExited

    private void lblIMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIMouseEntered
        this.lblI.setForeground(Color.yellow);
    }//GEN-LAST:event_lblIMouseEntered

    private void lblIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIMouseClicked
        cargarIndice('I');
    }//GEN-LAST:event_lblIMouseClicked

    private void lblIMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIMouseExited
        this.lblI.setForeground(Color.black);
    }//GEN-LAST:event_lblIMouseExited

    private void lblHMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHMouseEntered
        this.lblH.setForeground(Color.yellow);
    }//GEN-LAST:event_lblHMouseEntered

    private void lblHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHMouseClicked
        cargarIndice('H');
    }//GEN-LAST:event_lblHMouseClicked

    private void lblHMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHMouseExited
        this.lblH.setForeground(Color.black);
    }//GEN-LAST:event_lblHMouseExited

    private void lblGMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGMouseEntered
        this.lblG.setForeground(Color.yellow);
    }//GEN-LAST:event_lblGMouseEntered

    private void lblGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGMouseClicked
        cargarIndice('G');
    }//GEN-LAST:event_lblGMouseClicked

    private void lblGMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGMouseExited
        this.lblG.setForeground(Color.black);
    }//GEN-LAST:event_lblGMouseExited

    private void lblFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFMouseEntered
        this.lblF.setForeground(Color.yellow);
    }//GEN-LAST:event_lblFMouseEntered

    private void lblFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFMouseClicked
        cargarIndice('F');
    }//GEN-LAST:event_lblFMouseClicked

    private void lblFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFMouseExited
        this.lblF.setForeground(Color.black);
    }//GEN-LAST:event_lblFMouseExited

    private void lblEMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEMouseExited
        this.lblE.setForeground(Color.black);
    }//GEN-LAST:event_lblEMouseExited

    private void lblEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEMouseClicked
        cargarIndice('E');
    }//GEN-LAST:event_lblEMouseClicked

    private void lblEMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEMouseEntered
        this.lblE.setForeground(Color.yellow);
    }//GEN-LAST:event_lblEMouseEntered

    private void lblDMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDMouseExited
        this.lblD.setForeground(Color.black);
    }//GEN-LAST:event_lblDMouseExited

    private void lblDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDMouseClicked
        cargarIndice('D');
    }//GEN-LAST:event_lblDMouseClicked

    private void lblDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDMouseEntered
        this.lblD.setForeground(Color.yellow);
    }//GEN-LAST:event_lblDMouseEntered

    private void lblCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCMouseExited
        this.lblC.setForeground(Color.black);
    }//GEN-LAST:event_lblCMouseExited

    private void lblCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCMouseClicked
        cargarIndice('C');
    }//GEN-LAST:event_lblCMouseClicked

    private void lblCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCMouseEntered
        this.lblC.setForeground(Color.yellow);
    }//GEN-LAST:event_lblCMouseEntered

    private void lblBMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBMouseExited
        this.lblB.setForeground(Color.black);
    }//GEN-LAST:event_lblBMouseExited

    private void lblBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBMouseClicked
        cargarIndice('B');
    }//GEN-LAST:event_lblBMouseClicked

    private void lblBMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBMouseEntered
        this.lblB.setForeground(Color.yellow);
    }//GEN-LAST:event_lblBMouseEntered

    private void lblAMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAMouseExited
        this.lblA.setForeground(Color.black);
    }//GEN-LAST:event_lblAMouseExited

    private void lblAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAMouseClicked
        cargarIndice('A');
    }//GEN-LAST:event_lblAMouseClicked

    private void lblAMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAMouseEntered
        this.lblA.setForeground(Color.yellow);
    }//GEN-LAST:event_lblAMouseEntered

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        validarCampos();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCampos();
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void tblMiTablaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMiTablaMouseEntered
    }//GEN-LAST:event_tblMiTablaMouseEntered

    private void tblMiTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMiTablaMouseClicked
        txtBuscar.setText("");
        if (!(evt.isMetaDown())) {
            busqueda((String) (tblMiTabla.getValueAt(tblMiTabla.getSelectedRow(), 0)));
        } else {
            //menuTabla.show(evt.getComponent(), evt.getX(), evt.getY());
        }

    }//GEN-LAST:event_tblMiTablaMouseClicked

    private void btnEditarPalabraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarPalabraActionPerformed
        editarPalabra.asignarPalabra(palabraSeleccionada());
        editarPalabra.setVisible(true);
    }//GEN-LAST:event_btnEditarPalabraActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        insertarPalabra = new frmAñadirPalabra();
        editarPalabra = new frmEditarPalabra();
        completer.txtReiniciar();
        completer.txtPredictivo(true);
        mostrarTabla();
        txtBuscar.requestFocus();

    }//GEN-LAST:event_formWindowActivated

    private void lstSugerenciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstSugerenciasMouseClicked
        txtBuscar.setText(lstSugerencias.getSelectedValue().toString());
        busqueda(lstSugerencias.getSelectedValue().toString());
    }//GEN-LAST:event_lstSugerenciasMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void tblMiTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMiTablaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            busqueda((String) (tblMiTabla.getValueAt(tblMiTabla.getSelectedRow(), 0)));
        }
    }//GEN-LAST:event_tblMiTablaKeyPressed

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        JOptionPane.showMessageDialog(this, tblMiTabla.getValueAt(tblMiTabla.getSelectedRow(), 0));
    }//GEN-LAST:event_BuscarActionPerformed

    private void txtBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseClicked
        txtBuscar.selectAll();
    }//GEN-LAST:event_txtBuscarMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new frmDiccionario().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Archivo;
    private javax.swing.JMenuItem Buscar;
    private javax.swing.JButton btnAñadirPalabra;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditarPalabra;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblA;
    private javax.swing.JLabel lblB;
    private javax.swing.JLabel lblC;
    private javax.swing.JLabel lblD;
    private javax.swing.JLabel lblE;
    private javax.swing.JLabel lblF;
    private javax.swing.JLabel lblG;
    private javax.swing.JLabel lblH;
    private javax.swing.JLabel lblI;
    private javax.swing.JLabel lblJ;
    private javax.swing.JLabel lblK;
    private javax.swing.JLabel lblL;
    private javax.swing.JLabel lblM;
    private javax.swing.JLabel lblN;
    private javax.swing.JLabel lblO;
    private javax.swing.JLabel lblP;
    private javax.swing.JLabel lblQ;
    private javax.swing.JLabel lblR;
    private javax.swing.JLabel lblS;
    private javax.swing.JLabel lblT;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblU;
    private javax.swing.JLabel lblV;
    private javax.swing.JLabel lblW;
    private javax.swing.JLabel lblX;
    private javax.swing.JLabel lblY;
    private javax.swing.JLabel lblZ;
    private javax.swing.JLabel lblÑ;
    private javax.swing.JList lstSugerencias;
    private javax.swing.JPopupMenu menuTabla;
    private clases.pnlFondo pnlFondo1;
    private javax.swing.JPanel pnlIndice;
    private javax.swing.JPanel pnlReferencias;
    private javax.swing.JPanel pnlReferencias1;
    private javax.swing.JPanel pnlResultado;
    private clases.pnlTitulo pnlTitulo;
    private javax.swing.JToolBar tbOpciones;
    private javax.swing.JTable tblMiTabla;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
