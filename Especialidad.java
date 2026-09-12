/*
 * ANIMAL CARE PROJECT
 * Description: Represents veterinary specialties and manages their main operations.
 */

import javax.swing.*;
import java.util.List;

public class Especialidad {

    //Attributes
    private String codEspecialidad;
    private String nomEspecialidad;
    private double costoBase;

    //Empty constructor
    public Especialidad() {}

    //Constructor with parameters
    public Especialidad(String codEspecialidad, String nomEspecialidad, double costoBase) {
        this.codEspecialidad = codEspecialidad;
        this.nomEspecialidad = nomEspecialidad;
        this.costoBase = costoBase;
    }

    // Add
    public void ingresarEspecialidad(List<Especialidad> list, String codigo, String nombre, double costo) {
        list.add(new Especialidad(codigo, nombre, costo));
        JOptionPane.showMessageDialog(null, "¡Especialidad agregada!");
    }

    // Search
    public int buscarEspecialidad(List<Especialidad> list, String codigo) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCodEspecialidad().equalsIgnoreCase(codigo)) {
                return i;
            }
        }
        return -1;
    }

    // Modify
    public void modificaEspecialidad(List<Especialidad> list, String nombre, double costo, int posicion) {
        list.get(posicion).setNomEspecialidad(nombre);
        list.get(posicion).setCostoBase(costo);
        JOptionPane.showMessageDialog(null, "¡Especialidad modificada!");
    }

    // Delete
    public void eliminaEspecialidad(List<Especialidad> list, int posicion) {
        list.remove(posicion);
        JOptionPane.showMessageDialog(null, "¡Especialidad eliminada!");
    }

    // GETTERS AND SETTERS
    public String getCodEspecialidad() {
        return codEspecialidad;
    }
    public void setCodEspecialidad(String codEspecialidad) {
        this.codEspecialidad = codEspecialidad;
    }
    public String getNomEspecialidad() {
        return nomEspecialidad;
    }
    public void setNomEspecialidad(String nomEspecialidad) {
        this.nomEspecialidad = nomEspecialidad;
    }
    public double getCostoBase() {
        return costoBase;
    }
    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }
    @Override
    public String toString() {
        return codEspecialidad + " - " + nomEspecialidad + " ($" + costoBase + ")";
    }
}//end of class
