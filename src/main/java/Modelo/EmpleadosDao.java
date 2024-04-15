package Modelo;

import Controlador.OracleConecction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpleadosDao {
    static PreparedStatement ps;
    static ResultSet rs;
    static Connection con;
    static OracleConecction conectar = new OracleConecction();
    static Empleados e = new Empleados();
    
    public static List Listar(){
        List<Empleados> datos= new ArrayList<>();
        try {
            con= conectar.getConnection();
            ps=con.prepareStatement("select * from VST_EMPLEADO_GENERAL");
            rs=ps.executeQuery();
            while (rs.next()) {
                Empleados e = new Empleados();
                e.setCedula(rs.getString(1));
                e.setIdEmpleado(rs.getString(2));
                e.setPrimerNombre(rs.getString(3));
                e.setSegundoNombre(rs.getString(4));
                e.setPrimerApellido(rs.getString(5));
                e.setSegundoApellido(rs.getString(6));
                e.setDireccion(rs.getString(7));
                e.setPuesto(rs.getString(8));
                e.setSalario(rs.getDouble(9));
                datos.add(e);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return datos;
    }
    
    public int Update(Empleados empleado){
        int result=0;
        String sql="{CALL SP_EDITAR_EMPLEADO_BASE(?,?,?,?,?,?,?,?,?,?)}";
        try{
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);          
            
            callstmt.setString(1, empleado.getCedula());
            callstmt.setString(2, empleado.getIdEmpleado());
            callstmt.setString(3, empleado.getPrimerNombre());
            callstmt.setString(4, empleado.getSegundoNombre());
            callstmt.setString(5, empleado.getPrimerApellido());
            callstmt.setString(6, empleado.getSegundoApellido());
            callstmt.setString(7, empleado.getDireccion());
            callstmt.setString(8, empleado.getPuesto());
            callstmt.setDouble(9, empleado.getSalario());
            callstmt.setString(10, "");
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
    
    public int Add(Empleados empleado){
        int result=0;
        String sql="{CALL SP_AGREGAR_EMPLEADO_BASE(?,?,?,?,?,?,?,?,?,?)}";
        try{
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql);          
            
            callstmt.setString(1, empleado.getCedula());
            callstmt.setString(2, empleado.getIdEmpleado());
            callstmt.setString(3, empleado.getPrimerNombre());
            callstmt.setString(4, empleado.getSegundoNombre());
            callstmt.setString(5, empleado.getPrimerApellido());
            callstmt.setString(6, empleado.getSegundoApellido());
            callstmt.setString(7, empleado.getDireccion());
            callstmt.setString(8, empleado.getPuesto());
            callstmt.setDouble(9, empleado.getSalario());
            callstmt.setString(10, "");
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
    
    public int Delete(Empleados empleado){
        int result=0;
        String sql="{CALL SP_BORRAR_EMPLEADO_BASE(?,?)}";
        try {
            con = conectar.getConnection();
            CallableStatement callstmt = con.prepareCall(sql); 
            
            callstmt.setString(1, empleado.getCedula());
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
