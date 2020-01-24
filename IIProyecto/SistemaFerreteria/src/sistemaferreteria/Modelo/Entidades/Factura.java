package sistemaferreteria.Modelo.Entidades;

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
        this.total = 0.0;
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
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(Detalle d: productos){
            s.append(String.format("%s%n", d.toString()));
        }
        return s.toString();
    }
}
