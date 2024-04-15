package Controlador;

import Modelo.Clientes;
import Modelo.ClientesDao;
import Modelo.Detalle;
import Modelo.DetalleDao;
import Modelo.Facturas;
import Modelo.FacturasDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Roles;
import Modelo.RolesDao;
import Modelo.RolesEmpleado;
import Modelo.RolesEmpleadoDao;
import Vista.PantallaEmpleados;
import Vista.PantallaRoles;
import Vista.PantallaRolesEmpleado;
import Vista.Pantalla;
import Vista.AccionesFactura;
import Vista.ListarFactura;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Controlador implements ActionListener {

    Pantalla vista = new Pantalla();
    AccionesFactura vistaAccionesFactura = new AccionesFactura();
    ListarFactura vistaListarFactura = new ListarFactura();
    PantallaEmpleados vistaPantallaEmpleados = new PantallaEmpleados();
    PantallaRoles vistaPantallaRoles = new PantallaRoles();
    PantallaRolesEmpleado vistaPantallaRolesEmpleado = new PantallaRolesEmpleado();
    
    Clientes clientes = new Clientes();
    ClientesDao dao = new ClientesDao();
    Detalle detalle = new Detalle();
    DetalleDao detalleDao = new DetalleDao();
    Empleados empleados = new Empleados();
    EmpleadosDao empleadosDao = new EmpleadosDao();
    Roles roles = new Roles();
    RolesDao rolesDao = new RolesDao();
    RolesEmpleado rolesEmpleado = new RolesEmpleado();
    RolesEmpleadoDao rolesEmpleadoDao = new RolesEmpleadoDao();
    Facturas facturas = new Facturas();
    FacturasDao facturasDao = new FacturasDao();
    
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modeloEmpleado = new DefaultTableModel();
    DefaultTableModel modeloRoles = new DefaultTableModel();
    DefaultTableModel modeloRolesEmpleado = new DefaultTableModel();

    public Controlador(Pantalla vista) {
        this.vista = vista;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnBorrar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnNuevo.addActionListener(this);
    }
    public Controlador(ListarFactura vistaListarFactura) {
        this.vistaListarFactura = vistaListarFactura;
        this.vistaListarFactura.btnBuscar.addActionListener(this);
    }
    public Controlador(AccionesFactura vistaAccionesFactura) {
        this.vistaAccionesFactura = vistaAccionesFactura;
        this.vistaAccionesFactura.btnAgregarFactura.addActionListener(this);
        this.vistaAccionesFactura.btnEditarFactura.addActionListener(this);
        this.vistaAccionesFactura.btnBorrarFactura.addActionListener(this);
        this.vistaAccionesFactura.btnAgregarDetalle.addActionListener(this);
        this.vistaAccionesFactura.btnEditarDetalle.addActionListener(this);
        this.vistaAccionesFactura.btnBorrarDetalle.addActionListener(this);
    }
    public Controlador(PantallaEmpleados vistaPantallaEmpleados) {
        this.vistaPantallaEmpleados = vistaPantallaEmpleados;
        this.vistaPantallaEmpleados.btnListar.addActionListener(this);
        this.vistaPantallaEmpleados.btnAgregar.addActionListener(this);
        this.vistaPantallaEmpleados.btnActualizar.addActionListener(this); 
        this.vistaPantallaEmpleados.btnBorrar.addActionListener(this);
        this.vistaPantallaEmpleados.btnEditar.addActionListener(this);
        this.vistaPantallaEmpleados.btnNuevo.addActionListener(this);
    }
    
    public Controlador(PantallaRoles vistaPantallaRoles){
        this.vistaPantallaRoles = vistaPantallaRoles;
        this.vistaPantallaRoles.btnListarRol.addActionListener(this);
        this.vistaPantallaRoles.btnAgregarRol.addActionListener(this);
        this.vistaPantallaRoles.btnActualizarRol.addActionListener(this);
        this.vistaPantallaRoles.btnBorrarRol.addActionListener(this);
        this.vistaPantallaRoles.btnEditarRol.addActionListener(this);
        this.vistaPantallaRoles.btnNuevoRol.addActionListener(this);
    }
    
    public Controlador(PantallaRolesEmpleado vistaPantallaRolesEmpleado){
        this.vistaPantallaRolesEmpleado = vistaPantallaRolesEmpleado;
        this.vistaPantallaRolesEmpleado.btnListarRolEmpleado.addActionListener(this);
        this.vistaPantallaRolesEmpleado.btnAgregarRolEmpleado.addActionListener(this);
        this.vistaPantallaRolesEmpleado.btnActualizarRolEmpleado.addActionListener(this);
        this.vistaPantallaRolesEmpleado.btnBorrarRolEmpleado.addActionListener(this);
        this.vistaPantallaRolesEmpleado.btnEditarRolEmpleado.addActionListener(this);
        this.vistaPantallaRolesEmpleado.btnNuevoRolEmpleado.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //FACTURAS
        if (e.getSource() == vistaListarFactura.btnBuscar) {
            vaciarDetalleFactura();
            listarFactura(vistaListarFactura.tblDetalles);
        }
        if (e.getSource() == vistaAccionesFactura.btnAgregarFactura) {
            crearFactura();
            vaciarEncabezadoFacturaCUD();
        }
        if (e.getSource() == vistaAccionesFactura.btnEditarFactura) {
            modificarFactura();
            vaciarEncabezadoFacturaCUD();
        }
        if (e.getSource() == vistaAccionesFactura.btnBorrarFactura) {
            borrarFactura();
            vaciarEncabezadoFacturaCUD();
        }
        if (e.getSource() == vistaAccionesFactura.btnAgregarDetalle) {
            crearLineaFactura();
            vaciarDetalleFacturaCUD();
        }
        if (e.getSource() == vistaAccionesFactura.btnEditarDetalle) {
            modificarLineaFactura();
            vaciarDetalleFacturaCUD();
        }
        if (e.getSource() == vistaAccionesFactura.btnBorrarDetalle) {
            borrarLineaFactura();
            vaciarDetalleFacturaCUD();
        }
        //CLIENTES
        if (e.getSource() == vista.btnListar) {
            limpiarTabla();
            listar(vista.tblDatos);
            nuevoCliente();
        }
        if (e.getSource() == vista.btnAgregar) {
            limpiarTabla();
            add();
            listar(vista.tblDatos);
            nuevoCliente();
        }

        if (e.getSource() == vista.btnActualizar) {
            limpiarTabla();
            update();
            listar(vista.tblDatos);
            nuevoCliente();
        }
        
        if (e.getSource() == vista.btnNuevo) {
            limpiarTabla();
            nuevoCliente();
            listar(vista.tblDatos);
        }

        if (e.getSource() == vista.btnBorrar) {
            delete();
            limpiarTabla();
            listar(vista.tblDatos);
            nuevoCliente();
        }
        if (e.getSource() == vista.btnEditar) {
            int fila = vista.tblDatos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Debe Seleccionar Una fila");
            } else {
                String cedula = vista.tblDatos.getValueAt(fila, 0).toString();
                String nombre1 = vista.tblDatos.getValueAt(fila, 1).toString();
                String nombre2 = vista.tblDatos.getValueAt(fila, 2).toString();
                String apellido1 = vista.tblDatos.getValueAt(fila, 3).toString();
                String apellido2 = vista.tblDatos.getValueAt(fila, 4).toString();
                String direccion = vista.tblDatos.getValueAt(fila, 5).toString();

                vista.txtCedula.setText(cedula);
                vista.txtNombre1.setText(nombre1);
                vista.txtNombre2.setText(nombre2);
                vista.txtApellido1.setText(apellido1);
                vista.txtApellido2.setText(apellido2);
                vista.txtDirección.setText(direccion);
            }
        }
        
        //EMPLEADOS
        
        if (e.getSource() == vistaPantallaEmpleados.btnListar) {
            limpiarTablaEmpleado();
            listarEmpleados(vistaPantallaEmpleados.tblDatos);
            nuevoEmpleado();
        }
        if (e.getSource() == vistaPantallaEmpleados.btnAgregar) {
            limpiarTablaEmpleado();
            addEmpleado();
            listarEmpleados(vistaPantallaEmpleados.tblDatos);
            nuevoEmpleado();
        }
        if (e.getSource() == vistaPantallaEmpleados.btnNuevo) {
            limpiarTablaEmpleado();
            nuevoEmpleado();
            listarEmpleados(vistaPantallaEmpleados.tblDatos);
        }
        if (e.getSource() == vistaPantallaEmpleados.btnActualizar) {
            limpiarTabla();
            update();
            listar(vista.tblDatos);
            nuevoCliente();
        }
        if (e.getSource() == vistaPantallaEmpleados.btnBorrar) {
            deleteEmpleado();
            limpiarTablaEmpleado();
            listarEmpleados(vistaPantallaEmpleados.tblDatos);
            nuevoEmpleado();
        }
        if (e.getSource() == vistaPantallaEmpleados.btnEditar) {
            int fila = vistaPantallaEmpleados.tblDatos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vistaPantallaEmpleados, "Debe Seleccionar Una fila");
            } else {
                String cedula = vistaPantallaEmpleados.tblDatos.getValueAt(fila, 0).toString();
                String idEmpleado = vistaPantallaEmpleados.tblDatos.getValueAt(fila, 1).toString();
                String nombre1 = vistaPantallaEmpleados.tblDatos.getValueAt(fila, 2).toString();
                String nombre2 = vistaPantallaEmpleados.tblDatos.getValueAt(fila, 3).toString();
                String apellido1 = vistaPantallaEmpleados.tblDatos.getValueAt(fila, 4).toString();
                String apellido2 = vistaPantallaEmpleados.tblDatos.getValueAt(fila, 5).toString();
                String direccion = vistaPantallaEmpleados.tblDatos.getValueAt(fila, 6).toString();
                String puesto = vistaPantallaEmpleados.tblDatos.getValueAt(fila, 7).toString();


                vistaPantallaEmpleados.txtCedula.setText(cedula);
                vistaPantallaEmpleados.txtIdEmpleado.setText(idEmpleado);
                vistaPantallaEmpleados.txtNombre1.setText(nombre1);
                vistaPantallaEmpleados.txtNombre2.setText(nombre2);
                vistaPantallaEmpleados.txtApellido1.setText(apellido1);
                vistaPantallaEmpleados.txtApellido2.setText(apellido2);
                vistaPantallaEmpleados.txtDirección.setText(direccion);
                vistaPantallaEmpleados.txtPuesto.setText(puesto);
            }
        }
        
        //ROLES
        
        if (e.getSource() == vistaPantallaRoles.btnListarRol) {
            limpiarTablaRoles();
            listarRoles(vistaPantallaRoles.tblDatosRoles);
            nuevoRol();
        }        
        if (e.getSource() == vistaPantallaRoles.btnAgregarRol) {
            limpiarTablaRoles();
            addRol();
            listarRoles(vistaPantallaRoles.tblDatosRoles);
            nuevoRol();
        }        
        if (e.getSource() == vistaPantallaRoles.btnActualizarRol) {
            limpiarTablaRoles();
            updateRol();
            listarRoles(vistaPantallaRoles.tblDatosRoles);
            nuevoRol();
        }
        if (e.getSource() == vistaPantallaRoles.btnNuevoRol) {
            limpiarTablaRoles();
            nuevoRol();
            listarRoles(vistaPantallaRoles.tblDatosRoles);
        }
        if (e.getSource() == vistaPantallaRoles.btnBorrarRol) {
            deleteRol();
            limpiarTablaRoles();
            listarRoles(vistaPantallaRoles.tblDatosRoles);
            nuevoRol();
        }
        if (e.getSource() == vistaPantallaRoles.btnEditarRol) {
            int fila = vistaPantallaRoles.tblDatosRoles.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vistaPantallaRoles, "Debe seleccionar una fila");
            } else {
                String idRol = vistaPantallaRoles.tblDatosRoles.getValueAt(fila, 0).toString();
                String descripcion = vistaPantallaRoles.tblDatosRoles.getValueAt(fila, 1).toString();

                vistaPantallaRoles.txtIdRol.setText(idRol);
                vistaPantallaRoles.txtDescripcion.setText(descripcion);
            }
        }
        
        //ROLES EMPLEADO
        
        if (e.getSource() == vistaPantallaRolesEmpleado.btnListarRolEmpleado) {
            limpiarTablaRolesEmpleado();
            listarRolesEmpleado(vistaPantallaRolesEmpleado.tblDatosRolEmpleado);
            nuevoRolEmpleado();
        }        
        if (e.getSource() == vistaPantallaRolesEmpleado.btnAgregarRolEmpleado) {
            limpiarTablaRolesEmpleado();
            addRolEmpleado();
            listarRolesEmpleado(vistaPantallaRolesEmpleado.tblDatosRolEmpleado);
            nuevoRolEmpleado();
        }        
        if (e.getSource() == vistaPantallaRolesEmpleado.btnActualizarRolEmpleado) {
            limpiarTablaRolesEmpleado();
            updateRolEmpleado();
            listarRolesEmpleado(vistaPantallaRolesEmpleado.tblDatosRolEmpleado);
            nuevoRolEmpleado();
        }
        if (e.getSource() == vistaPantallaRolesEmpleado.btnNuevoRolEmpleado) {
            limpiarTablaRolesEmpleado();
            nuevoRolEmpleado();
            listarRolesEmpleado(vistaPantallaRolesEmpleado.tblDatosRolEmpleado);
        }
        if (e.getSource() == vistaPantallaRolesEmpleado.btnBorrarRolEmpleado) {
            deleteRolEmpleado();
            limpiarTablaRolesEmpleado();
            listarRolesEmpleado(vistaPantallaRolesEmpleado.tblDatosRolEmpleado);
            nuevoRolEmpleado();
        }
        if (e.getSource() == vistaPantallaRolesEmpleado.btnEditarRolEmpleado) {
            int fila = vistaPantallaRolesEmpleado.tblDatosRolEmpleado.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vistaPantallaRolesEmpleado, "Debe seleccionar una fila");
            } else {
                String idRol = vistaPantallaRolesEmpleado.tblDatosRolEmpleado.getValueAt(fila, 0).toString();
                String idEmpleado = vistaPantallaRolesEmpleado.tblDatosRolEmpleado.getValueAt(fila, 1).toString();

                vistaPantallaRolesEmpleado.txtCedula.setText(idRol);
                vistaPantallaRolesEmpleado.txtRol.setText(idEmpleado);
            }
        }
    }
    
    //FACTURAS
    public void vaciarDetalleFacturaCUD() {
        vistaAccionesFactura.txtIdFacturaDetalle.setText("");
        vistaAccionesFactura.txtIdProducto.setText("");
        vistaAccionesFactura.txtCantidad.setText("");
        vistaAccionesFactura.txtLinea.setText("");
    }
    public void vaciarEncabezadoFacturaCUD() {
        //vistaAccionesFactura.txtFechaFactura.setDate(null);
        vistaAccionesFactura.txtIdFactura.setText("");
        vistaAccionesFactura.txtTipoPago.setText("");
        vistaAccionesFactura.txtIdCliente.setText("");
        vistaAccionesFactura.txtIdEmpleado.setText("");
        vistaAccionesFactura.txtIdCliente.setText("");
        
    }
    public void vaciarDetalleFactura() {
        for (int i = 0; i < vistaListarFactura.tblDetalles.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
        vistaListarFactura.txtEmpleado.setText("");
        vistaListarFactura.txtCliente.setText("");
        vistaListarFactura.txtFecha.setText("");
        vistaListarFactura.txtTotal.setText("");
        vistaListarFactura.txtTipoPago.setText("");
        
    }
    public void borrarFactura(){
        Facturas factura = new Facturas();
        
        factura.setIdFactura(Integer.parseInt(vistaAccionesFactura.txtIdFactura.getText()));
        
        String resultado = FacturasDao.Eliminar(factura);
        vistaAccionesFactura.txtMensaje.setText(resultado);
    }
    public void borrarLineaFactura() {
        Detalle detalle = new Detalle();
        
        detalle.setLineaFactura(Integer.parseInt(vistaAccionesFactura.txtLinea.getText()));
        detalle.setIdFactura(Integer.parseInt(vistaAccionesFactura.txtIdFacturaDetalle.getText()));
        
        String resultado = DetalleDao.Eliminar(detalle);
        vistaAccionesFactura.txtMensaje.setText(resultado);
    }
    public void crearFactura() {
        Facturas factura = new Facturas();
        
        factura.setCedulaEmpleado(vistaAccionesFactura.txtIdCliente.getText());
        factura.setCedulaCliente(vistaAccionesFactura.txtIdEmpleado.getText());
        factura.setTipoPago(vistaAccionesFactura.txtTipoPago.getText());
        
        String resultado = FacturasDao.Agregar(factura);
        vistaAccionesFactura.txtMensaje.setText(resultado);
        
    }
    public void crearLineaFactura () {
        Detalle detalle = new Detalle();
        
        detalle.setDetalle(vistaAccionesFactura.txtIdProducto.getText());
        detalle.setCantidad(Integer.parseInt(vistaAccionesFactura.txtCantidad.getText()));
        detalle.setIdFactura(Integer.parseInt(vistaAccionesFactura.txtIdFacturaDetalle.getText()));
        
        String resultado = DetalleDao.Agregar(detalle);
        vistaAccionesFactura.txtMensaje.setText(resultado);
    }
    public void modificarFactura() {
        Facturas factura = new Facturas();
        
        factura.setIdFactura(Integer.parseInt(vistaAccionesFactura.txtIdFactura.getText()));
        factura.setCedulaCliente(vistaAccionesFactura.txtIdEmpleado.getText());
        factura.setTipoPago(vistaAccionesFactura.txtTipoPago.getText());
        factura.setCedulaEmpleado(vistaAccionesFactura.txtIdCliente.getText());
        //factura.setFechaPago(vistaAccionesFactura.txtFechaFactura.getDate());
        
        String resultado = FacturasDao.Modificar(factura);
        vistaAccionesFactura.txtMensaje.setText(resultado);
    }
    public void modificarLineaFactura() {
        Detalle detalle = new Detalle();
        
        detalle.setDetalle(vistaAccionesFactura.txtIdProducto.getText());
        detalle.setCantidad(Integer.parseInt(vistaAccionesFactura.txtCantidad.getText()));
        detalle.setIdFactura(Integer.parseInt(vistaAccionesFactura.txtIdFacturaDetalle.getText()));
        detalle.setLineaFactura(Integer.parseInt(vistaAccionesFactura.txtLinea.getText()));
        
        String resultado = DetalleDao.Modificar(detalle);
        vistaAccionesFactura.txtMensaje.setText(resultado);
    }
    public void listarFactura(JTable tabla) {
        int idFactura = Integer.parseInt(vistaListarFactura.txtIdFactura.getText());
        Facturas encabezadoFactura = facturasDao.Listar(idFactura);
        
        vistaListarFactura.txtEmpleado.setText(encabezadoFactura.getNombreEmpleado());
        vistaListarFactura.txtCliente.setText(encabezadoFactura.getNombreEmpleado());
        vistaListarFactura.txtFecha.setText(encabezadoFactura.getFechaPago()+"");
        vistaListarFactura.txtTotal.setText(encabezadoFactura.getTotalFactura()+"");
        vistaListarFactura.txtTipoPago.setText(encabezadoFactura.getTipoPago());
        
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Detalle> lista = detalleDao.Listar(idFactura);
        Object[] objeto = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getDetalle();
            objeto[1] = lista.get(i).getPrecio();
            objeto[2] = lista.get(i).getCantidad();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);
        
    }
    
    //CLIENTES
    public void nuevoCliente() {
        vista.txtNombre1.setText("");
        vista.txtNombre2.setText("");
        vista.txtApellido1.setText("");
        vista.txtApellido2.setText("");
        vista.txtCedula.setText("");
        vista.txtDirección.setText("");
    }

    public void delete() {
        int fila = vista.tblDatos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una fila para borrar");
        } else {
            clientes.setCedula(vista.tblDatos.getValueAt(fila, 0).toString());
            String id = clientes.getCedula();
            dao.Delete(clientes);
            System.err.println("Id affectado" + id);
            JOptionPane.showMessageDialog(vista, "Cliente Eliminado cedula "+id);
        }
    }

    public void add() {
        String cedula = vista.txtCedula.getText();
        String nombre1 = vista.txtNombre1.getText();
        String nombre2 = vista.txtNombre2.getText();
        String apellido1 = vista.txtApellido1.getText();
        String apellido2 = vista.txtApellido2.getText();
        String direccion = vista.txtDirección.getText();

        clientes.setCedula(cedula);
        clientes.setPrimerNombre(nombre1);
        clientes.setSegundoNombre(nombre2);
        clientes.setPrimerApellido(apellido1);
        clientes.setSegundoApellido(apellido2);
        clientes.setDireccion(direccion);

        int result = dao.Add(clientes);
        if (result == 1) {
            JOptionPane.showMessageDialog(vista, "Usuario Agregado con Exito.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error al agregar");
        }
        limpiarTabla();
    }

    public void update() {
        String fila = vista.txtCedula.getText();
        if (fila.equals("")) {
            JOptionPane.showMessageDialog(vista, "Seleccionar la opcion a editar");
        } else {
            String cedula = vista.txtCedula.getText();
            String nombre1 = vista.txtNombre1.getText();
            String nombre2 = vista.txtNombre2.getText();
            String apellido1 = vista.txtApellido1.getText();
            String apellido2 = vista.txtApellido2.getText();
            String direccion = vista.txtDirección.getText();

            clientes.setCedula(cedula);
            clientes.setPrimerNombre(nombre1);
            clientes.setSegundoNombre(nombre2);
            clientes.setPrimerApellido(apellido1);
            clientes.setSegundoApellido(apellido2);
            clientes.setDireccion(direccion);

            int result = dao.Update(clientes);
            if (result == 1) {
                JOptionPane.showMessageDialog(vista, "Usuario Actualizado con Exito.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar");
            }
            limpiarTabla();
        }

    }

    public void listar(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Clientes> lista = ClientesDao.Listar();
        Object[] objeto = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getCedula();
            objeto[1] = lista.get(i).getPrimerNombre();
            objeto[2] = lista.get(i).getSegundoNombre();
            objeto[3] = lista.get(i).getPrimerApellido();
            objeto[4] = lista.get(i).getSegundoApellido();
            objeto[5] = lista.get(i).getDireccion();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);
    }

    void centrarCeldas(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vista.tblDatos.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }
    
    void limpiarTabla() {
        for (int i = 0; i < vista.tblDatos.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
    
    //EMPLEADOS
    
    public void nuevoEmpleado() {
        vistaPantallaEmpleados.txtNombre1.setText("");
        vistaPantallaEmpleados.txtNombre2.setText("");
        vistaPantallaEmpleados.txtApellido1.setText("");
        vistaPantallaEmpleados.txtApellido2.setText("");
        vistaPantallaEmpleados.txtCedula.setText("");
        vistaPantallaEmpleados.txtIdEmpleado.setText("");
        vistaPantallaEmpleados.txtDirección.setText("");
        vistaPantallaEmpleados.txtPuesto.setText("");
        vistaPantallaEmpleados.txtSalario.setText("");
    }
    
    public void deleteEmpleado() {
        int fila = vistaPantallaEmpleados.tblDatos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaPantallaEmpleados, "Seleccione una fila para borrar");
        } else {
            empleados.setCedula(vistaPantallaEmpleados.tblDatos.getValueAt(fila, 0).toString());
            String id = empleados.getCedula();
            empleadosDao.Delete(empleados);
            System.err.println("Id affectado" + id);
            JOptionPane.showMessageDialog(vistaPantallaEmpleados, "Empleado Eliminado, cedula "+id);
        }
    }
    
    public void addEmpleado() {
        String cedula = vistaPantallaEmpleados.txtCedula.getText();
        String idEmpleado = vistaPantallaEmpleados.txtIdEmpleado.getText();
        String nombre1 = vistaPantallaEmpleados.txtNombre1.getText();
        String nombre2 = vistaPantallaEmpleados.txtNombre2.getText();
        String apellido1 = vistaPantallaEmpleados.txtApellido1.getText();
        String apellido2 = vistaPantallaEmpleados.txtApellido2.getText();
        String direccion = vistaPantallaEmpleados.txtDirección.getText();
        String puesto = vistaPantallaEmpleados.txtPuesto.getText();
        double salario = Double.parseDouble(vistaPantallaEmpleados.txtSalario.getText());

        empleados.setCedula(cedula);
        empleados.setIdEmpleado(idEmpleado);
        empleados.setPrimerNombre(nombre1);
        empleados.setSegundoNombre(nombre2);
        empleados.setPrimerApellido(apellido1);
        empleados.setSegundoApellido(apellido2);
        empleados.setDireccion(direccion);
        empleados.setPuesto(puesto);
        empleados.setSalario(salario);

        int result = empleadosDao.Add(empleados);
        if (result == 1) {
            JOptionPane.showMessageDialog(vistaPantallaEmpleados, "Usuario Agregado con Exito.");
        } else {
            JOptionPane.showMessageDialog(vistaPantallaEmpleados, "Error al agregar");
        }
        limpiarTablaEmpleado();
    }
    
    public void updateEmpleado(){
        int fila = vistaPantallaEmpleados.tblDatos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaPantallaEmpleados, "Seleccionar la opcion a editar");
        } else {
            String cedula = vistaPantallaEmpleados.txtCedula.getText();
            String idEmpleado = vistaPantallaEmpleados.txtIdEmpleado.getText();
            String nombre1 = vistaPantallaEmpleados.txtNombre1.getText();
            String nombre2 = vistaPantallaEmpleados.txtNombre2.getText();
            String apellido1 = vistaPantallaEmpleados.txtApellido1.getText();
            String apellido2 = vistaPantallaEmpleados.txtApellido2.getText();
            String direccion = vistaPantallaEmpleados.txtDirección.getText();
            String puesto = vistaPantallaEmpleados.txtPuesto.getText();
       

            empleados.setCedula(cedula);
            empleados.setIdEmpleado(idEmpleado);
            empleados.setPrimerNombre(nombre1);
            empleados.setSegundoNombre(nombre2);
            empleados.setPrimerApellido(apellido1);
            empleados.setSegundoApellido(apellido2);
            empleados.setDireccion(direccion);
            empleados.setPuesto(puesto);

            int result = empleadosDao.Update(empleados);
            if (result == 1) {
                JOptionPane.showMessageDialog(vistaPantallaEmpleados, "Empleado Actualizado con Exito.");
            } else {
                JOptionPane.showMessageDialog(vistaPantallaEmpleados, "Error al actualizar");
            }
            limpiarTablaEmpleado();
        }
    }
    
    public void listarEmpleados(JTable tabla) {
        centrarCeldas(tabla);
        modeloEmpleado = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modeloEmpleado);
        List<Empleados> lista = EmpleadosDao.Listar();
        Object[] objeto = new Object[9];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getCedula();
            objeto[1] = lista.get(i).getIdEmpleado();
            objeto[2] = lista.get(i).getPrimerNombre();
            objeto[3] = lista.get(i).getSegundoNombre();
            objeto[4] = lista.get(i).getPrimerApellido();
            objeto[5] = lista.get(i).getSegundoApellido();
            objeto[6] = lista.get(i).getDireccion();
            objeto[7] = lista.get(i).getPuesto();
            objeto[8] = lista.get(i).getSalario();
            modeloEmpleado.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);
    }
    
    void centrarCeldasEmpleado(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vistaPantallaEmpleados.tblDatos.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }
    
    void limpiarTablaEmpleado() {
        for (int i = 0; i < vistaPantallaEmpleados.tblDatos.getRowCount(); i++) {
            modeloEmpleado.removeRow(i);
            i = i - 1;
        }
    }
    
    //ROLES
    
    public void nuevoRol() {
        vistaPantallaRoles.txtIdRol.setText("");
        vistaPantallaRoles.txtDescripcion.setText("");
    }
    
    public void deleteRol() {
        int fila = vistaPantallaRoles.tblDatosRoles.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaPantallaRoles, "Seleccione una fila para borrar");
        } else {
            Roles rol = new Roles();
            rol.setIdRol(vistaPantallaRoles.tblDatosRoles.getValueAt(fila, 0).toString());

            int result = rolesDao.Delete(rol);
            if (result == 1) {
                JOptionPane.showMessageDialog(vistaPantallaRoles, "Rol eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(vistaPantallaRoles, "Error al eliminar el rol.");
            }
        }
    }

    public void addRol() {
        String idRol = vistaPantallaRoles.txtIdRol.getText();
        String descripcion = vistaPantallaRoles.txtDescripcion.getText();

        Roles rol = new Roles();
        rol.setIdRol(idRol);
        rol.setDescripcion(descripcion);
        int result = rolesDao.Add(rol);

        if (result == 1) {
            JOptionPane.showMessageDialog(vistaPantallaRoles, "Rol agregado con éxito.");
        } else {
            JOptionPane.showMessageDialog(vistaPantallaRoles, "Error al agregar el rol.");
        }
        
        limpiarTablaRoles();
    }
    
    public void updateRol() {
        String fila = vistaPantallaRoles.txtIdRol.getText();
        if (fila.equals("")) {
            JOptionPane.showMessageDialog(vistaPantallaRoles, "Seleccionar la opción a editar");
        } else {
            String idRol = vistaPantallaRoles.txtIdRol.getText();
            String descripcion = vistaPantallaRoles.txtDescripcion.getText();

            Roles rol = new Roles();
            rol.setIdRol(idRol);
            rol.setDescripcion(descripcion);

            int result = rolesDao.Update(rol);
            if (result == 1) {
                JOptionPane.showMessageDialog(vistaPantallaRoles, "Rol Actualizado con Éxito.");
            } else {
                JOptionPane.showMessageDialog(vistaPantallaRoles, "Error al actualizar el rol");
            }
            limpiarTablaRoles();
        }
    }

    public void listarRoles(JTable tabla) {
        centrarCeldasRoles(tabla);
        modeloRoles = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modeloRoles);
        List<Roles> lista = RolesDao.Listar();
        Object[] objeto = new Object[2];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getIdRol();
            objeto[1] = lista.get(i).getDescripcion();
            modeloRoles.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);
    }

    void centrarCeldasRoles(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vistaPantallaRoles.tblDatosRoles.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    } 
    
    void limpiarTablaRoles() {
        DefaultTableModel modeloRoles = (DefaultTableModel) vistaPantallaRoles.tblDatosRoles.getModel();
        modeloRoles.setRowCount(0);
    }

    //ROLES EMPLEADO
    
    public void nuevoRolEmpleado() {
        vistaPantallaRolesEmpleado.txtCedula.setText("");
        vistaPantallaRolesEmpleado.txtRol.setText("");
    }
    
    public void deleteRolEmpleado() {
        int fila = vistaPantallaRolesEmpleado.tblDatosRolEmpleado.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaPantallaRolesEmpleado, "Seleccione una fila para borrar");
        } else {
            RolesEmpleado rolEmpleado = new RolesEmpleado();
            rolEmpleado.setCedula(vistaPantallaRolesEmpleado.tblDatosRolEmpleado.getValueAt(fila, 0).toString());
            rolEmpleado.setRol(vistaPantallaRolesEmpleado.tblDatosRolEmpleado.getValueAt(fila, 1).toString());
            
            int result = rolesEmpleadoDao.Delete(rolEmpleado);
            if (result == 1) {
                JOptionPane.showMessageDialog(vistaPantallaRolesEmpleado, "Rol eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(vistaPantallaRolesEmpleado, "Error al eliminar el rol.");
            }
        }
    }
    
    public void addRolEmpleado() {
        String cedula = vistaPantallaRolesEmpleado.txtCedula.getText();
        String rol = vistaPantallaRolesEmpleado.txtRol.getText();

        RolesEmpleado rolesEmpleado = new RolesEmpleado();
        rolesEmpleado.setCedula(cedula);
        rolesEmpleado.setRol(rol);
        
        int result = rolesEmpleadoDao.Add(rolesEmpleado);
        if (result == 1) {
            JOptionPane.showMessageDialog(vistaPantallaRolesEmpleado, "Rol agregado con éxito al empleado.");
        } else {
            JOptionPane.showMessageDialog(vistaPantallaRolesEmpleado, "Error al agregar el rol al empleado.");
        }
    
        limpiarTablaRolesEmpleado();
    }
    
    public void updateRolEmpleado() {
        String nuevaCedula = vistaPantallaRolesEmpleado.txtCedula.getText();
        String nuevoRol = vistaPantallaRolesEmpleado.txtRol.getText();
        
        RolesEmpleado rolesEmpleado = new RolesEmpleado();
        rolesEmpleado.setCedula(nuevaCedula);
        rolesEmpleado.setRol(nuevoRol);
        
        int result = rolesEmpleadoDao.Update(rolesEmpleado, nuevoRol);

        if (result == 1) {
            JOptionPane.showMessageDialog(vistaPantallaRolesEmpleado, "Rol del Empleado Actualizado con Éxito.");
        } else {
            JOptionPane.showMessageDialog(vistaPantallaRolesEmpleado, "Error al actualizar el Rol del Empleado");
        }
        limpiarTablaRolesEmpleado();

    }
   
    public void listarRolesEmpleado(JTable tabla) {
        centrarCeldasRolesEmpleado(tabla);
        modeloRolesEmpleado = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modeloRolesEmpleado);
        List<RolesEmpleado> lista = RolesEmpleadoDao.Listar();
        Object[] objeto = new Object[2];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getCedula();
            objeto[1] = lista.get(i).getRol();
            modeloRolesEmpleado.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);
    }
    
    void centrarCeldasRolesEmpleado(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vistaPantallaRolesEmpleado.tblDatosRolEmpleado.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }
    
    void limpiarTablaRolesEmpleado() {
        for (int i = 0; i < vistaPantallaRolesEmpleado.tblDatosRolEmpleado.getRowCount(); i++) {
            modeloRolesEmpleado.removeRow(i);
            i = i - 1;
        }
    }
}
