package Modelo;

import Controlador.OracleConecction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
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
    
    public static String Agregar(Facturas datosFactura) {
        String msjSalida = "";
        
        String sql="{CALL SP_CREAR_FACTURA(?,?,?,?)}";
        try{
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);          
            
            callstmt.setString(1, datosFactura.getCedulaCliente());
            callstmt.setString(2, datosFactura.getCedulaEmpleado());
            callstmt.setString(3, datosFactura.getTipoPago());
            callstmt.registerOutParameter(4, Types.VARCHAR);
            callstmt.executeUpdate();
            
            msjSalida = callstmt.getString(4);
        }catch(Exception e){
            System.out.println("ERROR GENERADO: "+e.getMessage());
            msjSalida = "ERROR GENERADO: "+e.getMessage();
        }
        
        return msjSalida;
    }
    
    public static String Modificar(Facturas datosFactura) {
        String msjSalida = "";
        String sql="{CALL SP_ACTUALIZAR_FACTURA(?,?,?,?,?,?)}";
        java.sql.Date fechaSql = new java.sql.Date(datosFactura.getFechaPago().getTime());
        
        try{
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);        
            
            callstmt.setString(1, datosFactura.getCedulaCliente());
            callstmt.setString(2, datosFactura.getCedulaEmpleado());
            callstmt.setString(3, datosFactura.getTipoPago());
            callstmt.setInt(4, datosFactura.getIdFactura());
            callstmt.setDate(5, fechaSql);
            callstmt.registerOutParameter(6, Types.VARCHAR);
            callstmt.executeUpdate();
            
            msjSalida = callstmt.getString(6);
        }catch(Exception e){
            System.out.println("ERROR GENERADO: "+e.getMessage());
            msjSalida = "ERROR GENERADO: "+e.getMessage();
        }
        
        return msjSalida;
    }
    
    public static String Eliminar(Facturas datosFactura) {
        String msjSalida = "";
        String sql="{CALL SP_ELIMINAR_FACTURA(?,?)}";
        
        try{
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);        
            
            callstmt.setInt(1, datosFactura.getIdFactura());
            callstmt.registerOutParameter(2, Types.VARCHAR);
            callstmt.executeUpdate();
            
            msjSalida = callstmt.getString(2);
        }catch(Exception e){
            System.out.println("ERROR GENERADO: "+e.getMessage());
            msjSalida = "ERROR GENERADO: "+e.getMessage();
        }
        
        return msjSalida;
    }
    
}
