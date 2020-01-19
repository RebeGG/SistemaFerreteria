package sistemaferreteria;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sistemaferreteria.Controlador.Controlador;
import sistemaferreteria.Vista.VentanaInventario;
import sistemaferreteria.Vista.VentanaPrincipal;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//  (SistemaFerreteria)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class SistemaFerreteria {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | UnsupportedLookAndFeelException e) {
        }

        new SistemaFerreteria().init();
    }

    public void init() {
        Controlador controlador = new Controlador();
//        try {
//            controlador.cargar();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal("Inventario",controlador).init();
        });
    }
    
}
