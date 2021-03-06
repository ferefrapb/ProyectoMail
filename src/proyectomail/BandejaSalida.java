/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomail;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Desk
 */
public class BandejaSalida extends javax.swing.JFrame {

    Usuario User;
    String usuario ="";
    ArbolBinario Arbolito;
    
    public BandejaSalida(String Usuario) throws IOException {
        this.usuario = Usuario.trim();
        initComponents();
        lUsuario.setText(this.usuario);
        User = new Usuario(this.usuario);
        Arbolito = new ArbolBinario("C:\\MEIA\\Mensajes.txt", "Izquierda|Derecha|Receptor|Emisor|Fecha|Mensaje|Estatus", "|");
        this.setLocationRelativeTo(null);
        ActualizarBandeja();
        try
        {
            boolean Bandera=true;
            int i=0;
            jBandejaSalida.setText("");
            if(!lUsuario.getText().isEmpty()){
                while(Bandera && i < User.MensajesEnviados.size())
                {
                    /*if(User.MensajesRecibidos.get(i)[0].equals(lUsuario.getText().trim()))
                    {*/
                        if (jBandejaSalida.getText().length()!=0)
                        {
                            jBandejaSalida.append("\n\r"+"\n\r");
                        }
                        jBandejaSalida.append(User.MensajesEnviados.get(i)[0]+":\n\r"+User.MensajesEnviados.get(i)[2]+"\n\r"+User.MensajesEnviados.get(i)[1]);
                    /*}
                    else
                    {*/
                        if (!jBandejaSalida.getText().equals(""))
                        {
                            Bandera=false;
                        }
                    /*}*/
                    i++;
                }
            }
        }catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Mensaje de Error", JOptionPane.ERROR_MESSAGE, null);
        }
    }
    
     private void ActualizarBandeja()
    {
        try
        {
            String[] linea;
            User.MensajesEnviados=Arbolito.ObtenerMensajesEnviados(lUsuario.getText());
            for (int i = 0; i < User.MensajesEnviados.size(); i++) 
            {
                linea=User.MensajesEnviados.get(i);
            }
            
        }catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Mensaje de Error", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lUsuario = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jBandejaSalida = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lUsuario.setText("Usuario");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Bandeja de Salida");

        jBandejaSalida.setColumns(20);
        jBandejaSalida.setRows(5);
        jScrollPane1.setViewportView(jBandejaSalida);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(BandejaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BandejaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BandejaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BandejaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BandejaSalida().setVisible(true);
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea jBandejaSalida;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lUsuario;
    // End of variables declaration//GEN-END:variables
}
