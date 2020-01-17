package sistemaferreteria.Modelo;

import java.util.List;
import java.util.Observable;
import sistemaferreteria.Modelo.Entidades.Factura;
import sistemaferreteria.Modelo.Entidades.Inventario;
import sistemaferreteria.Modelo.Entidades.Producto;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//      (Modelo)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class Modelo extends Observable{
    
    private Inventario inventario;
    private Factura factura;
    private Producto producto;
    
    public Modelo(){
        this.factura = new Factura();
        this.producto = null;
        this.inventario = new Inventario();
    }

    public Inventario getInventario() {
        return inventario;
    }

//    public void setInventario(Inventario inventario) {
//        this.inventario = inventario;
//        setChanged();
//        notifyObservers();
//    }
    
    public void setInventario(List<Producto> inventario) {
        this.inventario.setInventario(inventario);
        setChanged();
        notifyObservers();
    }
    
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
        setChanged();
        notifyObservers();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        setChanged();
        notifyObservers();
    }
    
//    public void agregar(Producto producto) {
//        System.out.printf("Guardando registro: '%s'%n",
//                producto);
//        inventario.agregar(producto);
//        setChanged();
//        notifyObservers();
//    }

//    public List<Producto> listar() {
//        return inventario.listar();
//    }

//    public Producto obtener(Producto p) {
//        return inventario.obtener(p);
//    }

//    public void actualizar(Producto p) {
//        inventario.actualizar(p);
//        setChanged();
//        notifyObservers();
//    }

//    public Producto eliminar(Producto p) {
//        Producto r = inventario.eliminar(p);
//        if (r != null) {
//            setChanged();
//            notifyObservers();
//        }
//        return r;
//    }
    
    public Inventario obtenerModeloTabla() {
        return inventario.obtenerModelo();
    }
    
    public void agregarProductoFactura(Producto p){
        factura.agregarProducto(p);
    }
    
    public void eliminarProductoFactura(Producto p) throws Exception{
        factura.eliminarProducto(p);
    }
    
    public void borrarProductosFactura() throws Exception{
        factura.borrarProductos();
    }
}
