package logica;

import java.awt.*;
import java.security.MessageDigest;
import javax.swing.*;

// importaciones de SQL
import java.sql.*;

// importaciones de los paquetes
import interfaz.WindowComponent;
import datos.DatosEstudiante;
import datos.DatosMateria;
import datos.DatosNota;

public class Reporte
{
    // objetos para manejar los datos de la base de datos
    public static DatosEstudiante estudianteDAO;
    public static DatosMateria materiaDAO;
    public static DatosNota notaDAO;

    // constructor
    public Reporte()
    {
        this.estudianteDAO = new DatosEstudiante();
        this.materiaDAO = new DatosMateria();
        this.notaDAO = new DatosNota();
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
            e.printStackTrace();
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