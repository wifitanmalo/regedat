package logica;

// importaciones de swing
import javax.swing.JOptionPane;

// importaciones de awt
import java.awt.Container;

// importaciones de security
import java.security.SecureRandom;

// importaciones de sql
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

// importaciones de time
import java.time.LocalDateTime;


public class OTP
{
    private static final SecureRandom rng = new SecureRandom();
    private static final int LONGITUD_OTP = 6;
    private static final int MINUTOS_EXPIRACION = 5;


    // metodo para generar un OTP y enviarlo al correo
    public static String generarOTP(int idEstudiante, Container contenedor)
    {
        // generar código numérico de 6 dígitos
        StringBuilder sb = new StringBuilder(LONGITUD_OTP);
        for (int i = 0; i < LONGITUD_OTP; i++)
        {
            sb.append(rng.nextInt(10));
        }
        String otp = sb.toString();

        // calcula la fecha de expiración
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(MINUTOS_EXPIRACION);

        String consulta = "INSERT INTO OTP (idEstudiante, codigo, expiracion) " +
                "VALUES (?, ?, ?) " +
                "ON CONFLICT (idEstudiante) DO UPDATE " +
                "SET codigo = EXCLUDED.codigo, expiracion = EXCLUDED.expiracion;";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement codigo = conectar.prepareStatement(consulta))
        {
            codigo.setInt(1, idEstudiante);
            codigo.setString(2, otp);
            codigo.setTimestamp(3, Timestamp.valueOf(expiracion));
            codigo.executeUpdate();
            return otp;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contenedor,
                                        "Error al generar el código de verificación.",
                                        "Database Error",
                                        JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    // metodo para enviar el correo con el OTP
    public static void enviarOTP(String otp, String email, Container contenedor)
    {
        String asunto = "Código de verificación";
        String cuerpo = String.format("Hola,\n\n" +
                                    "Tu código de verificación para cambiar la contraseña es: %s\n" +
                                    "Este código expirará en %d minutos.\n",
                                    otp,
                                    MINUTOS_EXPIRACION);
        try
        {
            EmailSender.sendEmail(email, asunto, cuerpo);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contenedor,
                                        "No se pudo enviar el correo con el OTP.",
                                        "Error de Email",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }


    // metodo para omprobar si el OTP es válido y no ha expirado
    public static boolean validarOTP(int idEstudiante, String otpIngresado, Container contenedor)
    {
        String sql = "SELECT codigo, expiracion FROM OTP WHERE idEstudiante = ?";
        try (Connection conectar = Sistema.conectarDB();
             PreparedStatement estado = conectar.prepareStatement(sql))
        {
            estado.setInt(1, idEstudiante);
            try (ResultSet codigo = estado.executeQuery())
            {
                if (!codigo.next())
                {
                    return false;  // no existe
                }
                String otpGuardado = codigo.getString("codigo");
                Timestamp tsExp = codigo.getTimestamp("expiracion");
                LocalDateTime expiracion = tsExp.toLocalDateTime();
                // comparar codigo
                if (!otpGuardado.equals(otpIngresado)) return false;
                // verificar expiracion
                if (LocalDateTime.now().isAfter(expiracion)) return false;
                return true;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contenedor,
                                        "No se pudo validar el código OTP.",
                                        "Database error",
                                        JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}