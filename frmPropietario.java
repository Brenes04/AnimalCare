/*
 * ANIMAL CARE PROJECT
 * File: frmPropietario.java
 * Description: Graphical form to register, modify, list, and deactivate owners.
 *
 * Documentation added solely for academic and maintenance purposes.
 * The logic, instructions, and behavior of the original code were not modified.
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class frmPropietario extends JFrame {

    public JPanel panelPrincipal;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtDireccion;
    private JTextField txtCorreo;

    private JLabel txtEstado;
    private JRadioButton rbActivo;
    private JRadioButton rbInactivo;

    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnSalir;

    private JButton btnDesactivar;

    private JTable tablePropietario;

    private List<Propietario> listPropietario;
    private Validacion val = new Validacion();
    private DefaultTableModel modelTable;

    // Helper object used to call Owner methods.
    private Propietario propietario = new Propietario();

    public frmPropietario(List<Propietario> listPropietario) {

        this.listPropietario = listPropietario;

        setContentPane(panelPrincipal);
        AnimalCareTheme.apply(this, panelPrincipal);
        setTitle("Animal Care | Gestión de Propietarios");
        setSize(800, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Groups the RadioButtons so only one can remain selected.
        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(rbActivo);
        grupoEstado.add(rbInactivo);

        // By default, a new owner is active.
        rbActivo.setSelected(true);

        configurarTabla();

        // SAVE.
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPropietario();
            }
        });

        // MODIFY.
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarPropietario();
            }
        });

        // DEACTIVATE.
        btnDesactivar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desactivarPropietario();
            }
        });

        // EXIT.
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        /*
         * When a row in the table is selected,
         * its data is loaded so it can be modified.
         */
        tablePropietario.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    cargarDatosPropietario();
                }
            }
        });
    }

    /**
     * Configures the JTable columns.
     */
    public void configurarTabla() {

        String[] columnas = {
                "Identificación",
                "Nombre",
                "Teléfono",
                "Dirección",
                "Correo",
                "Estado"
        };

        modelTable = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePropietario.setModel(modelTable);

        actualizarTabla();
    }

    /**
     * Validates and saves a new owner.
     */
    public void guardarPropietario() {

        if (!revisarCampos()) {
            return;
        }

        // Duplicate identification numbers are not allowed.
        int posicion = propietario.buscarPropietario(
                listPropietario,
                txtId.getText().trim()
        );

        if (posicion != -1) {
            JOptionPane.showMessageDialog(this, "Esa identificación ya existe.");
            return;
        }

        boolean estado = rbActivo.isSelected();

        propietario.ingresarPropietario(listPropietario, txtId.getText().trim(), txtNombre.getText().trim(), txtTelefono.getText().trim(), txtDireccion.getText().trim(), txtCorreo.getText().trim(), estado);
        actualizarTabla();
        limpiarCampos();
    }

    //Modifies an existing owner. Here the status can also be changed Active <-> Inactive.
    public void modificarPropietario() {

        if (!revisarCampos()) {
            return;
        }

        int posicion = propietario.buscarPropietario(listPropietario, txtId.getText().trim());
        if (posicion == -1) {
            JOptionPane.showMessageDialog(this, "Propietario no encontrado.");
            return;
        }
        boolean estado = rbActivo.isSelected();

        // Instead of changing attributes directly from the form, the Owner class method is called. This makes the program more organized and easier to explain.
        propietario.modificarPropietario(listPropietario, txtNombre.getText().trim(), txtTelefono.getText().trim(), txtDireccion.getText().trim(), txtCorreo.getText().trim(), estado, posicion);
        actualizarTabla();
        limpiarCampos();
    }

    /** Deactivates the selected owner without removing the object from the list. */
    public void desactivarPropietario() {
        int fila = tablePropietario.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un propietario de la tabla.");
            return;
        }

        String id = modelTable.getValueAt(fila, 0).toString();
        int posicion = propietario.buscarPropietario(listPropietario, id);
        if (posicion != -1) {
            propietario.desactivarPropietario(listPropietario, posicion);
            actualizarTabla();
            limpiarCampos();
        }
    }

    // Validates all required fields in the form.
    public boolean revisarCampos() {

        if (!val.esCamRequerido(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Complete la identificación.");
            return false;
        }

        if (!val.esCamRequerido(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Complete el nombre.");
            return false;
        }

        if (!val.esTelefonoValido(txtTelefono.getText())) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener 8 dígitos.");
            return false;
        }

        if (!val.esCamRequerido(txtDireccion.getText())) {
            JOptionPane.showMessageDialog(this, "Complete la dirección.");
            return false;
        }

        if (!val.esCorreoValido(txtCorreo.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo válido.");
            return false;
        }
        return true;
    }

    //Loads the selected row data into the fields.
    public void cargarDatosPropietario() {
        int fila = tablePropietario.getSelectedRow();
        if (fila == -1) {
            return;
        }

        String id = modelTable.getValueAt(fila, 0).toString();
        int posicion = propietario.buscarPropietario(
                listPropietario,
                id
        );

        if (posicion == -1) {
            return;
        }

        Propietario p = listPropietario.get(posicion);

        txtId.setText(p.getIdPersona());
        txtNombre.setText(p.getNomPersona());
        txtTelefono.setText(p.getTelPersona());
        txtDireccion.setText(p.getDirPersona());
        txtCorreo.setText(p.getEmailPersona());

        // Visually displays the current status.
        if (p.isEstado()) {
            rbActivo.setSelected(true);
        } else {
            rbInactivo.setSelected(true);
        }
    }


    //Refreshes the table with the current contents of the list.
    public void actualizarTabla() {
        modelTable.setRowCount(0);
        for (Propietario p : listPropietario) {
            modelTable.addRow(new Object[]{
                    p.getIdPersona(),
                    p.getNomPersona(),
                    p.getTelPersona(),
                    p.getDirPersona(),
                    p.getEmailPersona(),
                    p.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

    //Clears the form and leaves Active selected.
    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtCorreo.setText("");

        rbActivo.setSelected(true);
        tablePropietario.clearSelection();
        txtId.requestFocus();
    }
}//end of class
