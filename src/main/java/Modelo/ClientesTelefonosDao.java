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

public class ClientesTelefonosDao {

    static PreparedStatement ps;
    static ResultSet rs;
    static Connection con;
    static OracleConecction conectar = new OracleConecction();
    ClientesTelefonos cientesTelefonos = new ClientesTelefonos();

    public static List Listar(String clienteTelefono_id) {
        List<ClientesTelefonos> datos= new ArrayList<>();
        String sql = "{CALL SP_SELECT_CLIENTE_TELEFONO_BASE(?,?,?)}";
        try {
            con = conectar.getConnection();
            CallableStatement stmt=con.prepareCall(sql);
            stmt.setString(1, clienteTelefono_id);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.registerOutParameter(3, OracleTypes.VARCHAR);
            
            stmt.execute();
            
            rs=(ResultSet) stmt.getObject(2);
            while (rs.next()) {
                datos.add(new ClientesTelefonos(clienteTelefono_id, rs.getString(1)));
            }
            
        } catch (Exception e) {
            System.out.println("ERROR GENERADO: " + e.getMessage());
        }
        return datos;
    }

  
    public int Add(ClientesTelefonos clienteTelefono) {
        int result = 0;
        String sql = "{CALL SP_AGREGAR_CLIENTE_TELEFONO_BASE(?,?,?)}";// CREAR VISTA PARA CLIENTES CORREOS
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, clienteTelefono.getCedula());
            callstmt.setString(2, clienteTelefono.getTelefono());
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

    public int Update(ClientesTelefonos clienteTelefono, String telefonoActual) {
        int result = 0;
        String sql = "{CALL SP_EDITAR_CLIENTE_TELEFONO_BASE(?,?,?,?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, clienteTelefono.getCedula());
            callstmt.setString(2, telefonoActual);
            callstmt.setString(3, clienteTelefono.getTelefono());
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

    public int Delete(ClientesTelefonos clienteTelefonos, String telefono) {
        int result = 0;
        String sql = "{CALL SP_BORRAR_CLIENTE_TELEFONO_BASE(?,?,?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);

            callstmt.setString(1, clienteTelefonos.getCedula());
            callstmt.setString(2, telefono);
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
