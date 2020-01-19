/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaferreteria.Vista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import sistemaferreteria.Controlador.Controlador;
import sistemaferreteria.Modelo.Tabla.ModeloColumnas;
import sistemaferreteria.Modelo.Tabla.ModeloTablaInventario;
import sistemaferreteria.Vista.Tabla.TablaInventario;

/**
 *
 * @author Fernanda
 */
public class VentanaTabla extends JFrame implements Observer{
    
    private Controlador controlador;
    private JPanel panelPrincipal;
    private JScrollPane controlDesplazamientoTabla;
    private TablaInventario tablaInventario;

    public VentanaTabla(String titulo, Controlador nuevoGestor) {
        super(titulo);
        this.controlador = nuevoGestor;
        configurar();
    }

    private void configurar() {
        ajustarComponentes(getContentPane());
        setResizable(true);
        pack();
        setMinimumSize(getSize());
        setSize(getWidth()*7 / 3, getHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void ajustarComponentes(Container c) {
        c.setLayout(new BorderLayout());

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        controlDesplazamientoTabla = new JScrollPane(
                tablaInventario = new TablaInventario(
                        new ModeloTablaInventario(controlador.getDatos().obtenerModeloTabla()),
                        new ModeloColumnas())
        );

        panelPrincipal.add(BorderLayout.CENTER, controlDesplazamientoTabla);

        c.add(BorderLayout.CENTER, panelPrincipal);
    }

    public void init() {
        controlador.registrarTabla(this);
        setVisible(true);
    }

    @Override
    public void update(Observable m, Object evt) {
        tablaInventario.actualizar();
    }
    
}
