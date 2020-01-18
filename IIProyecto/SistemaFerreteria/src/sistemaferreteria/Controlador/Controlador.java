package sistemaferreteria.Controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Scanner;
import sistemaferreteria.Modelo.DAO.ProductoDAO;
import sistemaferreteria.Modelo.Entidades.Herramienta;
import sistemaferreteria.Modelo.Entidades.Inventario;
import sistemaferreteria.Modelo.Entidades.Material;
import sistemaferreteria.Modelo.Entidades.Producto;
import sistemaferreteria.Modelo.Modelo;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//    (Controlador)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class Controlador {
    
    private Modelo datos;
    private ProductoDAO pd;
    
    public Controlador() {
        this(new Modelo());
    }

    public Controlador(Modelo datos) {
        this.datos = datos;
        this.pd = null;
    }

    public Modelo getDatos() {
        return datos;
    }

    public void setDatos(Modelo datos) {
        this.datos = datos;
    }

    public void registrar(Observer obs) {
        datos.addObserver(obs);
    }
    
    public void registrarTabla(Observer obs) {
        datos.obtenerModeloTabla().addObserver(obs);
    }

    public void suprimir(Observer actual) {
        System.out.printf("Suprimiendo: %s..%n", actual);
        datos.deleteObserver(actual);
    }
    
    public void cerrarAplicacion() {
        System.out.println("Aplicación finalizada normalmente..");
        System.exit(0);
    }
    
    
    //Métodos Administración de Inventario
    //lista todo el inventario
    public void listarInventario() throws Exception{
        pd = ProductoDAO.obtenerInstancia();
        datos.setInventario(pd.listar());
        datos.setPromedioConsultar(datos.getPromedioConsultar() + 1);
    }
    
    //busca según tipo de producto(Material/Herramienta)
    public void listarProductos(Producto p) throws Exception{
        pd = ProductoDAO.obtenerInstancia();
        datos.setInventario(pd.listar(p));
        datos.setPromedioConsultar(datos.getPromedioConsultar() + 1);
    }
     
    //busca según nombre de la Herramienta
    public void listarHerramientas(String nombre) throws Exception{
        pd = ProductoDAO.obtenerInstancia();
        datos.setInventario(pd.listarHerramienta(nombre));
        datos.setPromedioConsultar(datos.getPromedioConsultar() + 1);
    }
    
    //busca según nombre del Material
    public void listarMateriales(String nombre) throws Exception{
        pd = ProductoDAO.obtenerInstancia();
        datos.setInventario(pd.listarMaterial(nombre));
        datos.setPromedioConsultar(datos.getPromedioConsultar() + 1);
    }
    
    //busca un Producto específico
    public Producto obtenerProducto(Producto p) throws Exception{
        pd = ProductoDAO.obtenerInstancia();
        datos.setPromedioConsultar(datos.getPromedioConsultar() + 1);
        return pd.recuperar(p.getCodigo());
    }
    
    //agregar un producto
    public boolean agregarProducto(Producto p) throws Exception{
        pd = ProductoDAO.obtenerInstancia();
        datos.setPromedioAgregar(datos.getPromedioAgregar() + 1);
        return pd.agregar(p);
        //datos.agregar(p);////puede ser, pero no estoy segura, esto solo en caso de que tabla se vea al mismo tiempo que agrego cosas...
    }
    
    //actualizar un producto
    public boolean actualizarProducto(Producto p) throws Exception{
        pd = ProductoDAO.obtenerInstancia();
        datos.setPromedioActualizar(datos.getPromedioActualizar() + 1);
        return pd.actualizar(p);
        //datos.actualizar(p);//puede ser, pero no estoy segura, esto solo en caso de que tabla se vea al mismo tiempo que elimino cosas...
    }
    
    //eliminar un producto
    public boolean eliminarProducto(Producto p) throws Exception{
        pd = ProductoDAO.obtenerInstancia();
        datos.setPromedioEliminar(datos.getPromedioEliminar() + 1);
        return pd.eliminar(p.getCodigo());
        //datos.eliminar(p);//puede ser, pero no estoy segura, esto solo en caso de que tabla se vea al mismo tiempo que elimino cosas...
    }
    

    //Métodos Factura
    //agrega producto a la factura
    public void agregarProductoFactura(Producto p){
        datos.agregarProductoFactura(p);
    }
    
    //elimina producto de la factura
    public void eliminarProductoFactura(Producto p) throws Exception{
        datos.eliminarProductoFactura(p);
    }
    
    //elimmina todos los productos de la factura
    public void borrarProductosFactura() throws Exception{
        datos.borrarProductosFactura();
    }
    
    
    //cargar inventario
    public void cargar() throws Exception{
        pd = ProductoDAO.obtenerInstancia();
        Producto producto;
        Inventario productos = new Inventario();
        try (Scanner entrada = new Scanner(new FileInputStream("src/Archivos/inventario.txt"));) {
            entrada.useDelimiter("\t|\r\n");
            while (entrada.hasNext()){
                String codigo = entrada.next();
                String nombre = entrada.next();
                String medida = entrada.next();
                if(codigo.charAt(0) == 'H'){
                    int capacidad = entrada.nextInt();
                    int cantidadUnidades = entrada.nextInt();
                    producto = new Herramienta(codigo,nombre,medida,capacidad,cantidadUnidades);
                }
                else{
                    String tamano = entrada.next();
                    Double pesoKg = entrada.nextDouble();
                    producto = new Material(codigo,nombre,medida,tamano,pesoKg);
                }
                //productos.agregar(producto);
                pd.agregar(producto);
            }
            //System.out.println(productos.toString());
            entrada.close();
        } catch (FileNotFoundException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }
}
