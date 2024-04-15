package Modelo;

public class Roles {
    
    String idRol;
    String descripcion;
    
    public Roles(){
    }
    
    public Roles(String idRol, String descripcion){
        this.idRol = idRol;
        this.descripcion = descripcion;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
