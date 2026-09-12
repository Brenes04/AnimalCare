
//Description: Represents veterinarians and manages their data, experience, specialty, and status.

import javax.swing.*;
import java.util.List;

//Veterinarian class inherits the general data from Person and adds veterinarian-specific data: years of experience, specialty, and status.
public class Veterinario extends Persona {

    //own attributes
    private int aniosExperiencia; // Number of years of professional experience the veterinarian has.
    private String codEspecialidad; // Code of the specialty to which the veterinarian belongs.
    private boolean estado; // true = active, false = inactive.

    // Empty constructor.
    public Veterinario() {}

    // Constructor with parameters.
    public Veterinario(String idPersona, String nomPersona, String telPersona,String dirPersona, String emailPersona, int aniosExperiencia, String codEspecialidad, boolean estado) {
        super(idPersona, nomPersona, telPersona, dirPersona, emailPersona);

        this.aniosExperiencia = aniosExperiencia;
        this.codEspecialidad = codEspecialidad;
        this.estado = estado;
    }//

    // Method overloading
    @Override
    public void imprimeDatos() {
        System.out.println("ID Vet: " + getIdPersona() + " | Nombre: " + getNomPersona() + " | Experiencia: " + aniosExperiencia + " años" + " | Especialidad: " + codEspecialidad);
    }

    // Validates that the identification is not empty.
    @Override
    public boolean validarIdentificacion() {
        return getIdPersona() != null && !getIdPersona().trim().isEmpty();
    }

    // Returns the object profile type.
    //polymorphism, because the same method can produce a different result depending on the object.
    @Override
    public String getTipoPerfil() {
        return "Veterinario";
    }

    //Adds a veterinarian to the list.
    public void ingresarVeterinario(List<Veterinario> list, String idPersona, String nomPersona, String telPersona, String dirPersona, String emailPersona, int aniosExperiencia, String codEspecialidad, boolean estado) {
        list.add(new Veterinario(idPersona, nomPersona, telPersona, dirPersona, emailPersona, aniosExperiencia, codEspecialidad, estado));
        JOptionPane.showMessageDialog(null, "¡Veterinario registrado!");
    }

    //Searches for a veterinarian by identification. Returns the position if found or -1 if not found.
    public int buscarVeterinario(List<Veterinario> list, String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdPersona().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    //Modifies the data of an existing veterinarian.
    public void modificaVeterinario(List<Veterinario> list, String nombre, String telefono, String direccion, String correo, int aniosExperiencia, String codEspecialidad, boolean estado, int posicion) {
        Veterinario v = list.get(posicion);
        v.setNomPersona(nombre);
        v.setTelPersona(telefono);
        v.setDirPersona(direccion);
        v.setEmailPersona(correo);
        v.setAniosExperiencia(aniosExperiencia);
        v.setCodEspecialidad(codEspecialidad);
        v.setEstado(estado);

        JOptionPane.showMessageDialog(null, "¡Veterinario modificado!");
    }

    //Deactivates a veterinarian without removing it from the list.
    public void desactivarVeterinario(List<Veterinario> list, int posicion) {
        list.get(posicion).setEstado(false);
        JOptionPane.showMessageDialog(null, "¡Veterinario desactivado!");
    }

    // Getters and setters.
    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getCodEspecialidad() {
        return codEspecialidad;
    }

    public void setCodEspecialidad(String codEspecialidad) {
        this.codEspecialidad = codEspecialidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return getNomPersona() + " - " + aniosExperiencia + " años";
    }
}
