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

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//   (HerramientaDAO)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class HerramientaDAO {
    
    private static HerramientaDAO instancia = null;
    private Properties cfg;
    private String baseDatos;
    private String usuario;
    private String clave;

    private static final String CMD_LISTAR
            = "SELECT codigo, nombre, medida, capacidad, cantidadUnidades, precio FROM herramienta; ";
    private static final String CMD_LISTAR_POR_NOMBRE
            = "SELECT codigo, nombre, medida, capacidad, cantidadUnidades, precio FROM herramienta WHERE nombre=?; ";
    private static final String CMD_RECUPERAR
            = "SELECT codigo, nombre, medida, capacidad, cantidadUnidades, precio FROM herramienta WHERE codigo=?; ";
    private static final String CMD_AGREGAR
            = "INSERT INTO herramienta (codigo, nombre, medida, capacidad, cantidadUnidades, precio) "
            + "VALUES (?, ?, ?, ?, ?, ?); ";
    private static final String CMD_ACTUALIZAR
            = "UPDATE herramienta SET nombre=?, medida=?, capacidad=?, cantidadUnidades=?, precio=? "
            + "WHERE codigo=?; ";
    
    private HerramientaDAO() {
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

    public boolean agregar(Herramienta nuevaHerramienta) throws SQLException {
        boolean exito = false;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR)) {
            stm.clearParameters();
            
            stm.setString(1, nuevaHerramienta.getCodigo());
            stm.setString(2, nuevaHerramienta.getNombre());
            stm.setString(3, nuevaHerramienta.getMedida());
            stm.setInt(4, nuevaHerramienta.getCapacidad());
            stm.setInt(5, nuevaHerramienta.getCantidadUnidades());
            stm.setDouble(6, nuevaHerramienta.getPrecio());
            
            exito = stm.executeUpdate() == 1;
            
        }

        return exito;
    }

    public List<Herramienta> listar() throws SQLException {
        List<Herramienta> r = new ArrayList<>();

        try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR)) {

            while (rs.next()) {
                r.add(new Herramienta(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("medida"),
                        rs.getDouble("precio"),
                        rs.getInt("capacidad"),
                        rs.getInt("cantidadUnidades")
                ));
            }
        }

        return r;
    }
    
    public List<Herramienta> listarPorNombre(String nombre) throws SQLException {
        List<Herramienta> r = new ArrayList<>();

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_LISTAR_POR_NOMBRE)) {
            stm.clearParameters();
            stm.setString(1, nombre);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    r.add(new Herramienta(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("medida"),
                        rs.getDouble("precio"),
                        rs.getInt("capacidad"),
                        rs.getInt("cantidadUnidades")
                    ));
                }
            }
        }

        return r;
    }

    public Herramienta recuperar(String codigo) throws SQLException {
        Herramienta r = null;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR)) {
            stm.clearParameters();
            stm.setString(1, codigo);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    r = new Herramienta(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("medida"),
                        rs.getDouble("precio"),
                        rs.getInt("capacidad"),
                        rs.getInt("cantidadUnidades")
                    );
                }
            }
        }

        return r;
    }

    public boolean actualizar(Herramienta herramientaActual) throws SQLException {
        boolean exito = false;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR)) {
            stm.clearParameters();

            stm.setString(1, herramientaActual.getNombre());
            stm.setString(2, herramientaActual.getMedida());
            stm.setInt(3, herramientaActual.getCapacidad());
            stm.setInt(4, herramientaActual.getCantidadUnidades());
            stm.setDouble(5, herramientaActual.getPrecio());
            stm.setString(6, herramientaActual.getCodigo());

            exito = stm.executeUpdate() == 1;
        }

        return exito;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("[\n");
        try {
            List<Herramienta> herramientas = listar();
            for (Herramienta e : herramientas) {
                r.append(String.format("\t%s,%n", e));
            }
        } catch (SQLException ex) {
            r.append(String.format("(Excepción: '%s')", ex.getMessage()));
        }
        r.append("]");
        return r.toString();
    }

    public static HerramientaDAO obtenerInstancia() {
        if (instancia == null) {
            instancia = new HerramientaDAO();
        }
        return instancia;
    }

    private Connection obtenerConexion() throws SQLException {
        return GestorBD.obtenerInstancia().obtenerConexion(baseDatos, usuario, clave);
    }
}
