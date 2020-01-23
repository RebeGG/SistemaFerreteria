/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaferreteria.Modelo.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ESCINF
 */
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
    
    public Producto obtener(int i) {
        return inventario.get(i);
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
    
    public void eliminar(Material p){
        inventario.remove(p);
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
    
    public List<Material> listar() {
        throw new UnsupportedOperationException();
    }
}
