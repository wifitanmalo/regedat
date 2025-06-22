package interfaz;

// importaciones de awt
import java.awt.Color;

// importaciones de mail
import javax.mail.MessagingException;

// importaciones de swing
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// importaciones de paquete
import logica.EmailSender;
import logica.Estudiante;
import logica.OTP;
import logica.Sistema;

public class RecuperarClave extends JPanel
{
    // id del estudiante a recuperar
    public static int ID_ESTUDIANTE;

    // text boxes
    private JTextField campoOTP;
    private JPasswordField  campoClave;
    private JPasswordField  campoConfirmacion;

    // constructor
    public RecuperarClave()
    {
        inicializarPanel();
    }

    // method to initialize
    private void inicializarPanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FONDO_VENTANA);
        setBounds(0, 0, Main.ANCHO_VENTANA, Main.ALTURA_VENTANA);

        // crea el panel de los campos de texto
        JPanel campoPanel = WindowComponent.setPanel(WindowComponent.FONDO_BOTON,
                                                    (this.getWidth()/2) - (300/2),
                                                    (this.getHeight()-300)/2,
                                                    300,
                                                    260);

        // agrega el boton de regresar
        JButton botonVolver = WindowComponent.setBoton("Volver",
                                                        campoPanel.getX()/4,
                                                        161,
                                                        88,
                                                        50,
                                                        Color.decode("#8A9597"));
        WindowComponent.configurarTexto(botonVolver,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        14);
        WindowComponent.eventoBoton(botonVolver,
                                    () ->
                                    {
                                        WindowComponent.cambiarPanel(this, Main.MENU_PRINCIPAL);
                                        limpiarTodo();
                                    },
                                    Color.decode("#8A9597"),
                                    Color.decode("#AAAAAA"),
                                    Color.decode("#C7C8CA"));

        // agrega el boton de cambiar la clave
        JButton botonCambiar = WindowComponent.setBoton("Cambiar",
                                                        WindowComponent.xPositivo(campoPanel, 20),
                                                        botonVolver.getY(),
                                                        100,
                                                        50,
                                                        WindowComponent.FONDO_BOTON);
        WindowComponent.configurarTexto(botonCambiar,
                                            WindowComponent.COLOR_FUENTE,
                                            1,
                                            14);
        WindowComponent.eventoBoton(botonCambiar,
                                    () -> {
                                        try
                                        {
                                            String otp = campoOTP.getText();
                                            String clave = campoClave.getText();
                                            String confirmar = campoConfirmacion.getText();
                                            String hash = Sistema.hashSHA256(clave);
                                            if (!OTP.validarOTP(ID_ESTUDIANTE, otp, campoPanel))
                                            {
                                                WindowComponent.cuadroMensaje(campoPanel,
                                                                            "Código inválido.",
                                                                            "Error de autenticación",
                                                                            JOptionPane.ERROR_MESSAGE);
                                            }
                                            else if (!Sistema.compararClaves(clave, confirmar) )
                                            {
                                                WindowComponent.cuadroMensaje(campoPanel,
                                                                            "Las claves deben ser iguales.",
                                                                            "Error de autenticación",
                                                                            JOptionPane.ERROR_MESSAGE);
                                            }
                                            else if (clave.length() < 8)
                                            {
                                                WindowComponent.cuadroMensaje(campoPanel,
                                                                            "La clave debe ser mayor a 8 carácteres.",
                                                                            "Clave muy corta",
                                                                            JOptionPane.ERROR_MESSAGE);
                                            }
                                            else if (clave.length() > 64)
                                            {
                                                WindowComponent.cuadroMensaje(campoPanel,
                                                                            "La clave no debe exceder los 64 carácteres.",
                                                                            "Clave muy larga",
                                                                            JOptionPane.ERROR_MESSAGE);
                                            }
                                            else if (!Sistema.validarClave(clave))
                                            {
                                                WindowComponent.cuadroMensaje(campoPanel,
                                                                            "La clave debe tener una mayúscula, una minúscula, un número y un simbolo.",
                                                                            "Clave poco segura",
                                                                            JOptionPane.ERROR_MESSAGE);
                                            }
                                            else
                                            {
                                                // actualiza la clave en la base de datos
                                                Sistema.estudianteDAO.actualizarClave(ID_ESTUDIANTE, hash, campoPanel);
                                                WindowComponent.cuadroMensaje(campoPanel,
                                                                            "Clave actualizada con éxito.",
                                                                            "Recuperacion exitosa",
                                                                            JOptionPane.INFORMATION_MESSAGE);
                                                // envia la notificacion al correo electronico
                                                Estudiante estudiante = Sistema.estudianteDAO.obtenerEstudiante(ID_ESTUDIANTE);
                                                try
                                                {
                                                    EmailSender.sendEmail(estudiante.getCorreo(),
                                                                            "Clave actualizada",
                                                                            String.format("Hola, %s. La clave de tu cuenta %d ha sido actualizada con éxito.",
                                                                            estudiante.getNombres(),
                                                                            estudiante.getCodigo()));
                                                }
                                                catch (MessagingException e)
                                                {
                                                    throw new RuntimeException(e);
                                                }
                                                // regresa al menu principal
                                                WindowComponent.cambiarPanel(this, Main.MENU_PRINCIPAL);
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                            WindowComponent.cuadroMensaje(campoPanel,
                                                                        "Error al obtener el hash de las claves.",
                                                                        "Hash error",
                                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                        limpiarTodo();
                                    },
                                    WindowComponent.FONDO_BOTON,
                                    WindowComponent.FONDO_SOBRE_BOTON,
                                    WindowComponent.FONDO_PRESIONAR_BOTON);

        // titulo del codigo OTP
        JLabel tituloOTP = WindowComponent.setTexto("Código OTP",
                                                    (campoPanel.getWidth()-250)/2,
                                                    20,
                                                    250,
                                                    22);
        WindowComponent.configurarTexto(tituloOTP,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloOTP));

        // campo de texto del codigo OTP
        campoOTP = WindowComponent.setCampoTexto(tituloOTP.getX(),
                                                WindowComponent.yNegativo(tituloOTP,5),
                                                250,
                                                30);
        WindowComponent.configurarTexto(campoOTP,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // titulo de la clave
        JLabel tituloClave = WindowComponent.setTexto("Nueva clave",
                                                    tituloOTP.getX(),
                                                    WindowComponent.yNegativo(campoOTP,20),
                                                    250,
                                                    22);
        WindowComponent.configurarTexto(tituloClave,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloClave));

        // campo de texto del nombre
        campoClave = WindowComponent.setCampoClave(tituloOTP.getX(),
                                                    WindowComponent.yNegativo(tituloClave,5),
                                                    250,
                                                    30);
        WindowComponent.configurarTexto(campoClave,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // titulo de confirmar la clave
        JLabel tituloConfirmar = WindowComponent.setTexto("Confirmar clave",
                                                tituloClave.getX(),
                                                WindowComponent.yNegativo(campoClave,20),
                                                250,
                                                22);
        WindowComponent.configurarTexto(tituloConfirmar,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloConfirmar));

        // campo de texto para confirmar la clave
        campoConfirmacion = WindowComponent.setCampoClave(tituloConfirmar.getX(),
                                                        WindowComponent.yNegativo(tituloConfirmar,5),
                                                        250,
                                                        30);
        WindowComponent.configurarTexto(campoConfirmacion,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // agrega los componentes al contenedor
        add(campoPanel);
        add(botonVolver);
        add(botonCambiar);

        // agrega los componentes a este panel
        campoPanel.add(tituloOTP);
        campoPanel.add(campoOTP);
        campoPanel.add(tituloClave);
        campoPanel.add(campoClave);
        campoPanel.add(tituloConfirmar);
        campoPanel.add(campoConfirmacion);
    }

    // metodo para limpiar las cajas de texto
    public void limpiarTodo()
    {
        WindowComponent.limpiarCampo(campoOTP);
        WindowComponent.limpiarCampo(campoClave);
        WindowComponent.limpiarCampo(campoConfirmacion);
    }
}
