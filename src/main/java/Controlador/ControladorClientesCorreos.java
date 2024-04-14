package Controlador;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import Vista.PantallaClientesCorreos;
import Modelo.ClientesCorreos;
import Modelo.ClientesCorreosDao;
import java.awt.event.ActionListener;
        

/**
 *
 * @author User
 */
public class ControladorClientesCorreos implements ActionListener {
    
    PantallaClientesCorreos vista = new PantallaClientesCorreos();
    DefaultTableModel modelo = new DefaultTableModel();
    ClientesCorreos clientesCorreos = new ClientesCorreos();
    ClientesCorreosDao dao = new ClientesCorreosDao();

    public ControladorClientesCorreos(PantallaClientesCorreos vista) {
        this.vista = vista;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnBorrar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnNuevo.addActionListener(this);
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
            int fila = vista.tblDatos.getSelectedRow();
            update(fila);
            limpiarTabla();
            listar(vista.tblDatos);
            nuevoCliente();
        }
        
        if (e.getSource() == vista.btnNuevo) {
            limpiarTabla();
            nuevoCliente();
            listar(vista.tblDatos);
        }

        if (e.getSource() == vista.btnBorrar) {
            int fila = vista.tblDatos.getSelectedRow();
            delete(fila);
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

                vista.lblCedula.setText(cedula);
                vista.txtCorreo.setText(nombre1);
            }
        }
    }
    

    public void nuevoCliente() {
        vista.txtCorreo.setText("");
    }

    public void delete(int fila) {
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una fila para borrar");
        } else {
            String correoActual=vista.tblDatos.getValueAt(fila, 1).toString();
            clientesCorreos.setCedula(vista.tblDatos.getValueAt(fila, 0).toString());
            String id = clientesCorreos.getCedula();
            dao.Delete(clientesCorreos, correoActual);
            System.err.println("Id affectado" + id);
            JOptionPane.showMessageDialog(vista, "Correo Eliminado cliente id "+id);
        }
    }

    public void add() {
        String cedula = vista.lblCedula.getText();
        String correo = vista.txtCorreo.getText();

        clientesCorreos.setCedula(cedula);
        clientesCorreos.setCorreo(correo);

        int result = dao.Add(clientesCorreos);
        if (result == 1) {
            JOptionPane.showMessageDialog(vista, "Usuario Agregado con Exito.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error al agregar");
        }
        limpiarTabla();
    }

    public void update(int fila) {
        
        String verif = vista.lblCedula.getText();
        if (verif.equals("")) {
            JOptionPane.showMessageDialog(vista, "Seleccionar la opcion a editar");
        } else {
            String correoActual=vista.tblDatos.getValueAt(fila, 1).toString();
            String cedula = vista.lblCedula.getText();
            String correo = vista.txtCorreo.getText();

            clientesCorreos.setCedula(cedula);
            clientesCorreos.setCorreo(correo);

            int result = dao.Update(clientesCorreos, correoActual);
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
        List<ClientesCorreos> lista = ClientesCorreosDao.Listar(vista.lblCedula.getText());
        Object[] objeto = new Object[2];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getCedula();
            objeto[1] = lista.get(i).getCorreo();
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
