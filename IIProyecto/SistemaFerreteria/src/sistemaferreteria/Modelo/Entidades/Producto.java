package sistemaferreteria.Modelo.Entidades;

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

public abstract class Producto {
    private String nombre;
    private double medida;
    private String codigo;

    public Producto(String nombre, double medida, String codigo) {
        this.nombre = nombre;
        this.medida = medida;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMedida() {
        return medida;
    }

    public void setMedida(double medida) {
        this.medida = medida;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
}
