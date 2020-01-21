package sistemaferreteria.Modelo.Entidades;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//      (Detalle)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class Detalle {
    
    private int secuencia;
    private Producto producto;
    private double precio_total;

    public Detalle(int secuencia, Producto producto, double precio_total) {
        this.secuencia = secuencia;
        this.producto = producto;
        this.precio_total = precio_total;
    }
    
    public Detalle(){
        this(0,null,0.0);
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }
    
    @Override
    public String toString(){
        return String.format("%s, %f", getProducto().toString(), getPrecio_total());
    }
    
    public double calcularTotal(){
        if(producto != null){
            if(producto.getClass().equals(Herramienta.class)){
            Herramienta h = (Herramienta) producto;
            return (double)h.getPrecio() * h.getCantidadUnidades(); // check
            }
            else{
                Material m = (Material) producto;
                return (double)m.getPrecio() * m.getPesoKg();//check
            }
        }
        else{
            return 0.0;
        }
        
    }
}
