/*
 * ANIMAL CARE PROJECT
 * File: frmMenuPrincipal.java
 * Description: Main window that provides access to the different system modules.
 *
 * Documentation added solely for academic and maintenance purposes.
 * The logic, instructions, and behavior of the original code were not modified.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class frmMenuPrincipal extends JFrame {

    //form components
    public JPanel panelMenu;

    private JButton btnPropietario;
    private JButton btnMascota;
    private JButton btnEspecialidad;
    private JButton btnVeterinario;
    private JButton btnAgendarCita;
    private JButton btnReporte;
    private JButton btnSalir;

    // LISTS
    private List<Propietario> listPropietario;
    private List<Mascota> listMascota;
    private List<Especialidad> listEspecialidad;
    private List<Veterinario> listVeterinario;
    private List<ConsultaMedica> listConsulta;

    //Constructor
    public frmMenuPrincipal(List<Propietario> listPropietario, List<Mascota> listMascota, List<Especialidad> listEspecialidad, List<Veterinario> listVeterinario, List<ConsultaMedica> listConsulta) {
        this.listPropietario = listPropietario;
        this.listMascota = listMascota;
        this.listEspecialidad = listEspecialidad;
        this.listVeterinario = listVeterinario;
        this.listConsulta = listConsulta;

        //Window configuration
        setContentPane(panelMenu);
        AnimalCareTheme.apply(this, panelMenu);
        setTitle("Animal Care | Sistema de Gestión Veterinaria");
        setSize(620, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // OWNER button
        btnPropietario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmPropietario ventana = new frmPropietario(listPropietario);
                ventana.setVisible(true);
            }
        });

        // PET
        btnMascota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmMascota ventana = new frmMascota(listMascota, listPropietario);
                ventana.setVisible(true);
            }
        });

        // SPECIALTY
        btnEspecialidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmEspecialidad ventana = new frmEspecialidad(listEspecialidad);
                ventana.setVisible(true);
            }
        });

        // VETERINARIAN
        btnVeterinario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmVeterinario ventana = new frmVeterinario(listVeterinario, listEspecialidad);
                ventana.setVisible(true);
            }
        });

        // SCHEDULE APPOINTMENT
        btnAgendarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmConsultaMedica ventana = new frmConsultaMedica(listConsulta, listPropietario, listMascota, listEspecialidad, listVeterinario);
                ventana.setVisible(true);
            }
        });

        // REPORT
        btnReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmReporte ventana = new frmReporte(listConsulta, listMascota, listPropietario, listVeterinario, listEspecialidad);
                ventana.setVisible(true);
            }
        });

        // EXIT
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }//end of constructor

    //background image
    private void createUIComponents() {
        panelMenu = new PanelConFondo();
    }
}//end of class
