package datos;

import java.awt.Container;

// importaciones de Swing
import javax.swing.JOptionPane;

// importaciones de SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaz.MenuInicio;
import interfaz.MenuMateria;
import logica.Materia;
import logica.Inscripcion;
import interfaz.PanelMateria;
import interfaz.WindowComponent;

public class DatosMateria
{
    // constantes de los creditos
    public static int CREDITOS_ACTUALES = 0, MAXIMO_CREDITOS = 21;

    // constructor
    public DatosMateria()
    {

    }

    // metodo para verificar si una materia existe
    public static boolean materiaExiste(int idMateria, Container container)
    {
        // consulta de la materia
        String query = "SELECT 1 FROM Materia WHERE id = ?";
        try (Connection conectar = Inscripcion.conectarDB();
             PreparedStatement materia = conectar.prepareStatement(query))
        {
            materia.setInt(1, idMateria);
            ResultSet resultado = materia.executeQuery();
            return resultado.next();
        }
        catch (SQLException e)
        {
            WindowComponent.cuadroMensaje(container,
                                        "Error comprobando las materias.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // metodo para cargar las materias de un estudiante desde la base de datos
    public void cargarMaterias(Container contenedor, int idEstudiante)
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
            WindowComponent.cuadroMensaje(contenedor,
                                        "Error cargando las materias.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para crear una inscripcion en la base de datos
    public boolean inscribirMateria(int idMateria, Container contenedor)
    {
        // consultas
        String buscar = "SELECT 1 FROM Inscripcion WHERE idEstudiante = ? AND idMateria = ?";
        String insertar = "INSERT INTO Inscripcion (idEstudiante, idMateria) VALUES (?, ?)";

        try (Connection conectar = Inscripcion.conectarDB();
             PreparedStatement inscripcion = conectar.prepareStatement(buscar))
        {
            // llaves foraneas de la inscripcion
            inscripcion.setInt(1, MenuInicio.estudiante.getCodigo());
            inscripcion.setInt(2, idMateria);
            // ejecuta la consulta
            ResultSet resultado = inscripcion.executeQuery();

            // verifica si el estudiante se encuentra inscrito a la materia
            if (resultado.next())
            {
                WindowComponent.cuadroMensaje(contenedor,
                                            "Ya estás inscrito a esta materia.",
                                            "Inscripción duplicada",
                                            JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // inserta la inscripción en caso de no estar inscrito
            try (PreparedStatement insertStmt = conectar.prepareStatement(insertar))
            {
                // llaveas foraneas de la inscripcion
                insertStmt.setInt(1, MenuInicio.estudiante.getCodigo());
                insertStmt.setInt(2, idMateria);
                // ejecuta la consulta
                insertStmt.executeUpdate();
                return true;
            }
        }
        catch (SQLException e)
        {
            WindowComponent.cuadroMensaje(contenedor,
                                        "Error durante la inscripción.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // metodo para eliminar la inscripcion a una materia
    public void eliminarMateria(int idMateria, Container contenedor)
    {
        // consulta de eliminación
        String query = "DELETE FROM Inscripcion WHERE idEstudiante = ? AND idMateria = ?";
        try (Connection conectar = Inscripcion.conectarDB();
             PreparedStatement eliminar = conectar.prepareStatement(query))
        {
            // llaveas foraneas de la inscripcion
            eliminar.setInt(1, MenuInicio.estudiante.getCodigo());
            eliminar.setInt(2, idMateria);
            // ejecuta la consulta
            eliminar.executeUpdate();
        }
        catch (SQLException e)
        {
            WindowComponent.cuadroMensaje(contenedor,
                                        "Error durante la eliminación.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }
}