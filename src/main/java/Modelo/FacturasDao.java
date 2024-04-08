package Modelo;

import Controlador.OracleConecction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FacturasDao {
    static PreparedStatement ps;
    static ResultSet rs;
    static Connection con;
    static OracleConecction conectar = new OracleConecction();
    static Facturas f = new Facturas();
    
    public static Facturas Listar(int idFactura){
        Facturas datos = new Facturas();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            con= conectar.getConnection();
            ps=con.prepareStatement("SELECT * FROM VST_ENCABEZADO_FACTURA WHERE ID_FACTURA=?");
            ps.setInt(1, idFactura);
            rs=ps.executeQuery();
            while (rs.next()) {
                datos.setNombreCliente(rs.getString(2));
                datos.setNombreEmpleado(rs.getString(3));
                datos.setFechaPago(formato.parse(rs.getString(4)));
                datos.setTotalFactura(Double.parseDouble(rs.getString(5)));
                datos.setTipoPago(rs.getString(6));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return datos;
    }
    
}
