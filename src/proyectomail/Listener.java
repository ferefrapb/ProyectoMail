/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomail;
/**
 *
 * @author Josue Higueros
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Listener extends Thread {
    private Connection Conexion;
    private final org.postgresql.PGConnection pgconn;
    private String id;
    private String GrupoReceptor;
    private String GrupoEmisor;
    private String Receptor;
    private String Emisor;
    private String Asunto;
    private String Mensaje;
    private Notificacion Not;  

    Listener(Connection conn) throws SQLException {
		this.Conexion = conn;
		this.pgconn = (org.postgresql.PGConnection)conn;
		Statement stmt = conn.createStatement();
		stmt.execute("LISTEN q_event");
		stmt.close();
    }

    public void run() {
        while (true) {
            try {
                //Escucha en la base de Datos para ver si hay mensajes nuevos
                Statement stmt = Conexion.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT 1"); 
                rs.close();
                stmt.close();

	       //Recibe las notificaciones de JDBC
                org.postgresql.PGNotification notifications[] = pgconn.getNotifications();
                if (notifications != null) {
                    for (int i=0; i<notifications.length; i++) {
                        //Aca se recibe cada una de las notificaciones de Postgresql notifications[i] para luego descomponer el json que se recibe
                        String parameter = notifications[i].getParameter().replace("\\","");
                        String action = parameter.split("\\{")[1].split(",")[1].split(":")[1].substring(2,8);   
                                          
                        if(action.equals("INSERT")){
                        //Se comprueba si el correo es para mi
                            id = parameter.split("\\{")[2].replace("}","").split(",")[0].split(":")[1];
                            GrupoEmisor = parameter.split("\\{")[2].replace("}","").split(",")[1].split(":")[1];  
                            GrupoReceptor = parameter.split("\\{")[2].replace("}","").split(",")[2].split(":")[1];
                            Emisor = parameter.split("\\{")[2].replace("}","").split(",")[3].split(":")[1];
                            Receptor = parameter.split("\\{")[2].replace("}","").split(",")[4].split(":")[1];
                            Receptor = Receptor.replaceAll("\"", "");
                            Asunto = parameter.split("\\{")[2].replace("}","").split(",")[6].split(":")[1];
                            Mensaje = parameter.split("\\{")[2].replace("}","").split(",")[7].split(":")[1];
                            boolean existe = true;
                            
                            if(GrupoReceptor.equals("9")){
                                //si es para mi se envia el update con la respuesta
                                
                                try{
                                    
                                    //metodo para buscar aqui
                                    
                                    existe = BuscarUsuario(Receptor);
                                    
                                    
                                    if(existe){
                                        
                                BDD.getInstancia().Update(id, existe);
                                BDD.getInstancia().setMensaje("El Grupo " + GrupoEmisor + " te ha enviado un Correo." );
                                
                                Not = new Notificacion();
                                Not.setVisible(true);
                                
                                    }else{
                                        
                                        BDD.getInstancia().Update(id, existe);
                                        BDD.getInstancia().setMensaje("El Grupo " + GrupoEmisor + " trató de enviar un correo a un usuario no encontrado.");
                                        Not = new Notificacion();
                                        Not.setVisible(true);
                                    }
                                    
                                    
                                }catch(Exception x)
                                {
                                    
                                }

                             
                                //si es para mi enviar el update con la respuesta de que el usuario existe
                                //Deben de validar cada uno si el usuario existe o no en su ordenador y enviar la respuesta de esta forma al servidor
                                if(existe){
                                    BDD.getInstancia().Update(id, existe);
                                }else{
                                    BDD.getInstancia().Update(id, existe);
                                }                                        
                            }
                        }else{
                            
                            //UPDATE
                            
                            //comprobar si yo fui el que envie la solicitud
                            //Descomponer id, grupo emisor y grupo receptor en esta parte
                            id = parameter.split("\\{")[2].replace("}","").split(",")[0].split(":")[1];
                            GrupoEmisor = parameter.split("\\{")[2].replace("}","").split(",")[1].split(":")[1];
                            GrupoReceptor = parameter.split("\\{")[2].replace("}","").split(",")[2].split(":")[1];
                            Emisor = parameter.split("\\{")[2].replace("}","").split(",")[3].split(":")[1];
                            Receptor = parameter.split("\\{")[2].replace("}","").split(",")[4].split(":")[1];
                            Asunto = parameter.split("\\{")[2].replace("}","").split(",")[6].split(":")[1];
                            Mensaje = parameter.split("\\{")[2].replace("}","").split(",")[7].split(":")[1];
                            
                            //Aca deben de colocar su numero de Grupo 
                            if(GrupoEmisor.equals("9")){
				
                                String respuesta = parameter.split("\\{")[2].replace("}","").split(",")[8].split(":")[1];
                                 //Comprobar cual fue la respuesta
                                 if(respuesta.equals("false")){
                                    BDD.getInstancia().setMensaje("El grupo " + GrupoReceptor + " no ha encontrado el usuario al cual enviaste el correo." );
                                    Not = new Notificacion();
                                    Not.setVisible(true);
                                    
                                 }else{
                                    BDD.getInstancia().setMensaje("El grupo " + GrupoReceptor + " ha recibido el mensaje." );
                                    Not = new Notificacion();
                                    Not.setVisible(true);
                                    
                                 }
                                 
                                 //Para Eliminar la solicitud (NO ES NECESARIO, OPCIONAL)
                                 BDD.getInstancia().Delete(id);
                            }
                        }                                             
                    }
                }
                
            //Espera para la siguiente Notificacion
                Thread.sleep(500);
            } catch (SQLException | InterruptedException sqle) {
                    sqle.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private Boolean BuscarUsuario(String usuario){
        
        File arch = new File("C:/MEIA/Usuario.txt");
        FileReader Leer;
        BufferedReader bLeer;
        String linea;
        String[] campos;
        Boolean bandera = false;
        
        try{
            
            Leer = new FileReader(arch);
            bLeer = new BufferedReader(Leer);
            
            linea = bLeer.readLine();
            
            if(linea == null){   
                
                return false;     
                
            }
            else{         
                                
                while(linea != null){
                    
                    campos = linea.split("\\|");
                    
                    if((campos[0].equals(usuario))){ 
                        
                        bandera = true;
                        break;                       
                    }          
                    linea = bLeer.readLine();
                }
                
                Leer.close();
                bLeer.close();
                
                if(bandera)
                {
                    
                    return true;
                    
                }
                else
                {                   
                    return false;
                    
                }
            }
            
        }
        catch(IOException ex){
        
        return false;
        }
        
    }
}
