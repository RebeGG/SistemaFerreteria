package sistemaferreteria.Modelo.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
// (ConjuntoMateriales)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class ConjuntoMateriales  extends java.util.Observable implements Serializable{
     private List<Material> inventario;
    
    public ConjuntoMateriales() {
        inventario = new ArrayList<>();
    }
    
    public ConjuntoMateriales(List<Material> inventario){
        this.inventario = inventario;
    }
    
    public void setInventario(List<Material> inventario){
        this.inventario = inventario;
        setChanged();
        notifyObservers();
    }

    public List<Material> getInventario(){
        return inventario;
    }
    
    public int cantidadProductos() {
        return inventario.size();
    }
    
    public Material obtener(int i) {
        return inventario.get(i);
    }
    
    public void actualizar(Material m){
        int i = 0;
        for(Material auxM: inventario){
            if(auxM.getCodigo().equals(m.getCodigo())){
                i = inventario.indexOf(auxM);
                break;
            }
        }
        inventario.set(i, m);
        setChanged();
        notifyObservers();
    }

    public void borrar() {
        inventario.clear();
        setChanged();
        notifyObservers();
    }

    public void agregar(Material p) {
        inventario.add(p);
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("[\n");
        for (Material p : inventario) {
            r.append(String.format("\t%s,%n",p));
        }
        r.append("]");
        return r.toString();
    }

    public ConjuntoMateriales obtenerModelo() {
        return this;
    }
}
