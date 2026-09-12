/*
 * ANIMAL CARE PROJECT
 * File: Validacion.java
 * Description: Centralizes the validations used by the forms to check entered data.
 *
 * Documentation added solely for academic and maintenance purposes.
 * The logic, instructions, and behavior of the original code were not modified.
 */

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Centralized validation rules used by all forms.
 */
public class Validacion {

    //Email regular expression
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    //Phone regular expression
    private static final String PHONE_REGEX =
            "^[0-9]{8}$";

    //Date regex
    private static final String DATE_REGEX =
            "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

    //Time regex
    private static final String TIME_REGEX =
            "^([01]\\d|2[0-3]):[0-5]\\d$";

    public boolean esCamRequerido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    //Integer number
    public boolean esNumEntero(String texto) {
        if (!esCamRequerido(texto)) {
            return false;
        }
        try {
            Integer.parseInt(texto.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Decimal number
    public boolean esNumeroDecimal(String texto) {
        if (!esCamRequerido(texto)) {
            return false;
        }
        try {
            double numero = Double.parseDouble(texto.trim());
            return Double.isFinite(numero) && numero >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Valid email
    public boolean esCorreoValido(String correo) {
        return esCamRequerido(correo)
                && Pattern.matches(EMAIL_REGEX, correo.trim());
    }

    //Valid phone number
    public boolean esTelefonoValido(String telefono) {
        return esCamRequerido(telefono)
                && Pattern.matches(PHONE_REGEX, telefono.trim());
    }


     //Validates both the required yyyy-MM-dd format and the real calendar date.
    public boolean esFechaValida(String fecha) {
        if (!esCamRequerido(fecha)
                || !Pattern.matches(DATE_REGEX, fecha.trim())) {
            return false;
        }
        try {
            LocalDate.parse(fecha.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean esHoraValida(String hora) {
        return esCamRequerido(hora)
                && Pattern.matches(TIME_REGEX, hora.trim());
    }

    public boolean esPropietarioDuplicado(List<Propietario> lista, String id) {
        if (lista == null || id == null) {
            return false;
        }
        for (Propietario p : lista) {
            if (p.getIdPersona().equalsIgnoreCase(id.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean esMascotaDuplicada(List<Mascota> lista, String codigo) {
        if (lista == null || codigo == null) {
            return false;
        }
        for (Mascota m : lista) {
            if (m.getCodMascota().equalsIgnoreCase(codigo.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean esVeterinarioDuplicado(List<Veterinario> lista, String id) {
        if (lista == null || id == null) {
            return false;
        }
        for (Veterinario v : lista) {
            if (v.getIdPersona().equalsIgnoreCase(id.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean esEspecialidadDuplicada(List<Especialidad> lista, String codigo) {
        if (lista == null || codigo == null) {
            return false;
        }
        for (Especialidad e : lista) {
            if (e.getCodEspecialidad().equalsIgnoreCase(codigo.trim())) {
                return true;
            }
        }
        return false;
    }
}//end of class
