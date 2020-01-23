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

public class VentanaInventario extends javax.swing.JFrame implements Observer {

    private Controlador gestorPrincipal;
    private EstadoFormulario estado;
    private VentanaTablaHerramientas inventarioHerramientas;
    private VentanaTablaMateriales inventarioMateriales;
    private Modelo modelo = null;

    public VentanaInventario() {
        this("Inventario", null);
    }

    public VentanaInventario(String titulo, Controlador nuevoGestor) {
        super(titulo);
        this.gestorPrincipal = nuevoGestor;
        this.estado = new EstadoFormulario();
        this.inventarioHerramientas = new VentanaTablaHerramientas("Tabla Inventario de Herramientas", this.gestorPrincipal);
        this.inventarioMateriales = new VentanaTablaMateriales("Tabla Inventario de Materiales", this.gestorPrincipal);
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
        PropertyChangeListener change = (PropertyChangeEvent e) -> {
            estado.setModificado(true);
            actualizarBotones();
        };

        fldcodigo.getDocument().addDocumentListener(docCnt);
        fldname.getDocument().addDocumentListener(docCnt);
        fldmedida.getDocument().addDocumentListener(docCnt);
        comboCapacidad.addItemListener(itemCnt);
        spinnerCant.addPropertyChangeListener(change);
        spinnerPeso.addPropertyChangeListener(change);
        fieldPrecio.getDocument().addDocumentListener(docCnt);
    }

    public void actualizarBotones() {
        btnAgregar.setEnabled(estado.puedeAgregar());
        btnModificar.setEnabled(estado.puedeModificar());
        btnGuardar.setEnabled(estado.puedeGuardar());
        btnBuscar.setEnabled(estado.puedeBuscar());
        btnCancelar.setEnabled(estado.puedeCancelar());
        btnEjecutar.setEnabled(estado.puedeEjecutar());
        btnHerramientas.setEnabled(estado.enModoAgregar() || estado.enModoBusqueda());
        btnMateriales.setEnabled(estado.enModoAgregar() || estado.enModoBusqueda());
    }

