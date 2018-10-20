/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 *
 * @author ferefrapb
 */
public class manejadorArchivo 
{
     private boolean banderaUsuarioExiste=false;
    private String pathDescriptorBitacora="MEIA/Desc_Bitacora.txt";
    private String pathBitacora="MEIA/Bitacora.txt";
    private String pathUsuarioMaster="MEIA/Usuario.txt";
    
    public boolean verificarArchivoExiste()
    {
        File pathFolderMEIA = new File("C:\\MEIA");
        String strPathUsuario = pathFolderMEIA + "/Usuario.txt";
        File ArchivoUsuariosTxt = new File(strPathUsuario);
        if (pathFolderMEIA.exists() && ArchivoUsuariosTxt.exists() ) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Utilizar para crear directorio, archivo usuario+ descriptor, bitacora + Descriptor, 
    public void crearArchivosDelSistema(Usuario usuarioObj)
    {
        // Creando Directorio MEIA
        //Path p5 = Paths.get(System.getProperty("user.home"),"logs", "foo.log")
        File pathFolderMEIA = new File("C:\\MEIA");
        //String strError;
        try 
        {
             // if the directory does not exist, create it
            if (!pathFolderMEIA.exists()) 
            {      
                boolean result = false;
                try
                {
                    pathFolderMEIA.mkdir();
                    result = true;
                } 
                catch(SecurityException se)
                {
                //Manejar error..
                
                }
            }
        
            //Creando Archivo Usuario.txt
            String pathUsuariotxt = pathFolderMEIA + "/Usuario.txt";
            crearArchivotxt(pathUsuariotxt);

            //Creando Archivo Descriptor de Usuarios.
            String pathDescriptorUsuario = pathFolderMEIA + "/desc_usuario.txt";
            File archivoDescUsuario = new File(pathDescriptorUsuario);
                if (!archivoDescUsuario.exists()) 
                {
                    archivoDescUsuario.createNewFile();
                    
                    ZonedDateTime horaSistema = ZonedDateTime.now();
                    
                    String descripcion= "Usuarios del sistema.";
                    String tipo="archivos de datos";
                    String organizacion="Secuencial";
                    String creado =horaSistema.toString();
                    horaSistema = ZonedDateTime.now();
                    String modificado = horaSistema.toString();
                    String separadorDeCampos="|";
                    String llave="usuario";
                    String orden="Ascendente";
                    String registrosActivos="0";
                    String registrosInactivos="0";
                    String registroMaxXReorganizar="";
                    
                    crearDescriptor(pathDescriptorUsuario,pathUsuariotxt, descripcion, tipo, organizacion, usuarioObj.usuario, creado, modificado, separadorDeCampos, llave, orden, registrosActivos, registrosInactivos, registroMaxXReorganizar);
                }
            
            //Creando bitacora.txt
            String pathBitacoratxt = pathFolderMEIA + "/Bitacora.txt";
            crearArchivotxt(pathBitacoratxt);
            
            //Creando Archivo Descriptor bitacora
            String pathDescriptorBitacora = pathFolderMEIA + "/desc_bitacora.txt";
            File archivoDesBitacora = new File(pathDescriptorBitacora);
                if (!archivoDesBitacora.exists()) 
                {
                    archivoDesBitacora.createNewFile();
                    
                    ZonedDateTime horaSistema = ZonedDateTime.now();
                    
                    String descripcion= "Usuarios del sistema.";
                    String tipo="archivos de datos";
                    String organizacion="Apilo";
                    String creado =horaSistema.toString();
                    horaSistema = ZonedDateTime.now();
                    String modificado = horaSistema.toString();
                    String separadorDeCampos="|";
                    String llave="";
                    String orden="Ascendente";
                    String registrosActivos="0";
                    String registrosInactivos="0";
                    String registroMaxXReorganizar="10";
                    
                    crearDescriptor(pathDescriptorBitacora, pathBitacoratxt, descripcion, tipo, organizacion, usuarioObj.usuario, creado, modificado, separadorDeCampos, llave, orden, registrosActivos, registrosInactivos, registroMaxXReorganizar);
                
                }
            escribirArchivoBitacora(usuarioObj);
        } 
        catch (Exception e) 
        {
            
        }
        
       
        
    }
    
    public void crearDescriptor(String iPathAEscribir,String iPathAApuntar, String iDescripcion, String iTipo,String iOrganizacion, String iAutor, String iCreado, String iModificado, String iSeparadorCampos, String iLLave, String iOrden, String iRegristrosActivos, String iRegistrosInactivos, String iRMaximosXReorganizar)
    {
        File Archivo = new File(iPathAEscribir);
           //File Archivo = new File(strPath);
            try
            {
                FileWriter Escribir = new FileWriter(Archivo,true);
                BufferedWriter bw = new BufferedWriter(Escribir);
                bw.write("nombre_simbolico: "+iPathAApuntar + System.getProperty( "line.separator" ));
                bw.write("fecha_creacion: "+iCreado + System.getProperty( "line.separator" ));
                bw.write("usuario_creacion: "+iAutor+ System.getProperty( "line.separator" ) );
                bw.write("fecha_modificacion: "+iCreado+ System.getProperty( "line.separator" ) );
                bw.write("usuario_modificacion:: "+iAutor+ System.getProperty( "line.separator" ) );
                //bw.write("Creado: "+iTipo + System.getProperty( "line.separator" ));
                //bw.write("Modificado: "+iModificado+ System.getProperty( "line.separator" ) );
                //bw.write("Separador campos: "+iSeparadorCampos+ System.getProperty( "line.separator" ) );
                
                if (iOrganizacion=="Apilo") 
                {
                    bw.write("Registro Activos: "+iRegristrosActivos+ System.getProperty( "line.separator" ) );
                    bw.write("Registro Inactivos: "+iRegistrosInactivos + System.getProperty( "line.separator" ));
                    bw.write("Registro maxico x reorganizar: "+iRMaximosXReorganizar+ System.getProperty( "line.separator" ));
                }
                else
                {
                    bw.write("Llave: "+iLLave+ System.getProperty( "line.separator" ) );
                    bw.write("Orden: "+iOrden+ System.getProperty( "line.separator" ) );
                    bw.write("Registro Activos: "+iRegristrosActivos + System.getProperty( "line.separator" ));
                    bw.write("Registro Inactivos: "+iRegistrosInactivos+ System.getProperty( "line.separator" ) );
                    
                }
                //bw.write(strContenido+ System.getProperty( "line.separator" ));
                bw.close();
                Escribir.close();

                //return true;
            }
            catch(IOException ex)
            {
                //strError= ex.getMessage().toString();
                //return false;
            } 
    }
    
    public void crearArchivotxt(String inombreArchivo)
    {
    File ArchivoUsuariosTxt = new File(inombreArchivo);
                if (!ArchivoUsuariosTxt.exists()) 
                {
                    try
                    {
                        ArchivoUsuariosTxt.createNewFile();
                    //try(FileWriter Escribir = new FileWriter(Archivo,true))
                    //{
                    //Escribir.write(strContenido + System.getProperty("line.separator"));
                    //Escribir.close();
                    //}
                    //return true;
                
                    }
                    catch(IOException ex)
                    {
                    //manejar
                        //strError= ex.getMessage().toString();
                    //return false;
                    } 
                }
    }
    
    public boolean verificarUsuarioYPassword(Usuario iObjUsuario)
    {
        
        File pathFolderMEIA = new File("C:\\MEIA");
        String strPath = pathFolderMEIA + "/Bitacora.txt";
        String strError;
        boolean usuarioYPasswordCorrecto = false;
        File ArchivoUsuariosTxt = new File(strPath);
        
            FileReader LecturaArchivo;
            try {
                LecturaArchivo = new FileReader(ArchivoUsuariosTxt);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea="";
                try {
                    Linea=LeerArchivo.readLine();
                    String[] split;
                    Usuario objUsuarioArchivo = new Usuario();
                    if (Linea=="")
                    {
                        // Crear primer usuario de este programa
                        LecturaArchivo.close();
                        LeerArchivo.close();
                    }
                    else
                    {
                        while(Linea != null)
                        {
                            if(Linea!="")
                            {
                                split=Linea.split("\\|");
                                objUsuarioArchivo.usuario = split[0];
                                objUsuarioArchivo.nombre = split[1];
                                objUsuarioArchivo.apellido = split[2];
                                objUsuarioArchivo.password = split[3];
                                objUsuarioArchivo.rol = Integer.parseInt(split[4]);
                                objUsuarioArchivo.fechaNacimiento = split[5];
                                objUsuarioArchivo.correo = split[6];
                                objUsuarioArchivo.telefono = Integer.parseInt(split[7]);
                                objUsuarioArchivo.pathFotografia = split[8];
                                //objUsuarioArchivo.descripcion = split[9];
                                objUsuarioArchivo.estatus = Integer.parseInt(split[9]);
                            
                                if ((iObjUsuario.usuario.equals(objUsuarioArchivo.usuario))&&(iObjUsuario.password.equals(objUsuarioArchivo.password))) 
                                {
                                    usuarioYPasswordCorrecto = true;
                                    break;
                                }
                                else
                                {
                                    usuarioYPasswordCorrecto= false;
                                }
                            }
                            Linea=LeerArchivo.readLine();
                        }  
                    }
                    
                    LecturaArchivo.close();
                    LeerArchivo.close();
                    
                    //listaNombre.setModel(modeloNombre);
                    //listaApellido.setModel(modeloApellido);
                    strError="";
                    //return true;
                    
                } catch (IOException ex) {
                    strError= ex.getMessage().toString();
                    //return false;
                }
            } catch (FileNotFoundException ex) {
                strError= ex.getMessage().toString();
                //return false;
            }                      
    return usuarioYPasswordCorrecto;
    }
    
    public void leerArchivo (String iUsuario)
    {
        banderaUsuarioExiste=false;
        String strPath ="C:\\MEIA/Usuario.txt";
        String strError;
        File ArchivoUsuariosTxt = new File(strPath);
        if(ArchivoUsuariosTxt.exists()==true)
        {
            FileReader LecturaArchivo;
            try {
                LecturaArchivo = new FileReader(ArchivoUsuariosTxt);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea="";
                try {
                    Linea=LeerArchivo.readLine();
                    String[] split;
                    Usuario objUsuarioArchivo = new Usuario();
                    
                        while(Linea != null)
                        {
                            if(Linea!="")
                            {
                                split=Linea.split("|");
                                objUsuarioArchivo.usuario = split[0];
                                objUsuarioArchivo.nombre = split[1];
                                objUsuarioArchivo.apellido = split[2];
                                objUsuarioArchivo.password = split[3];
                                objUsuarioArchivo.rol = Integer.parseInt(split[4]);
                                objUsuarioArchivo.fechaNacimiento = split[5];
                                objUsuarioArchivo.correo = split[6];
                                objUsuarioArchivo.telefono = Integer.parseInt(split[7]);
                                objUsuarioArchivo.pathFotografia = split[8];
                                //objUsuarioArchivo.descripcion = split[9];
                                objUsuarioArchivo.estatus = Integer.parseInt(split[9]); 
                                
                                if (objUsuarioArchivo.usuario.equals(iUsuario))
                                {
                                    banderaUsuarioExiste=true;
                                }
                            }
                            Linea=LeerArchivo.readLine();
                        
                        }
                    LecturaArchivo.close();
                    LeerArchivo.close();
                    
                    //listaNombre.setModel(modeloNombre);
                    //listaApellido.setModel(modeloApellido);
                    strError="";
                    //return true;
                    
                } catch (IOException ex) {
                    strError= ex.getMessage().toString();
                    //return false;
                }
            } catch (FileNotFoundException ex) {
                strError= ex.getMessage().toString();
                //return false;
            }            
        }
    }
    
    public boolean getBanderaUsuarioExistente()
    {
        return banderaUsuarioExiste;
    }
    
    public boolean escribirArchivoBitacora(Usuario usuarioObj)
    {
        File Archivo = new File("C:\\MEIA/Bitacora.txt");
        //
        try
            {
                FileWriter Escribir = new FileWriter(Archivo,true);
                BufferedWriter bw = new BufferedWriter(Escribir);
                bw.write(usuarioObj.usuario+"|");
                bw.write(usuarioObj.nombre+"|");
                bw.write(usuarioObj.apellido+"|");
                bw.write(usuarioObj.password+"|");
                bw.write(usuarioObj.rol+"|");
                bw.write(usuarioObj.fechaNacimiento+"|");
                bw.write(usuarioObj.correo+"|");
                bw.write(usuarioObj.telefono+"|");
                bw.write(usuarioObj.pathFotografia+"|");
                //bw.write(usuarioObj.descripcion+"|");
                bw.write(usuarioObj.estatus+System.getProperty( "line.separator" ));
                
                bw.close();
                Escribir.close();
                
                return true;
            }
            catch(IOException ex)
            {
                //strError= ex.getMessage().toString();
                return false;
            } 
    }
    
    public int obtenerRegistroMaxEnBitacora()
    {
        File Archivo = new File("C:\\MEIA/Bitacora.txt");
        int registrosMaximos=0;
        if(Archivo.exists()==true)
        {
            FileReader LecturaArchivo;
            try {
                LecturaArchivo = new FileReader(Archivo);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea="";
                String[] split = null;
                try {
                    Linea=LeerArchivo.readLine();
                    //String[] split;
                    while(Linea != null)
                    {
                        if(Linea!="")
                        {
                            split=Linea.split("\\|");  
                        }
                        Linea=LeerArchivo.readLine();
                    }

                    LecturaArchivo.close();
                    LeerArchivo.close();
                    String sSplit =split[0].substring(31,split[0].length());
                    registrosMaximos=Integer.parseInt(sSplit);
                    
                    return registrosMaximos;
    
                    //strError="";
                    //return true;
                    
                } catch (IOException ex) {
                    //strError= ex.getMessage().toString();
                    //return false;
                }
            } catch (FileNotFoundException ex) {
                //strError= ex.getMessage().toString();
                //return false;
            }            
        }
        else
        {
            //strError="No existe el archivo";
            //return false;
        }
        return registrosMaximos;
    }
    
    
    public void copiarBitacoraAUsuarios()
    {
        File Archivo = new File(pathBitacora);
        if(Archivo.exists()==true)
        {
            FileReader LecturaArchivo;
            try {
                LecturaArchivo = new FileReader(Archivo);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea="";
                try {
                    Linea=LeerArchivo.readLine();
                    //String[] split = null;
                    while(Linea != null)
                    {
                        if(Linea!="")
                        {
                            //Ingrear a usuaro
                            escribirUsuarioMaster(Linea);

                            //split=Linea.split(",");

                        }
                        Linea=LeerArchivo.readLine();
                    }

                    LecturaArchivo.close();
                    LeerArchivo.close();
                    //Modificar desc Usuario
                    
                    //Limpiar Archivo
                    
                    
                    
                } catch (IOException ex) {
                    //strError= ex.getMessage().toString();
                    
                }
            } catch (FileNotFoundException ex) {
                //strError= ex.getMessage().toString();
                
            }            
        }
        else
        {
            //strError="No existe el archivo";
        }
    }
    
    public void escribirUsuarioMaster(String linea)
    {
        File Archivo = new File(pathUsuarioMaster);
           //File Archivo = new File(strPath);
            try
            {
                FileWriter Escribir = new FileWriter(Archivo,true);
                BufferedWriter bw = new BufferedWriter(Escribir);
                bw.write(linea+ System.getProperty( "line.separator" ));
                bw.close();
                Escribir.close();
            }
            catch(IOException ex)
            {
                //strError= ex.getMessage().toString();
            }  
    }
    
    public boolean escribirUsuario(Usuario usuarioObj)
    {
        File Archivo = new File("C:\\MEIA/Usuario.txt");
        //
        try
            {
                FileWriter Escribir = new FileWriter(Archivo,true);
                BufferedWriter bw = new BufferedWriter(Escribir);
                bw.write(usuarioObj.usuario+"|");
                bw.write(usuarioObj.nombre+"|");
                bw.write(usuarioObj.apellido+"|");
                bw.write(usuarioObj.password+"|");
                bw.write(usuarioObj.rol+"|");
                bw.write(usuarioObj.fechaNacimiento+"|");
                bw.write(usuarioObj.correo+"|");
                bw.write(usuarioObj.telefono+"|");
                bw.write(usuarioObj.pathFotografia+"|");
                bw.write(usuarioObj.estatus+System.getProperty( "line.separator" ));
                
                bw.close();
                Escribir.close();
                
                return true;
            }
            catch(IOException ex)
            {
                //strError= ex.getMessage().toString();
                return false;
            } 
    }
    
    public boolean UsuarioExiste(String User)
    {
        
        File pathFolderMEIA = new File("C:\\MEIA");
        String strPath = pathFolderMEIA + "/Usuario.txt";
        String strError;
        boolean usuarioCorrecto = false;
        File ArchivoUsuariosTxt = new File(strPath);
        
            FileReader LecturaArchivo;
            try {
                LecturaArchivo = new FileReader(ArchivoUsuariosTxt);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea="";
                try {
                    Linea=LeerArchivo.readLine();
                    String[] split;
                    Usuario objUsuarioArchivo = new Usuario();
                    if (Linea=="")
                    {
                        // Crear primer usuario de este programa
                        LecturaArchivo.close();
                        LeerArchivo.close();
                    }
                    else
                    {
                        while(Linea != null)
                        {
                            if(Linea!="")
                            {
                                split=Linea.split("\\|");
                                objUsuarioArchivo.usuario = split[0];
                                objUsuarioArchivo.nombre = split[1];
                                objUsuarioArchivo.apellido = split[2];
                                objUsuarioArchivo.password = split[3];
                                objUsuarioArchivo.rol = Integer.parseInt(split[4]);
                                objUsuarioArchivo.fechaNacimiento = split[5];
                                objUsuarioArchivo.correo = split[6];
                                objUsuarioArchivo.telefono = Integer.parseInt(split[7]);
                                objUsuarioArchivo.pathFotografia = split[8];
                                //objUsuarioArchivo.descripcion = split[9];
                                objUsuarioArchivo.estatus = Integer.parseInt(split[9]);
                            
                                if ((User.equals(objUsuarioArchivo.usuario))) 
                                {
                                    usuarioCorrecto = true;
                                }
                                else
                                {
                                    usuarioCorrecto= false;
                                }
                            }
                            Linea=LeerArchivo.readLine();
                        }  
                    }
                    
                    LecturaArchivo.close();
                    LeerArchivo.close();
                    
                    //listaNombre.setModel(modeloNombre);
                    //listaApellido.setModel(modeloApellido);
                    strError="";
                    //return true;
                    
                } catch (IOException ex) {
                    strError= ex.getMessage().toString();
                    //return false;
                }
            } catch (FileNotFoundException ex) {
                strError= ex.getMessage().toString();
                //return false;
            }                      
    return usuarioCorrecto;
    }
    
    public Usuario ObtenerUsuario(String User)
    {
        Usuario user = new Usuario();
        File pathFolderMEIA = new File("C:\\MEIA");
        String strPath = pathFolderMEIA + "/Usuario.txt";
        String strError = "";
        boolean usuarioCorrecto = false;
        File ArchivoUsuariosTxt = new File(strPath);
        
            FileReader LecturaArchivo;
            try {
                LecturaArchivo = new FileReader(ArchivoUsuariosTxt);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea="";
                try {
                    Linea=LeerArchivo.readLine();
                    String[] split;
                    Usuario objUsuarioArchivo = new Usuario();
                    if (Linea=="")
                    {
                        // Crear primer usuario de este programa
                        LecturaArchivo.close();
                        LeerArchivo.close();
                    }
                    else
                    {
                        while(Linea != null)
                        {
                            if(Linea!="")
                            {
                                split=Linea.split("\\|");
                                objUsuarioArchivo.usuario = split[0];
                                objUsuarioArchivo.nombre = split[1];
                                objUsuarioArchivo.apellido = split[2];
                                objUsuarioArchivo.password = split[3];
                                objUsuarioArchivo.rol = Integer.parseInt(split[4]);
                                objUsuarioArchivo.fechaNacimiento = split[5];
                                objUsuarioArchivo.correo = split[6];
                                objUsuarioArchivo.telefono = Integer.parseInt(split[7]);
                                objUsuarioArchivo.pathFotografia = split[8];
                                objUsuarioArchivo.estatus = Integer.parseInt(split[9]);
                            
                                if ((User.equals(objUsuarioArchivo.usuario))) 
                                {
                                    split=Linea.split("\\|");
                                    user.usuario = split[0];
                                    user.nombre = split[1];
                                    user.apellido = split[2];
                                    user.password = split[3];
                                    user.rol = Integer.parseInt(split[4]);
                                    user.fechaNacimiento = split[5];
                                    user.correo = split[6];
                                    user.telefono = Integer.parseInt(split[7]);
                                    user.pathFotografia = split[8];
                                    user.estatus = Integer.parseInt(split[9]);
                                    usuarioCorrecto= true;
                                }
                                else
                                {
                                    usuarioCorrecto= false;
                                }
                            }
                            Linea=LeerArchivo.readLine();
                        }  
                    }
                    
                    LecturaArchivo.close();
                    LeerArchivo.close();
                    
                } catch (IOException ex) {
                    strError= ex.getMessage().toString();
                    //return false;
                }
            } catch (FileNotFoundException ex) {
                strError= ex.getMessage().toString();
                //return false;
            }                      
        return user;
    }
    
    public void ModificaUsuario(Usuario objUsuario) throws IOException
    {
        BufferedReader file = new BufferedReader(new FileReader("C:\\MEIA/Usuario.txt"));
         File Archivo = new File("C:\\MEIA/Usuario.txt");
        String line;String input = "";
        while((line = file.readLine()) != null){
            if(line.contains(objUsuario.usuario))
            {
                FileWriter Escribir = new FileWriter(Archivo,true);
                BufferedWriter bw = new BufferedWriter(Escribir);
                bw.write(objUsuario.usuario+"|");
                bw.write(objUsuario.nombre+"|");
                bw.write(objUsuario.apellido+"|");
                bw.write(objUsuario.password+"|");
                bw.write(objUsuario.rol+"|");
                bw.write(objUsuario.fechaNacimiento+"|");
                bw.write(objUsuario.correo+"|");
                bw.write(objUsuario.telefono+"|");
                bw.write(objUsuario.pathFotografia+"|");
                bw.write(objUsuario.estatus+System.getProperty( "line.separator" ));
                
                bw.close();
                Escribir.close();
            }
        }
        FileOutputStream fileOut = new FileOutputStream("C:\\MEIA/Usuario.txt");
        fileOut.write(input.getBytes());
        fileOut.close();
    }
    
}
