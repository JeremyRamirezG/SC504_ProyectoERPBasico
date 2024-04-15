package Modelo;

import Controlador.OracleConecction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RolesDao {
    static PreparedStatement ps;
    static ResultSet rs;
    static Connection con;
    static OracleConecction conectar = new OracleConecction();
    static Roles r = new Roles();
    
    public static List Listar(){
        List<Roles> datos= new ArrayList<>();
        try {
            con= conectar.getConnection();
            ps=con.prepareStatement("select * from VST_ROLES");
            rs=ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles();
                r.setIdRol(rs.getString(1));
                r.setDescripcion(rs.getString(2));
                datos.add(r);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return datos;
    }
    
    public int Update(Roles rol) {
        int result = 0;
        String sql = "{CALL SP_EDITAR_ROL(?, ?, ?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, rol.getIdRol());
            callstmt.setString(2, rol.getDescripcion());
            callstmt.setString(3, "");

            result = callstmt.executeUpdate();
            if (result == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR GENERADO: " + e.getMessage());
        }
        return result;
    }

    public int Add(Roles rol) {
        int result = 0;
        String sql = "{CALL SP_AGREGAR_ROL(?, ?, ?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, rol.getIdRol());
            callstmt.setString(2, rol.getDescripcion());
            callstmt.setString(3, "");
            result = callstmt.executeUpdate();
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
    
    public int Delete(Roles rol){
        int result = 0;
        String sql = "{CALL SP_BORRAR_ROL(?, ?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, rol.getIdRol());
            callstmt.setString(2, "");
            
            result = callstmt.executeUpdate();
            if (result == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR GENERADO: " + e.getMessage());
        } 
        return result;
    }
    
}
