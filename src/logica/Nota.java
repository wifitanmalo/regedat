package logica;

public class Nota {
    // atributos
    private double puntaje;
    private double porcentaje;
    private double puntajeCalculado;

    // constructor
    public Nota(double puntaje, double porcentaje) {
        this.puntaje = puntaje;
        this.porcentaje = porcentaje;
        this.puntajeCalculado = puntaje * (porcentaje/100);
    }

    // setters y getters
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

    public void setPuntajeCalculado(double puntaje) {
        this.puntajeCalculado = puntaje;
    }
    public double getPuntajeCalculado() {
        return this.puntajeCalculado;
    }
}
