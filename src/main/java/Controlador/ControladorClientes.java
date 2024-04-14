package Controlador;

import Modelo.Clientes;
import Modelo.ClientesDao;
import Vista.PantallaClientes;
import Vista.PantallaClientesCorreos;
import Vista.PantallaClientesTelefonos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ControladorClientes implements ActionListener {

    PantallaClientes vista = new PantallaClientes();
    DefaultTableModel modelo = new DefaultTableModel();
    Clientes clientes = new Clientes();
    ClientesDao dao = new ClientesDao();

    public ControladorClientes(PantallaClientes vista) {
        this.vista = vista;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnBorrar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnNuevo.addActionListener(this);
        this.vista.btnTelefonoAd.addActionListener(this);
        this.vista.btnCorreoAd.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
        
        if (e.getSource() == vista.btnTelefonoAd) {
                int fila = vista.tblDatos.getSelectedRow();
                String nombreCompleto=vista.tblDatos.getValueAt(fila, 1).toString()+" "+
                        vista.tblDatos.getValueAt(fila, 2).toString()+" "+
                        vista.tblDatos.getValueAt(fila, 3).toString()+" "+
                        vista.tblDatos.getValueAt(fila, 4).toString();
                
                PantallaClientesTelefonos pantalla=new PantallaClientesTelefonos(
                        vista.tblDatos.getValueAt(fila, 0).toString(),
                        nombreCompleto
                );
                ControladorClientesTelefonos con=new ControladorClientesTelefonos(pantalla);
                pantalla.setVisible(true);
                pantalla.setLocationRelativeTo(null);
        }
        
        if (e.getSource() == vista.btnCorreoAd) {
                int fila = vista.tblDatos.getSelectedRow();
                String nombreCompleto=vista.tblDatos.getValueAt(fila, 1).toString()+" "+
                        vista.tblDatos.getValueAt(fila, 2).toString()+" "+
                        vista.tblDatos.getValueAt(fila, 3).toString()+" "+
                        vista.tblDatos.getValueAt(fila, 4).toString();
                        
                PantallaClientesCorreos pantalla=new PantallaClientesCorreos(
                        vista.tblDatos.getValueAt(fila, 0).toString(),
                        nombreCompleto
                );
                ControladorClientesCorreos con=new ControladorClientesCorreos(pantalla);
                pantalla.setVisible(true);      
                pantalla.setLocationRelativeTo(null);
        }
    }
    

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
