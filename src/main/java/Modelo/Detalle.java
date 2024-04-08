package Modelo;

/**
 *
 * @author jrg71
 */
public class Detalle {
    int lineaFactura;
    String detalle;
    Double precio;
    int cantidad;
    
    public Detalle(){
    }

    public Detalle(String detalle, Double precio, int cantidad) {
        this.detalle = detalle;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getLineaFactura() {
        return lineaFactura;
    }

    public void setLineaFactura(int lineaFactura) {
        this.lineaFactura = lineaFactura;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
