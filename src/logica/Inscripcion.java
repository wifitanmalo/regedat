package logica;

import datos.DatosEstudiante;
import interfaz.WindowComponent;
import datos.DatosMateria;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;

import java.sql.*;

public class Inscripcion
{
    // objeto para manejar los datos de las materias
    public static DatosMateria materiaDAO;
    // objeto para manejar los datos de los estudiantes
    public static DatosEstudiante estudianteDAO;

    // constructor
    public Inscripcion()
    {
        this.materiaDAO = new DatosMateria();
        this.estudianteDAO = new DatosEstudiante();
    }

    // metodo para encriptar una clave ingresada
    public static String hashSHA256(String clave) throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(clave.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // metodo para validar la conexion de la base de datos
    public static Connection conectarDB() throws SQLException
    {
            String url = "jdbc:postgresql://localhost:5432/regedat";
            String user = "postgres";
            String password = "uwu2704";
            return DriverManager.getConnection(url, user, password);
    }

    // metodo para validar el inicio de sesion de un estudiante
    public static boolean loginEstudiante(Container contenedor,
                                          JTextField codigo,
                                          JTextField contrasena)
    {
        try (Connection conn = conectarDB())
        {
            int id = Integer.parseInt(codigo.getText().trim());
            String hashContrasena = hashSHA256(contrasena.getText());

            String query = "SELECT * FROM Estudiante WHERE codigo = ? AND claveHash = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.setString(2, hashContrasena);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true si encontró el estudiante

        }
        catch (NumberFormatException e)
        {
            WindowComponent.cuadroMensaje(contenedor,
                                        "ID debe ser un entero.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}