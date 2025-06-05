package datos;

import java.awt.Container;

// importaciones de Swing
import javax.swing.JOptionPane;

// importaciones de SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaz.MenuMateria;
import logica.Materia;
import logica.Inscripcion;
import interfaz.PanelMateria;
import interfaz.WindowComponent;
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

    // metodo para cargar las materias de un estudiante desde la base de datos
    public void cargarMaterias(Container container, int idEstudiante)
    {
        String query = """
        SELECT m.id, m.nombre, m.creditos,
               i.puntajeTotal, i.porcentajeTotal
        FROM Inscripcion i
        JOIN Materia m ON i.idMateria = m.id
        WHERE i.idEstudiante = ?;
        """;

        try (Connection isConnected = Inscripcion.conectarDB();
             PreparedStatement statement = isConnected.prepareStatement(query))
        {
            statement.setInt(1, idEstudiante);
            ResultSet result = statement.executeQuery();

            CREDITOS_ACTUALES = 0;
            MenuMateria.panelMaterias.removeAll();

            while (result.next())
            {
                int id = result.getInt("id");
                String nombre = result.getString("nombre");
                int creditos = result.getInt("creditos");
                double puntajeTotal = result.getDouble("puntajeTotal");
                double porcentajeEvaluado = result.getDouble("porcentajeTotal");

                Materia materia = new Materia(id, nombre, creditos);
                materia.setPuntajeTotal(puntajeTotal);
                materia.setPorcentajeEvaluado(porcentajeEvaluado);

                PanelMateria panel = new PanelMateria(materia);
                panel.set_score_label(puntajeTotal);
                panel.set_evaluated_label(porcentajeEvaluado);

                CREDITOS_ACTUALES += creditos;
                MenuMateria.panelMaterias.add(panel);
            }
            // recarga el panel para mostrar los cambios
            WindowComponent.recargar(MenuMateria.panelMaterias);
        }
        catch (SQLException e)
        {
            WindowComponent.cuadroMensaje(container,
                                        "Error cargando las materias.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
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