    public void actualizarCampos() {
        if (btnHerramientas.isSelected() && (estado.getRegistroActual() != null)) {
            Herramienta actual = (Herramienta) estado.getRegistroActual();
            fldcodigo.setText(actual.getCodigo());
            fldname.setText(actual.getNombre());
            fldTamano.setEnabled(false);
            comboCapacidad.setSelectedIndex(actual.getCapacidad());
            fldmedida.setText(actual.getMedida());
            spinnerCant.setValue(actual.getCantidadUnidades());
            spinnerPeso.setEnabled(false);
            fieldPrecio.setText(Double.toString(actual.getPrecio()));
        }
        if (btnMateriales.isSelected() && (estado.getRegistroActual() != null)) {
            Material actual = (Material) estado.getRegistroActual();
            fldcodigo.setText(actual.getCodigo());
            fldname.setText(actual.getNombre());
            fldTamano.setText(actual.getTamano());
            comboCapacidad.setEnabled(false);
            fldmedida.setText(actual.getMedida());
            spinnerCant.setEnabled(false);
            spinnerPeso.setValue(actual.getPesoKg());
            fieldPrecio.setText(Double.toString(actual.getPrecio()));

        } else {
            fldcodigo.setText(null);
            fldname.setText(null);
            fldTamano.setText(null);
            comboCapacidad.setSelectedItem(null);
            fldmedida.setText(null);
            spinnerCant.setValue(0);
            spinnerPeso.setValue(0.0);
            fieldPrecio.setText(null);
        }
        fldcodigo.setEnabled(estado.enModoAgregar() && !estado.enModoBusqueda());
        fldname.setEnabled(!estado.enModoConsulta() || estado.enModoBusqueda());
        fldmedida.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda());
        comboCapacidad.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda() && btnHerramientas.isSelected());
        spinnerCant.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda() && btnHerramientas.isSelected());
        spinnerPeso.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda() && btnMateriales.isSelected());
        fldTamano.setEnabled(!estado.enModoConsulta() && !estado.enModoBusqueda() && btnMateriales.isSelected());
        fieldPrecio.setEnabled(estado.enModoAgregar() && !estado.enModoBusqueda());
    }

    private Producto capturarRegistro() {
        if (btnHerramientas.isSelected()) {
            return new Herramienta(
                    fldcodigo.getText(),
                    fldname.getText(),
                    fldmedida.getText(),
                    Double.parseDouble(fieldPrecio.getText()),
                    comboCapacidad.getSelectedIndex(),
                    (int) spinnerCant.getValue());
        }
        return new Material(
                fldcodigo.getText(),
                fldname.getText(),
                fldmedida.getText(),
                Double.parseDouble(fieldPrecio.getText()),
                fldTamano.getText(),
                (double) spinnerPeso.getValue()
        );
    }

    private Herramienta capturarRegistroHerramienta() {
        double precio;
        if (fieldPrecio.getText().isEmpty()) {
            precio = 0;
        } else {
            precio = Double.parseDouble(fieldPrecio.getText());
        }
        Herramienta r;
        r = new Herramienta(
                fldcodigo.getText(),
                fldname.getText(),
                fldmedida.getText(),
                precio,
                comboCapacidad.getSelectedIndex(),
                (int) spinnerCant.getValue()
        );
        return r;
    }

    private Material capturarRegistroMaterial() {
        double precio;
        if (fieldPrecio.getText().isEmpty()) {
            precio = 0;
        } else {
            precio = Double.parseDouble(fieldPrecio.getText());
        }
        Material r;
        r = new Material(
                fldcodigo.getText(),
                fldname.getText(),
                fldmedida.getText(),
                precio,
                fldTamano.getText(),
                (double) spinnerPeso.getValue()
        );
        return r;
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

    private Material actualizarRegistroMaterial(Material r) {
        if (r != null) {
            r.setCodigo(fldcodigo.getText());
            r.setNombre(fldname.getText());
            r.setMedida(fldmedida.getText());
            r.setTamano(fldTamano.getText());
            r.setPesoKg((double) spinnerPeso.getValue());
        }
        return r;
    }

    private Herramienta actualizarRegistroHerramienta(Herramienta r) {
        if (r != null) {
            r.setCodigo(fldcodigo.getText());
            r.setNombre(fldname.getText());
            r.setMedida(fldmedida.getText());
            r.setCapacidad(comboCapacidad.getSelectedIndex());
            r.setCantidadUnidades((int) spinnerCant.getValue());
        }
        return r;
    }

//    private Producto filtro(int opcion) {
//        Producto p = null;
//        String nombre;
//        switch (opcion) {
//            case 1:
//                p = new Herramienta();
//                break;
//            case 2:
//                p = new Herramienta();
//                nombre = fldname.getText();
//                p.setNombre(nombre);
//                break;
//            case 3:
//                p = new Material();
//                break;
//            case 4:
//                p = new Material();
//                nombre = fldname.getName();
//                p.setNombre(nombre);
//                break;
//        }
//        return p;
//    }
//    
//    private Herramienta filtroHerramienta(int opcion){
//        String nombre;
//        Herramienta p = new Herramienta();
//        switch(opcion){
//            case 1:
//                return p;
//            case 2:
//                nombre = fldname.getText();
//                p.setNombre(nombre);
//                break;
//        }
//        return p;
//    }
//    
//        private Material filtroMaterial(int opcion){
//        String nombre;
//        Material p = new Material();
//        switch(opcion){
//            case 1:
//                return p;
//            case 2:
//                nombre = fldname.getText();
//                p.setNombre(nombre);
//                break;
//        }
//        return p;
//    }
//    
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
        lblPrecio = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnHerramientas = new javax.swing.JRadioButton();
        btnMateriales = new javax.swing.JRadioButton();
        fldname = new javax.swing.JTextField();
        fldcodigo = new javax.swing.JTextField();
        fldmedida = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEjecutar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        fldTamano = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        comboCapacidad = new javax.swing.JComboBox<>();
        barraEstado = new javax.swing.JPanel();
        lblPromedio1 = new javax.swing.JLabel();
        lblPromedio2 = new javax.swing.JLabel();
        lblPromedio3 = new javax.swing.JLabel();
        lblPromedioConsulta = new javax.swing.JLabel();
        lblPromedioAgregar = new javax.swing.JLabel();
        lblPromedioModificar = new javax.swing.JLabel();
        lblKg = new javax.swing.JLabel();
        fieldPrecio = new javax.swing.JTextField();
        spinnerCant = new javax.swing.JSpinner();
        spinnerPeso = new javax.swing.JSpinner();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        cerrarApp = new javax.swing.JMenuItem();

        setSize(new java.awt.Dimension(800, 500));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblInventario.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 24)); // NOI18N
        lblInventario.setText("Inventario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 143, 0, 8);
        getContentPane().add(lblInventario, gridBagConstraints);

        lblCodigo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblCodigo.setText("Código");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 7, 4, 0);
        getContentPane().add(lblCodigo, gridBagConstraints);

        lblNombre.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblNombre.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 7, 4, 0);
        getContentPane().add(lblNombre, gridBagConstraints);

        lblMedida.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblMedida.setText("Medida");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 7, 4, 0);
        getContentPane().add(lblMedida, gridBagConstraints);

        lblCapacidad.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblCapacidad.setText("Capacidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 7, 4, 0);
        getContentPane().add(lblCapacidad, gridBagConstraints);

        lblCant.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblCant.setText("Cantidad de Unidades");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 7, 4, 0);
        getContentPane().add(lblCant, gridBagConstraints);

        lblPrecio.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblPrecio.setText("Precio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 7, 4, 0);
        getContentPane().add(lblPrecio, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel1.setText("Producto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 7, 4, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        btnProducto.add(btnHerramientas);
        btnHerramientas.setText("Herramientas");
        btnHerramientas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHerramientasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(btnHerramientas, gridBagConstraints);

        btnProducto.add(btnMateriales);
        btnMateriales.setText("Materiales");
        btnMateriales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterialesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        getContentPane().add(btnMateriales, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 0);
        getContentPane().add(fldname, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 0);
        getContentPane().add(fldcodigo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 0);
        getContentPane().add(fldmedida, gridBagConstraints);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 7);
        getContentPane().add(btnBuscar, gridBagConstraints);

        btnAgregar.setText("Añadir");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 7);
        getContentPane().add(btnAgregar, gridBagConstraints);

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 7);
        getContentPane().add(btnModificar, gridBagConstraints);

        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 7);
        getContentPane().add(btnEjecutar, gridBagConstraints);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 7);
        getContentPane().add(btnCancelar, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel2.setText("Tamaño");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 7, 4, 0);
        getContentPane().add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 0);
        getContentPane().add(fldTamano, gridBagConstraints);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 7);
        getContentPane().add(btnGuardar, gridBagConstraints);

        comboCapacidad.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        comboCapacidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Liviana", "Mediana", "Pesada" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 0);
        getContentPane().add(comboCapacidad, gridBagConstraints);

        barraEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        barraEstado.setLayout(new java.awt.GridBagLayout());

        lblPromedio1.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblPromedio1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPromedio1.setText("Promedio Consulta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 1);
        barraEstado.add(lblPromedio1, gridBagConstraints);

        lblPromedio2.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblPromedio2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPromedio2.setText("Promedio Agregar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        barraEstado.add(lblPromedio2, gridBagConstraints);

        lblPromedio3.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblPromedio3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPromedio3.setText("Promedio Modificar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        barraEstado.add(lblPromedio3, gridBagConstraints);

        lblPromedioConsulta.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblPromedioConsulta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        barraEstado.add(lblPromedioConsulta, gridBagConstraints);

        lblPromedioAgregar.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblPromedioAgregar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        barraEstado.add(lblPromedioAgregar, gridBagConstraints);

        lblPromedioModificar.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblPromedioModificar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        barraEstado.add(lblPromedioModificar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        getContentPane().add(barraEstado, gridBagConstraints);

        lblKg.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblKg.setText("Peso en kg");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 7, 4, 0);
        getContentPane().add(lblKg, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 0);
        getContentPane().add(fieldPrecio, gridBagConstraints);

        spinnerCant.setModel(new javax.swing.SpinnerNumberModel(0, null, 50, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 0);
        getContentPane().add(spinnerCant, gridBagConstraints);

        spinnerPeso.setModel(new javax.swing.SpinnerNumberModel(0.5d, 0.5d, 20.0d, 0.5d));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 0);
        getContentPane().add(spinnerPeso, gridBagConstraints);

        jMenu1.setText("Archivo");

        cerrarApp.setText("Cerrar");
        cerrarApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarAppActionPerformed(evt);
            }
        });
        jMenu1.add(cerrarApp);

        jMenuBar1.add(jMenu1);

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
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        estado.cambiarModoModificar();
        actualizar();
        fldname.requestFocusInWindow();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (estado.enModoAgregar()) {
            Producto p = capturarRegistro();
            estado.setRegistroActual(p);
            try {
                gestorPrincipal.agregarProducto(p);
            } catch (Exception ex) {
                Logger.getLogger(VentanaInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (estado.enModoActualizacion()) {
            if (btnHerramientas.isSelected()) {
                Herramienta p = (Herramienta) estado.getRegistroActual();
                if (p != null) {
                    p = actualizarRegistroHerramienta(p);
                }
                estado.setRegistroActual(p);
                try {
                    gestorPrincipal.actualizarProducto(p);
                } catch (Exception ex) {
                    Logger.getLogger(VentanaInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (btnMateriales.isSelected()) {
                Material p = (Material) estado.getRegistroActual();
                if (p != null) {
                    p = actualizarRegistroMaterial(p);
                }
                estado.setRegistroActual(p);
                try {
                    gestorPrincipal.actualizarProducto(p);
                } catch (Exception ex) {
                    Logger.getLogger(VentanaInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Ya existe este producto", "", JOptionPane.OK_OPTION);
        }
        estado.cambiarModoConsulta();
        actualizar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        if (estado.enModoBusqueda()) {
            if (btnHerramientas.isSelected() && fldname.getText().isEmpty()) {
                Herramienta h = (Herramienta) capturarRegistroHerramienta();
                estado.setRegistroActual(h);
                try {
                    gestorPrincipal.listarProductos(h);
                    estado.setRegistroActual(h);
                    System.out.println("Entro al filtro 1");
                    JOptionPane.showMessageDialog(this, "Búsqueda se ha realizado correctamente", "BUSQUEDA-FINALIZADA", JOptionPane.PLAIN_MESSAGE);
                    //actualizaCampos(p);
                } catch (Exception ex) {
                    System.out.println("I cant name");
                    Logger.getLogger(VentanaInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (btnHerramientas.isSelected() && !fldname.getText().isEmpty()) {
                Herramienta h = (Herramienta) capturarRegistroHerramienta();
                estado.setRegistroActual(h);
                try {
                    gestorPrincipal.listarHerramientas(fldname.getText());
                    estado.setRegistroActual(h);
                    System.out.println("Entro al filtro 2");
                    JOptionPane.showMessageDialog(this, "Búsqueda se ha realizado correctamente", "BUSQUEDA-FINALIZADA", JOptionPane.PLAIN_MESSAGE);
                    //actualizaCampos(p);
                } catch (Exception ex) {
                    System.out.println("I cant tool");
                    Logger.getLogger(VentanaInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (btnMateriales.isSelected() && fldname.getText().isEmpty()) {
                Material p = (Material) capturarRegistroMaterial();
                estado.setRegistroActual(p);
                try {
                    gestorPrincipal.listarProductos(p);
                    estado.setRegistroActual(p);
                    System.out.println("Entro al filtro 3");
                    JOptionPane.showMessageDialog(this, "Búsqueda se ha realizado correctamente", "BUSQUEDA-FINALIZADA", JOptionPane.PLAIN_MESSAGE);
                    //actualizaCampos(p);
                } catch (Exception ex) {
                    System.out.println("I cant nameM");
                    Logger.getLogger(VentanaInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (btnMateriales.isSelected() && !fldname.getText().isEmpty()) {
                Material p = (Material) capturarRegistroMaterial();
                estado.setRegistroActual(p);
                try {
                    gestorPrincipal.listarMateriales(fldname.getText());
                    estado.setRegistroActual(p);
                    System.out.println("Entro al filtro 4");
                    JOptionPane.showMessageDialog(this, "Búsqueda se ha realizado correctamente", "BUSQUEDA-FINALIZADA", JOptionPane.PLAIN_MESSAGE);
                    //actualizaCampos(p);
                } catch (Exception ex) {
                    System.out.println("I cant material");
                    Logger.getLogger(VentanaInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("bye:C");
            throw new UnsupportedOperationException();
        }
        estado.cambiarModoConsulta();
        btnProducto.clearSelection();
        actualizar();
    }//GEN-LAST:event_btnEjecutarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        estado.cambiarModoConsulta();
        actualizar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cerrarAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarAppActionPerformed
        gestorPrincipal.cerrarAplicacion();
    }//GEN-LAST:event_cerrarAppActionPerformed

    private void btnHerramientasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHerramientasActionPerformed
        inventarioHerramientas.init();
        actualizar();
    }//GEN-LAST:event_btnHerramientasActionPerformed

    private void btnMaterialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterialesActionPerformed
        inventarioMateriales.init();
        actualizar();
    }//GEN-LAST:event_btnMaterialesActionPerformed

    @Override
    public void update(Observable o, Object arg) {
        modelo = (Modelo) o;
        lblPromedioConsulta.setText(String.format("%d", modelo.getPromedioConsultar()));
        lblPromedioAgregar.setText(String.format("%d", modelo.getPromedioAgregar()));
        lblPromedioModificar.setText(String.format("%d", modelo.getPromedioActualizar()));
    }

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
    private javax.swing.JButton btnGuardar;
    private javax.swing.JRadioButton btnHerramientas;
    private javax.swing.JRadioButton btnMateriales;
    private javax.swing.JButton btnModificar;
    private javax.swing.ButtonGroup btnProducto;
    private javax.swing.JMenuItem cerrarApp;
    private javax.swing.JComboBox<String> comboCapacidad;
    private javax.swing.JTextField fieldPrecio;
    private javax.swing.JTextField fldTamano;
    private javax.swing.JTextField fldcodigo;
    private javax.swing.JTextField fldmedida;
    private javax.swing.JTextField fldname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblCant;
    private javax.swing.JLabel lblCapacidad;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblInventario;
    private javax.swing.JLabel lblKg;
    private javax.swing.JLabel lblMedida;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblPromedio1;
    private javax.swing.JLabel lblPromedio2;
    private javax.swing.JLabel lblPromedio3;
    private javax.swing.JLabel lblPromedioAgregar;
    private javax.swing.JLabel lblPromedioConsulta;
    private javax.swing.JLabel lblPromedioModificar;
    private javax.swing.JSpinner spinnerCant;
    private javax.swing.JSpinner spinnerPeso;
    // End of variables declaration//GEN-END:variables

}
