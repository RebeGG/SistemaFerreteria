package sistemaferreteria.Modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistemaferreteria.Modelo.DAO.FacturaDAO;
import sistemaferreteria.Modelo.DAO.GestorBD;
import sistemaferreteria.Modelo.DAO.HerramientaDAO;
import sistemaferreteria.Modelo.DAO.MaterialDAO;
import sistemaferreteria.Modelo.Entidades.Detalle;
import sistemaferreteria.Modelo.Entidades.Factura;
import sistemaferreteria.Modelo.Entidades.Inventario;
import sistemaferreteria.Modelo.Entidades.Producto;
import sistemaferreteria.Modelo.Entidades.Herramienta;
import sistemaferreteria.Modelo.Entidades.Material;

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
    private HerramientaDAO hd;
    private MaterialDAO md;
    private FacturaDAO fd;

    public Modelo() {
        GestorBD bd = GestorBD.obtenerInstancia();//realmente noo sé donde ponerlo o si va en el main más bien...
        this.activo = false;
        this.banderaMinuto = 0;
        this.factura = new Factura();
        this.inventario = new Inventario();
        this.producto = null;
        this.promedioAgregar = 0;
        this.promedioActualizar = 0;
        this.promedioConsultar = 0;
        this.promedioEliminar = 0;
        this.hd = null;
        this.md = null;
        this.fd = null;
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

    //Listar según producto(Material/Herramienta)
    public void listarProductos(Producto p) throws Exception{
        if(p != null){
            if(p.getClass().equals(Herramienta.class)){
                hd = HerramientaDAO.obtenerInstancia();
                List<Producto> inventarioAux = new ArrayList();
                for(Herramienta h:hd.listar()){
                    setPromedioConsultar(getPromedioConsultar() + 1);
                    inventarioAux.add(h);
                }
                setInventario(inventarioAux);
            }
            else{
                md = MaterialDAO.obtenerInstancia();
                List<Producto> inventarioAux = new ArrayList();
                for(Material m:md.listar()){
                    setPromedioConsultar(getPromedioConsultar() + 1);
                    inventarioAux.add(m);
                }
                setInventario(inventarioAux);
            }
        }
        else{
            throw new Exception("Parámetros de búsqueda vacíos");
        }
    }
    
    //busca según nombre de la Herramienta
    public void listarHerramientas(String nombre) throws Exception{
        hd = HerramientaDAO.obtenerInstancia();
        List<Producto> inventarioAux = new ArrayList();
        for(Herramienta h:hd.listarPorNombre(nombre)){
            setPromedioConsultar(getPromedioConsultar() + 1);
            inventarioAux.add(h);
        }
        setInventario(inventarioAux);
    }
    
    //busca según nombre del Material
    public void listarMateriales(String nombre) throws Exception{
        md = MaterialDAO.obtenerInstancia();
        List<Producto> inventarioAux = new ArrayList();
        for(Material m:md.listarPorNombre(nombre)){
            setPromedioConsultar(getPromedioConsultar() + 1);
            inventarioAux.add(m);
        }
        setInventario(inventarioAux);
    }
    
    //busca un Producto específico
    public Producto obtenerProducto(Producto p) throws Exception{
        if(p != null){
            if(p.getClass().equals(Herramienta.class)){
                hd = HerramientaDAO.obtenerInstancia();
                setPromedioConsultar(getPromedioConsultar() + 1);
                return hd.recuperar(p.getCodigo());
            }
            else{
                md = MaterialDAO.obtenerInstancia();
                setPromedioConsultar(getPromedioConsultar() + 1);
                return md.recuperar(p.getCodigo());
            }
        }
        else{
            throw new Exception("Parámetros de búsqueda vacíos");
        }
    }
    
    //agregar un producto
    public boolean agregarProducto(Producto p) throws Exception{
        if(p != null){
            if(p.getClass().equals(Herramienta.class)){
                hd = HerramientaDAO.obtenerInstancia();
                Herramienta h = (Herramienta) p;
                setPromedioAgregar(getPromedioAgregar() + 1);
                return hd.agregar(h);
            }
            else{
                md = MaterialDAO.obtenerInstancia();
                Material m = (Material) p;
                setPromedioAgregar(getPromedioAgregar() + 1);
                return md.agregar(m);
            }
        }
        else{
            throw new Exception("Parámetros de búsqueda vacíos");
        }
    }
    
    //actualizar un producto
    public boolean actualizarProducto(Producto p) throws Exception{
        if(p != null){
            if(p.getClass().equals(Herramienta.class)){
                hd = HerramientaDAO.obtenerInstancia();
                Herramienta h = (Herramienta) p;
                setPromedioActualizar(getPromedioActualizar() + 1);
                return hd.actualizar(h);
            }
            else{
                md = MaterialDAO.obtenerInstancia();
                Material m = (Material) p;
                setPromedioActualizar(getPromedioActualizar() + 1);
                return md.actualizar(m);
            }
        }
        else{
            throw new Exception("Parámetros de búsqueda vacíos");
        }
    }
    
    //eliminar un producto
    public boolean eliminarProducto(Producto p) throws Exception{
        if(p != null){
            if(p.getClass().equals(Herramienta.class)){
                hd = HerramientaDAO.obtenerInstancia();
                setPromedioEliminar(getPromedioEliminar() + 1);
                return hd.eliminar(p.getCodigo());
            }
            else{
                md = MaterialDAO.obtenerInstancia();
                setPromedioEliminar(getPromedioEliminar() + 1);
                return md.eliminar(p.getCodigo());
            }
        }
        else{
            throw new Exception("Parámetros de búsqueda vacíos");
        }
    }
    
    
    //Métodos Factura
    
    //agregar Factura
    public boolean agregarFactura() throws Exception{
        fd = FacturaDAO.obtenerInstancia();
        setPromedioAgregar(getPromedioAgregar() + 1);
        boolean resultado = fd.agregar(factura);
        factura = new Factura();
        return resultado;
    }
    
    //agrega producto a la factura
    public void agregarProductoFactura(Producto p)throws Exception{
        fd = FacturaDAO.obtenerInstancia();
        if(p != null){
            if(p.getClass().equals(Herramienta.class)){
                hd = HerramientaDAO.obtenerInstancia();
                Herramienta auxH = hd.recuperar(p.getCodigo());
                setPromedioConsultar(getPromedioConsultar() + 1);
                Herramienta h = (Herramienta) p;
                if(auxH.getCantidadUnidades() >= h.getCantidadUnidades()){
                    auxH.setCantidadUnidades(auxH.getCantidadUnidades() - h.getCantidadUnidades());
                    hd.actualizar(auxH);
                    setPromedioActualizar(getPromedioActualizar() + 1);
                }
                else{
                    throw new Exception("No existen unidades suficientes para realizar la compra.");
                }
            }
            else{
                md = MaterialDAO.obtenerInstancia();
                Material auxM = md.recuperar(p.getCodigo());
                setPromedioConsultar(getPromedioConsultar() + 1);
                Material m = (Material) p;
                if(auxM.getPesoKg() >= m.getPesoKg()){
                    auxM.setPesoKg(auxM.getPesoKg() - m.getPesoKg());
                    md.actualizar(auxM);
                    setPromedioActualizar(getPromedioActualizar() + 1);
                }
                else{
                    throw new Exception("No existen unidades suficientes para realizar la compra.");
                }
            }
        }
        else{
            throw new Exception("Parámetros de búsqueda vacíos");
        }
        
        Detalle detalle = new Detalle();
        detalle.setProducto(p);
        detalle.setPrecio_total(detalle.calcularTotal());
        factura.agregarDetalle(detalle);
        factura.setTotal(factura.calcularTotal());
    }
    
    //elimina producto de la factura
    public boolean eliminarProductoFactura(Producto p) throws Exception{
        fd = FacturaDAO.obtenerInstancia();
        if(p != null){
            if(p.getClass().equals(Herramienta.class)){
                hd = HerramientaDAO.obtenerInstancia();
                Herramienta auxH = hd.recuperar(p.getCodigo());
                setPromedioConsultar(getPromedioConsultar() + 1);
                Herramienta h = (Herramienta) p;
                auxH.setCantidadUnidades(auxH.getCantidadUnidades() + h.getCantidadUnidades());
                hd.actualizar(auxH);
                setPromedioActualizar(getPromedioActualizar() + 1);
            }
            else{
                md = MaterialDAO.obtenerInstancia();
                Material auxM = md.recuperar(p.getCodigo());
                setPromedioConsultar(getPromedioConsultar() + 1);
                Material m = (Material) p;
                auxM.setPesoKg(auxM.getPesoKg() + m.getPesoKg());
                md.actualizar(auxM);
                setPromedioActualizar(getPromedioActualizar() + 1);
            }
        }
        else{
            throw new Exception("Parámetros de búsqueda vacíos");
        }
        setPromedioEliminar(getPromedioEliminar() + 1);
        return fd.eliminarDetalle(factura.getNumero(), p.getCodigo());
    }
    
    public void cargar(){
        hd = HerramientaDAO.obtenerInstancia();
        md = MaterialDAO.obtenerInstancia();
        try (Scanner entrada = new Scanner(new FileInputStream("src/Archivos/inventario.txt"));) {
            entrada.useDelimiter("\t|\r\n");
            while (entrada.hasNext()){
                String codigo = entrada.next();
                String nombre = entrada.next();
                String medida = entrada.next();
                if(codigo.charAt(0) == 'H'){
                    int capacidad = entrada.nextInt();
                    int cantidadUnidades = entrada.nextInt();
                    Double precio = entrada.nextDouble();
                    try {
                        hd.agregar(new Herramienta(codigo,nombre,medida,precio,capacidad,cantidadUnidades));
                        setPromedioAgregar(getPromedioAgregar() + 1);
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                else{
                    String tamano = entrada.next();
                    Double pesoKg = entrada.nextDouble();
                    Double precio = entrada.nextDouble();
                    try {
                        md.agregar(new Material(codigo,nombre,medida,precio,tamano,pesoKg));
                        setPromedioAgregar(getPromedioAgregar() + 1);
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
            entrada.close();
        } catch (FileNotFoundException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }
}
