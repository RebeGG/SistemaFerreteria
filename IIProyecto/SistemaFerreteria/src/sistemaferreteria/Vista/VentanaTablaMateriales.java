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
import sistemaferreteria.Modelo.Tabla.Material.ModeloTablaInventarioM;
import sistemaferreteria.Modelo.Tabla.Material.ModeloColumnasM;
import sistemaferreteria.Vista.Tabla.Material.TablaInventarioMateriales;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//         II Proyecto
//  (VentanaTablaMateriales)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class VentanaTablaMateriales extends JFrame implements Observer{
    private Controlador controlador;
    private JPanel panelPrincipal;
    private JScrollPane controlDesplazamientoTabla;
    private TablaInventarioMateriales tablaInventario;

    public VentanaTablaMateriales(String titulo, Controlador nuevoGestor) {
        super(titulo);
        this.controlador = nuevoGestor;
        configurar();
    }

    private void configurar() {
        ajustarComponentes(getContentPane());
        setResizable(true);
        pack();
        setMinimumSize(getSize());
        setSize(getWidth()*9 / 6, getHeight());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void ajustarComponentes(Container c) {
        c.setLayout(new BorderLayout());

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        controlDesplazamientoTabla = new JScrollPane(
                tablaInventario = new TablaInventarioMateriales(
                        new ModeloTablaInventarioM(controlador.getDatos().obtenerModeloTablaM()),
                        new ModeloColumnasM())
        );

        panelPrincipal.add(BorderLayout.CENTER, controlDesplazamientoTabla);

        c.add(BorderLayout.CENTER, panelPrincipal);
    }

    public void init() {
        controlador.registrarTablaM(this);
        setVisible(true);
    }

    @Override
    public void update(Observable m, Object evt) {
        tablaInventario.actualizar();
    }
}
