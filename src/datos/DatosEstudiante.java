package datos;

// importaciones de SQL
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaz.WindowComponent;
import logica.Sistema;
import logica.Estudiante;

import javax.swing.*;

public class DatosEstudiante
{
    // metodo para verificar si un estudiante existe en la base de datos
    public boolean estudianteExiste(int codigoEstudiante, Container container)
    {
        String query = "SELECT 1 FROM Estudiante WHERE codigo = ?";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement estudiante = conectar.prepareStatement(query))
        {
            estudiante.setInt(1, codigoEstudiante);
            ResultSet resultado = estudiante.executeQuery();
            return resultado.next();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(container,
                                        "Error comprobando el estudiante.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // metodo para obtener la informacion de un estudiante
    public Estudiante obtenerEstudiante(int codigoBuscado)
    {
        String query = "SELECT * FROM Estudiante WHERE codigo = ?";

        try (Connection connection = Sistema.conectarDB();
             PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, codigoBuscado);
            ResultSet result = statement.executeQuery();

            if (result.next())
            {
                int codigo = result.getInt("codigo");
                String nombres = result.getString("nombres");
                String apellidos = result.getString("apellidos");
                String correo = result.getString("correo");
                String claveHash = result.getString("claveHash");
                String telefono = result.getString("telefono");

                return new Estudiante(codigo, nombres, apellidos, correo, claveHash, telefono);
            }
            else
            {
                return null; // estudiante no encontrado
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    // metodo para actualizar la clave de un estudiante
    public void actualizarClave(int idEstudiante, String nuevaClave, Container container)
    {
        String consulta = "UPDATE Estudiante SET claveHash = ? WHERE codigo = ?";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement actualizar = conectar.prepareStatement(consulta))
        {
            // atributos de la nota
            actualizar.setString(1, nuevaClave);
            actualizar.setInt(2, idEstudiante);
            // realiza la consulta
            actualizar.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            WindowComponent.cuadroMensaje(container,
                    "Error al actualizar la clave.",
                    "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}