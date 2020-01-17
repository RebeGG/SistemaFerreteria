package sistemaferreteria.Modelo.Entidades;

//  Universidad Nacional

import sistemaferreteria.Modelo.Entidades.Producto;

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

public class Material extends Producto {
    
    private double tamano, pesoKg;
    
    public Material(String nombre, double medida, double tamano, double pesoKg, String codigo) {
        super(nombre, medida, codigo);
        this.tamano = tamano;
        this.pesoKg = pesoKg;
    }
    
    public Material(){
        this("",0.0,0.0,0.0,"");
    }

    public double getTamano() {
        return tamano;
    }

    public void setTamano(double tamano) {
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
        return String.format("%s, %f, %f, %f", getNombre(), getMedida(), getTamano(), getPesoKg());
    }
       
}
