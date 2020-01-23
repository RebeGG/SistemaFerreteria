package sistemaferreteria.Vista;

//  Universidad Nacional

import java.util.Observable;
import java.util.Observer;
import sistemaferreteria.Controlador.Controlador;
import sistemaferreteria.Modelo.Modelo;

//  Facultad de Ciencias Exactas y Naturales
//  Escuela de Informática
//  
//     II Proyecto
//  (VentanaPrincipal)
//
//  Autores: Rebecca Garita Gutiérrez
//           María Fernanda González Arias
//
//  III Ciclo 2019

public class VentanaPrincipal extends javax.swing.JFrame implements Observer {
    Controlador gestor;
    public VentanaPrincipal(String titulo, Controlador gestor){
        super(titulo);
        this.gestor=gestor;
        this.inventario= new VentanaInventario("Inventario", this.gestor);
        this.factura= new VentanaFactura("Factura", this.gestor);
        this.detalles= new VentanaDetalles("Factura detallada", this.gestor);
        configurar();
    }
    
    private void configurar() {
        initComponents();
        setLocationRelativeTo(null);
        
    }
    public VentanaPrincipal() {
       this("Sistema de Ferreteria", null);
    }

    public void init() {
        gestor.registrar(this);
        setVisible(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        tituloSistema = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        salirMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        inventarioItem = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        facturacionItem = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Ferreteria");
        setSize(new java.awt.Dimension(600, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                cerrarVentana(evt);
            }
        });

        tituloSistema.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 24)); // NOI18N
        tituloSistema.setText("Sistema de Ferretería");

        jMenu1.setText("Archivo");

        salirMenuItem.setText("Salir");
        jMenu1.add(salirMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Inventario");

        inventarioItem.setText("Inventario");
        inventarioItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventarioItemActionPerformed(evt);
            }
        });
        jMenu2.add(inventarioItem);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Facturación");

        facturacionItem.setText("Facturación");
        facturacionItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturacionItemActionPerformed(evt);
            }
        });
        jMenu3.add(facturacionItem);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(tituloSistema)
                .addContainerGap(117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(tituloSistema)
                .addContainerGap(204, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inventarioItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventarioItemActionPerformed
        inventario.init();
    }//GEN-LAST:event_inventarioItemActionPerformed

    private void cerrarVentana(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_cerrarVentana
         gestor.cerrarAplicacion();
    }//GEN-LAST:event_cerrarVentana

    private void facturacionItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturacionItemActionPerformed
        factura.init();
        detalles.init();
    }//GEN-LAST:event_facturacionItemActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
    
     @Override
    public void update(Observable o, Object arg) {
        modelo = (Modelo) o;
    }
    
    private Modelo modelo = null;
    private VentanaInventario inventario;
    private VentanaFactura factura;
    private VentanaDetalles detalles;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem facturacionItem;
    private javax.swing.JMenuItem inventarioItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem salirMenuItem;
    private javax.swing.JLabel tituloSistema;
    // End of variables declaration//GEN-END:variables

}
