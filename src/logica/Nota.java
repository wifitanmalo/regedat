package logica;

public class Nota
{
    // atributos
    private String nombre;
    private double puntaje;
    private double porcentaje;
    private int idMateria;

    // constructor
    public Nota(String nombre,
                double puntaje,
                double porcentaje,
                int materia)
    {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.porcentaje = porcentaje;
        this.idMateria = materia;
    }

    // setters y getters
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

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }
    public int getIdMateria() {
        return this.idMateria;
    }
}
