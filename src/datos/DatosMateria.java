package datos;

import logica.Materia;
import java.util.ArrayList;

public class DatosMateria
{
    //
    public static int MAXIMO_CREDITOS = 21;
    public static int CREDITOS_ACTUALES = 0;

    // lista de materias matriculadas
    private ArrayList<Materia> listaMaterias;

    public DatosMateria() {
        this.listaMaterias = new ArrayList<>();
    }

    // metodo para agregar una materia a la lista
    public void crearMateria(Materia materia) {
        listaMaterias.add(materia);
        CREDITOS_ACTUALES += materia.getCreditos();
    }

    // metodo para eliminar una materia de la lista
    public void eliminarMateria(Materia materia) {
        listaMaterias.remove(materia);
        CREDITOS_ACTUALES -= materia.getCreditos();
    }

    // setters y getters
    public void setListaMaterias(ArrayList<Materia> listaMaterias)
    {
        this.listaMaterias = listaMaterias;
    }
    public ArrayList<Materia> getListaMaterias()
    {
        return listaMaterias;
    }
}
