package sistemaferreteria.Modelo.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
// (ConjuntoHerramientas)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class ConjuntoHerramientas extends java.util.Observable implements Serializable{
    private List<Herramienta> inventario;
    
    public ConjuntoHerramientas() {
        inventario = new ArrayList<>();
    }
    
    public ConjuntoHerramientas(List<Herramienta> inventario){
        this.inventario = inventario;
    }
    
    public void setInventario(List<Herramienta> inventario){
        this.inventario = inventario;
        setChanged();
        notifyObservers();
    }

    public List<Herramienta> getInventario(){
        return inventario;
    }
    
    public int cantidadProductos() {
        return inventario.size();
    }
    
    public Producto obtener(int i) {
        return inventario.get(i);
    }

    public void borrar() {
        inventario.clear();
        setChanged();
        notifyObservers();
    }

    public void agregar(Herramienta p) {
        inventario.add(p);
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("[\n");
        for (Herramienta p : inventario) {
            r.append(String.format("\t%s,%n",p));
        }
        r.append("]");
        return r.toString();
    }

    public ConjuntoHerramientas obtenerModelo() {
        return this;
    }
}
