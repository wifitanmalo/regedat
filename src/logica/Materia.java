package logica;

public class Materia
{
    // atributos
    private int id;
    private int idInscripcion;
    private String nombre;
    private int creditos;

    // atributos de rendimiento
    private double puntajeTotal, porcentajeEvaluado;

    // constantes de los puntajes
    public static final double MINIMO_PUNTAJE = 3.0, MAXIMO_PUNTAJE = 5.0;

    // constructor
    public Materia(int id, int idInscripcion, String nombre, int creditos)
    {
        this.id = id;
        this.idInscripcion = idInscripcion;
        this.nombre = nombre;
        this.puntajeTotal = 0;
        this.porcentajeEvaluado = 0;
        this.creditos = creditos;
    }

    // setters y getters
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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

    public void setPuntajeTotal(double puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }
    public double getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPorcentajeEvaluado(double porcentajeEvaluado) {
        this.porcentajeEvaluado = porcentajeEvaluado;
    }
    public double getPorcentajeEvaluado() {
        return this.porcentajeEvaluado;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
    public int getCreditos() {
        return creditos;
    }
}