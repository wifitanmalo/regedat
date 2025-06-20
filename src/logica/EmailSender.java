package logica;

// importaciones de util
import io.github.cdimascio.dotenv.Dotenv;
import java.io.UnsupportedEncodingException;

// importaciones de mail
import javax.mail.Authenticator;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

// importaciones de util
import java.util.Properties;

public class EmailSender {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String SMTP_HOST = dotenv.get("SMTP_HOST");
    private static final String SMTP_PORT = dotenv.get("SMTP_PORT");
    private static final String USERNAME = dotenv.get("SMTP_USERNAME");
    private static final String PASSWORD = dotenv.get("SMTP_PASSWORD");

    // metodo para enviar un correo electronico al usuario
    public static void sendEmail(String usuario, String asunto, String mensaje) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.localhost", "localhost");

        Session session = Session.getInstance(props, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try
        {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(USERNAME, "REGEDAT"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usuario, false));
            msg.setSubject(asunto);
            msg.setText(mensaje);
            msg.setSentDate(new java.util.Date());

            Transport.send(msg);
            System.out.println("Correo enviado a " + usuario);

        } catch (MessagingException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }
}