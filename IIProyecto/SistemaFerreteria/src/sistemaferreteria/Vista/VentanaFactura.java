package sistemaferreteria.Vista;

import sistemaferreteria.Controlador.Controlador;

public class VentanaFactura extends javax.swing.JFrame {

    private EstadoFormulario estado;
    private Controlador gestorPrincipal;

    public VentanaFactura() {
        this("Factura", null);
    }

    public VentanaFactura(String titulo, Controlador nuevoGestor) {
        super(titulo);
        this.gestorPrincipal = nuevoGestor;
        this.estado = new EstadoFormulario();
        configurar();
    }

    private void configurar() {
        initComponents();
    }

    public void init() {
        setVisible(true);
        actualizar();

    }

    public void actualizar() {

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        groupProducto = new javax.swing.ButtonGroup();
        lblSistemaFactura = new javax.swing.JLabel();
        lblProducto = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblCant = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        lblPeso = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnHerramientas = new javax.swing.JRadioButton();
        btnMateriales = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        campoCodigo = new javax.swing.JTextField();
        campoNombre = new javax.swing.JTextField();
        spinnerCant = new javax.swing.JSpinner();
        spinnerPeso = new javax.swing.JSpinner();
        campoPrecio = new javax.swing.JTextField();
        PanelEtiqueta = new javax.swing.JPanel();
        lblEtiqueta = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        campoTotal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblSistemaFactura.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 24)); // NOI18N
        lblSistemaFactura.setText("Sistema de Facturación");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 65, 5, 82);
        getContentPane().add(lblSistemaFactura, gridBagConstraints);

        lblProducto.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblProducto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblProducto.setText("Producto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblProducto, gridBagConstraints);

        lblCodigo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCodigo.setText("Código");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblCodigo, gridBagConstraints);

        lblNombre.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNombre.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblNombre, gridBagConstraints);

        lblCant.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblCant.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCant.setText("Cantidad de Unidades");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblCant, gridBagConstraints);

        lblPrecio.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblPrecio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPrecio.setText("Precio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblPrecio, gridBagConstraints);

        lblPeso.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblPeso.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPeso.setText("Peso en kg");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblPeso, gridBagConstraints);

        lblTotal.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotal.setText("Total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblTotal, gridBagConstraints);

        groupProducto.add(btnHerramientas);
        btnHerramientas.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        btnHerramientas.setText("Herramienta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        getContentPane().add(btnHerramientas, gridBagConstraints);

        groupProducto.add(btnMateriales);
        btnMateriales.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        btnMateriales.setText("Material");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(btnMateriales, gridBagConstraints);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 7);
        getContentPane().add(btnBuscar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(campoCodigo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(campoNombre, gridBagConstraints);

        spinnerCant.setModel(new javax.swing.SpinnerNumberModel(1, null, 40, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(spinnerCant, gridBagConstraints);

        spinnerPeso.setModel(new javax.swing.SpinnerNumberModel(0.5d, 0.5d, 20.0d, 0.5d));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(spinnerPeso, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(campoPrecio, gridBagConstraints);

        PanelEtiqueta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblEtiqueta.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblEtiqueta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEtiqueta.setText("Código de factura: ");
        PanelEtiqueta.add(lblEtiqueta);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 0, 0);
        getContentPane().add(PanelEtiqueta, gridBagConstraints);

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 7);
        getContentPane().add(btnNuevo, gridBagConstraints);

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 7);
        getContentPane().add(btnAgregar, gridBagConstraints);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 7);
        getContentPane().add(btnGuardar, gridBagConstraints);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 7);
        getContentPane().add(btnCancelar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(campoTotal, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
      
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
      
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaFactura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelEtiqueta;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JRadioButton btnHerramientas;
    private javax.swing.JRadioButton btnMateriales;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JTextField campoCodigo;
    private javax.swing.JTextField campoNombre;
    private javax.swing.JTextField campoPrecio;
    private javax.swing.JTextField campoTotal;
    private javax.swing.ButtonGroup groupProducto;
    private javax.swing.JLabel lblCant;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblEtiqueta;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPeso;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblSistemaFactura;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JSpinner spinnerCant;
    private javax.swing.JSpinner spinnerPeso;
    // End of variables declaration//GEN-END:variables
}
