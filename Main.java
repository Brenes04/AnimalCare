/*
 * ANIMAL CARE PROJECT
 * Description: Contains the program entry point and starts the Animal Care application.
 */

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Main system dynamic lists
        List<Propietario> listPropietario = new ArrayList<>();
        List<Mascota> listMascota = new ArrayList<>();
        List<Especialidad> listEspecialidad = new ArrayList<>();
        List<Veterinario> listVeterinario = new ArrayList<>();
        List<ConsultaMedica> listConsulta = new ArrayList<>();

        // Specialties initially available at the veterinary clinic
        listEspecialidad.add(new Especialidad("ESP01", "Medicina General", 15000));
        listEspecialidad.add(new Especialidad("ESP02", "Cirugía", 30000));
        listEspecialidad.add(new Especialidad("ESP03", "Dermatología", 20000));
        listEspecialidad.add(new Especialidad("ESP04", "Odontología", 18000));
        listEspecialidad.add(new Especialidad("ESP05", "Castración", 25000));

        // Open main menu
        frmMenuPrincipal menu = new frmMenuPrincipal(listPropietario, listMascota, listEspecialidad, listVeterinario, listConsulta);
        menu.setVisible(true);
    }
}