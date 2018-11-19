/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomail;

import java.sql.SQLException;

/**
 *
 * @author ferefrapb
 */
public class ProyectoMail {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
        accesoAlSistema formAccesoSistema = new accesoAlSistema();
        formAccesoSistema.setVisible(true);
    }
    
}
