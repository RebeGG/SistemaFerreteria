package sistemaferreteria.Modelo.Entidades;

import java.io.Serializable;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//    (Herramienta)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class Herramienta extends Producto implements Serializable{
    
    private static final int LIVIANO = 1;
    private static final int MEDIANO = 2;
    private static final int PESADO = 3;
    
    private int capacidad, cantidadUnidades;
    
    public Herramienta(String codigo, String nombre, String medida, double precio, int capacidad, int cantidadUnidades) {
        super(codigo, nombre, medida, precio);
        this.capacidad = capacidad;
        this.cantidadUnidades = cantidadUnidades;
    }
    
    public Herramienta(){
        this("","","",0.0,0,0);
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getCantidadUnidades() {
        return cantidadUnidades;
    }

    public void setCantidadUnidades(int cantidadUnidades) {
        this.cantidadUnidades = cantidadUnidades;
    }
    
    @Override
    public String toString(){
        return String.format("%s, %s, %s, %f, %d, %d", getCodigo(), getNombre(), getMedida(), getPrecio(), getCapacidad(), getCantidadUnidades());
    }

    @Override
    public Object[] toArray() {
        Object[] r = new Object[6];
        r[0] = getCodigo();
        r[1] = getNombre();
        r[2] = getMedida();
        switch(getCapacidad()){
            case LIVIANO:{
                r[3] = "Liviano";
            }break;
            case MEDIANO:{
                r[3] = "Mediano";
            }break;
            case PESADO:{
                r[3] = "Pesado";
            }break;
        }
        r[4] = 0.0;
        r[5] = getPrecio();
        return r;
    }

}
