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
public class Modelo extends Observable implements Runnable {

    private boolean activo;
    private Factura factura;
    private int banderaMinuto;
    private int promedioAgregar;
    private int promedioActualizar;
    private int promedioEliminar;
    private int promedioConsultar;
    private Inventario inventario;
    private Producto producto;
    private static final int MAX_ESPERA = 30000;
    private Thread hiloControl = null;

    public Modelo() {
        this.activo = false;
        this.banderaMinuto = 0;
        this.factura = new Factura();
        this.inventario = new Inventario();
        this.producto = null;
        this.promedioAgregar = 0;
        this.promedioActualizar = 0;
        this.promedioConsultar = 0;
        this.promedioEliminar = 0;
    }

    public synchronized boolean activo() {
        return activo;
    }

    public synchronized void establecerActivo(boolean activo) {
        this.activo = activo;
    }
    
    public int getBanderaMinuto() {
        return banderaMinuto;
    }
    
    public void setBanderaMinuto(int bandera) {
        this.banderaMinuto = bandera;
    }
    
    public Inventario getInventario() {
        return inventario;
    }
    
    public void setInventario(List<Producto> inventario) {
        this.inventario.setInventario(inventario);
        setChanged();
        notifyObservers();
    }
    
    //    public void setInventario(Inventario inventario) {
//        this.inventario = inventario;
//        setChanged();
//        notifyObservers();
//    }

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
    
    public int getPromedioAgregar() {
        return promedioAgregar;
    }
    
    public void setPromedioAgregar(int agregar) {
        this.promedioAgregar = agregar;
    }
    
    public int getPromedioActualizar() {
        return promedioActualizar;
    }
    
    public void setPromedioActualizar(int actualizar) {
        this.promedioActualizar = actualizar;
    }
    
    public int getPromedioConsultar() {
        return promedioConsultar;
    }

    public void setPromedioConsultar(int consultar) {
        this.promedioConsultar = consultar;
    }
    
    public int getPromedioEliminar() {
        return promedioEliminar;
    }
    
    public void setPromedioEliminar(int eliminar) {
        this.promedioEliminar = eliminar;
    }
    
    public Inventario obtenerModeloTabla() {
        return inventario.obtenerModelo();
    }
    
    
    //Métodos Hilo
    public void init() {
        hiloControl = new Thread(this);
        hiloControl.start();
    }

    @Override
    public void run() {
        while (hiloControl == Thread.currentThread()) {
            //provisional, por que hay que mostrar el promedio 2 veces por min...
            setBanderaMinuto(getBanderaMinuto() + 1);
            if(getBanderaMinuto() < 2){
                actualizar();
            }else{
                actualizar();
                setPromedioAgregar(0);
                setPromedioActualizar(0);
                setPromedioConsultar(0);
                setPromedioEliminar(0);
                setBanderaMinuto(0);
            }
            try {
                Thread.sleep(MAX_ESPERA);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void actualizar() {
        if (activo()) {
            setPromedioAgregar(promedioAgregar);
            setPromedioActualizar(promedioActualizar);
            setPromedioConsultar(promedioConsultar);
            setPromedioEliminar(promedioEliminar);
            setChanged();
            notifyObservers();
        }
    }
    
    
    //Métodos Factura
    public void agregarProductoFactura(Producto p) {
        factura.agregarProducto(p);
    }

    public void eliminarProductoFactura(Producto p) throws Exception {
        factura.eliminarProducto(p);
    }

    public void borrarProductosFactura() throws Exception {
        factura.borrarProductos();
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

}
