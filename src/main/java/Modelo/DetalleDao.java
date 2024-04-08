package Modelo;

import Controlador.OracleConecction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetalleDao {
    static PreparedStatement ps;
    static ResultSet rs;
    static Connection con;
    static OracleConecction conectar = new OracleConecction();
    static Detalle d = new Detalle();
    
    public List Listar(int idFactura){
        List<Detalle> datos= new ArrayList<>();
        try {
            con= conectar.getConnection();
            ps=con.prepareStatement("SELECT * FROM VST_ITEMS_FACTURA WHERE ID_FACTURA=?");
            ps.setInt(1, idFactura);
            rs=ps.executeQuery();
            while (rs.next()) {
                d =new Detalle();
                d.setDetalle(rs.getString(1));
                d.setPrecio(Double.parseDouble(rs.getString(2)));
                d.setCantidad(Integer.parseInt(rs.getString(3)));
                datos.add(d);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return datos;
    }
    
}
