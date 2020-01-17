package sistemaferreteria.Modelo.Entidades;

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

public class Herramienta extends Producto {
    
    private static final int LIVIANO = 1;
    private static final int MEDIANO = 2;
    private static final int PESADO = 3;
    
    private int capacidad, cantidadUnidades;
    
    public Herramienta(String nombre, double medida, int capacidad, int cantidadUnidades) {
        super(nombre, medida);
        this.capacidad = capacidad;
        this.cantidadUnidades = cantidadUnidades;
    }
    
    public Herramienta(){
        this("", 0.0,0,0);
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
        return String.format("%s, %f, %d, %d", getNombre(), getMedida(), getCapacidad(), getCantidadUnidades());
    }
}
