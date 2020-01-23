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
    
    public void eliminar(Herramienta p){
        inventario.remove(p);
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
    
    public List<Herramienta> listar() {
        throw new UnsupportedOperationException();
    }
}
