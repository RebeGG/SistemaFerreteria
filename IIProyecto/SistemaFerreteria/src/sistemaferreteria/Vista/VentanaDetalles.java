package sistemaferreteria.Vista;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import sistemaferreteria.Controlador.Controlador;
import sistemaferreteria.Modelo.Modelo;

public class VentanaDetalles extends javax.swing.JFrame implements Observer{

    private Controlador controlador;
    private Modelo modelo = null;
    
    public VentanaDetalles() {
        this("Factura Detallada", null);
    }
    public VentanaDetalles(String titulo, Controlador controlador){
        super(titulo);
        this.controlador= controlador;
        configurar();
    }
    
    private void configurar(){
        initComponents();
    }
    
    public void init(){
        controlador.registrar(this);
        setVisible(true);
     }
    
    @Override
    public void update(Observable o, Object arg) {
       modelo = (Modelo) o;
       DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
       lblSecuencia.setText(String.format("%d", modelo.getFactura().getNumero()));
       lblFechaActual.setText(String.format("%s%n", df.format(modelo.getFactura().getFecha())));
       areaDetalle.setText(modelo.getFactura().toString());
       lblTotalCalculo.setText(String.format("%f", modelo.getFactura().getTotal()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCodigo = new javax.swing.JLabel();
        lblSecuencia = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblFechaActual = new javax.swing.JLabel();
        lblDetalles = new javax.swing.JLabel();
        lblFactura = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaDetalle = new javax.swing.JTextArea();
        lblTotal = new javax.swing.JLabel();
        lblTotalCalculo = new javax.swing.JLabel();

        lblCodigo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblCodigo.setText("Código de Factura: ");

        lblSecuencia.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblSecuencia.setText("0");

        lblFecha.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblFecha.setText("Fecha: ");

        lblFechaActual.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N

        lblDetalles.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblDetalles.setText("Detalle");

        lblFactura.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 24)); // NOI18N
        lblFactura.setText("Factura");

        areaDetalle.setColumns(20);
        areaDetalle.setRows(5);
        jScrollPane1.setViewportView(areaDetalle);

        lblTotal.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        lblTotal.setText("Total:");

        lblTotalCalculo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFactura)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSecuencia)
                                .addGap(49, 49, 49)
                                .addComponent(lblFecha)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblDetalles))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalCalculo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblFactura)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCodigo)
                        .addComponent(lblSecuencia)
                        .addComponent(lblFecha))
                    .addComponent(lblFechaActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addComponent(lblDetalles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(lblTotalCalculo))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaDetalles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaDetalles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaDetalles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaDetalles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaDetalles().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaDetalle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDetalles;
    private javax.swing.JLabel lblFactura;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFechaActual;
    private javax.swing.JLabel lblSecuencia;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalCalculo;
    // End of variables declaration//GEN-END:variables

}
