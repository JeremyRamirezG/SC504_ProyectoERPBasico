package Modelo;

import Controlador.OracleConecction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
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
    
    public static String Agregar(Detalle datosLinea) {
        String msjSalida = "";
        
        String sql="{CALL SP_CREAR_LINEA_FACTURA(?,?,?,?)}";
        try{
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);          
            
            callstmt.setInt(1, datosLinea.getIdFactura());
            callstmt.setString(2, datosLinea.getDetalle());
            callstmt.setInt(3, datosLinea.getCantidad());
            callstmt.registerOutParameter(4, Types.VARCHAR);
            callstmt.executeUpdate();
            
            msjSalida = callstmt.getString(4);
        }catch(Exception e){
            System.out.println("ERROR GENERADO: "+e.getMessage());
            msjSalida = "ERROR GENERADO: "+e.getMessage();
        }
        
        return msjSalida;
    }
    
    public static String Modificar(Detalle datosLinea) {
        String msjSalida = "";
        String sql="{CALL SP_ACTUALIZAR_LINEA_FACTURA(?,?,?,?,?)}";
        
        try{
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);        
            
            callstmt.setInt(1, datosLinea.getIdFactura());
            callstmt.setString(2, datosLinea.getDetalle());
            callstmt.setInt(3, datosLinea.getCantidad());
            callstmt.setInt(4, datosLinea.getLineaFactura());
            callstmt.registerOutParameter(5, Types.VARCHAR);
            callstmt.executeUpdate();
            
            msjSalida = callstmt.getString(5);
        }catch(Exception e){
            System.out.println("ERROR GENERADO: "+e.getMessage());
            msjSalida = "ERROR GENERADO: "+e.getMessage();
        }
        
        return msjSalida;
    }
    
    public static String Eliminar(Detalle datosLinea) {
        String msjSalida = "";
        String sql="{CALL SP_ELIMINAR_LINEA_FACTURA(?,?,?)}";
        
        try{
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);        
            
            callstmt.setInt(1, datosLinea.getIdFactura());
            callstmt.setInt(2, datosLinea.getLineaFactura());
            callstmt.registerOutParameter(3, Types.VARCHAR);
            callstmt.executeUpdate();
            
            msjSalida = callstmt.getString(3);
        }catch(Exception e){
            System.out.println("ERROR GENERADO: "+e.getMessage());
            msjSalida = "ERROR GENERADO: "+e.getMessage();
        }
        
        return msjSalida;
    }
    
}
