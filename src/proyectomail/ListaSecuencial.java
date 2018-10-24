package proyectomail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;


/**
 *
 * @author luisp
 */
public class ListaSecuencial {
      
        private String NombreUsuario;
        public List<String> Lista;
        
        public ListaSecuencial(String usuario){

            try{
            File archivo;
            BufferedReader Lector;
            String[] contenido;
            String linea;
            Lista = new ArrayList();            
            this.NombreUsuario = usuario;
            archivo = new File("C:\\MEIA\\lista.txt");
            if (!archivo.exists()){
                CrearArchivos();
                return;
            }
            else
            {
                Lector = new BufferedReader(new FileReader("C:\\MEIA\\lista.txt"));
                linea=Lector.readLine();
                while(linea!=null)
                {
                    contenido = linea.split(Pattern.quote("|"));
                    if(contenido[1].toUpperCase().trim().equals(this.NombreUsuario.toUpperCase()) && contenido[5].equals("1"))
                    {
                        Lista.add(contenido[0].trim());
                    }
                    linea=Lector.readLine();
                }
                Lector.close();
                Lector = new BufferedReader(new FileReader("C:\\MEIA\\bitacora_lista.txt"));
                linea=Lector.readLine();
                while(linea!=null)
                {
                    contenido = linea.split(Pattern.quote("|"));
                    if(contenido[1].toUpperCase().trim().equals(this.NombreUsuario) && contenido[5].equals("1"))
                    {
                        Lista.add(contenido[0]);
                    }
                    linea=Lector.readLine();
                }
                Lector.close();
            }
            
            
            
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE, null);
                
        }
        }
        
        //Crea los archivos con sus descriptores ya llenos
        private void CrearArchivos(){
        try{
            File archivo;
            BufferedWriter Escritor;
            Date fechaActual = new Date();            
            DateFormat formatoFecha;
            
            formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            String fechaCreacion = formatoFecha.format(fechaActual);
            formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

            //Archivo Maestro
            archivo = new File("C:\\MEIA\\lista.txt");
            archivo.getParentFile().mkdirs(); 
            archivo.createNewFile();
            
            archivo = new File("C:\\MEIA\\desc_lista.txt");
            archivo.getParentFile().mkdirs(); 
            archivo.createNewFile();            
            
            //Descriptor de archivo maestro
            Escritor= new BufferedWriter(new FileWriter("C:\\MEIA\\desc_lista.txt"));
            Escritor.write("Archivo: C://MEIA/lista.txt\r\n");
            Escritor.write("Descripción: Listas de distribución.\r\n");
            Escritor.write("Tipo: Archivo de datos\r\n");
            Escritor.write("Organización: Secuencial\r\n");
            Escritor.write("Autor: admin\r\n");
            Escritor.write("Creado: " + formatoFecha.format(fechaActual) + "\r\n");
            Escritor.write("Modificado: " + formatoFecha.format(fechaActual) + "\r\n");
            Escritor.write("Separador de campos: |\r\n");
            Escritor.write("Llave: Nombre_lista, usuario\r\n");
            Escritor.write("Orden: Ascendente\r\n");
            Escritor.write("Registros activos: 0\r\n");
            Escritor.write("Registros inactivos: 0\r\n");
            Escritor.write("Máximo de registros x organización: 10\r\n");
            Escritor.close();
                       
            //Bitacora del archivo maestro
            archivo = new File("C:\\MEIA\\bitacora_lista.txt");
            archivo.getParentFile().mkdirs(); 
            archivo.createNewFile();

            //Descriptor de la bitacora del archivo maestro
            archivo = new File("C:\\MEIA\\desc_bitacora_lista.txt");
            archivo.getParentFile().mkdirs(); 
            archivo.createNewFile();            
            Escritor= new BufferedWriter(new FileWriter("C:\\MEIA\\desc_bitacora_lista.txt"));
            Escritor.write("Archivo: C://MEIA/bitacora_lista.txt\r\n");
            Escritor.write("Descripción: Bitácora de listas de distribución.\r\n");
            Escritor.write("Tipo: Archivo de datos\r\n");
            Escritor.write("Organización: Apilo\r\n");
            Escritor.write("Autor: admin\r\n");
            Escritor.write("Creado: " + formatoFecha.format(fechaActual) + "\r\n");
            Escritor.write("Modificado: " + formatoFecha.format(fechaActual) + "\r\n");
            Escritor.write("Separador de campos: |\r\n");
            Escritor.write("Llave: Nombre_lista, usuario\r\n");
            Escritor.write("Orden: Ascendente\r\n");
            Escritor.write("Registros activos: 0\r\n");
            Escritor.write("Registros inactivos: 0\r\n");
            Escritor.close();
            
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex.getMessage(), "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE, null);
                
        }
    }
        
        private void ActualizarDescriptorBitacora(boolean SeReorganiza, int NuevosRegistros){
            
            File Archivo;
            BufferedReader Lector;
            BufferedWriter Escritor;
            String linea;
            Date fechaActual = new Date();            
            DateFormat formatoFecha;
            String[] contenido;
                
            try{

                
                Archivo = new File("C:\\MEIA\\desc_bitacora_lista.txt");
                Lector = new BufferedReader(new FileReader("C:\\MEIA\\desc_bitacora_lista.txt"));
                Escritor = new BufferedWriter(new FileWriter("C:\\MEIA\\desc_bitacora_lista_temporal.txt"));
                
                    linea = Lector.readLine();
                    while(linea != null)
                    {
                        contenido = linea.split(Pattern.quote(":"));
                        if(contenido[0].equals("Modificado"))
                        {
                            formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                            linea=contenido[0] + ": " + formatoFecha.format(fechaActual);
                            
                        }else if(contenido[0].equals("Registros activos"))
                        {
                            if(SeReorganiza)
                            {
                                linea = contenido[0]+": 0";
                            }
                            else
                            {
                                Integer nR = Integer.valueOf(contenido[1].trim());
                                linea = contenido[0] + ": "+String.valueOf(nR+NuevosRegistros);
                            }
                        }
                        Escritor.write(linea + "\r\n");   
                        linea = Lector.readLine();
                    }
                    Escritor.close();
                    Lector.close();
                    Archivo.delete();
                    Archivo=new File("C:\\MEIA\\desc_bitacora_lista_temporal.txt");
                    Archivo.renameTo(new File("C:\\MEIA\\desc_bitacora_lista.txt"));
                
            }catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
            
        }
        
        private void ActualizarDescriptor(boolean SeReorganiza, int Registros, int RegistrosInactivos, int RegistrosActivos)
        {
            try{
                File Archivo;
                BufferedReader Lector;
                BufferedWriter Escritor;
                String linea, fechaCreacion;
                Date fechaActual = new Date();            
                DateFormat formatoFecha;
                String[] contenido;
                
                Archivo= new File("C:\\MEIA\\desc_lista.txt");

                Lector=new BufferedReader(new FileReader("C:\\MEIA\\desc_lista.txt"));
                Escritor = new BufferedWriter(new FileWriter("C:\\MEIA\\desc_lista_temporal.txt"));
                    linea=Lector.readLine();
                    while(linea!=null)
                    {
                        contenido= linea.split(Pattern.quote(":"));
                        if(contenido[0].equals("Modificado"))
                        {
                            formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                            linea=contenido[0]+": "+formatoFecha.format(fechaActual);
                        }
                        else if(contenido[0].equals("Registros activos"))
                        {
                            if(!SeReorganiza)
                            {
                            Integer nR=Integer.valueOf(contenido[1].trim());
                            linea=contenido[0]+": "+String.valueOf(nR+Registros);
                            }
                            else
                            {
                                linea=contenido[0]+": " +String.valueOf(Registros);
                            }
                        }
                        else if(contenido[0].equals("Registros activos"))
                            {
                                if(!SeReorganiza){
                                Integer nR=Integer.valueOf(contenido[1].trim());
                                linea=contenido[0]+": "+String.valueOf(nR+RegistrosActivos);
                                }
                                else
                                {
                                    linea=contenido[0]+": " +String.valueOf(Registros);
                                }
                        }
                        else if(contenido[0].equals("Registros inactivos"))
                            {
                                if(!SeReorganiza)
                                {
                                    Integer nR=Integer.valueOf(contenido[1].trim());
                                    linea=contenido[0]+": "+String.valueOf(nR+RegistrosInactivos);
                                }
                                else
                                {
                                    linea=contenido[0]+": 0";
                                }
                        }
                        Escritor.write(linea+"\r\n");    
                        linea=Lector.readLine();
                    }
                    Lector.close();
                    Escritor.close();
                     Archivo.delete();
                    Archivo=new File("C:\\MEIA\\desc_lista_temporal.txt");
                    Archivo.renameTo(new File("C:\\MEIA\\desc_lista.txt"));
                
            }catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
        }
        
        public void CrearLista(String NombreLista, String Descripcion)
        {
                File Archivo;
                BufferedReader Lector;
                BufferedWriter Escritor;
                String linea;       
                String[] contenido;
                Integer numReorg=0, numReg=0;
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                
            try{

                
                if(VerificarRegistro(NombreLista)==null)
                {
                    Lector = new BufferedReader(new FileReader("C:\\MEIA\\desc_bitacora_lista.txt"));
            
                    linea = Lector.readLine();
                    while(linea != null){
                        contenido = linea.split(Pattern.quote(":"));
                
                        if(contenido[0].trim().equalsIgnoreCase("Máximo de registros x organización")){
                            numReorg = Integer.parseInt(contenido[1].trim());
                        }else if(contenido[0].trim().equalsIgnoreCase("Registros Activos")){
                            numReg = Integer.parseInt(contenido[1].trim());
                            break;
                        }
                        linea = Lector.readLine();
                    }
                    Lector.close();
                    
                    if (numReg >= numReorg){
                        
                        Reorganizar();
                    }

                    
                    Escritor = new BufferedWriter(new FileWriter("C:\\MEIA\\bitacora_lista.txt", true));
                    contenido = new String[6];
                    contenido[0] = NombreLista;
                    contenido[1] = this.NombreUsuario;
                    contenido[2] = Descripcion;
                    contenido[3] = "0";
                    contenido[4] = dateFormat.format(date);
                    contenido[5] = "1";
                    Escritor.write(Join(contenido,"|") + "\r\n");
                    Escritor.close();
                    
                    ActualizarDescriptorBitacora(false,1);
                    JOptionPane.showMessageDialog(null, "Lista añadida", "Lista de Distribución.", JOptionPane.INFORMATION_MESSAGE, null);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Lista existente.", "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE, null);
                }
                  
            }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
        }
        
        public String VerificarRegistro(String NombreLista)
        {
            try{
                File Archivo;
                BufferedReader Lector;
                String linea;       
                String[] contenido;
                
                Lector=new BufferedReader(new FileReader("C:\\MEIA\\bitacora_lista.txt"));
                linea=Lector.readLine();
                while(linea!=null){
                    contenido = linea.split(Pattern.quote("|"));
                    if(contenido[0].toUpperCase().equals(this.NombreUsuario.toUpperCase())){
                        if( contenido[1].toUpperCase().equals(NombreLista.toUpperCase()) && contenido[5].equals("1"))
                        {
                            Lector.close();
                            return linea;
                        }
                    }
                    linea=Lector.readLine();
                }
                Lector.close();
                //Si no lo encuentra en la bitacora lo buscara en el archivo original
                Lector=new BufferedReader(new FileReader("C:\\MEIA\\lista.txt"));
                linea=Lector.readLine();
                while(linea!=null){
                    contenido = linea.split(Pattern.quote("|"));
                    if(contenido[0].toUpperCase().equals(this.NombreUsuario)){
                        if( contenido[1].toUpperCase().equals(NombreLista) && contenido[5].equals("1"))
                        {
                            Lector.close();
                            return linea;
                        }
                    }
                    linea=Lector.readLine();
                }
                Lector.close();
                  
            }catch(Exception ex){
                   JOptionPane.showMessageDialog(null, ex.getMessage(), "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
            return null;
        }
        
        public void Reorganizar()
        {
            try{
            // Variables Locales
            List<String> ListasBitacora = new ArrayList();
            List<String> ListasRegistros = new ArrayList();
            Integer numTotal, numActivos = 0;
            String[] contenido;
            BufferedReader lector;
            BufferedWriter escritor;
            String linea;
            
            lector = new BufferedReader(new FileReader("C:\\MEIA\\lista.txt"));
            
            linea = lector.readLine();
            while (linea != null){
                contenido= linea.split(Pattern.quote("|"));
                if(contenido[5].equals("1"))
                {
                    ListasRegistros.add(linea);
                }
                linea = lector.readLine();
            }
            lector.close();
            
            // Se lee de igual manera todo el archivo de la bitacora
            lector = new BufferedReader(new FileReader("C:\\MEIA\\bitacora_lista.txt"));
            
            linea = lector.readLine();
            while (linea != null){
                contenido= linea.split(Pattern.quote("|"));
                if(contenido[5].equals("1"))
                {
                    ListasBitacora.add(linea);
                }
                linea = lector.readLine();
            }
            lector.close();
            
            // Se ordenan sus datos por medio del método comparador definido
            Collections.sort(ListasBitacora, new Comparator() {
                
            @Override
            public int compare(Object obj1, Object obj2) {
                String[] espacios1, espacios2;
                int salida;
                
                espacios1 = obj1.toString().split(Pattern.quote("|"));
                espacios2 = obj2.toString().split(Pattern.quote("|"));
                
                // Si la misma persona es la que manda la solicitud entonces es por medio del segundo nombre
                // que aparece que se hará la comparación.
                salida = espacios1[1].trim().compareTo(espacios2[1].trim());
                if(salida == 0)
                {
                    salida = espacios1[0].trim().compareTo(espacios2[0].trim());
                    return salida;
                }
                else
                    return salida;
            }
            }); 
            
            numTotal = ListasBitacora.size() + ListasRegistros.size();
            
            // Se crea un archivo temporal en el cual se agregarán todos los datos que se estan reorganizando.
            escritor = new BufferedWriter(new FileWriter("C:\\MEIA\\temporal_lista.txt", false));
            
            // Se comienza un ciclo para agregar al temporal los datos de manera ordenada
            while (!ListasBitacora.isEmpty() && !ListasRegistros.isEmpty()){
                if (compare(ListasBitacora.get(0),ListasRegistros.get(0)) < 0){
                    // Si esta en la bitacora agrega el nuevo elemento con tamaÃ±o no variable
                    contenido = ListasBitacora.get(0).split(Pattern.quote("|"));
                    contenido[0] = (contenido[0] + "                              ").substring(0, 30);
                    contenido[1] = (contenido[1] + "                     ").substring(0, 20);
                    contenido[2] = (contenido[2] + "                                        ").substring(0, 40);
                    int t=("  " + contenido[3]).length();
                    contenido[3] = ("  " + contenido[3]).substring(t-3, t);
                    escritor.write(Join(contenido,"|") + "\r\n");     
                    numActivos++;
                    ListasBitacora.remove(0);
                }else{
                    //Se asume que al estar en el lista.txt ya esta en el tamaÃ±o fijo
                    escritor.write(ListasRegistros.get(0)+"\r\n");
                    contenido = ListasRegistros.get(0).split(Pattern.quote("|"));
                    if (contenido[5].trim().equals("1"))
                        numActivos++;
                    ListasRegistros.remove(0);
                }                
            }
            // Despues de que ya los fue agregando simultaneamente agrega todos los que faltan
            if (ListasBitacora.isEmpty()){
                for (String temp : ListasRegistros) {
                    numActivos++;
                    escritor.write(temp + "\r\n");
                }
            }else{
                for (String temp : ListasBitacora){
                    contenido = temp.split(Pattern.quote("|"));
                    contenido[0] = (contenido[0] + "                             ").substring(0, 30);
                    contenido[1] = (contenido[1] + "                      ").substring(0, 20);
                    contenido[2] = (contenido[2] + "                                        ").substring(0, 40);
                    int t=("  " + contenido[3]).length();
                    contenido[3] = ("  " + contenido[3]).substring(t-3, t);
                    escritor.write(Join(contenido,"|") + "\r\n");     
                    numActivos++;
                }
            }            
            escritor.close();
                     
            // Actualiza los descriptores para que muestren la cantidad de elementos que tiene ahora
            ActualizarDescriptorBitacora(true,0);
            ActualizarDescriptor(true,numActivos,0,0);
            
            
            File vAnterior = new File("C:\\MEIA\\lista.txt");
            File vNueva = new File("C:\\MEIA\\temporal_lista.txt");
            
            if(vAnterior.delete());
            {
            vNueva.renameTo(new File("C:\\MEIA\\lista.txt"));         
            }
            // Elimina la bitacora y crea una nueva vacia.
            vAnterior = new File("C:\\MEIA\\bitacora_lista.txt");
            vAnterior.delete();
            
            vNueva = new File("C:\\MEIA\\bitacora_lista.txt");
            vNueva.getParentFile().mkdirs(); 
            vNueva.createNewFile();
            
        }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, null);
                        
        }
        }
        
        private String Join(String [] contenido, String caracter)
        {
        String junto="";
        junto=contenido[0];
        for (int i = 1; i < contenido.length; i++) {
            
            junto=junto+caracter+contenido[i];
            
        }
        return junto;
    }
        public int compare(Object obj1, Object obj2) {
            
                String[] espacios1, espacios2;
                int salida;
                
                espacios1 = obj1.toString().split(Pattern.quote("|"));
                espacios2 = obj2.toString().split(Pattern.quote("|"));
                
                // Se compara con el segundo nombre de la persona.
                
                salida = espacios1[1].trim().compareTo(espacios2[1].trim());
                
                if(salida == 0)
                {
                    salida = espacios1[0].trim().compareTo(espacios2[0].trim());
                    return salida;
                }
                else
                    return salida;
            }
        
        public void EliminarLista(String NombreLista){
            
                RandomAccessFile Archivo;
                BufferedReader Lector;
                String linea;       
                String[] contenido;
            try{

                
                Archivo=new RandomAccessFile("C:\\MEIA\\lista.txt","rw");
                linea=Archivo.readLine();
                while(linea!=null)
                {
                    contenido = linea.split(Pattern.quote("|"));
                    if(contenido[1].trim().equals(this.NombreUsuario)&& contenido[0].trim().equals(NombreLista)&&contenido[5].trim().equals("1"))
                    {
                        Archivo.seek(Archivo.getFilePointer()-3);
                        Archivo.writeBytes("0");
                        Archivo.close();
                        ActualizarDescriptor(false,0,1,-1);
                        JOptionPane.showMessageDialog(null, "Lista eliminada correctamente.", "Lista eliminada.", JOptionPane.INFORMATION_MESSAGE, null);
                        return;
                    }
                    linea=Archivo.readLine();
                }
                Archivo.close();
                
                 Archivo=new RandomAccessFile("C:\\MEIA\\bitacora_lista.txt","rw");
                linea=Archivo.readLine();
                while(linea!=null)
                {
                    contenido = linea.split(Pattern.quote("|"));
                    if(contenido[1].trim().equals(this.NombreUsuario)&& contenido[0].trim().equals(NombreLista)&&contenido[5].trim().equals("1"))
                    {
                        Archivo.seek(Archivo.getFilePointer()-3);
                        Archivo.writeBytes("0");
                        Archivo.close();
                        ActualizarDescriptorBitacora(false,0);
                        Archivo.close();
                        JOptionPane.showMessageDialog(null, "Lista eliminada correctamente.", "Lista eliminada.", JOptionPane.INFORMATION_MESSAGE, null);
                        return;
                    }
                    linea=Archivo.readLine();
                }  
                Archivo.close();
                JOptionPane.showMessageDialog(null, "Lista no encontrada.",
                           "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE);
                
            }catch(Exception ex){
               JOptionPane.showMessageDialog(null, ex.getMessage(), "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
        }
        
        public void ModificarTamañoLista(String NombreLista, int CambioTamaño)
        {
            
                RandomAccessFile Archivo;
                String linea;       
                String[] contenido;
                int ubicacion=0;
                Reorganizar();
                
            try{

                Archivo=new RandomAccessFile("C:\\MEIA\\lista.txt","rw");
                linea=Archivo.readLine();
                while(linea!=null)
                {
                    contenido = linea.split(Pattern.quote("|"));
                    if(contenido[1].trim().equals(this.NombreUsuario) && contenido[0].trim().equals(NombreLista) && contenido[5].trim().equals("1"))
                    {
                        Archivo.seek(ubicacion);
                        contenido[0] = (contenido[0] + "                               ").substring(0, 30);
                        contenido[1] = (contenido[1] + "                      ").substring(0, 20);
                        contenido[2] = (contenido[2] + "                                         ").substring(0, 40);
                        
                        int valor = Integer.valueOf(contenido[3].trim())  +CambioTamaño;
                        int t=("  "+valor).length();
                        contenido[3] = ("  " + valor).substring(t-3, t);    
                        Archivo.writeBytes(Join(contenido,"|") + "\r\n");
                        Archivo.close();
                        ActualizarDescriptor(false,0,0,0);

                        Archivo.close();
                        return;
                    }
                    ubicacion=ubicacion+linea.length()+2;
                    linea=Archivo.readLine();
                    
                }
                Archivo.close();
                
                JOptionPane.showMessageDialog(null, "La lista no ha sido encontrada",
                           "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
        }
        
        public void ModificarDescripcionLista(String NombreLista, String NuevaDescripcion)
        {
                RandomAccessFile Archivo;
                String linea;       
                String[] contenido;
                int ubicacion=0;
                Reorganizar();
            
             try{
                 
                Archivo = new RandomAccessFile("C:\\MEIA\\lista.txt","rw");
                linea = Archivo.readLine();
                
                while(linea != null)
                {
                    contenido = linea.split(Pattern.quote("|"));
                    
                    if(contenido[1].trim().equals(this.NombreUsuario) && contenido[0].trim().equals(NombreLista) && contenido[5].trim().equals("1"))
                    {
                        Archivo.seek(ubicacion);
                        contenido[2] = (NuevaDescripcion + "                                         ").substring(0, 20);    
                        Archivo.writeBytes(Join(contenido,"|") + "\r\n");
                        Archivo.close();
                        ActualizarDescriptor(false,0,0,0);

                        Archivo.close();
                        JOptionPane.showMessageDialog(null, "Cambios hechos correctamente.",
                           "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE);
                        
                        return;
                    }
                    ubicacion = ubicacion+linea.length()+2;
                    linea=Archivo.readLine();
                    
                }
                
                Archivo.close();
                
                JOptionPane.showMessageDialog(null, "La lista no ha sido encontrada",
                           "Ocurrió un error.", JOptionPane.INFORMATION_MESSAGE);
                
            }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, null);
                
            }
        }
        
}
