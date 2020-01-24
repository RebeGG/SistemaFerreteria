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
import sistemaferreteria.Modelo.Entidades.Material;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//    (MaterialDAO)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class MaterialDAO {
    private static MaterialDAO instancia = null;
    private Properties cfg;
    private String baseDatos;
    private String usuario;
    private String clave;

    private static final String CMD_LISTAR
            = "SELECT codigo, nombre, medida, tamano, pesoKg, precio FROM material; ";
    private static final String CMD_LISTAR_POR_NOMBRE
            = "SELECT codigo, nombre, medida, tamano, pesoKg, precio FROM material WHERE nombre=?; ";
    private static final String CMD_RECUPERAR
            = "SELECT codigo, nombre, medida, tamano, pesoKg, precio FROM material WHERE codigo=?; ";
    private static final String CMD_AGREGAR
            = "INSERT INTO material (codigo, nombre, medida, tamano, pesoKg, precio) "
            + "VALUES (?, ?, ?, ?, ?, ?); ";
    private static final String CMD_ACTUALIZAR
            = "UPDATE material SET nombre=?, medida=?, tamano=?, pesoKg=?, precio=? "
            + "WHERE codigo=?; ";
    
    private MaterialDAO() {
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

    public boolean agregar(Material nuevoMaterial) throws SQLException {
        boolean exito = false;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR)) {
            stm.clearParameters();
            
            stm.setString(1, nuevoMaterial.getCodigo());
            stm.setString(2, nuevoMaterial.getNombre());
            stm.setString(3, nuevoMaterial.getMedida());
            stm.setString(4, nuevoMaterial.getTamano());
            stm.setDouble(5, nuevoMaterial.getPesoKg());
            stm.setDouble(6, nuevoMaterial.getPrecio());
            
            exito = stm.executeUpdate() == 1;

        }

        return exito;
    }

    public List<Material> listar() throws SQLException {
        List<Material> r = new ArrayList<>();

        try (Connection cnx = obtenerConexion();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_LISTAR)) {

            while (rs.next()) {
                r.add(new Material(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("medida"),
                        rs.getDouble("precio"),
                        rs.getString("tamano"),
                        rs.getDouble("pesoKg")
                ));
            }
        }

        return r;
    }
    
    public List<Material> listarPorNombre(String nombre) throws SQLException {
        List<Material> r = new ArrayList<>();

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_LISTAR_POR_NOMBRE)) {
            stm.clearParameters();
            stm.setString(1, nombre);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    r.add(new Material(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("medida"),
                        rs.getDouble("precio"),
                        rs.getString("tamano"),
                        rs.getDouble("pesoKg")
                    ));
                }
            }
        }

        return r;
    }

    public Material recuperar(String codigo) throws SQLException {
        Material r = null;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR)) {
            stm.clearParameters();
            stm.setString(1, codigo);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    r = new Material(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("medida"),
                        rs.getDouble("precio"),
                        rs.getString("tamano"),
                        rs.getDouble("pesoKg")
                    );
                }
            }
        }

        return r;
    }

    public boolean actualizar(Material materialActual) throws SQLException {
        boolean exito = false;

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR)) {
            stm.clearParameters();

            stm.setString(1, materialActual.getNombre());
            stm.setString(2, materialActual.getMedida());
            stm.setString(3, materialActual.getTamano());
            stm.setDouble(4, materialActual.getPesoKg());
            stm.setDouble(5, materialActual.getPrecio());
            stm.setString(6, materialActual.getCodigo());

            exito = stm.executeUpdate() == 1;
        }

        return exito;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("[\n");
        try {
            List<Material> materiales = listar();
            for (Material e : materiales) {
                r.append(String.format("\t%s,%n", e));
            }
        } catch (SQLException ex) {
            r.append(String.format("(Excepción: '%s')", ex.getMessage()));
        }
        r.append("]");
        return r.toString();
    }

    public static MaterialDAO obtenerInstancia() {
        if (instancia == null) {
            instancia = new MaterialDAO();
        }
        return instancia;
    }

    private Connection obtenerConexion() throws SQLException {
        return GestorBD.obtenerInstancia().obtenerConexion(baseDatos, usuario, clave);
    }
}
