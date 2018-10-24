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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author ferefrapb
 */
public class SecuencialIndizado {
        private String NombreUsuario;
        private int PrimerRegistro;
        public List<String[]> Indice;
        private int BloqueActual;
        private int RegistrosActivos;
        private int RegistrosInactivos;
        
    public SecuencialIndizado(String Usuario)
    {
        try{
            NombreUsuario=Usuario;
                BufferedReader Lector;
                String linea;
                String[] contenido;
                Indice= new ArrayList();
                File Archivo=new File("C:\\MEIA\\vacios.txt");
                if (!Archivo.exists()) 
                {
                    Archivo.createNewFile();
                }
                creaIndiceListaUsuario();
                Lector=new BufferedReader(new FileReader("C:\\MEIA\\desc_indice_lista_usuario.txt"));
                linea=Lector.readLine();
                while(linea!=null)
                {
                    contenido= linea.split(Pattern.quote(":"));
                switch (contenido[0]) {
                    case "registro_inicial":
                        PrimerRegistro=Integer.valueOf(contenido[1].trim());
                        break;
                    case "bloque_actual":
                        BloqueActual=Integer.valueOf(contenido[1].trim());
                        break;   
                    case "registros_activos":
                        RegistrosActivos=Integer.valueOf(contenido[1].trim());
                        break;
                    case "registros_inactivos":
                        RegistrosInactivos=Integer.valueOf(contenido[1].trim());
                        break;
                }
                        linea=Lector.readLine();
                }
                Lector.close();
                    
                Lector=new BufferedReader(new FileReader("C:\\MEIA\\indice_lista_usuario.txt"));
                linea=Lector.readLine();
                //Se cargan las lineas del indice que existen
                while(linea!=null)
                {
                    Indice.add(linea.split(Pattern.quote("|")));
                    linea=Lector.readLine();
                }
                    Lector.close();
        }catch(IOException | NumberFormatException ex)
        {
            
        }
    }
    //Método que crea archivo Lista Usuario
    public void creaArchivoListaUsuario(int NumeroBloque, String Usuario)
    {
        try{
            File Archivo;
            BufferedWriter Escritor;
            Date fechaActual = new Date();            
            DateFormat formatoFecha;
            
            Archivo=new File("C:\\MEIA\\lista_usuario_"+String.valueOf(NumeroBloque)+".txt");
            if (!Archivo.exists()) {
                Archivo.createNewFile();
            }
            ActualizarDescriptorBloque(NumeroBloque, 0);
            
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                           "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //Actualiza Descriptor del Bloque Enviado por Parámetro
    private void ActualizarDescriptorBloque(int NumeroBloque, int NuevosRegistros)
    {
        try{
            String UsuarioLogeado = NombreUsuario;
            Date fechaActual = new Date();            
            DateFormat formatoFecha;
            File Archivo;
            BufferedReader Lector;
            BufferedWriter Escritor;
            String linea;
            String [] contenido;
            
            formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            String fechaCreacion = formatoFecha.format(fechaActual);
            formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

            Archivo=new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt");
            if(!Archivo.exists())
            {
                creaDescriptorListaUsuario( NumeroBloque, UsuarioLogeado);
                /*Archivo.createNewFile();

                Escritor=new BufferedWriter(new FileWriter("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt"));
            
                Escritor.write("Nombre simbólico del archivo: lista_usuario_"+String.valueOf(NumeroBloque)+".txt \r\n");
                Escritor.write("Usuario creador: admin \r\n");
                Escritor.write("Fecha y hora de creación: "+fechaCreacion+"\r\n");
                Escritor.write("Fecha de ultima modificación: "+formatoFecha.format(fechaActual)+"\r\n");
                Escritor.write("Número de Registros: 0 \r\n");
                Escritor.write("Máximo de registros: 5 \r\n");
                Escritor.write("Metadatos: Usuario|grupo|amigo|fecha_transaccion|estatus \r\n");
                Escritor.write("Delimitador: |");
                Escritor.close();*/
            }
            else
            {
                Archivo=new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt");
                Lector=new BufferedReader(new FileReader("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt"));
                Escritor = new BufferedWriter(new FileWriter("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+"_temp.txt"));
                    linea=Lector.readLine();
                    while(linea!=null)
                    {
                        contenido= linea.split(Pattern.quote(":"));
                    switch (contenido[0].trim()) {
                        case "fecha_modificacion":
                            formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                            linea=contenido[0]+": "+formatoFecha.format(fechaActual);
                            break;
                        case "usuario_modificacion":
                            linea=contenido[0]+": "+UsuarioLogeado;
                            break;
                        case "#_registros":
                            Integer nR=Integer.valueOf(contenido[1].trim());
                            linea=contenido[0]+": "+String.valueOf(nR+NuevosRegistros);
                            break;
                    }
                        Escritor.write(linea+"\r\n");   
                        linea=Lector.readLine();
                    }
                    Escritor.close();
                    Lector.close();
                    Archivo.delete();
                    Archivo=new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+"_temp.txt");
                    Archivo.renameTo(new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt"));
            }
        }
        catch(IOException | NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                           "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //Crea Descriptor por Bloque de Datos
    public void creaDescriptorListaUsuario(int NumeroBloque, String Usuario) throws IOException
    {
        String UsuarioLogeado = NombreUsuario;
        Date fechaActual = new Date();            
        DateFormat formatoFecha;
        File Archivo;
        BufferedReader Lector;
        BufferedWriter Escritor;
        String linea;
        String [] contenido;

        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaCreacion = formatoFecha.format(fechaActual);
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        Archivo=new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt");
        try
        {
            //creaDescriptorListaUsuario( NumeroBloque, UsuarioLogeado);
            Archivo=new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt");
            Archivo.createNewFile();

            Escritor=new BufferedWriter(new FileWriter("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt"));

            Escritor.write("nombre_simbolico: lista_usuario_"+String.valueOf(NumeroBloque)+".txt \r\n");
            Escritor.write("fecha_creacion: "+fechaCreacion+"\r\n");
            Escritor.write("usuario_creacion: "+Usuario.trim()+" \r\n");
            Escritor.write("fecha_modificacion: "+formatoFecha.format(fechaActual)+"\r\n");
            Escritor.write("usuario_modificacion: "+Usuario.trim()+" \r\n");
            Escritor.write("#_registros: 0 \r\n");
            Escritor.write("registros_activos: 0 \r\n");
            Escritor.write("registros_inactivos: 0 \r\n");
            Escritor.write("max_reorganizacion: 5 \r\n");
            Escritor.close();
        }
        catch(IOException | NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                           "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        
        
    }
    
    public void creaDescriptorIndiceListaUsuario()
    {
        BufferedWriter Escritor;
        Date fechaActual = new Date();            
        DateFormat formatoFecha;
        File Archivo;

        formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        String fechaCreacion = formatoFecha.format(fechaActual);
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            Escritor=new BufferedWriter(new FileWriter("C:\\MEIA\\desc_indice_lista_usuario.txt"));
            Escritor.write("nombre_simbolico: desc_indice_lista_usuario\r\n");
            Escritor.write("fecha_creacion: "+fechaCreacion+"\r\n");
            Escritor.write("usuario_creacion: "+NombreUsuario+" \r\n");
            Escritor.write("fecha_modificacion: "+formatoFecha.format(fechaActual)+"\r\n");
            Escritor.write("usuario_modificacion: "+NombreUsuario+" \r\n");
            Escritor.write("#_registros: 0 \r\n");
            Escritor.write("#_bloques: 0 \r\n");
            Escritor.write("bloque_actual: 1 \r\n");
            Escritor.write("registro_inicial: 0 \r\n");
            Escritor.write("registros_activos: 0 \r\n");
            Escritor.write("registros_inactivos: 0 \r\n");
            Escritor.write("max_reorganizacion: 5 \r\n");


            /*.write("Nombre simbólico del archivo: Indice_grupo_amigos.txt \r\n");
            Escritor.write("Usuario creador: admin \r\n");
            Escritor.write("Fecha y hora de creación: "+fechaCreacion+"\r\n");
            Escritor.write("Fecha de ultima modificación: "+formatoFecha.format(fechaActual)+"\r\n");
            Escritor.write("Número de registros: 0 \r\n");
            Escritor.write("Número de registros activos: 0 \r\n");
            Escritor.write("Número de registros inactivos: 0 \r\n");
            Escritor.write("Registro inicial: -1 \r\n");
            Escritor.write("Número de bloques: 1 \r\n");
            Escritor.write("Bloque Actual: 1 \r\n");
            Escritor.write("Metadatos: Registro|Posicion|Usuario|grupo|amigo|Siguiente \r\n");
            Escritor.write("Delimitador: |");*/
            Escritor.close();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                           "Error", JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    //Crea archivo de Lista de Usuario por Bloque
    private void CrearListaUsuario(int NumeroBloque)
    {
        try{
            File Archivo;
            
            Archivo=new File("C:\\MEIA\\lista_usuario_"+String.valueOf(NumeroBloque)+".txt");
            if (!Archivo.exists()) {
                Archivo.createNewFile();
            }
            actualizaDescriptorListaUsuario(NumeroBloque,0);
            
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                           "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //Crea el Índice de la Lista de Usuarios, que se utiliza al inicializar la clase SecuencialIndizado
    public void creaIndiceListaUsuario()
    {
        try{
            BufferedWriter Escritor;
            Date fechaActual = new Date();            
            DateFormat formatoFecha;
            File Archivo;
            
            formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            String fechaCreacion = formatoFecha.format(fechaActual);
            formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            
            Indice= new ArrayList();
           
            Archivo=new File("C:\\MEIA\\indice_lista_usuario.txt");
            if (!Archivo.exists()) 
            {
                 Archivo.createNewFile();
            }
            Archivo = new File("C:\\MEIA\\desc_indice_lista_usuario.txt");
            if(!Archivo.exists())
            {
                creaDescriptorIndiceListaUsuario();
                /*.write("Nombre simbólico del archivo: Indice_grupo_amigos.txt \r\n");
                Escritor.write("Usuario creador: admin \r\n");
                Escritor.write("Fecha y hora de creación: "+fechaCreacion+"\r\n");
                Escritor.write("Fecha de ultima modificación: "+formatoFecha.format(fechaActual)+"\r\n");
                Escritor.write("Número de registros: 0 \r\n");
                Escritor.write("Número de registros activos: 0 \r\n");
                Escritor.write("Número de registros inactivos: 0 \r\n");
                Escritor.write("Registro inicial: -1 \r\n");
                Escritor.write("Número de bloques: 1 \r\n");
                Escritor.write("Bloque Actual: 1 \r\n");
                Escritor.write("Metadatos: Registro|Posicion|Usuario|grupo|amigo|Siguiente \r\n");
                Escritor.write("Delimitador: |");*/
                RegistrosActivos=0;
                RegistrosInactivos=0;
            }
            
            
            CrearListaUsuario(1);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                           "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //Inserta el registro en los archivos correspondientes
    public Boolean InsertarRegistro(String NombreLista, String NombreUsuario, String Descripcion)
    {
        try{
            int Num;
                
            if (VerificarRegistro(NombreLista, NombreUsuario)==null) 
            {
                if (EsListaUsuarioValida(BloqueActual)) 
                {
                    
                    Num=InsertaListaUsuario(NombreLista, NombreUsuario, Descripcion, BloqueActual);
                }
                else
                {
                    Num=InsertaListaUsuario(NombreLista, NombreUsuario, Descripcion, BloqueActual+1);
                }
                InsertarIndiceListaUsuario(BloqueActual,Num, NombreLista, NombreUsuario);
                return true;
            }
            else
            {
                return false;
            }
        }catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, null);
        }
        return false;
        
    }
    
    //Inserta Datos en Archivo Lista de Usuarios
    private int InsertaListaUsuario(String Lista, String Usuario, String Descripcion, int Bloque)
    {
        try{
            int NumeroRegistros=0;
            BufferedReader Lector;
            String linea;
            String[] contenido=new String[6];
            Date fechaActual = new Date();            
            DateFormat formatoFecha;
            formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            if (Bloque>BloqueActual) {
                BloqueActual=Bloque;
                
            }
            File Archivo =new File("C:\\MEIA\\lista_usuario_"+String.valueOf(Bloque)+".txt");
            if(!Archivo.exists())
            {
                CrearListaUsuario(Bloque);
            }
            BufferedWriter Escritor = new BufferedWriter(new FileWriter(Archivo,true));
            
            Lector=new BufferedReader(new FileReader("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(Bloque)+".txt"));
            linea=Lector.readLine();
            while(linea!=null)
            {
                contenido= linea.split(Pattern.quote(":"));
                if(contenido[0].equals("#_registros"))
                {
                    NumeroRegistros=Integer.valueOf(contenido[1].trim());
                    break;
                }
                linea=Lector.readLine();
            }
            Lector.close();
            Escritor.close();
            NumeroRegistros++;
            
            Archivo =new File("C:\\MEIA\\lista_usuario_"+String.valueOf(Bloque)+".txt");
            Escritor = new BufferedWriter(new FileWriter(Archivo,true));
            //Nombre_lista, Usuario, Usuario_asociado, Descripcion, Fecha_creacion, Estatus
            contenido=new String[6];
            contenido[0]=(Lista+"                              ").substring(0,30);
            contenido[1]=(Usuario+"                       ").substring(0,20);
            contenido[2]=(this.NombreUsuario+"                              ").substring(0,20);
            contenido[3]=(Descripcion+"                                     ").substring(0,40);
            contenido[4]=formatoFecha.format(fechaActual);
            contenido[5]="1";
            Escritor.write(Join(contenido,"|")+"\r\n");
            Escritor.close();
            ActualizarDescriptorBloque(BloqueActual,1);
            
            return NumeroRegistros;
            }catch(IOException | NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
        return -1;
    }
    
    //Verifica índice del registro
    public String[] VerificarRegistro(String NombreAmigo, String NombreLista)
    {
        try{
            String Registro="0|1.1|"+NombreUsuario+"|"+NombreLista+"|"+NombreAmigo+"|1";
            String[]Contenido;
            int Comparacion;
            if (0!=Indice.size()) 
            {
                //Compara directamente contra el primer registro
                Contenido=Indice.get(PrimerRegistro-1);
                Comparacion=CompararIndice(Registro.split(Pattern.quote("|")),Contenido);
                //Si es menor al primer registro no existe
                if(Comparacion<0)
                {
                        return null;
                }
                else if(Comparacion==0)
                {
                    return Contenido;
                }
                //Sino leera los siguientes
                while(!Contenido[5].equals("-1")){
                    Contenido=Indice.get(Integer.valueOf(Contenido[5])-1);
                    Comparacion=CompararIndice(Registro.split(Pattern.quote("|")),Contenido);
                    if(Comparacion<0)
                    {
                        return null;
                    }
                    else if(Comparacion==0)
                    {
                        return Contenido;
                    }
                }
                return null;
                
            }
            else
            {
                return null;
            }
        
        }catch(Exception ex)
        {
            
        }
        return null;
    }
    
    //Realiza la comparación de índices en el método VerificarRegistros
    public int CompararIndice(String[]obj1, String[] obj2) 
    {
        int salida;

        salida = obj1[2].trim().compareTo(obj2[2].trim());
        if(salida == 0)
        {
            salida = obj1[3].trim().compareTo(obj2[3].trim());
            if(salida == 0)
            {
                salida = obj1[4].trim().compareTo(obj2[4].trim());
                return salida;
            }
            else
            {
                return salida;
            }
        }
        else
        {
            return salida;
        }
    }
    
    //Realiza la transformación de array de contenido a string concatenando el char de separación
    private String Join(String [] contenido, String caracter)
    {
        String junto;
        junto=contenido[0];
        for (int i = 1; i < contenido.length; i++) {
            junto=junto+caracter+contenido[i];
        }
        return junto;
    }
    
    //Inserta Datos en Archivo Índice de Lista de Usuarios
    private void InsertarIndiceListaUsuario(int NumeroBloque, int NumeroEnBloque, String Lista, String Usuario)
    {
         try{
            BufferedReader Lector;
            BufferedWriter Escritor;
            String linea;
            List<String> vacios=new ArrayList();
            int NoRegistro;
            //Se empieza leyendo el archivo de filas de indice nulas y se cargan a memoria
            Lector=new BufferedReader(new FileReader("C:\\MEIA\\vacios.txt"));
            linea=Lector.readLine();
            while(linea!=null)
            {
                vacios.add(linea);
                linea=Lector.readLine();
            }
            Lector.close();
            //Si hay algun espacio vacio se escoge el primero y se vuelve a escribir el resto.
             if (0!=vacios.size()) 
             {
                 NoRegistro=Integer.valueOf(vacios.get(0));
                 RegistrosInactivos--;
                 vacios.remove(0);
                 Escritor=new BufferedWriter(new FileWriter("C:\\MEIA\\vacios.txt"));
                 for (int i = 0; i < vacios.size(); i++) {
                     Escritor.write(vacios.get(i)+"\r\n");
                 }
                 Escritor.close();
             }
             else
             {
                 //Si no hay ninguno vacio se insertara al final del indice
                 NoRegistro=Indice.size()+1;
             }
             //Se crea el nuevo registro 
             //No. Registro, Posición, Nombre_lista, Usuario, Usuario_asociado, Siguiente, Estatus.
             String [] NuevoRegistro= new String[7];
             NuevoRegistro[0]=String.valueOf(NoRegistro);
             NuevoRegistro[1]=String.valueOf(NumeroBloque)+"."+String.valueOf(NumeroEnBloque);
             NuevoRegistro[2]=Lista;
             NuevoRegistro[3]=this.NombreUsuario;;
             NuevoRegistro[4]=Usuario;
             NuevoRegistro[5]="-1";
             NuevoRegistro[6]="1";
             
             //Si en caso no existe ningun registro se asigna directamente.
             if (RegistrosActivos==0) 
             {
                PrimerRegistro=Indice.size()+1;
                BloqueActual=1;
                if(Indice.size()==0)
                {
                    Indice.add(NuevoRegistro);
                }
                else
                {
                    Indice.set(NoRegistro-1, NuevoRegistro);
                }
             }
             else
             {
                 BloqueActual=NumeroBloque;
                 //Sino se asignara y se determinara su posicion
                String[] temp=Indice.get(PrimerRegistro-1);
                String[] aux;
                aux = temp;
                Boolean SeUbico=false;
                int Comparacion=CompararIndice(NuevoRegistro,temp);
                //Se compara con el primer registro
                 if (Comparacion<0) 
                 {
                     PrimerRegistro=Integer.valueOf(NuevoRegistro[0]);
                     NuevoRegistro[5]=temp[0];
                     if(Indice.size()==NoRegistro-1)
                            {
                              Indice.add(NuevoRegistro);
                            }
                            else
                            {
                                Indice.set(NoRegistro-1, NuevoRegistro);
                            }
                     RegistrosActivos++;
                     actualizaIndiceListaUsuario();
                     return;
                 }
                 else
                 {
                    while(!temp[5].equals("-1"))
                    {
                        aux=temp;
                        temp=Indice.get(Integer.valueOf(temp[5])-1);
                        Comparacion=CompararIndice(NuevoRegistro,temp);
                        
                        if (Comparacion<0) 
                        {
                            aux[5]=NuevoRegistro[0];
                            NuevoRegistro[5]=temp[0];
                            Indice.set(Integer.valueOf(aux[0])-1, aux);
                            if(Indice.size()==NoRegistro-1)
                            {
                              Indice.add(NuevoRegistro);
                            }
                            else
                            {
                                Indice.set(NoRegistro-1, NuevoRegistro);
                            }
                            
                            SeUbico=true;
                            break;
                        }
                        
                    }
                 }
                //Si al final terminar el ciclo no se ubico el registro es porque su lugar despues del ultimo
                 if(!SeUbico)
                 {
                    temp[5]=NuevoRegistro[0];
                    Indice.set(Integer.valueOf(temp[0])-1, temp);
                    if(Indice.size()==NoRegistro-1)
                            {
                              Indice.add(NuevoRegistro);
                            }
                            else
                            {
                                Indice.set(NoRegistro, NuevoRegistro);
                            }
                 }

             }   
            RegistrosActivos++;
            actualizaIndiceListaUsuario();
            }catch(IOException | NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
    }
    
    //Verifica Donde se Puede Insertar un Registro en Descriptor de Lista de Usuarios
    private Boolean EsListaUsuarioValida(int Bloque)
    {
        boolean SePuede = false;
        try{
            int NumeroRegistros=0;
            int NumeroMaximo=0;
                BufferedReader Lector;
                String linea;
                String[] contenido;
                
                Lector=new BufferedReader(new FileReader("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(Bloque)+".txt"));
                linea=Lector.readLine();
                    while(linea!=null)
                    {
                        contenido= linea.split(Pattern.quote(":"));
                switch (contenido[0]) 
                    {
                        case "#_registros":
                            NumeroRegistros=Integer.valueOf(contenido[1].trim());
                            break;
                        case "max_reorganizacion":
                            NumeroMaximo=Integer.valueOf(contenido[1].trim());
                            break;   
                    }
                        linea=Lector.readLine();
                    }
                    Lector.close();
                    if (NumeroMaximo>NumeroRegistros) 
                    {
                        SePuede = true;
                    }
                    return SePuede;
                
            }catch(IOException | NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
        return SePuede;
    }
     
    //Actualiza el Índice de Lista de Usuarios
    public void actualizaIndiceListaUsuario()
    {
        try{
            File Archivo;
            BufferedWriter Escritor;
            
            Archivo=new File("C:\\MEIA\\indice_lista_usuario.txt");
            if (Archivo.exists()) {
                Archivo.delete();
            }
            Archivo.createNewFile();
            
            Escritor=new BufferedWriter(new FileWriter(("C:\\MEIA\\indice_lista_usuario.txt")));
            
            for (int i = 0; i < Indice.size(); i++) 
            {
                Escritor.write(Join(Indice.get(i),"|")+" \r\n");
            }
            Escritor.close();
            ActualizarDescIndiceListaUsuario();
            
        }
        catch(Exception ex)
        {
            
        }
    }
    
    //Actualiza el Descriptor del Índice de Lista de Usuario
    private void ActualizarDescIndiceListaUsuario()
    {
        try{
                File Archivo;
                BufferedReader Lector;
                BufferedWriter Escritor;
                String linea;
                Date fechaActual = new Date();            
                DateFormat formatoFecha;
                String[] contenido;
                formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                
                Archivo=new File("C:\\MEIA\\desc_indice_lista_usuario.txt");
                Lector=new BufferedReader(new FileReader("C:\\MEIA\\desc_indice_lista_usuario.txt"));
                Escritor = new BufferedWriter(new FileWriter("C:\\MEIA\\desc_indice_lista_usuario.txt"));
                    linea=Lector.readLine();
                    while(linea!=null)
                    {
                        contenido= linea.split(Pattern.quote(":"));
                    switch (contenido[0].trim()) {
                        case "fecha_modificacion":
                            linea=contenido[0]+": "+formatoFecha.format(fechaActual);
                            break;
                        case "usuario_modificacion":
                            linea=contenido[0]+": "+this.NombreUsuario;
                            break;
                        case "#_bloques":
                            linea=contenido[0]+": "+String.valueOf(BloqueActual);
                            break;
                        case "bloque_actual":
                            linea=contenido[0]+": "+String.valueOf(BloqueActual);
                            break;
                        case "registro_inicial":
                            linea=contenido[0]+": "+String.valueOf(PrimerRegistro);
                            break;
                        case "registros_activos":
                            linea=contenido[0]+": "+String.valueOf(RegistrosActivos);
                        break;
                        case "registros_inactivos":
                        linea=contenido[0]+": "+String.valueOf(RegistrosInactivos);
                        break;
                        case "#_registros":
                             linea=contenido[0]+": "+String.valueOf(RegistrosInactivos+RegistrosActivos);
                            break;
                    }
                        Escritor.write(linea+"\r\n");   
                        linea=Lector.readLine();
                    }
                    Escritor.close();
                    Lector.close();
                    Archivo.delete();
                    Archivo=new File("C:\\MEIA\\desc_indice_lista_usuario_temp.txt");
                    Archivo.renameTo(new File("C:\\MEIA\\desc_indice_lista_usuario.txt"));
                
            }catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
    }
    
        //Actualiza el descriptor de la lista de usuarios
    public void actualizaDescriptorListaUsuario(int NumeroBloque, int NuevosRegistros)
    {
        try{
            String UsuarioLogeado = NombreUsuario;
            Date fechaActual = new Date();            
            DateFormat formatoFecha;
            File Archivo;
            BufferedReader Lector;
            BufferedWriter Escritor;
            String linea;
            String [] contenido;
            
            formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            String fechaCreacion = formatoFecha.format(fechaActual);
            formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

            Archivo=new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt");
            if(!Archivo.exists())
            {
                creaDescriptorListaUsuario( NumeroBloque, UsuarioLogeado);
            }
            else
            {
                Archivo=new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt");
                Lector=new BufferedReader(new FileReader("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt"));
                Escritor = new BufferedWriter(new FileWriter("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+"_temp.txt"));
                    linea=Lector.readLine();
                    while(linea!=null)
                    {
                        contenido= linea.split(Pattern.quote(":"));
                        switch (contenido[0].trim()) {
                            case "fecha_modificacion":
                                formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                                linea=contenido[0]+": "+formatoFecha.format(fechaActual);
                                break;
                            case "usuario_modificacion":
                                linea=contenido[0]+": "+this.NombreUsuario;
                                break;
                            case "#_registros":
                                Integer nR=Integer.valueOf(contenido[1].trim());
                                linea=contenido[0]+": "+String.valueOf(nR+NuevosRegistros);
                                break;
                    }
                        Escritor.write(linea+"\r\n");   
                        linea=Lector.readLine();
                    }
                    Escritor.close();
                    Lector.close();
                    Archivo.delete();
                    Archivo=new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+"_temp.txt");
                    Archivo.renameTo(new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(NumeroBloque)+".txt"));
            }
        }
        catch(IOException | NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                           "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //Eliminar Registro en archivos respectivos
    public Boolean EliminarRegistro(String NUsuario, String Lista, String Usuario) throws IOException
    {
       try{
        String[] PosicionIndice;
        BufferedWriter Escritor;
        File Archivo;
        PosicionIndice=Indice.get(PrimerRegistro-1);
        Boolean Exito=false;
        String[] contenido;
        //Verifica si el que se va a eliminar es el primero de la lista
        /*NuevoRegistro[0]=String.valueOf(NoRegistro);
        NuevoRegistro[1]=String.valueOf(NumeroBloque)+"."+String.valueOf(NumeroEnBloque);
        NuevoRegistro[2]=Lista;
        NuevoRegistro[3]=this.NombreUsuario;;
        NuevoRegistro[4]=Usuario;
        NuevoRegistro[5]="-1";
        NuevoRegistro[6]="1";*/
        if (PosicionIndice[3].equals(NUsuario)&&PosicionIndice[2].equals(Lista)&&PosicionIndice[4].equals(Usuario)) 
        {
            PrimerRegistro=Integer.valueOf(PosicionIndice[5]);
            RegistrosActivos--;
            RegistrosInactivos++;
            PosicionIndice[6]="0";
            Archivo = new File("C:\\MEIA\\vacios.txt");
            Escritor = new BufferedWriter(new FileWriter(Archivo,true));
            Escritor.write(PosicionIndice[0]+"\r\n");
            Escritor.close();
            Indice.set(Integer.valueOf(PosicionIndice[0])-1, PosicionIndice);
            contenido=PosicionIndice[1].split(Pattern.quote("."));
            EliminarEnMaestro(Integer.valueOf(contenido[0]),Integer.valueOf(contenido[1]));
            actualizaIndiceListaUsuario();
            Exito=true;
        }
        else
        {
            String[] temp;
            String[] aux;
            temp=Indice.get(PrimerRegistro-1);
            while(!temp[5].equals("-1"))
            {
                aux=temp;
                temp=Indice.get(Integer.valueOf(temp[5])-1);
                        
                if (temp[3].equals(NUsuario)&&temp[2].equals(Lista)&&temp[4].equals(Usuario)) 
                {
                    aux[5]=temp[5];
                    temp[6]="0";
                    Indice.set(Integer.valueOf(aux[0])-1, aux);
                    Archivo = new File("C:\\MEIA\\vacios.txt");
                    Escritor = new BufferedWriter(new FileWriter(Archivo,true));
                    Escritor.write(temp[0]+"\r\n");
                    Escritor.close();
                    contenido=temp[1].split(Pattern.quote("."));
                    EliminarEnMaestro(Integer.valueOf(contenido[0]),Integer.valueOf(contenido[1]));
                    RegistrosActivos--;
                    RegistrosInactivos++;
                    actualizaIndiceListaUsuario();
                    Exito=true;
                    break;
                }        
            }
            
            
    }
        return Exito;
       }catch(NumberFormatException | IOException ex)
       {
           
       }
        return false;
    }
    
    //Elimina Registro en Maestro Lista Usuario
    private void EliminarEnMaestro(int NoBloque, int NoLinea)
    {
        try{
            BufferedWriter Escritor;
            String Linea;            
            RandomAccessFile Archivo;
            
            Archivo=new  RandomAccessFile("C:\\MEIA\\lista_usuario_"+String.valueOf(NoBloque)+".txt","rw");
            
            for (int i = 0; i < NoLinea; i++) {
                Linea=Archivo.readLine();
            }
            Archivo.seek(Archivo.getFilePointer()-3);
            Archivo.writeBytes("0");
            Archivo.close();
            ActualizarDescriptorBloque(NoBloque,0);
            
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                           "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //Devuelve Usuario de Lista Índice
    public List<String> DevolverUsuariosdeLista(String Lista)
    {
        String [] temp;
        List<String> Listado=new ArrayList();
        /*NuevoRegistro[0]=String.valueOf(NoRegistro);
        NuevoRegistro[1]=String.valueOf(NumeroBloque)+"."+String.valueOf(NumeroEnBloque);
        NuevoRegistro[2]=Lista;
        NuevoRegistro[3]=this.NombreUsuario;;
        NuevoRegistro[4]=Usuario;
        NuevoRegistro[5]="-1";
        NuevoRegistro[6]="1";*/
         if (RegistrosActivos!=0) {
            temp=Indice.get(PrimerRegistro-1);
            if ((temp[3].trim().toUpperCase().compareTo(this.NombreUsuario.trim().toUpperCase()))==0 && (temp[2].trim().toUpperCase().compareTo(Lista.trim().toUpperCase()))==0 )
                {
                    Listado.add(temp[4]);
                }   
            while(!temp[5].equals("-1"))
            {
                temp=Indice.get(Integer.valueOf(temp[5])-1);
                       
                //Si el usuario es mas grande se ira al siguiente
                if ((temp[3].trim().toUpperCase().compareTo(this.NombreUsuario.trim().toUpperCase()))==0 && (temp[2].trim().toUpperCase().compareTo(Lista.trim().toUpperCase()))==0 )
                {
                    Listado.add(temp[4]);
                }     
            }
            }
        return Listado;
    }
    
     public void EliminacionUsuario(String Nombre)
    {
        try{
            String [] temp;
         
             /*NuevoRegistro[0]=String.valueOf(NoRegistro);
             NuevoRegistro[1]=String.valueOf(NumeroBloque)+"."+String.valueOf(NumeroEnBloque);
             NuevoRegistro[2]=Lista;
             NuevoRegistro[3]=this.NombreUsuario;;
             NuevoRegistro[4]=Usuario;
             NuevoRegistro[5]="-1";
             NuevoRegistro[6]="1";*/
            if (RegistrosActivos!=0) {
            temp=Indice.get(PrimerRegistro-1);
            if ((temp[3].trim().toUpperCase().compareTo(Nombre.trim().toUpperCase()))==0 || (temp[4].trim().toUpperCase().compareTo(Nombre.trim().toUpperCase()))==0 )
                {
                    EliminarRegistro(temp[3], temp[2], temp[4]);
                }   
            while(!temp[5].equals("-1"))
            {
                temp=Indice.get(Integer.valueOf(temp[5])-1);
                       
                //Si el usuario es mas grande se ira al siguiente
                if ((temp[3].trim().toUpperCase().compareTo(Nombre.trim().toUpperCase()))==0 || (temp[4].trim().toUpperCase().compareTo(Nombre.trim().toUpperCase()))==0 )
                {
                    EliminarRegistro(temp[3], temp[2], temp[4]);
                }        
            }
            }
        }catch(Exception ex)
        {
            
        }
    }
    
    public void EliminarLista(String User, String NombreLista)
    {
        try{
            String [] temp;
            List<String> Listado=new ArrayList();;
        
            if (RegistrosActivos!=0) {
            temp=Indice.get(PrimerRegistro-1);
            if ((temp[4].trim().toUpperCase().compareTo(User.trim().toUpperCase()))==0 && (temp[2].trim().toUpperCase().compareTo(NombreLista.trim().toUpperCase()))==0 )
                {
                    EliminarRegistro(temp[3], temp[2], temp[4]);
                }   
            while(!temp[5].equals("-1"))
            {
                temp=Indice.get(Integer.valueOf(temp[5])-1);
                       
                //Si el usuario es mas grande se ira al siguiente
                if ((temp[4].trim().toUpperCase().compareTo(User.trim().toUpperCase()))==0 && (temp[2].trim().toUpperCase().compareTo(NombreLista.trim().toUpperCase()))==0 )
                {
                    EliminarRegistro(temp[3], temp[2], temp[4]);
                }       
            }
            }
        }catch(Exception ex)
        {
            
        }
    }
    
    //Reorganiza Registros de Lista de Usuario
     public void Reorganizar() throws FileNotFoundException, IOException
    {
        int NumeroBloques=0;
        BufferedReader Lector;
        RandomAccessFile Archivo;
        BufferedWriter Escritor;
        String linea;
        String[] contenido;                
            
        for (int i = 1; i <= BloqueActual; i++) {
            Archivo=new  RandomAccessFile("C:\\MEIA\\lista_usuario_"+String.valueOf(i)+".txt","rw");     
            linea=Archivo.readLine();
            int contador = 1;
            while(linea!=null)
            {
                if (BloqueActual == i) {
                    Archivo.close();
                    EliminarVacios();
                    actualizaIndiceListaUsuario();
                    return;
                }
                contenido= linea.split(Pattern.quote("|"));
                if(contenido[5].equals("0"))
                {                           
                    Archivo.seek(Archivo.getFilePointer()-linea.length() - 2);
                    String registro = ObtenerUltimoRegistro();
                    String[] contenidoRegistro = registro.split(Pattern.quote("|"));
                    Archivo.writeBytes(registro + "\r\n");  
                    Archivo.seek(Archivo.getFilePointer()-linea.length() - 2);
                    for (String[] item : Indice ) {
                        if ((item[2].equals(contenidoRegistro[0].replaceAll(" ", ""))) && (item[3].equals(contenidoRegistro[1].replaceAll(" ", ""))) && (item[4].equals(contenidoRegistro[2].replaceAll(" ", "")))) {
                            item[1] = String.valueOf(i) + "." + String.valueOf(contador);
                        }
                    }              
                }
                else{
                    contador++;                   
                }
                linea=Archivo.readLine();
            }
            Archivo.close();
            actualizaIndiceListaUsuario();
        }
    
    }
     
    //Elimina Registros Vacíos de Lista de Usuario 
    private void EliminarVacios() throws FileNotFoundException, IOException
    {
        int NumeroBloques=0;
        BufferedReader Lector;
        File Archivo;
        BufferedWriter Escritor;
        String linea;
        String[] contenido; 
        int contador=0;
        int contador2=1;
        
        Lector = new BufferedReader( new FileReader("C:\\MEIA\\lista_usuario_"+String.valueOf(BloqueActual)+".txt"));
        Escritor = new BufferedWriter(new FileWriter("C:\\MEIA\\Templista_usuario_"+String.valueOf(BloqueActual)+".txt"));
        linea = Lector.readLine();
        while(linea != null){
            contenido = linea.split(Pattern.quote("|"));
            if (!contenido[5].equals("0")) {                
                Escritor.write(linea);
                Escritor.newLine();
                for (String[] item : Indice ) {
                        if ((item[2].equals(contenido[0].replaceAll(" ", ""))) && (item[3].equals(contenido[1].replaceAll(" ", ""))) && (item[4].equals(contenido[2].replaceAll(" ", "")))) {
                            item[1] = String.valueOf(BloqueActual) + "." + String.valueOf(contador2);
                        }
                    } 
                contador2++;
            }
            else{
                contador--;
            }
            linea = Lector.readLine();
        }
        ActualizarDescriptorBloque(BloqueActual, contador);
        Lector.close();
        Escritor.close();
        Archivo = new File("C:\\MEIA\\lista_usuario_"+String.valueOf(BloqueActual)+".txt");
        Archivo.delete();
        Archivo = new File("C:\\MEIA\\Templista_usuario_"+String.valueOf(BloqueActual)+".txt");
        Archivo.renameTo(new File("C:\\MEIA\\lista_usuario_"+String.valueOf(BloqueActual)+".txt"));
        //Agregar si todos los elementos del registro son 0
        
    }
    
    //
    private String ObtenerUltimoRegistro() throws FileNotFoundException, IOException
    {
        int NumeroRegistros=0;
        BufferedReader Lector;
        File Archivo;
        BufferedWriter Escritor;
        String linea;
        String Registro;
        String[] contenido;
        
        Lector=new BufferedReader(new FileReader("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(BloqueActual)+".txt"));
        linea=Lector.readLine();
        while(linea!=null)
        {
            contenido= linea.split(Pattern.quote(":"));
            if(contenido[0].equals("#_registros"))
            {
                NumeroRegistros=Integer.valueOf(contenido[1].trim());
                break;
            }
            linea=Lector.readLine();
        }
        Lector.close();

        Lector=new BufferedReader(new FileReader("C:\\MEIA\\lista_usuario_"+String.valueOf(BloqueActual)+".txt"));        
        for (int i = 0; i < NumeroRegistros; i++) {
            linea=Lector.readLine();
        }           
        Registro = linea;
        NumeroRegistros--;
        Lector.close();
        
        if (NumeroRegistros > 0) {
            Lector=new BufferedReader(new FileReader("C:\\MEIA\\lista_usuario_"+String.valueOf(BloqueActual)+".txt"));
            Escritor=new BufferedWriter(new FileWriter("C:\\MEIA\\Templista_usuario_"+String.valueOf(BloqueActual)+".txt"));
            for (int i = 0; i < NumeroRegistros; i++) {
                Escritor.write(Lector.readLine());
                Escritor.newLine();
            }
            Lector.close();
            Escritor.close();
            File archivot = new File("C:\\MEIA\\lista_usuario_"+String.valueOf(BloqueActual)+".txt");
            archivot.delete();
            archivot = new File("C:\\MEIA\\Templista_usuario_"+String.valueOf(BloqueActual)+".txt");
            archivot.renameTo(new File("C:\\MEIA\\lista_usuario_"+String.valueOf(BloqueActual)+".txt"));
            ActualizarDescriptorBloque(BloqueActual,-1);
        }
        else{
            Archivo = new File("C:\\MEIA\\desc_lista_usuario_"+String.valueOf(BloqueActual)+".txt");
            Archivo.delete();
            Archivo = new File("C:\\MEIA\\lista_usuario_"+String.valueOf(BloqueActual)+".txt");
            Archivo.delete();
            BloqueActual--;           
        }                                  
         
        return Registro;
    }
    
}
