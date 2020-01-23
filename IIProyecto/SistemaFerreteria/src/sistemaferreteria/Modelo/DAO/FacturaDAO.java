package sistemaferreteria.Modelo.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import sistemaferreteria.Modelo.Entidades.Factura;
import sistemaferreteria.Modelo.Entidades.Detalle;
import sistemaferreteria.Modelo.Entidades.Herramienta;
import sistemaferreteria.Modelo.Entidades.Material;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//     (FacturaDAO)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019
public class FacturaDAO {

    private static FacturaDAO instancia = null;
    private Properties cfg;
    private String baseDatos;
    private String usuario;
    private String clave;

    private static final String CMD_AGREGAR
            = "INSERT INTO factura (numero, fecha, total) "
            + "VALUES (?, ?, ?); ";
    private static final String CMD_AGREGAR_DETALLE_MATERIAL
            = "INSERT INTO detalle (secuencia, numero_factura, codigo_material, precio_total) "
            + "VALUES (?, ?, ?, ?); ";
    private static final String CMD_ACTUALIZAR
            = "UPDATE detalle SET secuencia=?, codigo_material=?, codigo_herramienta=?, precio_total=? "
            + "WHERE numero_factura=?; ";
    private static final String CMD_AGREGAR_DETALLE_HERRAMIENTA
            = "INSERT INTO detalle (secuencia, numero_factura, codigo_herramienta, precio_total) "
            + "VALUES (?, ?, ?, ?); ";

    private FacturaDAO() {
        this.cfg = new Properties();
        try {
            this.cfg.load(getClass().getResourceAsStream("configuracion.properties"));
            this.baseDatos = cfg.getProperty("base_datos");
            this.usuario = cfg.getProperty("usuario");
            this.clave = cfg.getProperty("clave");
        } catch (IOException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }

    public boolean agregar(Factura nuevaFactura) throws SQLException {
        boolean exito1 = false, exito2 = false, exito3 = false;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR)) {
            stm.clearParameters();

            stm.setInt(1, nuevaFactura.getNumero());
            stm.setDate(2, (Date) nuevaFactura.getFecha());
            stm.setDouble(3, nuevaFactura.getTotal());

            exito2 = stm.executeUpdate() == 1;
        }

        for (Detalle d : nuevaFactura.getProductos()) {
            if (d.getProducto().getClass().equals(Material.class)) {
                try (Connection cnx = obtenerConexion();
                        PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR_DETALLE_MATERIAL)) {
                    stm.clearParameters();

                    stm.setInt(1, d.getSecuencia());
                    stm.setInt(2, nuevaFactura.getNumero());
                    stm.setString(3, d.getProducto().getCodigo());
                    stm.setDouble(4, d.getPrecio_total());

                    exito2 = stm.executeUpdate() == 1;
                }
            }
            else{
                try (Connection cnx = obtenerConexion();
                        PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR_DETALLE_HERRAMIENTA)) {
                    stm.clearParameters();

                    stm.setInt(1, d.getSecuencia());
                    stm.setInt(2, nuevaFactura.getNumero());
                    stm.setString(3, d.getProducto().getCodigo());
                    stm.setDouble(4, d.getPrecio_total());

                    exito3 = stm.executeUpdate() == 1;
                }
                
            }

        }

        return exito1 && (exito2 || exito3);
    }

    public boolean actualizar(Factura facturaActual) throws SQLException {
        boolean exito = false;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR)) {
            stm.clearParameters();

            for (Detalle d : facturaActual.getProductos()) {
                stm.setInt(1, d.getSecuencia());
                stm.setString(2, d.getProducto().getCodigo());
                stm.setDouble(3, d.getPrecio_total());
                stm.setInt(4, facturaActual.getNumero());
            }

            exito = stm.executeUpdate() == 1;
        }

        return exito;
    }

    public static FacturaDAO obtenerInstancia() {
        if (instancia == null) {
            instancia = new FacturaDAO();
        }
        return instancia;
    }

    private Connection obtenerConexion() throws SQLException {
        return GestorBD.obtenerInstancia().obtenerConexion(baseDatos, usuario, clave);
    }
}
