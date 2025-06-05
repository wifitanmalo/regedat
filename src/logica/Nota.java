package logica;

public class Nota
{
    // atributos
    private int id;
    private int idInscripcion;
    private String nombre;
    private double puntaje;
    private double porcentaje;

    // constructor
    public Nota(int id,
                int idInscripcion,
                String nombre,
                double puntaje,
                double porcentaje)
    {
        this.id = id;
        this.idInscripcion = idInscripcion;
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.porcentaje = porcentaje;
    }

    // setters y getters
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }
    public int getIdInscripcion() {
        return this.idInscripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }
    public double getPuntaje() {
        return this.puntaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
    public double getPorcentaje() {
        return this.porcentaje;
    }
}