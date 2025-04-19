package interfaz;

import logica.Materia;
import logica.Nota;

public class Main {
    public static void main(String[] args)
    {
        // materia con sus respectivas notas
        Materia calculo = new Materia(111021, "Calculo monovariable", 3);
        Nota t1 = new Nota(5, 10);
        Nota t2 = new Nota(4.2, 10);
        Nota t3 = new Nota(5, 10);
        Nota p1 = new Nota(3.4, 30);
        Nota p2 = new Nota(1.4, 20);
        Nota p3 = new Nota(0.5, 20);

        // agrega las notas a la materia
        calculo.agregarNota(t1);
        calculo.agregarNota(t2);
        calculo.agregarNota(t3);
        calculo.agregarNota(p1);
        calculo.agregarNota(p2);
        calculo.agregarNota(p3);

        // calcula el total de las notas obtenidas
        calculo.calcularPuntaje();

        // imprime la informacion de la materia
        System.out.println("Asignatura: " + calculo.getNombre());
        System.out.println("- Puntaje obtenido: " + calculo.getPuntajeTotal());
    }
}