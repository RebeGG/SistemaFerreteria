package sistemaferreteria.Modelo.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import sistemaferreteria.Modelo.Entidades.Factura;
import sistemaferreteria.Modelo.Entidades.Detalle;

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
    private static final String CMD_AGREGAR_DETALLE
            = "INSERT INTO detalle (numero_factura, secuencia, codigo_producto, precio_total) "
            + "VALUES (?, ?, ?, ?); ";
    private static final String CMD_ACTUALIZAR
            = "UPDATE detalle SET secuencia=?, codigo_producto=?, precio_total=? "
            + "WHERE numero_factura=?; ";
    private static final String CMD_ELIMINAR
            = "DELETE FROM factura WHERE numero=?; ";
    private static final String CMD_ELIMINAR_DETALLE_POR_FACTURA
            = "DELETE FROM detalle WHERE numero_factura=?; ";
    private static final String CMD_ELIMINAR_DETALLE
            = "DELETE FROM detalle WHERE numero_factura=?,codigo_producto=?; ";//revisar
    
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
        boolean exito1 = false, exito2 = false;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR)) {
            stm.clearParameters();
            
            stm.setInt(1, nuevaFactura.getNumero());
            stm.setDate(2, (Date) nuevaFactura.getFecha());
            stm.setDouble(3, nuevaFactura.getTotal());
            
            exito2 = stm.executeUpdate() == 1;
        }
        
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR_DETALLE)) {
            stm.clearParameters();
            
            for(Detalle d : nuevaFactura.getProductos()){
                stm.setInt(1, nuevaFactura.getNumero());
                stm.setInt(1, d.getSecuencia());
                stm.setString(1, d.getProducto().getCodigo());
                stm.setDouble(1, d.getPrecio_total());
            }
            
            exito2 = stm.executeUpdate() == 1;
        }

        return exito1 && exito2;
    }

    public boolean actualizar(Factura facturaActual) throws SQLException {
        boolean exito = false;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR)) {
            stm.clearParameters();

            for(Detalle d: facturaActual.getProductos()){
                stm.setInt(1, d.getSecuencia());
                stm.setString(2, d.getProducto().getCodigo());
                stm.setDouble(3, d.getPrecio_total());
                stm.setInt(4, facturaActual.getNumero());
            }

            exito = stm.executeUpdate() == 1;
        }

        return exito;
    }

    public boolean eliminar(int numero) throws SQLException {
        boolean exito1 = false;
        boolean exito2 = false;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ELIMINAR)) {
            stm.clearParameters();
            stm.setInt(1, numero);

            exito1 = stm.executeUpdate() == 1;
        }
        
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ELIMINAR_DETALLE_POR_FACTURA)) {
            stm.clearParameters();
            stm.setInt(1, numero);

            exito2 = stm.executeUpdate() == 1;
        }

        return exito1 && exito2;
    }
    
    public boolean eliminarDetalle(int numero, String codigo) throws SQLException {
        boolean exito = false;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ELIMINAR_DETALLE)) {
            stm.clearParameters();
            stm.setInt(1, numero);
            stm.setString(2, codigo);

            exito = stm.executeUpdate() == 1;
        }

        return exito;
    }

    public MaterialDAO eliminarTodos() {
        throw new UnsupportedOperationException();
    }

//    @Override
//    public String toString() {
//        StringBuilder r = new StringBuilder("[\n");
//        try {
//            List<Factura> facturas = listar();
//            for (Factura e : facturas) {
//                r.append(String.format("\t%s,%n", e));
//            }
//        } catch (SQLException ex) {
//            r.append(String.format("(Excepción: '%s')", ex.getMessage()));
//        }
//        r.append("]");
//        return r.toString();
//    }

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
