package logica;

public class Estudiante
{
    private int codigo;
    private String nombres;
    private String apellidos;
    private String correo;
    private String claveHash;
    private String telefono;

    public Estudiante (int codigo,
                       String nombres,
                       String apellidos,
                       String correo,
                       String claveHash,
                       String telefono)
    {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.claveHash = claveHash;
        this.telefono = telefono;
    }

    // setters y getters
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public int getCodigo() {
        return codigo;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getNombres() {
        return nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getApellidos() {
        return apellidos;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getCorreo() {
        return correo;
    }

    public void setClaveHash(String claveHash) {
        this.claveHash = claveHash;
    }
    public String getClaveHash() {
        return claveHash;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }
    public String getTelefono() {
        return telefono;
    }
}