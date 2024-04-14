package Modelo;

public class ClientesCorreos {
    String cedula;
    String correo;

    public ClientesCorreos(String cedula, String correo) {
        this.cedula = cedula;
        this.correo = correo;
    }

    public ClientesCorreos() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
    
}
