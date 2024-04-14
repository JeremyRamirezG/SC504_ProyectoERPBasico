package Modelo;

public class ClientesTelefonos {
    String cedula;
    String telefono;

    public ClientesTelefonos(String cedula, String telefono) {
        this.cedula = cedula;
        this.telefono = telefono;
    }

    public ClientesTelefonos() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
}
