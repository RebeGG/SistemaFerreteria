package sistemaferreteria.Modelo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//     (GestorBD)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class GestorBD {
    
    private static GestorBD instancia = null;
    
    private GestorBD() {
        System.out.println("Inicializando manejador BD..");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }

    public static GestorBD obtenerInstancia() {
        if (instancia == null) {
            instancia = new GestorBD();
        }
        return instancia;
    }

    public Connection obtenerConexion(String baseDatos,
            String usuario, String clave)
            throws SQLException {
        Connection cnx;
        String URL_conexion
                = String.format("%s//%s/%s",
                        "jdbc:mysql:", "localhost", baseDatos);

        cnx = DriverManager.getConnection(URL_conexion, usuario, clave);
        return cnx;
    }
}
