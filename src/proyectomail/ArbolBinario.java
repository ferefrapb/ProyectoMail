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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class ArbolBinario {
    
    RandomAccessFile RArchivo;
    BufferedReader Lector;
    BufferedWriter Escritor;
    
    File Archivo; 
    File Descriptor;
    File Lineas;
    
    SimpleDateFormat Formato;
    List<Integer> LineasVacias;
    String NombreArchivo;
    
    int Raiz;
    int Registros;
    int Activos;
    int Inactivos;
    String Metadato;
    String Separador;
    static int tamaño;
    
    public ArbolBinario(String nombreArchivo, String metadato, String separador) throws IOException{
        this.Formato = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");   
        NombreArchivo = nombreArchivo;
        String Linea;
        tamaño = ConvertirACadena(" ", " ", Formato.format(new Date()), " ").length() + 2;
        LineasVacias = new ArrayList();
        
        Archivo = new File(nombreArchivo);
        Descriptor = new File(Archivo.getParent() + "\\Desc_" + Archivo.getName());
        Lineas = new File(Archivo.getParent() + "\\Lineas_" + Archivo.getName());
        if(!Archivo.exists()) Archivo.createNewFile();
        if (!Descriptor.exists()) {
            Escritor = new BufferedWriter(new FileWriter(Descriptor));
            WriteLine("Nombre del archivo:" + nombreArchivo);                
            WriteLine("Fecha y hora de creaciÃ³n:" + Formato.format(new Date()));    
            WriteLine("Fecha de Ãºltima modificaciÃ³n:" + Formato.format(new Date()));
            WriteLine("Raiz:-1");     
            WriteLine("NÃºmero de Registros:0");
            WriteLine("Registros Activos:0");
            WriteLine("Registros Inactivos:0");
            WriteLine("Metadatos:" + metadato);
            WriteLine("Delimitador:" + separador);
            Escritor.close();
            Metadato = metadato;
            Separador = separador;
            Raiz = -1;
            Registros = 0;
            Activos = 0;
            Inactivos = 0;
        }
        else{
            Lector = new BufferedReader(new FileReader(Descriptor));
            for (int i = 0; i < 3; i++) {                
                 Lector.readLine();                 
            }
            Raiz = Integer.parseInt(Lector.readLine().substring(5));
            Registros = Integer.parseInt(Lector.readLine().substring(20));
            Activos = Integer.parseInt(Lector.readLine().substring(18));
            Inactivos = Integer.parseInt(Lector.readLine().substring(20));            
            Metadato = Lector.readLine().substring(10);
            Separador = Lector.readLine().substring(12);                                   
            Lector.close();
        } 
        if (!Lineas.exists()) {
            Lineas.createNewFile();
        }
        else{
            Lector = new BufferedReader(new FileReader(Lineas));
            String linea = Lector.readLine();
            while(linea != null){
                LineasVacias.add(Integer.valueOf(linea));
                linea = Lector.readLine();
            }
            Lector.close();
        }
    }
    
    private void ActualizarDescriptor() throws IOException{
        
        File Temp = new File(Descriptor.getParent() + "\\Temp" + Descriptor.getName());
        Lector = new BufferedReader(new FileReader(Descriptor));
        Escritor = new BufferedWriter(new FileWriter(Temp));  
        WriteLine(Lector.readLine());         
        WriteLine(Lector.readLine());
        Lector.readLine();
        WriteLine("Fecha de Ãºltima modificaciÃ³n:" + Formato.format(new Date()));
        Lector.readLine();
        WriteLine("Raiz:" + String.valueOf(Raiz));
        Lector.readLine();
        WriteLine("NÃºmero de Registros:" + String.valueOf(Registros));
        Lector.readLine();
        WriteLine("Registros Activos:" + String.valueOf(Activos));
        Lector.readLine();
        WriteLine("Registros Inactivos:" + String.valueOf(Inactivos));        
        WriteLine(Lector.readLine());
        WriteLine(Lector.readLine());
        Lector.close();
        Escritor.close();
        
        Descriptor.delete();
        Temp.renameTo(new File(Archivo.getParent() + "\\Desc_" + Archivo.getName()));
        Descriptor = new File(Archivo.getParent() + "\\Desc_" + Archivo.getName());
    }
    
    public void Insertar(String Receptor, String Emisor, String Fecha, String Mensaje) throws IOException{
        int Posicion = 0;
        int Actual = 0;
        if (LineasVacias.isEmpty()) {
           Escritor = new BufferedWriter(new FileWriter(Archivo, true)); 
           WriteLine(ConvertirACadena(Receptor, Emisor, Fecha, Mensaje));           
           Posicion = Registros + 1;
           Escritor.close();           
        }
        else{
           RArchivo = new RandomAccessFile(Archivo.getAbsolutePath(), "rw");
           RArchivo.seek((LineasVacias.get(0) - 1) * tamaño);
           RArchivo.writeBytes(ConvertirACadena(Receptor, Emisor, Fecha, Mensaje)+ "\r\n");
           Posicion = LineasVacias.get(0);
           LineasVacias.remove(0);
           RArchivo.close();
        }
        
        Registros++;
        Activos++;
        
        boolean centinela = false;
        if (Raiz == -1) {
            Raiz = 1;
            ActualizarDescriptor();
            return;
        }
        String Registro = ObtenerRegistro(Raiz);
        Actual = Raiz;
        String[] obj1 = new String[3];
        obj1[0] = Receptor;
        obj1[1] = Emisor;
        obj1[2] = Fecha;
        
        if (Registro == null) {
            Raiz = Posicion;
        }
        else{            
            do{
                String[] datos = Registro.split(Pattern.quote("|")); 
                String[] obj2 = new String[3];
                obj2[0] = datos[2].trim();
                obj2[1] = datos[3].trim();
                obj2[2] = datos[4].trim();
                if (Comparar(obj1, obj2)) {
                    if (!datos[1].trim().equals("-1")) {
                        Actual = Integer.parseInt(datos[1].trim());
                        Registro = ObtenerRegistro(Actual);
                    }
                    else{
                        RArchivo = new RandomAccessFile(Archivo.getAbsolutePath(), "rw");
                        RArchivo.seek(((Actual - 1) * tamaño) + 31);
                        RArchivo.writeBytes(PadRight(String.valueOf(Posicion), 30, " "));
                        RArchivo.close();
                        centinela = true;
                    }
                }
                else{
                    if (!datos[0].trim().equals("-1")) {
                        Actual = Integer.parseInt(datos[0].trim());
                        Registro = ObtenerRegistro(Actual);
                    }
                    else{
                        RArchivo = new RandomAccessFile(Archivo.getAbsolutePath(), "rw");
                        RArchivo.seek(((Actual - 1) * tamaño));
                        RArchivo.writeBytes(PadRight(String.valueOf(Posicion), 30, " "));
                        RArchivo.close();
                        centinela = true;
                    }
                }
            } while(!centinela);
        }
        ActualizarDescriptor();
    }
    
    private String ConvertirACadena(String Receptor, String Emisor, String Fecha, String Mensaje){
        List<String> Datos =  new ArrayList();
        Datos.add(PadRight("-1", 30, " "));
        Datos.add(PadRight("-1", 30, " "));
        Datos.add(PadRight(Receptor, 20, " "));
        Datos.add(PadRight(Emisor, 20, " "));
        Datos.add(PadRight(Fecha, 30, " "));
        Datos.add(PadRight(Mensaje, 255, " "));
        Datos.add(PadRight("1", 5, " "));
        return Join(Datos.toArray(new String[7]), "|");               
    }
    
    private String ObtenerRegistro(int num) throws FileNotFoundException, IOException{
        RArchivo = new RandomAccessFile(Archivo.getAbsolutePath(), "rw");
        RArchivo.seek((num - 1) * tamaño);
        String Registro = RArchivo.readLine();
        RArchivo.close();
        return Registro;
    }
    public boolean Comparar(String[]obj1, String[] obj2) {
        int salida;

        salida = obj1[0].trim().compareTo(obj2[0].trim());
        if(salida == 0)
        {
            salida = obj1[1].trim().compareTo(obj2[1].trim());
            if(salida == 0)
            {
                salida = obj1[2].trim().compareTo(obj2[2].trim());
                
            }       
        }       
        return salida > 0;
    }
    private void WriteLine(String cadena){
        try {
            Escritor.write(cadena);
            Escritor.newLine();
        } catch (IOException ex) {
            Logger.getLogger(ArbolBinario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String Join(String [] contenido, String caracter)
    {
        String junto;
        junto=contenido[0];
        for (int i = 1; i < contenido.length; i++) {
            junto=junto+caracter+contenido[i];
        }
        return junto;
    }
    
    private String PadRight(String cadena, int tamaño, String caracter){
        int resta = tamaño - cadena.length();
        String padright = cadena;
        for (int i = 0; i < resta; i++) {
            padright += caracter;
        }
        return padright;
    }
    
    public List<String[]> ObtenerMensajes(String Receptor) throws IOException{
        List<String[]> ListaDatos = new ArrayList();
        if (Raiz == -1) {
            return ListaDatos;
        }
        String Registro = ObtenerRegistro(Raiz);        
        String[] datos;
        boolean centinela = false;
        int Actual = Raiz;
        String[] Obj1 = new String[3];
        Obj1[0] = Receptor;
        Obj1[1] = "";
        Obj1[2] = "";
        do{
            datos = Registro.split(Pattern.quote("|")); 
            if (datos[2].trim().equals(Receptor)) {
                ListaDatos.addAll(BusquedaRecursiva(Actual, Receptor));
                centinela = true;
            }          
            else{                
                String[] Obj2 = new String[3];
                Obj2[0] = datos[2];
                Obj2[1] = datos[3];
                Obj2[2] = datos[4];
                
                if (Comparar(Obj1, Obj2)) {
                    Actual = Integer.parseInt(datos[1].trim());
                    if (Actual != -1) {
                        Registro = ObtenerRegistro(Actual);  
                    }
                    else{
                        return ListaDatos;
                    }
                }
                else{
                    Actual = Integer.parseInt(datos[0].trim());
                    if (Actual != -1) {
                         Registro = ObtenerRegistro(Actual);  
                    } 
                    else{
                        return ListaDatos;
                    }
                }
            }
        } while(!centinela);
        return ListaDatos;
    }
    
    private List<String[]> BusquedaRecursiva(int nodo, String Receptor) throws IOException{
        String Registro = ObtenerRegistro(nodo);
        List<String[]> ListaDatos = new ArrayList();
        String[] datos;
        datos = Registro.split(Pattern.quote("|")); 
       if (!datos[0].trim().equals("-1")) {
            ListaDatos.addAll(BusquedaRecursiva(Integer.valueOf(datos[0].trim()), Receptor));
            }
            if (datos[6].trim().equals("1")) {
                String[] datosEncontrados = new String[3];
                datosEncontrados[0] = datos[3].trim();
                datosEncontrados[1] = datos[4].trim();
                datosEncontrados[2] = datos[5].trim();
                ListaDatos.add(datosEncontrados);
            }
            if (!datos[1].trim().equals("-1")) {
                ListaDatos.addAll(BusquedaRecursiva(Integer.valueOf(datos[1].trim()), Receptor));
            }         
        return ListaDatos;
    }
    
    public boolean EliminarMensaje(String[] eliminar) throws IOException{
        int Actual = Raiz;
        int Papa = -1;
        int Hijo = -1;
        if (Raiz == -1) {
            return false;
        }
        String Registro = ObtenerRegistro(Raiz);
        String[] datos;
        boolean centinela = false;
        boolean HijoDerecho = false;
        do {
            datos = Registro.split(Pattern.quote("|"));
            if (datos[2].trim().equals(eliminar[0]) && datos[3].trim().equals(eliminar[1]) && datos[4].trim().equals(eliminar[2]) && datos[5].trim().equals(eliminar[3])) {                          
                centinela = true;
            }
            else{
                String[] obj = new String[3];
                obj[0] = datos[2].trim();
                obj[1] = datos[3].trim();
                obj[2] = datos[4].trim();
                if (Comparar(eliminar, obj)) {
                    if (!datos[1].trim().equals("-1")) {                      
                        Papa = Actual;
                        Actual = Integer.valueOf(datos[1].trim());
                        HijoDerecho = true;
                    }
                    else{                       
                        return false;
                    }
                }
                else{
                    if (!datos[0].trim().equals("-1")) {                      
                        Papa = Actual;
                        Actual = Integer.valueOf(datos[0].trim());
                        HijoDerecho = false;
                    }
                    else{                       
                        return false;
                    }
                }
                Registro = ObtenerRegistro(Actual);
            }
        } while (!centinela);                           
        
        
        if (datos[0].trim().equals("-1") && datos[1].trim().equals("-1")) {
            if (Actual == Raiz) {
                Raiz = -1;                
            }
            else{
                RArchivo = new RandomAccessFile(Archivo.getAbsolutePath(), "rw");
                if (HijoDerecho) {
                    RArchivo.seek(((Papa - 1) * tamaño) + 31);
                }
                else{
                    RArchivo.seek(((Papa - 1) * tamaño));
                }
                RArchivo.writeBytes(PadRight("-1", 30, " "));
                RArchivo.close();
            }
        }
        else if (!datos[0].trim().equals("-1") && datos[1].trim().equals("-1")) {
            if (Actual == Raiz) {
                Raiz = Integer.valueOf(datos[0].trim());
            }
            else{
                RArchivo = new RandomAccessFile(Archivo.getAbsolutePath(), "rw");
                if (HijoDerecho) {
                    RArchivo.seek(((Papa - 1) * tamaño) + 31);
                }
                else{
                    RArchivo.seek(((Papa - 1) * tamaño));
                }
                RArchivo.writeBytes(PadRight(String.valueOf(datos[0].trim()), 30, " "));
                RArchivo.close();
            }
        }
        else if (datos[0].trim().equals("-1") && !datos[1].trim().equals("-1")) {
            if (Actual == Raiz) {
                Raiz = Integer.valueOf(datos[1].trim());
            }
            else{
                RArchivo = new RandomAccessFile(Archivo.getAbsolutePath(), "rw");
                if (HijoDerecho) {
                    RArchivo.seek(((Papa - 1) * tamaño) + 31);
                }
                else{
                    RArchivo.seek(((Papa - 1) * tamaño));
                }
                RArchivo.writeBytes(PadRight(String.valueOf(datos[1].trim()), 30, " "));
                RArchivo.close();
            }
        }
        else{
            String Tregistro = ObtenerRegistro(Integer.valueOf(datos[0].trim()));
            String[] Tdatos = Tregistro.split(Pattern.quote("|"));
            int Tactual = Integer.valueOf(datos[0].trim());
            int Tpapa = Actual;
            while(!Tdatos[1].trim().equals("-1")){
                Tpapa = Tactual;
                Tactual = Integer.valueOf(datos[1].trim());
                Tregistro = ObtenerRegistro(Tactual);
                Tdatos = Tregistro.split(Pattern.quote("|"));                
            }
            if (Actual == Raiz) {
                RArchivo = new RandomAccessFile(Archivo.getAbsolutePath(), "rw");                                         
                if(!datos[0].trim().equals(String.valueOf(Tactual))){
                    RArchivo.seek(((Tactual - 1) * tamaño));
                    RArchivo.writeBytes(PadRight(datos[0].trim(), 30, " "));    
                }
                RArchivo.seek(((Tactual - 1) * tamaño) + 31);
                RArchivo.writeBytes(PadRight(datos[1].trim(), 30, " "));
                RArchivo.seek(((Tpapa - 1) * tamaño) + 31);
                RArchivo.writeBytes(PadRight("-1", 30, " "));
                RArchivo.close();
                Raiz = Tactual;
            }
            else{
                RArchivo = new RandomAccessFile(Archivo.getAbsolutePath(), "rw");
                if (HijoDerecho) {
                    RArchivo.seek(((Papa - 1) * tamaño) + 31);
                }
                else{
                    RArchivo.seek(((Papa - 1) * tamaño));
                }
                RArchivo.writeBytes(PadRight(String.valueOf(Tactual), 30, " "));
                RArchivo.seek(((Tactual - 1) * tamaño));
                RArchivo.writeBytes(PadRight(datos[0].trim(), 30, " "));
                RArchivo.seek(((Tactual - 1) * tamaño) + 31);
                RArchivo.writeBytes(PadRight(datos[1].trim(), 30, " "));
                RArchivo.seek(((Tpapa - 1) * tamaño) + 31);
                RArchivo.writeBytes(PadRight("-1", 30, " "));
                RArchivo.close();
            }
        }
        RArchivo = new RandomAccessFile(Archivo.getAbsolutePath(), "rw");
        RArchivo.seek(((Actual - 1) * tamaño) + tamaño - 7);
        RArchivo.writeBytes(PadRight("0", 5, " "));
        RArchivo.close();
        Activos--;
        Inactivos++;        
        ActualizarDescriptor();
        
        return true;
    }
    
    public void Reorganizar() throws FileNotFoundException, IOException{
        File Temp = new File(Archivo.getParent() + "\\Temp" + Archivo.getName());
        Lector = new BufferedReader(new FileReader(Archivo));
        Escritor = new BufferedWriter(new FileWriter(Temp, true));
        String linea = Lector.readLine();
        String[] datos;
        int contador = 1;
        while(linea != null && !linea.equals("")){
            datos = linea.split(Pattern.quote("|"));
            if (datos[6].trim().equals("0")) {
                WriteLine(PadRight("", tamaño - 2, "-") + "\r\n");
                LineasVacias.add(contador);
            }
            else{
                WriteLine(linea);
            }
            linea = Lector.readLine();
        }
        
        Lector.close();
        Escritor.close();
        
        Archivo.delete();
        Temp.renameTo(new File(NombreArchivo));
        Archivo = new File(NombreArchivo);
        
        Registros -= Inactivos;
        Inactivos = 0;
        
        Lineas.delete();
        Lineas = new File(Archivo.getParent() + "\\Lineas_" + Archivo.getName());
        Escritor = new BufferedWriter(new FileWriter(Lineas, true));
        for (int x : LineasVacias) {
            WriteLine(String.valueOf(x));
        }
        Escritor.close();
        
        ActualizarDescriptor();
        
    }        
}
