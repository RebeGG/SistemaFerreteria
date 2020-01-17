package sistemaferreteria.Modelo.Entidades;

//  Universidad Nacional

import java.io.Serializable;

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
    
    public Producto(String codigo, String nombre, String medida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.medida = medida;
    }

    public Producto(){
        this("","","");
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
    
    public abstract Object[] toArray();
    
    public static int getFieldCount(){
        return 5;
    }
    
    @Override
    public abstract String toString();
    
}
