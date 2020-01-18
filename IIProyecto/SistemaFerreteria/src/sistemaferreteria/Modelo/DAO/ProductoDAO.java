package sistemaferreteria.Modelo.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import sistemaferreteria.Modelo.Entidades.Herramienta;
import sistemaferreteria.Modelo.Entidades.Material;
import sistemaferreteria.Modelo.Entidades.Producto;

//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//     (Producto)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class ProductoDAO {
    
    private static ProductoDAO instancia = null;

    private Properties cfg;
    private String baseDatos;
    private String usuario;
    private String clave;

    private static final String CMD_LISTAR_HERRAMIENTA
            = "SELECT codigo, nombre, medida, capacidad, cantidadUnidades FROM producto;";//creo que falta linkear la otra tabla(herramienta)
    private static final String CMD_LISTAR_MATERIAL
            = "SELECT codigo, nombre, medida, tamano, pesoKg FROM producto;";//creo que falta linkear la otra tabla(material)
    private static final String CMD_AGREGAR_HERRAMIENTA //esto de fijo es diferente
            = "INSERT INTO herramienta (codigo, nombre, medida, capacidad, cantidadUnidades) "
            + "VALUES (?, ?, ?, ?, ?);";
    private static final String CMD_AGREGAR_MATERIAL //esto de fijo es diferente
            = "INSERT INTO material (codigo, nombre, medida, tamano, pesoKg) "
            + "VALUES (?, ?, ?, ?, ?);";
    
    private ProductoDAO() {
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

    public boolean agregar(Producto producto) throws SQLException {//probalemente no esta bien, REVISAR
        boolean exito = false;

        if(producto.getClass() == Herramienta.class){
            Herramienta h = (Herramienta) producto;
            try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR_HERRAMIENTA)) {
                stm.clearParameters();
                stm.setString(1, h.getCodigo());
                stm.setString(2, h.getNombre());
                stm.setString(3, h.getMedida());
                stm.setInt(4, h.getCapacidad());
                stm.setInt(5, h.getCantidadUnidades());
            
                exito = stm.executeUpdate() == 1;
            }
        }
        else{
            Material m = (Material) producto;
            try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR_MATERIAL)) {
                stm.clearParameters();
                stm.setString(1, m.getCodigo());
                stm.setString(2, m.getNombre());
                stm.setString(3, m.getMedida());
                stm.setString(4, m.getTamano());
                stm.setDouble(5, m.getPesoKg());
            
                exito = stm.executeUpdate() == 1;
            }
        }
        return exito;
    }

    public List<Producto> listar() throws SQLException {//tal vez agregando algun distintivo al codigo se puede arreglar esto...
        List<Producto> r = new ArrayList<>();

        try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR_HERRAMIENTA)) { //CMD_LISTAR_MATERIAL 

//            //llamar a producto, como unir las dos...?
//            while (rs.next()) {
//                r.add(new Herramienta(
//                        rs.getString("codigo"),
//                        rs.getString("nombre"),
//                        rs.getString("medida"),
//                        rs.getInt("capacidad"),
//                        rs.getInt("cantidadUnidades")
//                ));
//                r.add(new Material(
//                        rs.getString("codigo"),
//                        rs.getString("nombre"),
//                        rs.getString("medida"),
//                        rs.getString("tamano"),
//                        rs.getDouble("pesoKg")
//                ));
//            }
        }

        return r;
    }
    
    public List<Producto> listar(Producto producto) throws SQLException {
        List<Producto> r = new ArrayList<>();
        
        if(producto.getClass() == Herramienta.class){
            try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR_HERRAMIENTA)) {

                while (rs.next()) {
                    r.add(new Herramienta(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("medida"),
                        rs.getInt("capacidad"),
                        rs.getInt("cantidadUnidades")
                    ));
                }
            }
        }
        else{
            try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR_MATERIAL)) {

                while (rs.next()) {
                    r.add(new Material(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("medida"),
                        rs.getString("tamano"),
                        rs.getDouble("pesoKg")
                    ));
                }
            }
        }
        return r;
    }
    
    public List<Producto> listarHerramienta(String nombre) throws SQLException {
        List<Producto> r = new ArrayList<>();
        
            try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR_HERRAMIENTA)) {

                while (rs.next()) {
                    if(rs.getObject("nombre", String.class).equals(nombre)){
                        r.add(new Herramienta(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("medida"),
                            rs.getInt("capacidad"),
                            rs.getInt("cantidadUnidades")
                        ));
                    }
                }
            }
        return r;
    }
    
    public List<Producto> listarMaterial(String nombre) throws SQLException {
        List<Producto> r = new ArrayList<>();
        
            try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR_MATERIAL)) {

                while (rs.next()) {
                    if(rs.getObject("nombre", String.class).equals(nombre)){
                        r.add(new Material(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("medida"),
                            rs.getString("tamano"),
                            rs.getDouble("pesoKg")
                        ));
                    }
                }
            }
        return r;
    }

    //not sure yet
    public Producto recuperar(String codigo) {
        throw new UnsupportedOperationException();
    }
    
    public boolean actualizar(Producto p) {
        throw new UnsupportedOperationException();
    }

    public boolean eliminar(String codigo) {
        throw new UnsupportedOperationException();
    }

    public ProductoDAO eliminarTodos() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("[\n");
        try {
            List<Producto> inventario = listar();
            for (Producto p : inventario) {
                r.append(String.format("\t%s,%n", p));
            }
        } catch (SQLException ex) {
            r.append(String.format("(Excepción: '%s')", ex.getMessage()));
        }
        r.append("]");
        return r.toString();
    }

    public static ProductoDAO obtenerInstancia() {
        if (instancia == null) {
            instancia = new ProductoDAO();
        }
        return instancia;
    }

    private Connection obtenerConexion() throws SQLException {
        return GestorBD.obtenerInstancia().obtenerConexion(baseDatos, usuario, clave);
    }
}
