package sistemaferreteria.Modelo.Entidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//      (Factura)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class Factura {
    
    private int numero;
    private Date fecha;
    private List<Detalle> productos;
    private double total;

    public Factura(int numero, Date fecha) {
        this.numero = numero;
        this.fecha = new Date();
        this.productos = new ArrayList<>();
        this.total = 0;
    }
    
    public Factura(){
        this(0, null);
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Detalle> getProductos() {
        return productos;
    }

    public void setProductos(List<Detalle> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public void agregarDetalle(Detalle d){
        boolean bandera = false;
        if(d != null){
            for(Detalle detalle : productos){
                if(detalle.getProducto().getCodigo().equals(d.getProducto().getCodigo())){
                    bandera = true;
                    if(detalle.getProducto().getClass().equals(Herramienta.class)){
                        Herramienta productoH = (Herramienta) detalle.getProducto();
                        Herramienta h = (Herramienta) d.getProducto();
                        productoH.setCantidadUnidades(productoH.getCantidadUnidades() + h.getCantidadUnidades());
                        detalle.setProducto(productoH);
                        break;
                    }
                    else{
                        Material productoM = (Material) detalle.getProducto();
                        Material m = (Material) d.getProducto();
                        productoM.setPesoKg(productoM.getPesoKg() + m.getPesoKg());
                        detalle.setProducto(productoM);
                        break;
                    }
                }
            }
            if(!bandera){
                productos.add(d);
            }
        }
    }
    
    public double calcularTotal(){
        double total = 0.0;
        for(Detalle d : productos){
            total = total + d.getPrecio_total();
        }
        return total;
    }
    
    public void eliminarDetalle(Detalle d) throws Exception{
        if(productos.contains(d)){
            productos.remove(d);
        }else{
            throw new Exception("Producto no existe en la factura.");
        }
    }
    
    public void borrarProductos() throws Exception{
        if(!productos.isEmpty()){
            productos.clear();
        }else{
            throw new Exception("Factura no contiene ningún producto hasta el momento.");
        }
    }
    
    public Detalle obtener(Detalle d){
        if(productos.contains(d)){
            return productos.get(productos.indexOf(d));
        }
        return null;
    }
    
    @Override
    public String toString(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder s = new StringBuilder("---------------- FERRETERÍA ---------------\n");
        s.append(String.format("Número de Factura: %s%n", getNumero()));
        s.append(String.format("Fecha: %s%n", df.format(getFecha())));
        s.append("Produtos.................................");
        for(Detalle d: productos){
            s.append(String.format("%s%n", d.toString()));
        }
        s.append(".........................................");
        return s.toString();
    }
}
