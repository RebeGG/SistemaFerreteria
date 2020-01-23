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
import sistemaferreteria.Modelo.Tabla.Herramienta.ModeloTablaInventarioH;
import sistemaferreteria.Modelo.Tabla.Herramienta.ModeloColumnasH;
import sistemaferreteria.Vista.Tabla.Herramienta.TablaInventarioHerramientas;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//         II Proyecto
//  (VentanaTablaHerramientas)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class VentanaTablaHerramientas extends JFrame implements Observer{
    private Controlador controlador;
    private JPanel panelPrincipal;
    private JScrollPane controlDesplazamientoTabla;
    private TablaInventarioHerramientas tablaInventario;

    public VentanaTablaHerramientas(String titulo, Controlador nuevoGestor) {
        super(titulo);
        this.controlador = nuevoGestor;
        configurar();
    }

    private void configurar() {
        ajustarComponentes(getContentPane());
        setResizable(true);
        pack();
        setMinimumSize(getSize());
        setSize(getWidth()*9 / 5, getHeight());
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void ajustarComponentes(Container c) {
        c.setLayout(new BorderLayout());

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        controlDesplazamientoTabla = new JScrollPane(
                tablaInventario = new TablaInventarioHerramientas(
                        new ModeloTablaInventarioH(controlador.getDatos().obtenerModeloTablaH()),
                        new ModeloColumnasH())
        );

        panelPrincipal.add(BorderLayout.CENTER, controlDesplazamientoTabla);

        c.add(BorderLayout.CENTER, panelPrincipal);
    }

    public void init() {
        controlador.registrarTablaH(this);
        setVisible(true);
    }

    @Override
    public void update(Observable m, Object evt) {
        tablaInventario.actualizar();
    }
}
