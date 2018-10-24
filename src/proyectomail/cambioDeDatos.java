/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomail;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ferefrapb
 */
public class cambioDeDatos 
{
     public void cambiar(String usuario, String parametro, String cambiar) throws ParseException
    {
        Usuario UsuarioaModificar = new Usuario(usuario);
        manejadorArchivo manejador = new manejadorArchivo();
        UsuarioaModificar = manejador.ObtenerUsuario(usuario);
        String paramlowercase = parametro.toLowerCase();
        int num = 0;
        switch ( paramlowercase ) {
            case "usuario":
                num = 1;
                break;
            case "nombre":
                num = 2;
                break;
            case "apellido":
                num = 3;
                break;
            case "password":
                num = 4;
                break;
            case "rol":
                num = 5;
                break;
            case "fecha":
                num = 6;
                break;
            case "correo":
                num = 7;
                break;
            case "telefono":
                num = 8;
                break;
            case "path":
                num = 9;
                break;
            case "estatus":
                num = 10;
                break;
            default:
                break;
        }
        switch ( num ) {
            case 1:
                UsuarioaModificar.usuario = cambiar;
                break;
            case 2:
                UsuarioaModificar.nombre = cambiar;
                break;
            case 3:
                UsuarioaModificar.apellido = cambiar;
                break;
            case 4:
                UsuarioaModificar.password = cambiar;
                break;
            case 5:
                UsuarioaModificar.rol = Integer.parseInt(cambiar);
                break;
            case 6:
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                Date date = formatter.parse(cambiar);
                DateFormat fechaHora = new SimpleDateFormat("dd-MMM-yyyy");
                String convertido = fechaHora.format(date);
                UsuarioaModificar.fechaNacimiento = convertido;
                break;
            case 7:
                UsuarioaModificar.correo = cambiar;
                break;
            case 8:
                UsuarioaModificar.telefono = Integer.parseInt(cambiar);
                break;
            case 9:
                UsuarioaModificar.pathFotografia = cambiar;
                break;
            case 10:
                UsuarioaModificar.estatus = Integer.parseInt(cambiar);
                break;
            default:
                break;
        }
        manejador.escribirArchivoBitacora(UsuarioaModificar);
    }
    
}
