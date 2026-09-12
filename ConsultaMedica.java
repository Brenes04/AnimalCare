//ANIMAL CARE PROJECT
//Description: Represents a medical consultation and contains operations to register, search, and modify consultations.

import javax.swing.*;
import java.util.List;

// One of the most important classes because it relates several parts of the system: pet, owner, specialty, and veterinarian.
public class ConsultaMedica {

    //Attributes
    private int numConsulta;
    private String fecha;
    private String hora;
    private String motivo;
    private double costo;
    private String diagnostico;

    //Relationship between classes
    private String codMascota;
    private String idPropietario;
    private String codEspecialidad;
    private String idVeterinario;

    //Empty constructor
    public ConsultaMedica() {}

    //Constructor with parameters
    public ConsultaMedica(int numConsulta, String fecha, String hora, String codMascota, String idPropietario, String codEspecialidad, String idVeterinario, String motivo, double costo, String diagnostico) {
        this.numConsulta = numConsulta;
        this.fecha = fecha;
        this.hora = hora;
        this.codMascota = codMascota;
        this.idPropietario = idPropietario;
        this.codEspecialidad = codEspecialidad;
        this.idVeterinario = idVeterinario;
        this.motivo = motivo;
        this.costo = costo;
        this.diagnostico = diagnostico;
    }

    // Add / Schedule
    public void agendarCita(List<ConsultaMedica> list, int numConsulta, String fecha, String hora, String codMascota, String idPropietario, String codEspecialidad, String idVeterinario, String motivo, double costo, String diagnostico) {
        list.add(new ConsultaMedica(numConsulta,fecha, hora, codMascota, idPropietario, codEspecialidad, idVeterinario, motivo, costo, diagnostico));
        JOptionPane.showMessageDialog(null, "¡Cita agendada!");
    }

    // Search
    public int buscarConsulta(List<ConsultaMedica> list, int numConsulta) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNumConsulta() == numConsulta) {
                return i;
            }
        }
        return -1;
    }

    // Modify
    public void modificarConsulta(List<ConsultaMedica> list, int posicion, String fecha, String hora, String codMascota, String idPropietario, String codEspecialidad, String idVeterinario, String motivo, double costo, String diagnostico) {
        ConsultaMedica c = list.get(posicion);
        c.setFecha(fecha);
        c.setHora(hora);
        c.setCodMascota(codMascota);
        c.setIdPropietario(idPropietario);
        c.setCodEspecialidad(codEspecialidad);
        c.setIdVeterinario(idVeterinario);
        c.setMotivo(motivo);
        c.setCosto(costo);
        c.setDiagnostico(diagnostico);
        JOptionPane.showMessageDialog(null, "Consulta modificada correctamente");
    }

    // GETTERS AND SETTERS

    public int getNumConsulta() {
        return numConsulta;
    }

    public void setNumConsulta(int numConsulta) {
        this.numConsulta = numConsulta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCodMascota() {
        return codMascota;
    }

    public void setCodMascota(String codMascota) {
        this.codMascota = codMascota;
    }

    public String getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(String idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getCodEspecialidad() {
        return codEspecialidad;
    }

    public void setCodEspecialidad(String codEspecialidad) {
        this.codEspecialidad = codEspecialidad;
    }

    public String getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(String idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
}
