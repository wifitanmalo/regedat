package datos;

// importaciones de awt
import java.awt.Container;

// importaciones de swing
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// importaciones de SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaz.MenuInicio;
import interfaz.MenuMateria;
import logica.Carrera;
import logica.Materia;
import logica.Sistema;
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

    // metodo para obtener el id de una inscripcion
    public Integer obtenerIdInscripcion(int idMateria)
    {
        String consulta = "SELECT id FROM Inscripcion WHERE idEstudiante = ? AND idMateria = ?";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement inscripcion = conectar.prepareStatement(consulta))
        {
            inscripcion.setInt(1, MenuInicio.ESTUDIANTE_ACTUAL.getCodigo());
            inscripcion.setInt(2, idMateria);
            ResultSet resultado = inscripcion.executeQuery();
            if (resultado.next()) return resultado.getInt("id");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // metodo para obtener el puntaje total de una materia
    public double getPuntajeTotal(int idInscripcion, Container container)
    {
        String consulta = "SELECT COALESCE(SUM(valor * (porcentaje / 100.0)), 0) FROM Nota WHERE idInscripcion = ?";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement estado = conectar.prepareStatement(consulta))
        {
            estado.setInt(1, idInscripcion);
            ResultSet valor = estado.executeQuery();
            if (valor.next())
            {
                return valor.getDouble(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(container,
                    "Error al obtener el puntaje total.",
                    "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return 0.0;
    }


    // metodo para obtener la suma de los porcentajes de una materia
    public double getPorcentajeTotal(int idInscripcion, Container container)
    {
        String consulta = "SELECT COALESCE(SUM(porcentaje), 0) FROM Nota WHERE idInscripcion = ?";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement estado = conectar.prepareStatement(consulta))
        {
            estado.setInt(1, idInscripcion);
            ResultSet percentage = estado.executeQuery();
            if (percentage.next())
            {
                return percentage.getDouble(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(container,
                                        "Error al obtener el porcentaje.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
        return 0.0;
    }

    // metodo para verificar si una materia existe
    public static boolean materiaExiste(int idInscripcion, Container container)
    {
        // consulta de la materia
        String query = "SELECT 1 FROM Materia WHERE id = ?";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement materia = conectar.prepareStatement(query))
        {
            materia.setInt(1, idInscripcion);
            ResultSet resultado = materia.executeQuery();
            return resultado.next();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
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
               i.puntajeTotal, i.porcentajeTotal, i.id AS idIns
        FROM Inscripcion i
        JOIN Materia m ON i.idMateria = m.id
        WHERE i.idEstudiante = ?;
        """;

        try (Connection isConnected = Sistema.conectarDB();
             PreparedStatement statement = isConnected.prepareStatement(query))
        {
            statement.setInt(1, idEstudiante);
            ResultSet result = statement.executeQuery();

            CREDITOS_ACTUALES = 0;
            MenuMateria.PANEL_MATERIAS.removeAll();

            while (result.next())
            {
                int id = result.getInt("id");
                int idInscripcion = result.getInt("idIns");
                String nombre = result.getString("nombre");
                int creditos = result.getInt("creditos");
                double puntajeTotal = result.getDouble("puntajeTotal");
                double porcentajeEvaluado = result.getDouble("porcentajeTotal");

                Materia materia = new Materia(id, idInscripcion, nombre, creditos);
                materia.setPuntajeTotal(puntajeTotal);
                materia.setPorcentajeEvaluado(porcentajeEvaluado);

                PanelMateria panel = new PanelMateria(materia);
                panel.set_score_label(puntajeTotal);
                panel.set_evaluated_label(porcentajeEvaluado);

                Sistema.evaluarRiesgo(nombre, puntajeTotal, porcentajeEvaluado, panel, false);
                CREDITOS_ACTUALES += creditos;
                MenuMateria.PANEL_MATERIAS.add(panel);
            }
            // recarga el panel para mostrar los cambios
            WindowComponent.recargar(MenuMateria.PANEL_MATERIAS);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(contenedor,
                                        "Error al cargar las materias.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para crear una inscripcion en la base de datos
    public boolean inscribirMateria(int idInscripcion, Container contenedor)
    {
        // consultas
        String buscar = "SELECT 1 FROM Inscripcion WHERE idEstudiante = ? AND idInscripcion = ?";
        String insertar = "INSERT INTO Inscripcion (idEstudiante, idInscripcion) VALUES (?, ?)";

        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement inscripcion = conectar.prepareStatement(buscar))
        {
            // llaves foraneas de la inscripcion
            inscripcion.setInt(1, MenuInicio.ESTUDIANTE_ACTUAL.getCodigo());
            inscripcion.setInt(2, idInscripcion);
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
                insertStmt.setInt(1, MenuInicio.ESTUDIANTE_ACTUAL.getCodigo());
                insertStmt.setInt(2, idInscripcion);
                // ejecuta la consulta
                insertStmt.executeUpdate();
                return true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(contenedor,
                                        "Error durante la inscripción.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // metodo para eliminar la inscripcion a una materia
    public void eliminarMateria(int idInscripcion, Container contenedor)
    {
        // consulta de eliminación
        String query = "DELETE FROM Inscripcion WHERE idEstudiante = ? AND id = ?";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement eliminar = conectar.prepareStatement(query))
        {
            // llaveas foraneas de la inscripcion
            eliminar.setInt(1, MenuInicio.ESTUDIANTE_ACTUAL.getCodigo());
            eliminar.setInt(2, idInscripcion);
            // ejecuta la consulta
            eliminar.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(contenedor,
                                        "Error durante la eliminación.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para actualizar el puntaje/porcentaje total de una materia
    public void actualizarMateria(int idInscripcion, Container container)
    {
        String consulta = """
            UPDATE Inscripcion
            SET puntajeTotal = (SELECT COALESCE(SUM(valor * (porcentaje / 100.0)), 0) 
                                FROM Nota WHERE idInscripcion = ?),
                porcentajeTotal = (SELECT COALESCE(SUM(porcentaje), 0) 
                                   FROM Nota WHERE idInscripcion = ?)
            WHERE id = ?
        """;

        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement actualizar = conectar.prepareStatement(consulta))
        {
            // se verifica que la suma de los porcentajes no sea mayor que 100
            if (getPorcentajeTotal(idInscripcion, container) > 100.0)
            {
                WindowComponent.cuadroMensaje(container,
                        "La suma de los porcentajes es mayor que 100.",
                        "Error de límite",
                        JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                // asigna el ID de la inscripción paa las tres ocurrencias
                actualizar.setInt(1, idInscripcion);
                actualizar.setInt(2, idInscripcion);
                actualizar.setInt(3, idInscripcion);
                // ejecuta la consulta
                actualizar.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(container,
                                        "Error al actualizar la materia.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para cargar las carreras desde la base de datos
    public void cargarCarreras(JComboBox listadoCarreras, Carrera opcionTodas, Container contenedor)
    {
        // limpia todas las materias previas
        listadoCarreras.removeAllItems();
        // agrega la opción de todas las materias
        listadoCarreras.addItem(opcionTodas);

        String consulta = "SELECT id, nombre FROM Carrera ORDER BY nombre";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement estado = conectar.prepareStatement(consulta);
             ResultSet carrera = estado.executeQuery())
        {
            while (carrera.next())
            {
                int id = carrera.getInt("id");
                String nombre = carrera.getString("nombre");
                listadoCarreras.addItem(new Carrera(id, nombre));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contenedor,
                    "Error al cargar las carreras.",
                    "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para obtener todas las materias por carrera desde la base de datos
    public void listarMaterias(int idCarrera, DefaultTableModel modeloTabla, Container contenedor)
    {
        // Limpiar filas previas
        modeloTabla.setRowCount(0);

        String consultaTodas = "SELECT id, nombre FROM Materia ORDER BY nombre";
        String consultaPorCarrera = "SELECT m.id, m.nombre " +
                "FROM CarreraMateria cm " +
                "JOIN Materia m ON cm.idMateria = m.id " +
                "WHERE cm.idCarrera = ? " +
                "ORDER BY m.nombre";

        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement listado = idCarrera < 0
                     ? conectar.prepareStatement(consultaTodas)
                     : conectar.prepareStatement(consultaPorCarrera))
        {
            if (idCarrera >= 0)
            {
                listado.setInt(1, idCarrera);
            }
            try (ResultSet materia = listado.executeQuery())
            {
                while (materia.next())
                {
                    int codigo = materia.getInt("id");
                    String nombre = materia.getString("nombre");
                    modeloTabla.addRow(new Object[]{codigo, nombre});
                }
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contenedor,
                    "Error al cargar el listado de materias.",
                    "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}