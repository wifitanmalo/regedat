package datos;

// importaciones de SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logica.Inscripcion;
import logica.Estudiante;

public class DatosEstudiante
{
    public Estudiante obtenerEstudiante(int codigoBuscado)
    {
        String query = "SELECT * FROM Estudiante WHERE codigo = ?";

        try (Connection connection = Inscripcion.conectarDB();
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
            System.err.println("Error al obtener estudiante: " + e.getMessage());
            return null;
        }
    }

}
