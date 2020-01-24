package sistemaferreteria.Modelo.Entidades;

import java.io.Serializable;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//     (Producto)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public abstract class Producto implements Serializable{
    
    private String codigo, nombre, medida;
    private double precio;
    
    public Producto(String codigo, String nombre, String medida, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.medida = medida;
        this.precio = precio;
    }

    public Producto(){
        this("","","",0.0);
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public abstract Object[] toArray();
    
    @Override
    public abstract String toString();
    
}
