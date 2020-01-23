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
//    //lista todo el inventario
//    public void listarInventario() throws Exception{
//        pd = ProductoDAO.obtenerInstancia();
//        datos.setInventario(pd.listar());
//        datos.setPromedioConsultar(datos.getPromedioConsultar() + 1);
//    }
    
    //busca según tipo de producto(Material/Herramienta)
    public void listarProductos(Producto p) throws Exception{
        datos.listarProductos(p);
        datos.setPromedioConsultar(datos.getPromedioConsultar() + 1);
    }
     
    //busca según nombre de la Herramienta
    public void listarHerramientas(String nombre) throws Exception{
        datos.listarHerramientas(nombre);
        datos.setPromedioConsultar(datos.getPromedioConsultar() + 1);
    }
    
    //busca según nombre del Material
    public void listarMateriales(String nombre) throws Exception{
        datos.listarMateriales(nombre);
        datos.setPromedioConsultar(datos.getPromedioConsultar() + 1);
    }
    
    //busca un Producto específico
    public Producto obtenerProducto(Producto p) throws Exception{
        datos.setPromedioConsultar(datos.getPromedioConsultar() + 1);
        return datos.obtenerProducto(p);
    }
    
    //agregar un producto
    public boolean agregarProducto(Producto p) throws Exception{
        datos.setPromedioAgregar(datos.getPromedioAgregar() + 1);
        return datos.agregarProducto(p);
    }
    
    //actualizar un producto
    public boolean actualizarProducto(Producto p) throws Exception{
        datos.setPromedioActualizar(datos.getPromedioActualizar() + 1);
        return datos.actualizarProducto(p);
    }
    
    //eliminar un producto
    public boolean eliminarProducto(Producto p) throws Exception{
        datos.setPromedioEliminar(datos.getPromedioEliminar() + 1);
        return datos.eliminarProducto(p);
    }
    

    //Métodos Factura
    //agrega factura
    public boolean agregarFactura()throws Exception{
        return datos.agregarFactura();
    }
    
    //agrega producto a la factura
    public boolean agregarProductoFactura(Producto p)throws Exception{
        return datos.agregarProducto(p);
    }
    
    //cargar inventario
    public void cargar(){
        datos.cargar();
    }
}
