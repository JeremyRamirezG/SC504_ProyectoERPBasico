package Modelo;

import Controlador.OracleConecction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ClientesCorreosDao {

    static PreparedStatement ps;
    static ResultSet rs;
    static Connection con;
    static OracleConecction conectar = new OracleConecction();
    ClientesCorreos cientescorreos = new ClientesCorreos();

    public static List Listar(String clienteCorreo_id) {
        List<ClientesCorreos> datos= new ArrayList<>();
        String sql = "{CALL SP_SELECT_CLIENTE_CORREO_BASE(?,?,?)}";
        try {
            con = conectar.getConnection();
            CallableStatement stmt=con.prepareCall(sql);
            stmt.setString(1, clienteCorreo_id);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.registerOutParameter(3, OracleTypes.VARCHAR);
            
            stmt.execute();
            
            rs=(ResultSet) stmt.getObject(2);
            while (rs.next()) {
                datos.add(new ClientesCorreos(clienteCorreo_id, rs.getString(1)));
            }
            
        } catch (Exception e) {
            System.out.println("ERROR GENERADO: " + e.getMessage());
        }
        return datos;
    }


    public int Add(ClientesCorreos clienteCorreo) {
        int result = 0;
        String sql = "{CALL SP_AGREGAR_CLIENTE_CORREO_BASE(?,?,?)}";// CREAR VISTA PARA CLIENTES CORREOS
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, clienteCorreo.getCedula());
            callstmt.setString(2, clienteCorreo.getCorreo());
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

    public int Update(ClientesCorreos clienteCorreo, String correoActual) {
        int result = 0;
        String sql = "{CALL SP_EDITAR_CLIENTE_CORREO_BASE(?,?,?,?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, clienteCorreo.getCedula());
            callstmt.setString(2, correoActual);
            callstmt.setString(3, clienteCorreo.getCorreo());
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

    public int Delete(ClientesCorreos clienteCorreos, String correo) {
        int result = 0;
        String sql = "{CALL SP_BORRAR_CLIENTE_CORREO_BASE(?,?,?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, clienteCorreos.getCedula());
            callstmt.setString(2, correo);
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
