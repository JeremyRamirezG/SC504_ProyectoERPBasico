package Controlador;

import Modelo.Clientes;
import Modelo.ClientesDao;
import Modelo.Detalle;
import Modelo.DetalleDao;
import Modelo.Facturas;
import Modelo.FacturasDao;
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
    
    Clientes clientes = new Clientes();
    ClientesDao dao = new ClientesDao();
    Detalle detalle = new Detalle();
    DetalleDao detalleDao = new DetalleDao();
    Facturas facturas = new Facturas();
    FacturasDao facturasDao = new FacturasDao();
    
    DefaultTableModel modelo = new DefaultTableModel();

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
}
