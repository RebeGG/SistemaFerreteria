package sistemaferreteria.Vista.Tabla.Herramienta;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTable;
import static javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import sistemaferreteria.Modelo.Tabla.Herramienta.ModeloTablaInventarioH;
import sistemaferreteria.Vista.Tabla.RenderizadorCelda;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//         II Proyecto
// (TablaInventarioHerramientas)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class TablaInventarioHerramientas extends JTable {
    public TablaInventarioHerramientas(TableModel modeloDatos, TableColumnModel modeloColumnas) {
        super(modeloDatos, modeloColumnas);
        configurar();
    }
    
    private void configurar() {
        setIntercellSpacing(new Dimension(2, 2));
        setRowHeight(getFontMetrics(getFont()).getHeight() * 4 / 3);
        setDefaultRenderer(Object.class, new RenderizadorCelda());
        
        setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
    }
    
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        c.setBackground(((row % 2) == 0)
                ? RenderizadorCelda.COLOR_FILA_PAR
                : RenderizadorCelda.COLOR_FILA_IMPAR);
        c.setForeground(Color.BLACK);
        return c;
    }
    
    public void actualizar() {
        ((ModeloTablaInventarioH) getModel()).actualizar();
    }
}
