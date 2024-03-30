package Modelo;

import Controlador.OracleConecction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientesDao {
    static PreparedStatement ps;
    static ResultSet rs;
    static Connection con;
    static OracleConecction conectar = new OracleConecction();
    static Clientes c = new Clientes();
    
    public static List Listar(){
        List<Clientes> datos= new ArrayList<>();
        try {
            con= conectar.getConnection();
            ps=con.prepareStatement("select * from VST_CLIENTE_GENERAL");
            rs=ps.executeQuery();
            while (rs.next()) {
                Clientes c=new Clientes();
                c.setCedula(rs.getString(1));
                c.setPrimerNombre(rs.getString(2));
                c.setSegundoNombre(rs.getString(3));
                c.setPrimerApellido(rs.getString(4));
                c.setSegundoApellido(rs.getString(5));
                c.setDireccion(rs.getString(6));
                datos.add(c);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return datos;
    }
    
    public int Add(Clientes cliente){
        int result=0;
        String sql="{CALL SP_AGREGAR_CLIENTE_BASE(?,?,?,?,?,?,?)}";
        try{
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);          
            
            callstmt.setString(1, cliente.getCedula());
            callstmt.setString(2, cliente.getPrimerNombre());
            callstmt.setString(3, cliente.getSegundoNombre());
            callstmt.setString(4, cliente.getPrimerApellido());
            callstmt.setString(5, cliente.getSegundoApellido());
            callstmt.setString(6, cliente.getDireccion());
            callstmt.setString(7, "");
            result=callstmt.executeUpdate();
            if(result==1){
                return 1;
            }else{
                return 0;
            }
        }catch(Exception e){
            System.out.println("ERROR GENERADO: "+e.getMessage());
        }
        return result;
    }
    
    public int Update(Clientes cliente){
        int result=0;
        String sql="{CALL SP_EDITAR_CLIENTE_BASE(?,?,?,?,?,?,?)}";
        try{
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);          
            
            callstmt.setString(1, cliente.getCedula());
            callstmt.setString(2, cliente.getPrimerNombre());
            callstmt.setString(3, cliente.getSegundoNombre());
            callstmt.setString(4, cliente.getPrimerApellido());
            callstmt.setString(5, cliente.getSegundoApellido());
            callstmt.setString(6, cliente.getDireccion());
            callstmt.setString(7, "");
            result=callstmt.executeUpdate();
            if(result==1){
                return 1;
            }else{
                return 0;
            }
        }catch(Exception e){
            System.out.println("ERROR GENERADO: "+e.getMessage());
        }
        return result;
    }
    
    public int Delete(Clientes cliente){
        int result=0;
        String sql="{CALL SP_BORRAR_CLIENTE_BASE(?,?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql); 
            
            callstmt.setString(1, cliente.getCedula());
            callstmt.setString(2, "");
            
            result=callstmt.executeUpdate();
            if(result==1){
                return 1;
            }else{
                return 0;
            }
            
        } catch (Exception e) {
            System.out.println("ERROR GENERADO: "+e.getMessage());
        }
        return result;
    }
}