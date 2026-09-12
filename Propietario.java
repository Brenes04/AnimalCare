//Description: Represents pet owners and manages their data and status.

import javax.swing.*;
import java.util.List;
//Owner class inherits the general data from Person
public class Propietario extends Persona {
    //attribute
    private boolean estado;

    // Empty constructor
    public Propietario() {}

    // Constructor with parameters
    public Propietario(String idPersona, String nomPersona, String telPersona, String dirPersona, String emailPersona, boolean estado) {
        super(idPersona, nomPersona, telPersona, dirPersona, emailPersona);
        this.estado = estado;
    }//END OF CONSTRUCTOR

    // Print data
    @Override
    public void imprimeDatos() {
        System.out.println("ID: " + getIdPersona());
        System.out.println("Nombre: " + getNomPersona());
        System.out.println("Teléfono: " + getTelPersona());
        System.out.println("Dirección: " + getDirPersona());
        System.out.println("Email: " + getEmailPersona());
        System.out.println("Estado: " + (estado ? "Activo" : "Inactivo"));
    }//END OF PRINT DATA

    // Validate identification
    @Override
    public boolean validarIdentificacion() {
        return getIdPersona() != null && !getIdPersona().trim().isEmpty();
    }//END OF VALIDATION

    // Profile type
    @Override
    public String getTipoPerfil() {
        return "Propietario";
    }

    // Add owner
    public void ingresarPropietario(List<Propietario> list, String idPersona, String nombre,String telefono, String direccion, String email, boolean estado) {
        list.add(new Propietario(idPersona,nombre, telefono, direccion, email, estado));
        JOptionPane.showMessageDialog(null, "¡Propietario registrado!");
    }//END OF ADD OWNER

    // Search owner
    public int buscarPropietario(List<Propietario> list, String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdPersona().equals(id)) {
                return i;
            }
        }
        return -1;
    }//END OF SEARCH OWNER

    // Modify owner
    public void modificarPropietario(List<Propietario> list, String nombre, String telefono, String direccion, String email, boolean estado, int posicion) {
        Propietario p = list.get(posicion);
        p.setNomPersona(nombre);
        p.setTelPersona(telefono);
        p.setDirPersona(direccion);
        p.setEmailPersona(email);
        p.setEstado(estado);
        JOptionPane.showMessageDialog(null, "¡Propietario modificado exitosamente!");
    }//END OF MODIFY

    // Deactivate owner
    public void desactivarPropietario(List<Propietario> list, int posicion) {
        list.get(posicion).setEstado(false);
        JOptionPane.showMessageDialog(null, "¡Propietario desactivado!");
    }//END OF DEACTIVATE

    // STATUS GETTER AND SETTER
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return getIdPersona() + " - " + getNomPersona();
    }
}//END OF CLASS
