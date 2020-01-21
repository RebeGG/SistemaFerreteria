package sistemaferreteria.Modelo.Entidades;

import java.io.Serializable;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//     (Material)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class Material extends Producto implements Serializable{
    
    private String tamano; 
    private double pesoKg;
    
    public Material(String codigo, String nombre, String medida, double precio, String tamano, double pesoKg) {
        super(codigo, nombre, medida, precio);
        this.tamano = tamano;
        this.pesoKg = pesoKg;
    }
    
    public Material(){
        this("","","",0.0,"",0.0);
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }
    
    @Override
    public String toString(){
        return String.format("%s, %s, %s, %f, %s, %f", getCodigo(), getNombre(), getMedida(), getPrecio(), getTamano(), getPesoKg());
    }
    
    @Override
    public Object[] toArray() {
        Object[] r = new Object[6];
        r[0] = getCodigo();
        r[1] = getNombre();
        r[2] = getMedida();
        r[3] = "-";
        r[4] = getTamano();
        r[5] = getPrecio();
        return r;
    }       
}
