package sistemaferreteria.Modelo.Tabla.Herramienta;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

//  Universidad Nacional
//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//   (ModeloColumnasH)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class ModeloColumnasH extends DefaultTableColumnModel {
    
    public ModeloColumnasH() {
        super();
        configurar();
    }

    private void configurar() {
        for (int i = 0; i < ENCABEZADO.length; i++) {

            // Defina la instancia para la i-ésima columna de la tabla..
            TableColumn c = new TableColumn(i);

            c.setHeaderValue(ENCABEZADO[i]);
            c.setPreferredWidth(ANCHO_COLUMNA[i]);

            c.setHeaderRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    // Define el espacio alrededor de los títulos del encabezado..
                    setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

                    // Cambia el color de los títulos..
                    setForeground(Color.WHITE);
                    setBackground(new Color(96, 96, 128));

                    // Usa negritas para el texto..
                    c.setFont(c.getFont().deriveFont(Font.BOLD));

                    return c;
                }

            });
            addColumn(c);
        }

    }

    @Override
    public int getColumnCount() {
        return ENCABEZADO.length;
    }

    private static final String[] ENCABEZADO = {
        "Código", "Nombre", "Medida", "Capacidad", "Precio", "Cantidad"
    };
    private static final int[] ANCHO_COLUMNA = {
        50, 50, 50, 50, 50, 50
    };
}
