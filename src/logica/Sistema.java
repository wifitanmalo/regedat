package logica;

// importaciones de awt
import java.awt.Color;
import java.awt.Container;

// importaciones de security
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

// importaciones de SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// importaciones de swing
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// importaciones de util

// importaciones de los paquetes
import interfaz.MenuMateria;
import interfaz.PanelMateria;
import interfaz.WindowComponent;
import datos.DatosEstudiante;
import datos.DatosMateria;
import datos.DatosNota;

public class Sistema
{
    // objetos para manejar los datos de la base de datos
    public static DatosEstudiante estudianteDAO;
    public static DatosMateria materiaDAO;
    public static DatosNota notaDAO;

    // constructor
    public Sistema()
    {
        this.estudianteDAO = new DatosEstudiante();
        this.materiaDAO = new DatosMateria();
        this.notaDAO = new DatosNota();
    }

    // metodo para encriptar una clave ingresada
    public static String hashSHA256(String clave) throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(clave.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // metodo para comprobar si una clave es lo suficientemente segura
    public static boolean validarClave(String clave)
    {
        boolean tieneMayuscula = clave.matches(".*[A-Z].*");
        boolean tieneMinuscula = clave.matches(".*[a-z].*");
        boolean tieneNumero = clave.matches(".*[0-9].*");
        boolean tieneSimbolo = clave.matches(".*[^a-zA-Z0-9].*");
        return tieneMayuscula && tieneMinuscula && tieneNumero && tieneSimbolo;
    }

    // metodo para comparar dos claves y verificar si son iguales
    public static boolean compararClaves(String clave1, String clave2)
    {
        try
        {
            String hash1 = hashSHA256(clave1);
            String hash2 = hashSHA256(clave2);
            return hash1.equals(hash2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
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
            String hashClave = hashSHA256(contrasena.getText());

            String query = "SELECT * FROM Estudiante WHERE codigo = ? AND claveHash = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.setString(2, hashClave);

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

    // metodo para evaluar si una materia se encuentra en riesgo o no
    public static void evaluarRiesgo(double puntajeActual,
                                    double porcentajeEvaluado,
                                    PanelMateria panel,
                                    boolean popUps
    )
    {
        if (puntajeActual >= Materia.MINIMO_PUNTAJE) // se ha aprobado la materia
        {
            if (popUps) {
                WindowComponent.cuadroMensaje(
                        MenuMateria.PANEL_MATERIAS,
                        "¡Has aprobado la materia!",
                        "¡Felicitaciones!",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
            panel.setBackground(Color.decode("#C5EF48")); // panel verde limon
            panel.setTextoColor(Color.decode("#64820A")); // texto verde limon oscuro
            return;
        }

        // calcula cuánto se necesita en el restante para llegar al minimo aprobatorio
        double notaRestante = calcularNotaRestante(puntajeActual, porcentajeEvaluado, Materia.MINIMO_PUNTAJE);

        // se ha perdido la materia
        if (notaRestante > Materia.MAXIMO_PUNTAJE)
        {
            if (popUps)
            {
                WindowComponent.cuadroMensaje(
                        MenuMateria.PANEL_MATERIAS,
                        "Has perdido la materia",
                        "Fin de la línea",
                        JOptionPane.WARNING_MESSAGE
                );
            }
            panel.setBackground(Color.decode("#FF746C")); // panel rojo pastel
            panel.setTextoColor(Color.decode("#BA2820")); // texto rojo
        }
        else if (notaRestante >= (Materia.MAXIMO_PUNTAJE*(0.85)) ) // riesgo alto, necesita una nota casi perfecta para aprobar
        {
            if (popUps)
            {
                String mensaje = String.format(
                        "Necesitas %.2f en el resto de la materia para aprobar.",
                        notaRestante
                );
                WindowComponent.cuadroMensaje(
                                                MenuMateria.PANEL_MATERIAS,
                                                mensaje,
                                                "Riesgo alto",
                                                JOptionPane.WARNING_MESSAGE
                );
            }
            panel.setBackground(Color.decode("#FFB347")); // panel naranja pastel
            panel.setTextoColor(Color.decode("#E86100")); // texto naranja fuerte
        }
        else if (notaRestante >= Materia.MINIMO_PUNTAJE) // riesgo medio, necesita una nota promedio para aprobar
        {
            if (popUps)
            {
                String mensaje = String.format(
                        "Necesitas %.2f en el resto de la materia para aprobar.",
                        notaRestante
                );
                WindowComponent.cuadroMensaje(
                        MenuMateria.PANEL_MATERIAS,
                        mensaje,
                        "Riesgo medio",
                        JOptionPane.WARNING_MESSAGE
                );
            }
            panel.setBackground(Color.decode("#FDFD96")); // panel amarillo pastel
            panel.setTextoColor(Color.decode("#5E5E00")); // texto amarillo oscuro
        }
        else
        {
            if (popUps) // sin riesgo, necesita muy poquita nota para aprobar
            {
                String mensaje = String.format(
                        "Necesitas %.2f en el resto de la materia para aprobar.",
                        notaRestante
                );
                WindowComponent.cuadroMensaje(
                        MenuMateria.PANEL_MATERIAS,
                        mensaje,
                        "Sin riesgo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
            panel.setBackground(Color.LIGHT_GRAY); // panel gris claro
            panel.setTextoColor(Color.decode("#515151")); // texto gris
        }
    }

    // metodo para calcular cuánto hay que sacar en el resto de la materia para aprobarla
    public static double calcularNotaRestante(double puntajeActual,
                                                double porcentajeEvaluado,
                                                double minAprobatorio
    ) {
        // 1) Si ya completó el 100%:
        if (porcentajeEvaluado >= 100.0) {
            return (puntajeActual >= minAprobatorio) ? 0.0 : Double.POSITIVE_INFINITY;
        }
        // 2) Si ya está por encima del mínimo antes de terminar:
        if (puntajeActual >= minAprobatorio) {
            return 0.0;
        }
        // 3) Cálculo del porcentaje restante
        double porcentajeRestante = 100.0 - porcentajeEvaluado;
        // 4) Despejar notaRestante
        double notaRestante = (minAprobatorio - puntajeActual) * 100.0 / porcentajeRestante;
        return Math.max(notaRestante, 0.0);
    }
}