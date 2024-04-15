package Modelo;

public class RolesEmpleado {
    
    String cedula;
    String rol;
    
    public RolesEmpleado(){
    }
    
    public RolesEmpleado(String cedula, String rol){
        this.cedula = cedula;
        this.rol = rol;
    }
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
