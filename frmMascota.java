/*
 * ANIMAL CARE PROJECT
 * File: frmMascota.java
 * Description: Graphical form to register, modify, list, and deactivate pets.
 *
 * Documentation added solely for academic and maintenance purposes.
 * The logic, instructions, and behavior of the original code were not modified.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class frmMascota extends JFrame {

    public JPanel panelPrincipal;

    private JTextField txtCodigo;
    private JTextField txtNombre;

    private JComboBox<String> cbxEspecie;
    private JComboBox<String> cbxRaza;
    private JComboBox<String> cbxPropietario;

    private JRadioButton rbMacho;
    private JRadioButton rbHembra;

    private JRadioButton rbActivo;
    private JRadioButton rbInactivo;

    private JSpinner spEdad;

    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnDesactivar;
    private JButton btnSalir;

    private JTable tableMascota;

    private List<Mascota> listMascota;
    private List<Propietario> listPropietario;
    private DefaultTableModel modelTabla;
    Mascota mascota = new Mascota();

    // CONSTRUCTOR
    public frmMascota(List<Mascota> listMascota, List<Propietario> listPropietario) {
        this.listMascota = listMascota;
        this.listPropietario = listPropietario;
        setContentPane(panelPrincipal);
        AnimalCareTheme.apply(this, panelPrincipal);
        setTitle("Animal Care | Gestión de Mascotas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 550);
        setLocationRelativeTo(null);


        // Group sex options
        ButtonGroup grupoSexo = new ButtonGroup();
        grupoSexo.add(rbMacho);
        grupoSexo.add(rbHembra);
        rbMacho.setSelected(true);

        // Group status options
        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(rbActivo);
        grupoEstado.add(rbInactivo);
        rbActivo.setSelected(true);

        // Configure age
        spEdad.setModel(new SpinnerNumberModel(0, 0, 50, 1));

        // Code is not editable
        txtCodigo.setEditable(false);

        // Initial loads
        cargarEspecies();
        cargarRazas();
        cargarPropietarios();
        generarCodigoMascota();
        configurarTabla();

        // SPECIES CHANGE
        cbxEspecie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarRazas();
            }
        });

        // SAVE
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarMascota();
            }
        });

        // MODIFY
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarMascota();
            }
        });//end of MODIFY button

        // DEACTIVATE
        btnDesactivar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desactivarMascota();
            }
        });

        // EXIT
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });//end of exit button

        // SELECT ROW
        tableMascota.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosMascota();
            }
        });
    }// end of constructor

    // SAVE PET
    public void guardarMascota() {
        if (txtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre de la mascota.");
            return;
        }
        if (cbxPropietario.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un propietario.");
            return;
        }

        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String especie = String.valueOf(cbxEspecie.getSelectedItem());
        String raza = String.valueOf(cbxRaza.getSelectedItem());
        String sexo;
        if (rbMacho.isSelected()) {
            sexo = "Macho";
        } else {
            sexo = "Hembra";
        }
        int edad = (int) spEdad.getValue();
        String propietario = String.valueOf(cbxPropietario.getSelectedItem());
        String idPropietario = propietario.split(" - ")[0];
        int pos = mascota.buscarMascota(listMascota, codigo);
        if (pos != -1) {
            JOptionPane.showMessageDialog(null, "La mascota ya existe.");
            return;
        }
        mascota.ingresarMascota(listMascota, codigo, nombre, especie, raza, sexo, edad, idPropietario, rbActivo.isSelected());
        actualizarTabla();
        limpiarCampos();
        generarCodigoMascota();
    }

    // MODIFY PET
    public void modificarMascota() {
        String codigo = txtCodigo.getText();
        int pos = mascota.buscarMascota(listMascota, codigo);
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Mascota no encontrada.");
            return;
        }

        if (txtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre.");
            return;
        }
        String especie = String.valueOf(cbxEspecie.getSelectedItem());
        String raza = String.valueOf(cbxRaza.getSelectedItem());
        String sexo;
        if (rbMacho.isSelected()) {
            sexo = "Macho";
        } else {
            sexo = "Hembra";
        }
        int edad = (int) spEdad.getValue();
        String propietario = String.valueOf(cbxPropietario.getSelectedItem());
        String idPropietario = propietario.split(" - ")[0];
        mascota.modificaMascota(listMascota, txtNombre.getText(), especie, raza, sexo, edad, idPropietario, rbActivo.isSelected(), pos);
        actualizarTabla();
        limpiarCampos();
        generarCodigoMascota();
    }

    // DEACTIVATE PET
    public void desactivarMascota() {
        int fila = tableMascota.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una mascota.");
            return;
        }
        String codigo = modelTabla.getValueAt(fila, 0).toString();
        int pos = mascota.buscarMascota(listMascota,codigo);
        if (pos != -1) {
            mascota.desactivarMascota(listMascota,pos);
            actualizarTabla();
            limpiarCampos();
            generarCodigoMascota();
        }
    }

    // LOAD SPECIES
    public void cargarEspecies() {
        cbxEspecie.removeAllItems();
        cbxEspecie.addItem("Perro");
        cbxEspecie.addItem("Gato");
        cbxEspecie.addItem("Conejo");
        cbxEspecie.addItem("Ave");
        cbxEspecie.addItem("Hámster");
        cbxEspecie.addItem("Cobaya");
        cbxEspecie.addItem("Otro");
    }

    // LOAD BREEDS
    public void cargarRazas() {
        cbxRaza.removeAllItems();
        String especie =String.valueOf(cbxEspecie.getSelectedItem());
        if (especie.equals("Perro")) {
            cbxRaza.addItem("Labrador Retriever");
            cbxRaza.addItem("Golden Retriever");
            cbxRaza.addItem("Pastor Alemán");
            cbxRaza.addItem("Poodle");
            cbxRaza.addItem("Chihuahua");
            cbxRaza.addItem("Schnauzer");
            cbxRaza.addItem("Husky Siberiano");
            cbxRaza.addItem("Rottweiler");
            cbxRaza.addItem("Shih Tzu");
            cbxRaza.addItem("Pug");
            cbxRaza.addItem("Beagle");
            cbxRaza.addItem("Mestizo");
        }
        else if (especie.equals("Gato")) {
            cbxRaza.addItem("Siamés");
            cbxRaza.addItem("Persa");
            cbxRaza.addItem("Maine Coon");
            cbxRaza.addItem("Bengalí");
            cbxRaza.addItem("Angora");
            cbxRaza.addItem("Sphynx");
            cbxRaza.addItem("Mestizo");
        }
        else if (especie.equals("Conejo")) {
            cbxRaza.addItem("Mini Lop");
            cbxRaza.addItem("Cabeza de León");
            cbxRaza.addItem("Rex");
            cbxRaza.addItem("Holandés");
            cbxRaza.addItem("Mestizo");
        }
        else if (especie.equals("Ave")) {
            cbxRaza.addItem("Periquito");
            cbxRaza.addItem("Canario");
            cbxRaza.addItem("Loro");
            cbxRaza.addItem("Cacatúa");
            cbxRaza.addItem("Agapornis");
            cbxRaza.addItem("Otra");
        }
        else if (especie.equals("Hámster")) {
            cbxRaza.addItem("Sirio");
            cbxRaza.addItem("Ruso");
            cbxRaza.addItem("Roborovski");
            cbxRaza.addItem("Chino");
        }
        else if (especie.equals("Cobaya")) {
            cbxRaza.addItem("Americana");
            cbxRaza.addItem("Abisinia");
            cbxRaza.addItem("Peruana");
            cbxRaza.addItem("Otra");
        }
        else {
            cbxRaza.addItem("No especificada");
        }
    }

    // LOAD OWNERS
    public void cargarPropietarios() {
        cbxPropietario.removeAllItems();
        for (int i = 0; i < listPropietario.size(); i++) {
            if (listPropietario.get(i).isEstado()) {
                cbxPropietario.addItem(listPropietario.get(i).getIdPersona() + " - " + listPropietario.get(i).getNomPersona());
            }
        }
    }

    // GENERATE CODE
    public void generarCodigoMascota() {
        int mayor = 0;
        for (int i = 0; i < listMascota.size(); i++) {
            String codigo = listMascota.get(i).getCodMascota();
            if (codigo.startsWith("MAS")) {
                try {
                    int numero = Integer.parseInt(codigo.substring(3));
                    if (numero > mayor) {
                        mayor = numero;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }//end of for loop
        mayor++;
        txtCodigo.setText(String.format("MAS%03d", mayor));
    }//end of generarCodigo

    // CONFIGURE TABLE
    public void configurarTabla() {
        String[] columnas = {
                "Código",
                "Nombre",
                "Especie",
                "Raza",
                "Sexo",
                "Edad",
                "Propietario",
                "Estado"
        };
        modelTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableMascota.setModel(modelTabla);
        actualizarTabla();
    }
    // UPDATE TABLE
    public void actualizarTabla() {
        modelTabla.setRowCount(0);
        for (int i = 0; i < listMascota.size(); i++) {
            Mascota m = listMascota.get(i);
            modelTabla.addRow(new Object[]{
                    m.getCodMascota(),
                    m.getNomMascota(),
                    m.getEspecie(),
                    m.getRaza(),
                    m.getSexo(),
                    m.getEdad(),
                    m.getIdPropietario(),
                    m.isEstado() ? "Activo" : "Inactivo"

            });
        }
    }

    // LOAD PET FROM TABLE
    public void cargarDatosMascota() {
        int fila = tableMascota.getSelectedRow();
        if (fila == -1) {
            return;
        }
        String codigo = modelTabla.getValueAt(fila, 0).toString();
        int pos = mascota.buscarMascota(listMascota,codigo);
        if (pos == -1) {
            return;
        }
        Mascota m =listMascota.get(pos);
        txtCodigo.setText(m.getCodMascota());
        txtNombre.setText(m.getNomMascota());
        cbxEspecie.setSelectedItem(m.getEspecie());
        cargarRazas();
        cbxRaza.setSelectedItem(m.getRaza());
        if (m.getSexo().equalsIgnoreCase("Macho")){
            rbMacho.setSelected(true);
        } else {
            rbHembra.setSelected(true);
        }

        spEdad.setValue(m.getEdad());


        for (int i = 0; i < cbxPropietario.getItemCount(); i++) {
            String item = cbxPropietario.getItemAt(i);
            if (item.startsWith(m.getIdPropietario() + " - ")) {
                cbxPropietario.setSelectedIndex(i);
                break;
            }
        }

        if (m.isEstado()) {
            rbActivo.setSelected(true);
        } else {
            rbInactivo.setSelected(true);
        }
    }

    // CLEAR FIELDS
    public void limpiarCampos() {
        txtNombre.setText("");
        if (cbxEspecie.getItemCount() > 0) {
            cbxEspecie.setSelectedIndex(0);
        }

        cargarRazas();
        rbMacho.setSelected(true);
        spEdad.setValue(0);
        if (cbxPropietario.getItemCount() > 0) {

            cbxPropietario.setSelectedIndex(0);
        }
        rbActivo.setSelected(true);
        tableMascota.clearSelection();
    }
}// end of class
