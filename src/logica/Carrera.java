package logica;

public class Carrera
{
    // atributos
    private final int id;
    private final String nombre;

    // constructor
    public Carrera(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    // setters and getters
    public int getId() {
        return id;
    }

    @Override
    public String toString()
    {
        return nombre;
    }
}
