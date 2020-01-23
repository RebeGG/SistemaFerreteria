package sistemaferreteria.Vista;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import sistemaferreteria.Controlador.Controlador;
import sistemaferreteria.Modelo.Entidades.Herramienta;
import sistemaferreteria.Modelo.Entidades.Material;
import sistemaferreteria.Modelo.Entidades.Producto;
import sistemaferreteria.Modelo.Modelo;

public class VentanaFactura extends javax.swing.JFrame implements Observer {

    private EstadoFormulario estado;
    private Controlador gestorPrincipal;
    private Modelo modelo = null;

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
        definirManejadores();
        setLocationRelativeTo(null);
    }

    public void init() {
        gestorPrincipal.registrar(this);
        setVisible(true);
        actualizar();
    }

    public void actualizar() {
        actualizarBotones();
        actualizarCampos();
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
        PropertyChangeListener change = (PropertyChangeEvent e) -> {
            estado.setModificado(true);
            actualizarBotones();
        };

        campoCodigo.getDocument().addDocumentListener(docCnt);
        campoNombre.getDocument().addDocumentListener(docCnt);
        spinnerCant.addPropertyChangeListener(change);
        spinnerPeso.addPropertyChangeListener(change);
        campoPrecio.getDocument().addDocumentListener(docCnt);
        campoTotal.getDocument().addDocumentListener(docCnt);
    }

    public void actualizarBotones() {
        btnNuevo.setEnabled(estado.puedeAgregar() && !estado.enModoConsulta());
        btnAgregar.setEnabled(estado.puedeGuardar() && !estado.enModoConsulta() && !estado.puedeBuscar());
        btnBuscar.setEnabled(estado.puedeBuscar());
        btnCancelar.setEnabled(estado.puedeCancelar());
        btnEjecutar.setEnabled(estado.puedeEjecutar());
        btnGuardar.setEnabled(estado.puedeEjecutar() && estado.puedeAgregar());
        btnHerramientas.setEnabled(estado.enModoAgregar() || estado.enModoBusqueda());
        btnMateriales.setEnabled(estado.enModoAgregar() || estado.enModoBusqueda());
    }

    public void actualizarCampos() {
        if (btnHerramientas.isSelected() && (!estado.enModoAgregar()) && (estado.getRegistroActual() != null)) {
            Herramienta actual = (Herramienta) estado.getRegistroActual();
            campoCodigo.setText(actual.getCodigo());
            campoNombre.setText(actual.getNombre());
            campoPrecio.setText(Double.toString(actual.getPrecio()));
            spinnerCant.setValue(actual.getCantidadUnidades());
            spinnerPeso.setEnabled(false);
        }
        if (btnMateriales.isSelected() && (!estado.enModoAgregar()) && (estado.getRegistroActual() != null)) {
            Material actual = (Material) estado.getRegistroActual();
            campoCodigo.setText(actual.getCodigo());
            campoNombre.setText(actual.getNombre());
            campoPrecio.setText(Double.toString(actual.getPrecio()));
            spinnerCant.setEnabled(false);
            spinnerPeso.setValue(actual.getPesoKg());

        } else {
            campoCodigo.setText(null);
            campoNombre.setText(null);
            campoPrecio.setText(null);
            spinnerCant.setValue(0);
            spinnerPeso.setValue(0.5);
        }
        campoCodigo.setEnabled(estado.enModoBusqueda() || estado.enModoAgregar());
        campoNombre.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda());
        campoPrecio.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda());
        spinnerCant.setEnabled(!estado.enModoConsulta() && estado.enModoBusqueda() && btnHerramientas.isSelected());
        spinnerPeso.setEnabled(!estado.enModoConsulta() && estado.enModoBusqueda() && btnMateriales.isSelected());
        campoTotal.setEnabled(false);
    }

    private Material actualizarRegistroMaterial(Material r) {
        if (r != null) {
            r.setCodigo(campoCodigo.getText());
            r.setNombre(campoNombre.getText());
            r.setPesoKg((double) spinnerPeso.getValue());
            r.setPrecio(Double.parseDouble(campoPrecio.getText()));
        }
        return r;
    }

    private Herramienta actualizarRegistroHerramienta(Herramienta r) {
        if (r != null) {
            r.setCodigo(campoCodigo.getText());
            r.setNombre(campoNombre.getText());
            r.setCantidadUnidades((int) spinnerCant.getValue());
            r.setPrecio(Double.parseDouble(campoPrecio.getText()));
        }
        return r;
    }

    private Producto filtro(int opcion) {
        String codigo;
        int cantidadUnidades;
        double pesoKg;
        Producto r = null;
        switch (opcion) {
            case 1:
                r = new Herramienta();
                Herramienta x = (Herramienta) r;
                codigo = campoCodigo.getText();
                cantidadUnidades = (int) spinnerCant.getValue();
                if (codigo.isEmpty() && cantidadUnidades == 0) {
                    JOptionPane.showMessageDialog(this, "Favor ingresar todos los datos", "", JOptionPane.ERROR_MESSAGE);
                } else {
                    x.setCodigo(codigo);
                    x.setCantidadUnidades(cantidadUnidades);
                    return x;
                }
            case 2:
                r = new Material();
                Material n = (Material) r;
                codigo = campoCodigo.getText();
                pesoKg = (double) spinnerPeso.getValue();
                if (codigo.isEmpty() && pesoKg == 0.0) {
                    JOptionPane.showMessageDialog(this, "Favor ingresar todos los datos", "", JOptionPane.ERROR_MESSAGE);
                } else {
                    n.setCodigo(codigo);
                    n.setPesoKg(pesoKg);
                    return n;
                }
        }
        return null;
    }

    private void actualizaCamposHerramienta(Producto p) {
        Herramienta h = (Herramienta)p;
        campoCodigo.setText(h.getCodigo());
        campoNombre.setText(h.getNombre());
        spinnerCant.setValue(h.getCantidadUnidades());
        campoPrecio.setText(Double.toString(h.getPrecio()));
    }

    private void actualizaCamposMateriales(Producto p) {
        Material m = (Material)p;
        campoCodigo.setText(m.getCodigo());
        campoNombre.setText(m.getNombre());
        spinnerCant.setValue(m.getPesoKg());
        campoPrecio.setText(Double.toString(m.getPrecio()));
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
        barraEstado = new javax.swing.JPanel();
        lblEtiqueta = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        campoTotal = new javax.swing.JTextField();
        btnEjecutar = new javax.swing.JButton();

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
        btnHerramientas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHerramientasActionPerformed(evt);
            }
        });
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
        btnMateriales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterialesActionPerformed(evt);
            }
        });
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
        gridBagConstraints.gridx = 7;
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

        spinnerCant.setModel(new javax.swing.SpinnerNumberModel(0, 0, 40, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(spinnerCant, gridBagConstraints);

        spinnerPeso.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 20.0d, 0.5d));
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

        barraEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblEtiqueta.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblEtiqueta.setText("Promedio");
        barraEstado.add(lblEtiqueta);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 0, 0);
        getContentPane().add(barraEstado, gridBagConstraints);

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
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
        gridBagConstraints.gridx = 7;
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
        gridBagConstraints.gridx = 7;
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
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 7);
        getContentPane().add(btnCancelar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(campoTotal, gridBagConstraints);

        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 7);
        getContentPane().add(btnEjecutar, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        estado.cambiarModoBuscar();
        actualizar();
        campoCodigo.requestFocusInWindow();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        estado.cambiarModoBuscar();
        actualizar();
        campoCodigo.requestFocusInWindow();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (estado.enModoActualizacion()) {
            if (btnHerramientas.isSelected()) {
                Herramienta p = (Herramienta) estado.getRegistroActual();
                estado.setRegistroActual(p);
                try {
                    gestorPrincipal.agregarProductoFactura(p);
                } catch (Exception ex) {
                    Logger.getLogger(VentanaFactura.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Material p = (Material) estado.getRegistroActual();
                estado.setRegistroActual(p);
                try {
                    gestorPrincipal.agregarProductoFactura(p);
                } catch (Exception ex) {
                    Logger.getLogger(VentanaFactura.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        estado.cambiarModoConsulta();
        actualizar();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "¿Ya terminó su facturación?", "", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_CANCEL_OPTION) {
            try {
                gestorPrincipal.agregarFactura();
            } catch (Exception ex) {
                Logger.getLogger(VentanaFactura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        estado.cambiarModoConsulta();
        actualizar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        estado.cambiarModoConsulta();
        actualizar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnHerramientasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHerramientasActionPerformed
        actualizar();
    }//GEN-LAST:event_btnHerramientasActionPerformed

    private void btnMaterialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterialesActionPerformed
        actualizar();
    }//GEN-LAST:event_btnMaterialesActionPerformed

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        if (estado.enModoBusqueda()) {
            if (btnHerramientas.isSelected()) {
                Herramienta p = (Herramienta) filtro(1);
                //estado.setRegistroActual(p);
                try {
                    estado.setRegistroActual(gestorPrincipal.obtenerProducto(p));
                    actualizaCamposHerramienta(p);
                } catch (Exception ex) {
                    Logger.getLogger(VentanaFactura.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Material p = (Material)filtro(2);
                //estado.setRegistroActual(p);
                try {
                    estado.setRegistroActual(gestorPrincipal.obtenerProducto(p));
                } catch (Exception ex) {
                    Logger.getLogger(VentanaFactura.class.getName()).log(Level.SEVERE, null, ex);
                }
                actualizaCamposMateriales(p);
            }
        }
        //estado.cambiarModoModificar();
        //actualizar();
    }//GEN-LAST:event_btnEjecutarActionPerformed

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
    private javax.swing.JPanel barraEstado;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEjecutar;
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

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
