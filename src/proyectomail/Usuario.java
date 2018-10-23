/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomail;

/**
 *
 * @author ferefrapb
 */
public class Usuario 
{
    public ListaSecuencial Lista;
    public SecuencialIndizado ListaUsuario;
    
    public String usuario;
    public String nombre;
    public String apellido;
    public String password;
    public int rol=0;
    public String fechaNacimiento;
    public String correo;
    public int telefono;
    public String pathFotografia;
    //public String descripcion;
    public int estatus=1;
    
    public Usuario(String Usuario)
    {
        this.usuario = Usuario;
        Lista = new ListaSecuencial(this.usuario);
        ListaUsuario = new SecuencialIndizado(this.usuario);
    }

    //Set de Usuarios
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setPathFotografia(String pathFotografia) {
        this.pathFotografia = pathFotografia;
    }

   // public void setDescripcion(String descripcion) {
   //     this.descripcion = descripcion;
   // }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    //Get's de usuario
    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getPassword() {
        return password;
    }

    public int getRol() {
        return rol;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getPathFotografia() {
        return pathFotografia;
    }

  //  public String getDescripcion() {
  //      return descripcion;
  //  }

    public int getEstatus() {
        return estatus;
    }
    
}
