package sistemaferreteria.Controlador;

import java.util.Observer;
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
    
    public Controlador() {
        this(new Modelo());
    }

    public Controlador(Modelo datos) {
        this.datos = datos;
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
    
    public void registrarTablaH(Observer obs) {
        datos.obtenerModeloTablaH().addObserver(obs);
    }
    
        public void registrarTablaM(Observer obs) {
        datos.obtenerModeloTablaM().addObserver(obs);
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
    
    //busca según tipo de producto(Material/Herramienta)
    public void listarProductos(Producto p) throws Exception{
        datos.listarProductos(p);
    }
     
    //busca según nombre de la Herramienta
    public void listarHerramientas(String nombre) throws Exception{
        datos.listarHerramientas(nombre);
    }
    
    //busca según nombre del Material
    public void listarMateriales(String nombre) throws Exception{
        datos.listarMateriales(nombre);
    }
    
    //busca un Producto específico
    public Producto obtenerProducto(Producto p) throws Exception{
        return datos.obtenerProducto(p);
    }
    
    //agregar un producto
    public boolean agregarProducto(Producto p) throws Exception{
        return datos.agregarProducto(p);
    }
    
    //actualizar un producto
    public boolean actualizarProducto(Producto p) throws Exception{
        return datos.actualizarProducto(p);
    }
    

    //Métodos Factura
    //agrega factura
    public boolean agregarFactura()throws Exception{
        return datos.agregarFactura();
    }
    
    //agrega producto a la factura
    public void agregarProductoFactura(Producto p)throws Exception{
        datos.agregarProductoFactura(p);
    }
    
    //cargar inventario
    public void cargar(){
        datos.cargar();
    }
}
