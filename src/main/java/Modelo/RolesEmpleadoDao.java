package Modelo;

import Controlador.OracleConecction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RolesEmpleadoDao {
    static PreparedStatement ps;
    static ResultSet rs;
    static Connection con;
    static OracleConecction conectar = new OracleConecction();
    static RolesEmpleado r = new RolesEmpleado();
    
    public static List Listar() {
        List<RolesEmpleado> datos = new ArrayList<>();
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement("select * from VST_ROLES_EMPLEADO");
            rs = ps.executeQuery();
            while (rs.next()) {
                RolesEmpleado e = new RolesEmpleado();
                e.setCedula(rs.getString(1));
                e.setRol(rs.getString(2));
                datos.add(e);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return datos;
    }
    
    public int Update(RolesEmpleado rolesEmpleado, String nuevoRol) {
        int result = 0;
        String sql = "{CALL SP_ACTUALIZAR_ROL_EMPLEADO(?, ?, ?, ?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, rolesEmpleado.getCedula());
            callstmt.setString(2, rolesEmpleado.getRol()); // Aquí utilizamos el rol antiguo directamente
            callstmt.setString(3, nuevoRol);
            callstmt.setString(4, "");

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



    
    public int Add(RolesEmpleado rolesEmpleado) {
        int result = 0;
        String sql = "{CALL SP_AGREGAR_ROL_EMPLEADO(?, ?, ?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, rolesEmpleado.getCedula());
            callstmt.setString(2, rolesEmpleado.getRol());
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
    
    public int Delete(RolesEmpleado rolesEmpleado) {
        int result = 0;
        String sql = "{CALL SP_BORRAR_ROL_EMPLEADO(?, ?, ?)}"; 
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, rolesEmpleado.getCedula());
            callstmt.setString(2, rolesEmpleado.getRol());
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
}
