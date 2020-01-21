package sistemaferreteria.Modelo.Tabla;

import javax.swing.table.AbstractTableModel;
import sistemaferreteria.Modelo.Entidades.Inventario;
import sistemaferreteria.Modelo.Entidades.Producto;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//      II Proyecto
// (ModeloTablaInventario)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class ModeloTablaInventario extends AbstractTableModel {
   
    private final Inventario inventario;
    
    public ModeloTablaInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    @Override
    public int getRowCount() {
        return inventario.cantidadProductos();
    }

    @Override
    public int getColumnCount() {
        return Producto.getFieldCount();
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
        String.class, String.class, String.class, Integer.class, Double.class, Double.class
    };
    
}
