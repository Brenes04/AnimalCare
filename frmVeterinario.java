/*
 * ANIMAL CARE PROJECT
 * File: frmVeterinario.java
 * Description: Graphical form to register, modify, list, and deactivate veterinarians.
 *
 * Documentation added solely for academic and maintenance purposes.
 * The logic, instructions, and behavior of the original code were not modified.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Form for managing veterinarians.
 * Allows adding, modifying, listing, and deactivating veterinarians.
 * The specialty is selected from a JComboBox loaded with
 * the specialties registered in the Specialties module.
 */
public class frmVeterinario extends JFrame {

    public JPanel panelPrincipal;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtDireccion;
    private JTextField txtCorreo;

    // Medical license was replaced with years of experience.
    private JSpinner spExperiencia;

    // Combo box that displays the available specialties.
    private JComboBox<String> cbxEspecialidad;

    private JRadioButton rbActivo;

    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnDesactivar;
    private JButton btnSalir;

    private JTable tableVeterinario;
    private JRadioButton rbInactivo;

    private List<Veterinario> listVeterinario;
    private List<Especialidad> listEspecialidad;

    private Validacion val = new Validacion();
    private DefaultTableModel modelTabla;

    // Helper object used to call Veterinarian class methods.
    private Veterinario veterinario = new Veterinario();

