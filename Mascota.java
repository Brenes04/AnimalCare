/*
 * Description: Represents pet information and the operations associated with its management.
 */

import javax.swing.*;
import java.util.List;

public class Mascota {

    //Attributes
    private String codMascota;
    private String nomMascota;
    private String especie;
    private String raza;
    private String sexo;
    private int edad;
    private String idPropietario;
    private boolean estado;

    // Empty constructor
    public Mascota() {}

    // Constructor with parameters
    public Mascota(String codMascota, String nomMascota, String especie, String raza, String sexo, int edad, String idPropietario, boolean estado) {
        this.codMascota = codMascota;
        this.nomMascota = nomMascota;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.edad = edad;
        this.idPropietario = idPropietario;
        this.estado = estado;
    }

    // ADD PET
    public void ingresarMascota(List<Mascota> listMascota, String codMascota, String nomMascota, String especie, String raza, String sexo, int edad, String idPropietario, boolean estado) {
        listMascota.add(new Mascota(codMascota,nomMascota, especie, raza, sexo, edad, idPropietario, estado));
        JOptionPane.showMessageDialog(null, "¡Mascota registrada correctamente!");
    }//end of add pet

    // SEARCH PET
    public int buscarMascota(List<Mascota> listMascota,String codMascota) {
        for (int i = 0; i < listMascota.size(); i++) {
            if (listMascota.get(i).getCodMascota().equals(codMascota)) {
                return i;
            }//end of if
        }//end of for loop
        return -1;
    }//end of search pet

    // MODIFY PET
    public void modificaMascota(List<Mascota> listMascota, String nomMascota, String especie, String raza, String sexo, int edad, String idPropietario, boolean estado, int pos) {
        listMascota.get(pos).setNomMascota(nomMascota);
        listMascota.get(pos).setEspecie(especie);
        listMascota.get(pos).setRaza(raza);
        listMascota.get(pos).setSexo(sexo);
        listMascota.get(pos).setEdad(edad);
        listMascota.get(pos).setIdPropietario(idPropietario);
        listMascota.get(pos).setEstado(estado);
        JOptionPane.showMessageDialog(null, "¡Mascota modificada!");
    }//END OF MODIFY PET

    // DEACTIVATE PET
    public void desactivarMascota(List<Mascota> list, int posicion) {
        list.get(posicion).setEstado(false);
        JOptionPane.showMessageDialog(null, "¡Mascota desactivada!");
    }//END OF DEACTIVATE PET

    //Recursively counts pets older than five years. This method is used by the reports and demonstrates recursion.
    public int contarMayoresDeCincoRecursivo(List<Mascota> lista, int indice) {
        //Every recursive method needs a stopping condition.
        if (lista == null || indice >= lista.size()) {
            return 0;
        }
        //Check the current pet
        int actual = lista.get(indice).getEdad() > 5 ? 1 : 0;
        //calls itself again.
        return actual + contarMayoresDeCincoRecursivo(lista, indice + 1);
    }

    // GETTERS AND SETTERS
    public String getCodMascota() {
        return codMascota;
    }
    public void setCodMascota(String codMascota) {
        this.codMascota = codMascota;
    }
    public String getNomMascota() {
        return nomMascota;
    }
    public void setNomMascota(String nomMascota) {
        this.nomMascota = nomMascota;
    }
    public String getEspecie() {
        return especie;
    }
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    public String getRaza() {
        return raza;
    }
    public void setRaza(String raza) {
        this.raza = raza;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getIdPropietario() {
        return idPropietario;
    }
    public void setIdPropietario(String idPropietario) {this.idPropietario = idPropietario;}
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    @Override
    public String toString() {
        return codMascota + " - " + nomMascota;
    }
}// end of class