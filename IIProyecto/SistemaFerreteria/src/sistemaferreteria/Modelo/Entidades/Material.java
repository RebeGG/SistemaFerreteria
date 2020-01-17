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
    
    private double pesoKg;
    private String tamano;
    
    public Material(String codigo, String nombre, String medida, String tamano, double pesoKg) {
        super(codigo, nombre, medida);
        this.tamano = tamano;
        this.pesoKg = pesoKg;
    }
    
    public Material(){
        this("","","","",0.0);
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
        return String.format("%s, %s, %s, %s, %f", getCodigo(), getNombre(), getMedida(), getTamano(), getPesoKg());
    }
    
    @Override
    public Object[] toArray() {
        Object[] r = new Object[7];
        r[0] = getCodigo();
        r[1] = getNombre();
        r[2] = getMedida();
        r[3] = "-";
        r[4] = getTamano();
        return r;
    }       
}
