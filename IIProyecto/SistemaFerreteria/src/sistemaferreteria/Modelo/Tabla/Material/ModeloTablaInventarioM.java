/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaferreteria.Modelo.Tabla.Material;

import javax.swing.table.AbstractTableModel;
import sistemaferreteria.Modelo.Entidades.ConjuntoMateriales;
import sistemaferreteria.Modelo.Entidades.Material;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//       II Proyecto
// (ModeloTablaInventarioM)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class ModeloTablaInventarioM extends AbstractTableModel{
         private final ConjuntoMateriales inventario;
    
    public ModeloTablaInventarioM(ConjuntoMateriales inventario) {
        this.inventario = inventario;
    }

    @Override
    public int getRowCount() {
        return inventario.cantidadProductos();
    }

    @Override
    public int getColumnCount() {
        return Material.getFieldCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object r = inventario.obtener(rowIndex).toArray()[columnIndex];
        return r;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        super.setValueAt(value, rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return CLASE_COLUMNA[columnIndex];
    }

    public void actualizar() {
        fireTableDataChanged();
    }

    private static final Class<?>[] CLASE_COLUMNA = {
        String.class, String.class, String.class, String.class, Double.class, Double.class
    };
}
