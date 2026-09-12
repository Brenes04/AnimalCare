
 //Description: Base class that contains the common data of people registered in the system.

//inherited by Veterinarian and Owner
public abstract class Persona {

    //Attributes
    private String idPersona;
    private String nomPersona;
    private String telPersona;
    private String dirPersona;
    private String emailPersona;

    // Empty constructor
    public Persona() {
    }

    // Constructor with parameters
    public Persona(String idPersona, String nomPersona, String telPersona, String dirPersona, String emailPersona) {
        this.idPersona = idPersona;
        this.nomPersona = nomPersona;
        this.telPersona = telPersona;
        this.dirPersona = dirPersona;
        this.emailPersona = emailPersona;
    }//END OF CONSTRUCTOR

    // Abstract methods
    public abstract void imprimeDatos();

    public abstract boolean validarIdentificacion();

    public abstract String getTipoPerfil();

    // Validate data
    public boolean validarDatosCompletos(Validacion validacion) {

        return validacion.esCamRequerido(idPersona)
                && validacion.esCamRequerido(nomPersona)
                && validacion.esTelefonoValido(telPersona)
                && validacion.esCamRequerido(dirPersona)
                && validacion.esCorreoValido(emailPersona);
    }

    // GETTERS AND SETTERS

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNomPersona() {
        return nomPersona;
    }

    public void setNomPersona(String nomPersona) {
        this.nomPersona = nomPersona;
    }

    public String getTelPersona() {
        return telPersona;
    }

    public void setTelPersona(String telPersona) {
        this.telPersona = telPersona;
    }

    public String getDirPersona() {
        return dirPersona;
    }

    public void setDirPersona(String dirPersona) {
        this.dirPersona = dirPersona;
    }

    public String getEmailPersona() {
        return emailPersona;
    }

    public void setEmailPersona(String emailPersona) {
        this.emailPersona = emailPersona;
    }
}
