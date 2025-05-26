package logica;

public class Nota
{
    // atributos
    private String nombre;
    private double puntaje;
    private double porcentaje;
    private Materia materia;

    // constructor
    public Nota(String nombre,
                double puntaje,
                double porcentaje,
                Materia materia)
    {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.porcentaje = porcentaje;
        this.materia = materia;
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

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    public Materia getMateria() {
        return this.materia;
    }
}
