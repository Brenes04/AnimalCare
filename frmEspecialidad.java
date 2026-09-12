/*
 * ANIMAL CARE PROJECT
 * File: frmEspecialidad.java
 * Description: Graphical form to manage veterinary specialties.
 *
 * Documentation added solely for academic and maintenance purposes.
 * The logic, instructions, and behavior of the original code were not modified.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//frmEspecialidad
public class frmEspecialidad extends JFrame {

    public JPanel panelPrincipal;

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtCosto;

    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnDesactivar;
    private JButton btnSalir;

    private JTable tableEspecialidad;

    private List<Especialidad> listEspecialidad;

    private DefaultTableModel modelTabla;

    Especialidad especialidad = new Especialidad();
    Validacion val = new Validacion();

    // CONSTRUCTOR
    public frmEspecialidad(List<Especialidad> listEspecialidad) {
        this.listEspecialidad = listEspecialidad;
        setContentPane(panelPrincipal);
        AnimalCareTheme.apply(this, panelPrincipal);
        setTitle("Animal Care | Especialidades Veterinarias");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        configurarTabla();

        // SAVE
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarEspecialidad();
            }
        });

        // MODIFY
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEspecialidad();
            }
        });

        // DEACTIVATE / DELETE
        btnDesactivar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEspecialidad();
            }
        });

        // EXIT
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // SELECT FROM TABLE
        tableEspecialidad.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosEspecialidad();
            }
        });
    }// end of constructor

    // SAVE SPECIALTY
    public void guardarEspecialidad() {
        if (!val.esCamRequerido(txtCodigo.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese el código de la especialidad.");
            return;
        }

        if (!val.esCamRequerido(txtNombre.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre de la especialidad.");
            return;
        }

        if (!val.esNumeroDecimal(txtCosto.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un costo válido.");
            return;
        }

        String codigo = txtCodigo.getText();
        int pos = especialidad.buscarEspecialidad(listEspecialidad, codigo);
        if (pos != -1) {
            JOptionPane.showMessageDialog(null, "El código de especialidad ya existe.");
            return;
        }
        double costo = Double.parseDouble(txtCosto.getText());
        especialidad.ingresarEspecialidad(listEspecialidad, txtCodigo.getText(), txtNombre.getText(), costo);
        actualizarTabla();
        limpiarCampos();
    }

    // MODIFY SPECIALTY
    public void modificarEspecialidad() {
        String codigo = txtCodigo.getText().trim();
        if (!val.esCamRequerido(codigo)) {
            JOptionPane.showMessageDialog(null, "Seleccione una especialidad.");
            return;
        }

        int pos = especialidad.buscarEspecialidad(listEspecialidad, codigo);
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Especialidad no encontrada.");
            return;
        }

        if (!val.esCamRequerido(txtNombre.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre.");
            return;
        }

        if (!val.esNumeroDecimal(txtCosto.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un costo válido.");
            return;
        }
        double costo = Double.parseDouble(txtCosto.getText());
        especialidad.modificaEspecialidad(listEspecialidad, txtNombre.getText(), costo, pos);
        actualizarTabla();
        limpiarCampos();
    }

    // DELETE SPECIALTY
    public void eliminarEspecialidad() {
        int fila = tableEspecialidad.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una especialidad de la tabla.");
            return;
        }
        String codigo = modelTabla.getValueAt(fila, 0).toString();
        int pos = especialidad.buscarEspecialidad(listEspecialidad, codigo);
        if (pos != -1) {
            especialidad.eliminaEspecialidad(listEspecialidad, pos);
            actualizarTabla();
            limpiarCampos();
        }
    }

    // CONFIGURE TABLE
    public void configurarTabla() {
        String[] columnas = {"Código", "Nombre", "Costo"};
        modelTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableEspecialidad.setModel(modelTabla);
        actualizarTabla();
    }

    // UPDATE TABLE
    public void actualizarTabla() {
        modelTabla.setRowCount(0);
        for (int i = 0; i < listEspecialidad.size(); i++) {
            Especialidad e = listEspecialidad.get(i);
            modelTabla.addRow(new Object[]{e.getCodEspecialidad(), e.getNomEspecialidad(), e.getCostoBase()});
        }
    }

    // LOAD TABLE DATA
    public void cargarDatosEspecialidad() {
        int fila = tableEspecialidad.getSelectedRow();
        if (fila == -1) {
            return;
        }
        txtCodigo.setText(modelTabla.getValueAt(fila, 0).toString());
        txtNombre.setText(modelTabla.getValueAt(fila, 1).toString());
        txtCosto.setText(modelTabla.getValueAt(fila, 2).toString());
    }

    // CLEAR FIELDS
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCosto.setText("");
        tableEspecialidad.clearSelection();
        txtCodigo.requestFocus();
    }
}// end of class
