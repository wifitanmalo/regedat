package logica;

public class Carrera
{
    // atributos
    private int id;
    private final String nombre;

    // constructor
    public Carrera(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    // setters and getters
    public void setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return nombre;
    }
}
