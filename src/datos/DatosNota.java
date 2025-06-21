package datos;

// awt import
import java.awt.Container;

// sql imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// swing imports
import javax.swing.JPanel;
import javax.swing.JOptionPane;

// package imports
import interfaz.PanelNota;
import interfaz.WindowComponent;
import logica.Sistema;
import logica.Materia;
import logica.Nota;

public class DatosNota
{
    // constructor
    public DatosNota()
    {

    }

    // metodo para cargar las notas de una materia desde la base de datos
    public void cargarNotas(Materia materia, JPanel panelNotas)
    {
        String consulta = "SELECT * FROM Nota WHERE idInscripcion = ?";
        try (Connection conectado = Sistema.conectarDB();
             PreparedStatement estado = conectado.prepareStatement(consulta))
        {
            estado.setInt(1, Sistema.materiaDAO.obtenerIdInscripcion(materia.getId()));
            ResultSet notaActual = estado.executeQuery();
            // limpia el panel de las notas
            panelNotas.removeAll();
            while (notaActual.next())
            {
                int id = notaActual.getInt("id");
                int idInscripcion = notaActual.getInt("idInscripcion");
                String nombre = notaActual.getString("nombre");
                double valor = notaActual.getDouble("valor");
                double porcentaje = notaActual.getDouble("porcentaje");

                // crea una nueva nota con toda la información
                Nota nuevaNota = new Nota(id, idInscripcion, nombre, valor, porcentaje);

                // crea el panel con la nota creada
                PanelNota nuevoPanel = new PanelNota(materia, nuevaNota, panelNotas);
                nuevoPanel.setTextoPuntaje(String.valueOf(valor));
                nuevoPanel.setTextoPorcentaje(String.valueOf(porcentaje));
                nuevoPanel.setValorNota(valor*(porcentaje/100.0));
            }
            // recarga el panel para mostrar los cambios
            WindowComponent.recargar(panelNotas);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(panelNotas,
                                        "Error al cargar la base de datos.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para crear una nota en la base de datos
    public void crearNota(int idInscripcion, String nombre, Container container)
    {
        String consulta = "INSERT INTO Nota(idInscripcion, nombre) VALUES(?, ?)";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement crear = conectar.prepareStatement(consulta))
        {
            crear.setInt(1, idInscripcion);
            crear.setString(2, nombre);
            crear.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(container,
                                        "Error durante la creación.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to delete a grade in the database
    public void eliminarNota(Nota nota, Container contenedor)
    {
        String query = "DELETE FROM Nota WHERE id = ? AND idInscripcion = ?";
        try (Connection conectado = Sistema.conectarDB();
             PreparedStatement eliminar = conectado.prepareStatement(query))
        {
            eliminar.setInt(1, nota.getId());
            eliminar.setInt(2, nota.getIdInscripcion());
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

    // metodo para eliminar todas las notas de una materia
    public void eliminarTodo(int idInscripcion, Container contenedor)
    {
        String query = "DELETE FROM Nota WHERE idInscripcion = ?";
        try (Connection conectado = Sistema.conectarDB();
             PreparedStatement eliminar = conectado.prepareStatement(query))
        {
            eliminar.setInt(1, idInscripcion);
            eliminar.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(contenedor,
                                        "Error al eliminar las notas.",
                                        "Data error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para actualizar una nota en la base de datos
    public void actualizarPuntaje(Nota nota, double newScore, Container container)
    {
        String consulta = "UPDATE Nota SET valor = ? WHERE id = ? AND idInscripcion = ?";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement actualizar = conectar.prepareStatement(consulta))
        {
            // atributos de la nota
            actualizar.setDouble(1, newScore);
            actualizar.setInt(2, nota.getId());
            actualizar.setInt(3, nota.getIdInscripcion());
            // realiza la consulta
            actualizar.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(container,
                                        "Error al actualizar la nota.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to update the percentage of a grade
    public void actualizarPorcentaje(Nota nota, double nuevoPorcentaje, Container contenedor)
    {
        String consulta = "UPDATE Nota SET porcentaje = ? WHERE id = ? AND idInscripcion = ?";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement actualizar = conectar.prepareStatement(consulta))
        {
            // atributos de la nota
            actualizar.setDouble(1, nuevoPorcentaje);
            actualizar.setInt(2, nota.getId());
            actualizar.setInt(3, nota.getIdInscripcion());
            // realiza la consulta
            actualizar.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(contenedor,
                                        "Error al actualizar la nota.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }
}