     //Form constructor.
    public frmVeterinario(List<Veterinario> listVeterinario, List<Especialidad> listEspecialidad) {
        this.listVeterinario = listVeterinario;
        this.listEspecialidad = listEspecialidad;

        setContentPane(panelPrincipal);
        AnimalCareTheme.apply(this, panelPrincipal);
        setTitle("Animal Care | Gestión de Veterinarios");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Groups the RadioButtons so only one can be selected.
        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(rbActivo);
        grupoEstado.add(rbInactivo);
        rbActivo.setSelected(true);

        // Configures the experience spinner: minimum 0 and maximum 60 years.
        spExperiencia.setModel(new SpinnerNumberModel(0, 0, 60, 1));

        // Loads registered specialties into the JComboBox.
        cargarComboEspecialidad();

        // Configures the table columns.
        String[] columnas = {
                "Identificación",
                "Nombre",
                "Teléfono",
                "Dirección",
                "Correo",
                "Experiencia",
                "Especialidad",
                "Estado"
        };

        modelTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableVeterinario.setModel(modelTabla);
        actualizarTabla();

        // Save button: calls the guardarVeterinario() method.
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarVeterinario();
            }
        });

        // Modify button: calls the modificarVeterinario() method.
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarVeterinario();
            }
        });

        // Deactivate button: calls the desactivarVeterinario() method.
        btnDesactivar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desactivarVeterinario();
            }
        });

        // Exit button: closes only this window.
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // When a table row is selected, its data is loaded
        // into the fields to make modification easier.
        tableVeterinario.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosVeterinario();
            }
        });
    }

    //Validates the data and adds a new veterinarian.
    public void guardarVeterinario() {

        if (!val.esCamRequerido(txtId.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese la identificación.");
            return;
        }

        if (!val.esCamRequerido(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre.");
            return;
        }

        if (!val.esTelefonoValido(txtTelefono.getText())) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener 8 dígitos.");
            return;
        }

        if (!val.esCamRequerido(txtDireccion.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese la dirección.");
            return;
        }

        if (!val.esCorreoValido(txtCorreo.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo válido.");
            return;
        }

        if (cbxEspecialidad.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una especialidad.");
            return;
        }

        // Prevents registering two veterinarians with the same identification.
        int posicion = veterinario.buscarVeterinario(listVeterinario, txtId.getText());

        if (posicion != -1) {
            JOptionPane.showMessageDialog(this, "La identificación ya está registrada.");
            return;
        }

        int experiencia = (Integer) spExperiencia.getValue();

        // The combo box displays "code - name", so only the code is used.
        String codEspecialidad = cbxEspecialidad.getSelectedItem().toString().split(" - ")[0];
        veterinario.ingresarVeterinario(listVeterinario, txtId.getText(), txtNombre.getText(), txtTelefono.getText(), txtDireccion.getText(), txtCorreo.getText(), experiencia, codEspecialidad, rbActivo.isSelected());
        actualizarTabla();
        limpiarCampos();
    }

    //Modifies the veterinarian corresponding to the entered identification.
    public void modificarVeterinario() {

        String id = txtId.getText().trim();

        if (!val.esCamRequerido(id)) {
            JOptionPane.showMessageDialog(this, "Seleccione o ingrese la identificación del veterinario.");
            return;
        }

        int posicion = veterinario.buscarVeterinario(listVeterinario, id);

        if (posicion == -1) {
            JOptionPane.showMessageDialog(this, "No existe un veterinario con esa identificación.");
            return;
        }

        if (!val.esCamRequerido(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre.");
            return;
        }

        if (!val.esTelefonoValido(txtTelefono.getText())) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener 8 dígitos.");
            return;
        }

        if (!val.esCamRequerido(txtDireccion.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese la dirección.");
            return;
        }

        if (!val.esCorreoValido(txtCorreo.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo válido.");
            return;
        }

        if (cbxEspecialidad.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una especialidad.");
            return;
        }

        int experiencia = (Integer) spExperiencia.getValue();
        String codEspecialidad = cbxEspecialidad.getSelectedItem().toString().split(" - ")[0];
        veterinario.modificaVeterinario(
                listVeterinario,
                txtNombre.getText(),
                txtTelefono.getText(),
                txtDireccion.getText(),
                txtCorreo.getText(),
                experiencia,
                codEspecialidad,
                rbActivo.isSelected(),
                posicion
        );

        actualizarTabla();
        limpiarCampos();
    }

    //Deactivates the veterinarian selected in the table.
    public void desactivarVeterinario() {
        int fila = tableVeterinario.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un veterinario de la tabla.");
            return;
        }

        String id = modelTabla.getValueAt(fila, 0).toString();
        int posicion = veterinario.buscarVeterinario(listVeterinario, id);

        if (posicion != -1) {
            veterinario.desactivarVeterinario(listVeterinario, posicion);
            actualizarTabla();
            limpiarCampos();
        }
    }

    //Loads all registered specialties into the JComboBox.
    public void cargarComboEspecialidad() {
        cbxEspecialidad.removeAllItems();
        if (listEspecialidad != null) {
            for (Especialidad e : listEspecialidad) {cbxEspecialidad.addItem(e.getCodEspecialidad() + " - " + e.getNomEspecialidad());
            }
        }
    }

    //Updates the JTable with all registered veterinarians.
    public void actualizarTabla() {

        modelTabla.setRowCount(0);

        for (Veterinario v : listVeterinario) {
            modelTabla.addRow(new Object[]{
                    v.getIdPersona(),
                    v.getNomPersona(),
                    v.getTelPersona(),
                    v.getDirPersona(),
                    v.getEmailPersona(),
                    v.getAniosExperiencia(),
                    v.getCodEspecialidad(),
                    v.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

     // Loads the selected row data into the form controls.

    public void cargarDatosVeterinario() {

        int fila = tableVeterinario.getSelectedRow();

        if (fila == -1) {
            return;
        }

        String id = modelTabla.getValueAt(fila, 0).toString();
        int posicion = veterinario.buscarVeterinario(listVeterinario, id);
        if (posicion == -1) {
            return;
        }

        Veterinario v = listVeterinario.get(posicion);

        txtId.setText(v.getIdPersona());
        txtNombre.setText(v.getNomPersona());
        txtTelefono.setText(v.getTelPersona());
        txtDireccion.setText(v.getDirPersona());
        txtCorreo.setText(v.getEmailPersona());
        spExperiencia.setValue(v.getAniosExperiencia());

        // Selects the veterinarian's specialty in the combo box.
        for (int i = 0; i < cbxEspecialidad.getItemCount(); i++) {
            String item = cbxEspecialidad.getItemAt(i);
            if (item.startsWith(v.getCodEspecialidad() + " - ")) {
                cbxEspecialidad.setSelectedIndex(i);
                break;
            }
        }

        if (v.isEstado()) {
            rbActivo.setSelected(true);
        } else {
            rbInactivo.setSelected(true);
        }
    }

     // Clears the controls after saving, modifying, or deactivating.
    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtCorreo.setText("");

        spExperiencia.setValue(0);
        rbActivo.setSelected(true);
        if (cbxEspecialidad.getItemCount() > 0) {
            cbxEspecialidad.setSelectedIndex(0);
        }

        tableVeterinario.clearSelection();
        txtId.requestFocus();
    }
}//END OF CLASS
