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
    
    private String nombre;
    private Date fecha;//could change
    private List<Producto> productos; 
    private double total;

    public Factura(String nombre, Date fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.productos = new ArrayList<>();
        this.total = 0;
    }
    
    public Factura(){
        this("", null);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public void agregarProducto(Producto p){
        if(p.getClass().equals(Herramienta.class)){
            Herramienta h = (Herramienta) p;
            if(productos.contains(p)){
                h.setCantidadUnidades(h.getCantidadUnidades()+1);
                productos.set(productos.indexOf(p), h);
            }
            else{
                productos.add(p);
            }
        }
        else{
            Material m = (Material) p;
            if(productos.contains(p)){
                m.setPesoKg(m.getPesoKg() + 1);//mmmm no creo, revisar
                productos.set(productos.indexOf(p), m);
            }
            else{
                productos.add(p);
            }
        }
    }
    
    public void eliminarProducto(Producto p) throws Exception{
        if(productos.contains(p)){
            productos.remove(p);
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
    
    public Producto obtener(Producto p){
        if(productos.contains(p)){
            return productos.get(productos.indexOf(p));
        }
        return null;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(String.format("Nombre: %s%n", getNombre()));
        s.append("Produtos.................................");
        for(Producto p: productos){
            s.append(String.format("%s%n", p.toString()));
        }
        s.append(".........................................");
        return s.toString();
    }
}
