package sistemaferreteria.Modelo.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//     (Inventario)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class Inventario extends Observable implements Serializable{

    private List<Producto> inventario;
    
    public Inventario() {
        inventario = new ArrayList<>();
    }
    
    public Inventario(List<Producto> inventario){
        this.inventario = inventario;
    }
    
    public void setInventario(List<Producto> inventario){
        this.inventario = inventario;
    }

    public List<Producto> getInventario(){
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

    public void agregar(Producto p) {
        inventario.add(p);
        setChanged();
        notifyObservers();
    }
    
    public void eliminar(Producto p){
        inventario.remove(p);
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("[\n");
        for (Producto p : inventario) {
            r.append(String.format("\t%s,%n",p));
        }
        r.append("]");
        return r.toString();
    }

    public Inventario obtenerModelo() {
        return this;
    }
    
    public List<Producto> listar() {
        throw new UnsupportedOperationException();
    }

}
