package logica;

import java.util.ArrayList;

public class Materia
{
    // atributos
    private int id;
    private String nombre;
    private int creditos;

    // atributo de rendimiento
    private double puntajeTotal;

    // constantes de los puntajes
    public static final double MINIMO_PUNTAJE = 3.0;
    public static final double MAXIMO_PUNTAJE = 5.0;

    // lista de notas
    private ArrayList<Nota> listaNotas;

    // constructor
    public Materia(int id, String nombre, int creditos)
    {
        this.id = id;
        this.nombre = nombre;
        this.puntajeTotal = 0;
        this.creditos = creditos;
        this.listaNotas = new ArrayList<>();
    }

    // metodo para agregar una calificacion
    public void agregarNota(Nota nota)
    {
        listaNotas.add(nota);
    }

    // metodo para calcular el puntaje total
    public void calcularPuntaje()
    {
        this.puntajeTotal = 0;
        for (Nota nota : listaNotas)
        {
            this.puntajeTotal += nota.getPuntaje() * (nota.getPorcentaje()/100);
        }
    }

    // setters y getters
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
    public int getCreditos() {
        return creditos;
    }

    public void setListaNotas(ArrayList<Nota> listaNotas) {
        this.listaNotas = listaNotas;
    }
    public ArrayList<Nota> getListaNotas() {
        return listaNotas;
    }
}
