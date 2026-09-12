/*
 * ANIMAL CARE PROJECT
 * File: frmConsultaMedica.java
 * Description: Graphical form to register, search, and modify medical consultations.
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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

public class frmConsultaMedica extends JFrame {

    public JPanel panelPrincipal;

    private JTextField txtNumConsulta;
    private JTextField txtMotivo;
    private JTextField txtCosto;
    private JTextField txtDiagnostico;

    private JSpinner spnFecha;
    private JSpinner spnHora;

    private JComboBox<String> cbxPropietario;
    private JComboBox<String> cbxMascota;
    private JComboBox<String> cbxEspecialidad;
    private JComboBox<String> cbxVeterinario;

    private JButton btnGuardar;
    private JButton btnBuscar;
    private JButton btnModificar;
    private JButton btnLimpiar;
    private JButton btnSalir;

    private JTable tableConsulta;

    private List<ConsultaMedica> listConsulta;
    private List<Propietario> listPropietario;
    private List<Mascota> listMascota;
    private List<Especialidad> listEspecialidad;
    private List<Veterinario> listVeterinario;

    private ConsultaMedica consulta = new ConsultaMedica();
    private Especialidad especialidad = new Especialidad();
    private Validacion val = new Validacion();

    private DefaultTableModel modelTable;
    private boolean actualizandoFormulario = false;

    public frmConsultaMedica(List<ConsultaMedica> listConsulta, List<Propietario> listPropietario, List<Mascota> listMascota, List<Especialidad> listEspecialidad, List<Veterinario> listVeterinario) {
        this.listConsulta = listConsulta;
        this.listPropietario = listPropietario;
        this.listMascota = listMascota;
        this.listEspecialidad = listEspecialidad;
        this.listVeterinario = listVeterinario;

        setContentPane(panelPrincipal);
        AnimalCareTheme.apply(this,panelPrincipal);
        setTitle("Animal Care | Agenda de Citas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 680);
        setLocationRelativeTo(null);

        // FIELDS THE USER DOES NOT MODIFY
        txtNumConsulta.setEditable(false);
        txtCosto.setEditable(false);

        // CONFIGURE DATE AND TIME
        configurarFecha();
        configurarHora();

        // CONFIGURE TABLE
        configurarTabla();

        // LOAD DATA
        cargarPropietarios();
        cargarEspecialidades();
        cargarMascotas();
        cargarCosto();
        cargarVeterinarios();
        generarNumeroConsulta();

        // OWNER
        cbxPropietario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMascotas();
            }
        });

        // SPECIALTY
        cbxEspecialidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCosto();
                cargarVeterinarios();
                if (!actualizandoFormulario) {
                    mostrarTablaVeterinarios();
                }
            }
        });

        // SAVE
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarConsulta();
            }
        });

        // SEARCH
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarConsultaEnTabla();
            }
        });

        // MODIFY
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarConsulta();
            }
        });

        // CLEAR
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                generarNumeroConsulta();
            }
        });

        // EXIT
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });//end of exit action

        // SELECT CONSULTATION FROM TABLE
        tableConsulta.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    cargarDatosConsulta();
                }//end of if
            }//end of void method
        });//end of tableConsulta
    }//end of frmConsulta

    // CONFIGURE DATE
    public void configurarFecha() {
        SpinnerDateModel modeloFecha = new SpinnerDateModel();
        spnFecha.setModel(modeloFecha);
        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy");
        spnFecha.setEditor(editorFecha);
    }

    // CONFIGURE TIME
    public void configurarHora() {
        SpinnerDateModel modeloHora = new SpinnerDateModel();
        spnHora.setModel(modeloHora);
        JSpinner.DateEditor editorHora = new JSpinner.DateEditor(spnHora, "HH:mm");
        spnHora.setEditor(editorHora);
    }

    // GET DATE IN FORMAT FOR SAVING
    public String obtenerFecha() {
        Date fecha = (Date) spnFecha.getValue();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fecha);
    }

    // GET TIME
    public String obtenerHora() {
        Date hora = (Date) spnHora.getValue();
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        return formato.format(hora);
    }

    // CONFIGURE TABLE
    public void configurarTabla() {
        String[] columnas = {
                "N° Consulta",
                "Fecha",
                "Hora",
                "Mascota",
                "Propietario",
                "Especialidad",
                "Veterinario",
                "Motivo",
                "Costo",
                "Diagnóstico"
        };

        modelTable = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableConsulta.setModel(modelTable);
        actualizarTabla();
    }

    // GENERATE CONSULTATION NUMBER
    public void generarNumeroConsulta() {
        int mayor = 0;
        for (ConsultaMedica c : listConsulta) {
            if (c.getNumConsulta() > mayor) {
                mayor = c.getNumConsulta();
            }
        }
        txtNumConsulta.setText(String.valueOf(mayor + 1));
    }

    // LOAD OWNERS
    public void cargarPropietarios() {
        cbxPropietario.removeAllItems();
        for (Propietario p : listPropietario) {
            if (p.isEstado()) {
                cbxPropietario.addItem(p.getIdPersona() + " - " + p.getNomPersona());
            }
        }
    }

    // LOAD PETS
    public void cargarMascotas() {
        cbxMascota.removeAllItems();
        if (cbxPropietario.getSelectedItem() == null) {
            return;
        }
        String propietario = cbxPropietario.getSelectedItem().toString();
        String idPropietario = propietario.split(" - ")[0];
        for (Mascota m : listMascota) {
            if (m.isEstado() && m.getIdPropietario().equalsIgnoreCase(idPropietario)) {
                cbxMascota.addItem(m.getCodMascota() + " - " + m.getNomMascota());
            }
        }
    }

    // LOAD SPECIALTIES
    public void cargarEspecialidades() {
        cbxEspecialidad.removeAllItems();
        for (Especialidad e : listEspecialidad) {
            cbxEspecialidad.addItem(e.getCodEspecialidad() + " - " + e.getNomEspecialidad());
        }
    }

    // LOAD COST
    public void cargarCosto() {
        if (cbxEspecialidad.getSelectedItem() == null) {
            txtCosto.setText("");
            return;
        }
        String seleccion = cbxEspecialidad.getSelectedItem().toString();
        String codigo = seleccion.split(" - ")[0];

        int posicion = especialidad.buscarEspecialidad(listEspecialidad, codigo);
        if (posicion != -1) {
            txtCosto.setText(String.valueOf(listEspecialidad.get(posicion).getCostoBase()));
        } else {
            txtCosto.setText("");
        }
    }

    // LOAD VETERINARIANS
    public void cargarVeterinarios() {
        cbxVeterinario.removeAllItems();
        if (cbxEspecialidad.getSelectedItem() == null) {
            return;
        }

        String seleccion = cbxEspecialidad.getSelectedItem().toString();
        String codigoEspecialidad = seleccion.split(" - ")[0];
        for (Veterinario v : listVeterinario) {
            if (v.isEstado() && v.getCodEspecialidad().equalsIgnoreCase(codigoEspecialidad)) {
                cbxVeterinario.addItem(v.getIdPersona() + " - " + v.getNomPersona());
            }
        }
    }

    // SHOW VETERINARIANS
    public void mostrarTablaVeterinarios() {
        if (cbxEspecialidad.getSelectedItem() == null || cbxVeterinario.getItemCount() == 0) {
            return;
        }

        String codigoEspecialidad = cbxEspecialidad.getSelectedItem().toString().split(" - ")[0];
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{
                        "Identificación",
                        "Nombre",
                        "Teléfono",
                        "Correo",
                        "Experiencia"
                }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Veterinario v : listVeterinario) {
            if (v.isEstado() && v.getCodEspecialidad().equalsIgnoreCase(codigoEspecialidad)) {
                modelo.addRow(new Object[]{v.getIdPersona(), v.getNomPersona(), v.getTelPersona(), v.getEmailPersona(), v.getAniosExperiencia() + " años"});
            }
        }

        JTable tablaVeterinarios = new JTable(modelo);
        tablaVeterinarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaVeterinarios);
        scroll.setPreferredSize(new java.awt.Dimension(650, 180));
        int opcion = JOptionPane.showConfirmDialog(this, scroll, "Seleccione un veterinario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion == JOptionPane.OK_OPTION && tablaVeterinarios.getSelectedRow() != -1) {
            int fila = tablaVeterinarios.getSelectedRow();
            String idVeterinario = modelo.getValueAt(fila, 0).toString();
            for (int i = 0; i < cbxVeterinario.getItemCount(); i++) {
                if (cbxVeterinario.getItemAt(i).startsWith(idVeterinario + " - ")) {
                    cbxVeterinario.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    // CHECK FIELDS
    public boolean revisarCampos() {
        if (cbxPropietario.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un propietario.");

            return false;
        }

        if (cbxMascota.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una mascota.");
            return false;
        }

        if (cbxEspecialidad.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una especialidad.");
            return false;
        }

        if (cbxVeterinario.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "No hay un veterinario activo para esa especialidad.");
            return false;
        }

        if (!val.esCamRequerido(txtMotivo.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese el motivo de la consulta.");
            return false;
        }
        return true;
    }

    // SAVE CONSULTATION
    public void guardarConsulta() {
        if (!revisarCampos()) {
            return;
        }

        int numero = Integer.parseInt(txtNumConsulta.getText());
        if (consulta.buscarConsulta(listConsulta, numero) != -1) {
            JOptionPane.showMessageDialog(this, "El número de consulta ya existe.");
            generarNumeroConsulta();
            return;
        }

        String idPropietario = cbxPropietario.getSelectedItem().toString().split(" - ")[0];
        String codMascota = cbxMascota.getSelectedItem().toString().split(" - ")[0];
        String codEspecialidad = cbxEspecialidad.getSelectedItem().toString().split(" - ")[0];
        String idVeterinario = cbxVeterinario.getSelectedItem().toString().split(" - ")[0];
        double costo = Double.parseDouble(txtCosto.getText());
        consulta.agendarCita(listConsulta, numero, obtenerFecha(), obtenerHora(), codMascota, idPropietario, codEspecialidad, idVeterinario, txtMotivo.getText().trim(), costo, txtDiagnostico.getText().trim());
        actualizarTabla();
        limpiarCampos();
        generarNumeroConsulta();
    }

    // SEARCH CONSULTATION
    public void buscarConsultaEnTabla() {
        String entrada = JOptionPane.showInputDialog(this, "Digite el número de consulta:");
        if (entrada == null) {
            return;
        }

        if (!val.esNumEntero(entrada)) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido.");
            return;
        }

        int numero = Integer.parseInt(entrada.trim());
        int posicion = consulta.buscarConsulta(listConsulta, numero);
        if (posicion == -1) {
            JOptionPane.showMessageDialog(this, "Consulta no encontrada.");
            return;
        }

        for (int fila = 0; fila < modelTable.getRowCount(); fila++) {
            if (Integer.parseInt(modelTable.getValueAt(fila, 0).toString()) == numero) {
                tableConsulta.setRowSelectionInterval(fila, fila);
                return;
            }
        }
    }

    // MODIFY CONSULTATION
    public void modificarConsulta() {
        if (!revisarCampos()) {
            return;
        }

        int numero = Integer.parseInt(txtNumConsulta.getText());
        int posicion = consulta.buscarConsulta(listConsulta, numero);
        if (posicion == -1) {
            JOptionPane.showMessageDialog(this, "Consulta no encontrada.");
            return;
        }

        String idPropietario = cbxPropietario.getSelectedItem().toString().split(" - ")[0];
        String codMascota = cbxMascota.getSelectedItem().toString().split(" - ")[0];
        String codEspecialidad = cbxEspecialidad.getSelectedItem().toString().split(" - ")[0];
        String idVeterinario = cbxVeterinario.getSelectedItem().toString().split(" - ")[0];
        double costo = Double.parseDouble(txtCosto.getText());
        consulta.modificarConsulta(listConsulta, posicion, obtenerFecha(), obtenerHora(), codMascota, idPropietario, codEspecialidad, idVeterinario, txtMotivo.getText().trim(), costo, txtDiagnostico.getText().trim());
        actualizarTabla();
        limpiarCampos();
        generarNumeroConsulta();
    }

    // UPDATE TABLE
    public void actualizarTabla() {
        modelTable.setRowCount(0);
        for (ConsultaMedica c : listConsulta) {
            modelTable.addRow(new Object[]{
                    c.getNumConsulta(),
                    c.getFecha(),
                    c.getHora(),
                    buscarNombreMascota(c.getCodMascota()),
                    buscarNombrePropietario(c.getIdPropietario()),
                    buscarNombreEspecialidad(c.getCodEspecialidad()),
                    buscarNombreVeterinario(c.getIdVeterinario()),
                    c.getMotivo(),
                    c.getCosto(),
                    c.getDiagnostico()
            });
        }
    }

    // LOAD CONSULTATION DATA
    public void cargarDatosConsulta() {
        int fila = tableConsulta.getSelectedRow();

        if (fila == -1) {
            return;
        }
        actualizandoFormulario = true;
        int numero = Integer.parseInt(modelTable.getValueAt(fila, 0).toString());
        int posicion = consulta.buscarConsulta(listConsulta, numero);
        if (posicion == -1) {
            actualizandoFormulario = false;
            return;
        }

        ConsultaMedica c = listConsulta.get(posicion);
        txtNumConsulta.setText(
                String.valueOf(c.getNumConsulta()));

        // LOAD DATE
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = formatoFecha.parse(c.getFecha());
            spnFecha.setValue(fecha);
        } catch (ParseException ex) {
            spnFecha.setValue(new Date());
        }

        // LOAD TIME
        try {
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
            Date hora = formatoHora.parse(c.getHora());
            spnHora.setValue(hora);
        } catch (ParseException ex) {
            spnHora.setValue(new Date());
        }
        txtMotivo.setText(c.getMotivo());
        txtCosto.setText(String.valueOf(c.getCosto()));
        txtDiagnostico.setText(c.getDiagnostico());
        seleccionarCombo(cbxPropietario, c.getIdPropietario());
        cargarMascotas();
        seleccionarCombo(cbxMascota, c.getCodMascota());
        seleccionarCombo(cbxEspecialidad, c.getCodEspecialidad());
        cargarCosto();
        cargarVeterinarios();
        seleccionarCombo(cbxVeterinario, c.getIdVeterinario());
        actualizandoFormulario = false;
    }

    // SELECT COMBO BOX
    public void seleccionarCombo(JComboBox<String> combo, String codigo) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            String elemento = combo.getItemAt(i);
            if (elemento.startsWith(codigo + " - ")) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    // SEARCH PET NAME
    public String buscarNombreMascota(String codigo) {
        for (Mascota m : listMascota) {
            if (m.getCodMascota().equalsIgnoreCase(codigo)) {
                return m.getNomMascota();
            }
        }
        return "N/A";
    }

    // SEARCH OWNER NAME
    public String buscarNombrePropietario(String id) {
        for (Propietario p : listPropietario) {
            if (p.getIdPersona().equalsIgnoreCase(id)) {
                return p.getNomPersona();
            }
        }
        return "N/A";
    }

    // SEARCH SPECIALTY NAME
    public String buscarNombreEspecialidad(String codigo) {
        for (Especialidad e : listEspecialidad) {
            if (e.getCodEspecialidad().equalsIgnoreCase(codigo)) {
                return e.getNomEspecialidad();
            }
        }
        return "N/A";
    }

    // SEARCH VETERINARIAN NAME
    public String buscarNombreVeterinario(String id) {
        for (Veterinario v : listVeterinario) {
            if (v.getIdPersona().equalsIgnoreCase(id)) {
                return v.getNomPersona();
            }
        }
        return "N/A";
    }

    // CLEAR FIELDS
    public void limpiarCampos() {
        actualizandoFormulario = true;
        // Current date and time
        spnFecha.setValue(new Date());

        spnHora.setValue(new Date());

        txtMotivo.setText("");

        txtDiagnostico.setText("");
        tableConsulta.clearSelection();

        if (cbxPropietario.getItemCount() > 0) {
            cbxPropietario.setSelectedIndex(0);
        }


        if (cbxEspecialidad.getItemCount() > 0) {
            cbxEspecialidad.setSelectedIndex(0);
        }//end of if

        cargarMascotas();
        cargarCosto();
        cargarVeterinarios();
        actualizandoFormulario = false;
    }
}//end of class