/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Desk
 */

public class menuListaUsuario extends javax.swing.JFrame {

    /**
     * Creates new form menuListaUsuario
     */
    Usuario User;
    public menuListaUsuario(Usuario user) {
        initComponents();
        this.setLocationRelativeTo(null);
        User = user;
        Object[] objectArray = User.Listas.Lista.toArray();
        String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);
        lLista.setListData(stringArray);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        bAgregarUsuario = new javax.swing.JButton();
        bGrabar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tfNombreLista = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfDescripcionLista = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lLista = new javax.swing.JList<>();
        cbUsuarioLista = new javax.swing.JComboBox<>();
        tbAgregarEliminarUsuario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        bReorganizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Mantenimiento a Lista-Usuario");

        bAgregarUsuario.setText("Agregar Usuario");
        bAgregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarUsuarioActionPerformed(evt);
            }
        });

        bGrabar.setText("Grabar Lista");
        bGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGrabarActionPerformed(evt);
            }
        });

        bEliminar.setText("Eliminar Usuario");
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });

        jLabel2.setText("Ingrese Nombre de Lista:");

        jLabel3.setText("Ingrese Descripción de la Lista:");

        lLista.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lListaValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lLista);

        jButton1.setText("Eliminar Lista");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        bReorganizar.setText("Reorganizar");
        bReorganizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bReorganizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(tfDescripcionLista)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(40, 40, 40)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tbAgregarEliminarUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(bReorganizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bAgregarUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                        .addComponent(cbUsuarioLista, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfNombreLista, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)))
                            .addGap(0, 0, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addComponent(bGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfNombreLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(tfDescripcionLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(bGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbUsuarioLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tbAgregarEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bAgregarUsuario)
                        .addGap(10, 10, 10)
                        .addComponent(bEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(bReorganizar))
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGrabarActionPerformed
        if(tfNombreLista.getText().trim().length()==0 || tfDescripcionLista.getText().trim().length()==0)
        {
            JOptionPane.showMessageDialog(rootPane, "Debe Ingresar Nombre de Lista y Descripción", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(tfNombreLista.getText().length()>20 || tfDescripcionLista.getText().length()>40)
        {
            JOptionPane.showMessageDialog(rootPane, "La Descripción No Debe ser Mayor a 40", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else
        {
            String NombreLista = tfNombreLista.getText().trim().replace("ñ", "n");
            String DescLista = tfDescripcionLista.getText().trim().replace("ñ", "n");
            User.Listas.CrearLista(NombreLista, DescLista);
            User.Listas = new ListaSecuencial(User.usuario);
            Object[] objectArray = User.Listas.Lista.toArray();
            String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);
            lLista.setListData(stringArray);
            lLista.setSelectedIndex(0);
            tfNombreLista.setText("")  ;
            tfDescripcionLista.setText("");
        }
    }//GEN-LAST:event_bGrabarActionPerformed

    private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
        try{
            if (User.ListaUsuario.EliminarRegistro(User.usuario, lLista.getSelectedValue().toString(),cbUsuarioLista.getSelectedItem().toString() ))
            {
                User.Listas.ModificarTamañoLista(lLista.getSelectedValue().toString(),-1);
                tbAgregarEliminarUsuario.setText("");
                if (lLista.getSelectedIndex() != -1)
                {

                    cbUsuarioLista.removeAllItems();
                    List<String> Listado = User.ListaUsuario.DevolverUsuariosdeLista(lLista.getSelectedValue().toString());
                    for (int i = 0; i < Listado.size(); i++) {
                        cbUsuarioLista.addItem(Listado.get(i));
                    }
                }
                JOptionPane.showMessageDialog(null, "Se ha Eliminado el Usuario",
                    "Eliminación", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(rootPane, "El usuario a Eliminar No Es Parte de la Lista", "Eliminación", JOptionPane.INFORMATION_MESSAGE, null);
            }
        }catch(Exception ex)
        {

        }
    }//GEN-LAST:event_bEliminarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cbUsuarioLista.removeAllItems();
        User.Listas.EliminarLista(lLista.getSelectedValue().toString());
        User.ListaUsuario.EliminarLista(User.usuario, lLista.getSelectedValue().toString());
        User.Listas=new ListaSecuencial(User.usuario);
        lLista.setListData((String[]) User.Listas.Lista.toArray());
        if (0!=User.Listas.Lista.size())
        {
            lLista.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bAgregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarUsuarioActionPerformed
         if(tbAgregarEliminarUsuario.getText().trim().length()!=0)
        {
            if (tfDescripcionLista.getText().isEmpty()||tfDescripcionLista.getText().equals("")) 
            {
                tfDescripcionLista.setText("Sin Descripción");
            }
            //String NombreLista, String NombreUsuario, String Descripcion
            if( User.ListaUsuario.InsertarRegistro(lLista.getSelectedValue().toString(), tbAgregarEliminarUsuario.getText().trim().toUpperCase(), tfDescripcionLista.getText()))
            {
                User.Listas.ModificarTamañoLista(lLista.getSelectedValue().toString(),1);
                tbAgregarEliminarUsuario.setText("");
                cbUsuarioLista.removeAllItems();
                List<String> Listado = User.ListaUsuario.DevolverUsuariosdeLista(lLista.getSelectedValue().toString());
                for (int i = 0; i < Listado.size(); i++) {
                    cbUsuarioLista.addItem(Listado.get(i));
                }
                JOptionPane.showMessageDialog(null, "Se ha agregado el usuario",
                    "Agregar en Lista", JOptionPane.INFORMATION_MESSAGE);
            }else
            JOptionPane.showMessageDialog(rootPane, "El Usuario ya es Parte de la Lista", "Agregar a Lista", JOptionPane.INFORMATION_MESSAGE, null);

        }
    }//GEN-LAST:event_bAgregarUsuarioActionPerformed

    private void bReorganizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bReorganizarActionPerformed
        try {
            User.ListaUsuario.Reorganizar();
        } catch (IOException ex) {
            Logger.getLogger(menuListaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bReorganizarActionPerformed

    private void lListaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lListaValueChanged
        
        try{

            if (lLista.getSelectedIndex() != -1)
            {

                cbUsuarioLista.removeAllItems();
                List<String> Listado = User.ListaUsuario.DevolverUsuariosdeLista(lLista.getSelectedValue().toString());
                for (int i = 0; i < Listado.size(); i++) {
                    cbUsuarioLista.addItem(Listado.get(i));
                }
            }

        }catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Mensaje de Error", JOptionPane.ERROR_MESSAGE, null);
        }
        
    }//GEN-LAST:event_lListaValueChanged

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
            java.util.logging.Logger.getLogger(menuListaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menuListaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menuListaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menuListaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAgregarUsuario;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bGrabar;
    private javax.swing.JButton bReorganizar;
    private javax.swing.JComboBox<String> cbUsuarioLista;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lLista;
    private javax.swing.JTextField tbAgregarEliminarUsuario;
    private javax.swing.JTextField tfDescripcionLista;
    private javax.swing.JTextField tfNombreLista;
    // End of variables declaration//GEN-END:variables
}
