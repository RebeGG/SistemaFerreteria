package sistemaferreteria.Vista;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import sistemaferreteria.Controlador.Controlador;
import sistemaferreteria.Modelo.Entidades.Herramienta;
import sistemaferreteria.Modelo.Entidades.Material;

public class VentanaInventario extends javax.swing.JFrame {

    private Controlador gestorPrincipal;
    private EstadoFormulario estado;

    public VentanaInventario() {
        this("Ejemplo 17", null);
    }

    public VentanaInventario(String titulo, Controlador nuevoGestor) {
        super(titulo);
        this.gestorPrincipal = nuevoGestor;
        this.estado = new EstadoFormulario();
        configurar();
    }

    private void configurar() {
        initComponents();
        definirManejadores();
        setLocationRelativeTo(null);
    }

    private void definirManejadores() {
        DocumentListener docCnt = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                estado.setModificado(true);
                actualizarBotones();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                estado.setModificado(true);
                actualizarBotones();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                estado.setModificado(true);
                actualizarBotones();
            }

        };
        ItemListener itemCnt = (ItemEvent e) -> {
            estado.setModificado(true);
            actualizarBotones();
        };

        fldcodigo.getDocument().addDocumentListener(docCnt);
        fldname.getDocument().addDocumentListener(docCnt);
        fldmedida.getDocument().addDocumentListener(docCnt);
        comboCapacidad.addItemListener(itemCnt);
        fieldPesoKg.getDocument().addDocumentListener(docCnt);
        fldCant.getDocument().addDocumentListener(docCnt);
    }

    public void actualizarBotones() {
        btnAgregar.setEnabled(estado.puedeAgregar());
        btnModificar.setEnabled(estado.puedeModificar());
        btnEliminar.setEnabled(estado.puedeEliminar());
        btnGuardar.setEnabled(estado.puedeGuardar());
        btnBuscar.setEnabled(estado.puedeBuscar());
        btnCancelar.setEnabled(estado.puedeCancelar());
        btnEjecutar.setEnabled(estado.puedeEjecutar());
    }

    public void actualizarCampos() {
        if (btnHerramientas.isSelected() && (!estado.enModoAgregar()) && (estado.getRegistroActual() != null)) {
            Herramienta actual = (Herramienta) estado.getRegistroActual();
            fldcodigo.setText(actual.getCodigo());
            fldname.setText(actual.getNombre());
            fldTamano.setEditable(false);
            comboCapacidad.setSelectedIndex(actual.getCapacidad());
            fldmedida.setText(actual.getMedida());
            fldCant.setText(Integer.toString(actual.getCantidadUnidades()));
            fieldPesoKg.setEnabled(false);

        }
        if (btnMateriales.isSelected() && (!estado.enModoAgregar()) && (estado.getRegistroActual() != null)) {
            Material actual = (Material) estado.getRegistroActual();
            fldcodigo.setText(actual.getCodigo());
            fldname.setText(actual.getNombre());
            fldTamano.setText(actual.getTamano());
            comboCapacidad.setEnabled(false);
            fldmedida.setText(actual.getMedida());
            fldCant.setEnabled(false);
            fieldPesoKg.setText(Double.toString(actual.getPesoKg()));

        } else {

            fldcodigo.setText(null);
            fldname.setText(null);
            fldTamano.setText(null);
            comboCapacidad.setSelectedItem(null);
            fldmedida.setText(null);
            fieldPesoKg.setText(null);
            fldCant.setText(null);
        }

        fldcodigo.setEnabled(estado.enModoAgregar() && !estado.enModoBusqueda());
        fldname.setEnabled(!estado.enModoConsulta() || estado.enModoBusqueda());
        fldmedida.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda());
        comboCapacidad.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda() && btnHerramientas.isSelected());
        fldCant.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda() && btnHerramientas.isSelected());
        fieldPesoKg.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda() && btnMateriales.isSelected());
        fldTamano.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda() && btnMateriales.isSelected());
    }

    private Herramienta capturarRegistroHerramienta() {
        Herramienta r;
        r = new Herramienta(
                fldcodigo.getText(),
                fldname.getText(),
                fldmedida.getText(),
                comboCapacidad.getSelectedIndex(),
                Integer.parseInt(fldCant.getText())
        );
        return r;
    }

    private Material capturarRegistroMaterial() {
        Material r;
        r = new Material(
                fldcodigo.getText(),
                fldname.getText(),
                fldmedida.getText(),
                fldTamano.getText(),
                Double.parseDouble(fieldPesoKg.getText())
        );
        return r;
    }

    public void init() {
        setVisible(true);
        actualizar();
    }

    public void actualizar() {
        actualizarBotones();
        actualizarCampos();
    }

    private Material actualizarRegistroMaterial(Material r) {
        if (r != null) {
            r.setCodigo(fldcodigo.getText());
            r.setNombre(fldname.getText());
            r.setMedida(fldmedida.getText());
            r.setTamano(fldTamano.getText());
            r.setPesoKg(Double.parseDouble(fieldPesoKg.getText()));
        }
        return r;
    }

    private void actualizaCamposMaterial(Material p) {
        fldcodigo.setText(p.getCodigo());
        fldname.setText(p.getNombre());
        fldmedida.setText(p.getMedida());
        fldTamano.setText(p.getTamano());
        fieldPesoKg.setText(Double.toString(p.getPesoKg()));
    }

    private Herramienta actualizarRegistroHerramienta(Herramienta r) {
        if (r != null) {
            r.setCodigo(fldcodigo.getText());
            r.setNombre(fldname.getText());
            r.setMedida(fldmedida.getText());
            r.setCapacidad(comboCapacidad.getSelectedIndex());
            r.setCantidadUnidades(Integer.parseInt(fldCant.getText()));
        }
        return r;
    }

    private void actualizaCampos(Herramienta p) {
        fldcodigo.setText(p.getCodigo());
        fldname.setText(p.getNombre());
        fldmedida.setText(p.getMedida());
        comboCapacidad.setSelectedIndex(p.getCapacidad());
        fldCant.setText(Integer.toString(p.getCantidadUnidades()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btnProducto = new javax.swing.ButtonGroup();
        lblInventario = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblMedida = new javax.swing.JLabel();
        lblCapacidad = new javax.swing.JLabel();
        lblCant = new javax.swing.JLabel();
        lblKg = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnHerramientas = new javax.swing.JRadioButton();
        btnMateriales = new javax.swing.JRadioButton();
        fldname = new javax.swing.JTextField();
        fldcodigo = new javax.swing.JTextField();
        fldmedida = new javax.swing.JTextField();
        fieldPesoKg = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEjecutar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        fldTamano = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        comboCapacidad = new javax.swing.JComboBox<>();
        fldCant = new javax.swing.JTextField();
        barraEstado = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblInventario.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 24)); // NOI18N
        lblInventario.setText("Inventario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 8);
        getContentPane().add(lblInventario, gridBagConstraints);

        lblCodigo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblCodigo.setText("Código");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(lblCodigo, gridBagConstraints);

        lblNombre.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblNombre.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(lblNombre, gridBagConstraints);

        lblMedida.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblMedida.setText("Medida");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(lblMedida, gridBagConstraints);

        lblCapacidad.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblCapacidad.setText("Capacidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(lblCapacidad, gridBagConstraints);

        lblCant.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblCant.setText("Cantidad de Unidades");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(lblCant, gridBagConstraints);

        lblKg.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblKg.setText("Peso en kg");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(lblKg, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel1.setText("Producto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        btnProducto.add(btnHerramientas);
        btnHerramientas.setText("Herramientas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(btnHerramientas, gridBagConstraints);

        btnProducto.add(btnMateriales);
        btnMateriales.setText("Materiales");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(btnMateriales, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(fldname, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(fldcodigo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(fldmedida, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(fieldPesoKg, gridBagConstraints);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(btnBuscar, gridBagConstraints);

        btnAgregar.setText("Añadir");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(btnAgregar, gridBagConstraints);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(btnEliminar, gridBagConstraints);

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        getContentPane().add(btnModificar, gridBagConstraints);

        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(btnEjecutar, gridBagConstraints);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        getContentPane().add(btnCancelar, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel2.setText("Tamaño");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(fldTamano, gridBagConstraints);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(btnGuardar, gridBagConstraints);

        comboCapacidad.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        comboCapacidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Liviana", "Mediana", "Pesada" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(comboCapacidad, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        getContentPane().add(fldCant, gridBagConstraints);

        barraEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        barraEstado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel3.setText("Promedio");
        barraEstado.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(barraEstado, gridBagConstraints);

        jMenu1.setText("Archivo");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Selección");

        jMenuItem1.setText("tablaItem");
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        estado.cambiarModoAgregar();
        actualizar();
        fldcodigo.requestFocusInWindow();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        estado.cambiarModoBuscar();
        actualizar();
         fldname.requestFocusInWindow();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        estado.cambiarModoConsulta();
        actualizar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        estado.cambiarModoModificar();
        actualizar();
        fldname.requestFocusInWindow();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        estado.cambiarModoConsulta();
        actualizar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        estado.cambiarModoConsulta();
        actualizar();
    }//GEN-LAST:event_btnEjecutarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        estado.cambiarModoConsulta();
        actualizar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaInventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barraEstado;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JRadioButton btnHerramientas;
    private javax.swing.JRadioButton btnMateriales;
    private javax.swing.JButton btnModificar;
    private javax.swing.ButtonGroup btnProducto;
    private javax.swing.JComboBox<String> comboCapacidad;
    private javax.swing.JTextField fieldPesoKg;
    private javax.swing.JTextField fldCant;
    private javax.swing.JTextField fldTamano;
    private javax.swing.JTextField fldcodigo;
    private javax.swing.JTextField fldmedida;
    private javax.swing.JTextField fldname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JLabel lblCant;
    private javax.swing.JLabel lblCapacidad;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblInventario;
    private javax.swing.JLabel lblKg;
    private javax.swing.JLabel lblMedida;
    private javax.swing.JLabel lblNombre;
    // End of variables declaration//GEN-END:variables
}
