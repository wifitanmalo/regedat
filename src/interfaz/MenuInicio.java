package interfaz;

// importaciones de awt
import java.awt.*;

// importaciones de swing
import javax.swing.*;

// importaciones de los paquetes
import datos.DatosEstudiante;
import logica.Estudiante;
import logica.OTP;
import logica.Sistema;

public class MenuInicio extends JPanel
{
    // objeto del estudiante actual
    public static Estudiante ESTUDIANTE_ACTUAL;

    // campos de texto
    private JTextField campoCodigo;
    private JPasswordField campoClave;

    // objeto del contenedor
    private final Container contenedor;

    // objeto del menu recuperar clave
    private final RecuperarClave menuRecuperar;

    // objeto del menu de las materias
    public static MenuMateria MENU_MATERIA;

    // objeto del reporte
    public static Sistema sistema;

    // constructor
    public MenuInicio()
    {
        this.sistema = new Sistema();
        this.MENU_MATERIA = new MenuMateria();
        MENU_MATERIA.setVisible(false);
        this.menuRecuperar = new RecuperarClave();
        menuRecuperar.setVisible(false);
        this.contenedor = WindowComponent.getContenedor();
        contenedor.add(MENU_MATERIA);
        contenedor.add(menuRecuperar);
        iniciarPanel();
    }

    // metodo para inicializar el panel
    private void iniciarPanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FONDO_VENTANA);
        setBounds(0, 0, Main.ANCHO_VENTANA, Main.ALTURA_VENTANA);

        // agrega un panel con los campos de texto
        JPanel panelCampos = WindowComponent.setPanel(WindowComponent.FONDO_BOTON,
                                                    (this.getWidth()/2) - (300/2),
                                                    180,
                                                    300,
                                                    170);

        ImageIcon icon = new ImageIcon("logoUnivalle.jpg");
        Image imagen = icon.getImage().getScaledInstance(93, 140, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(imagen));
        logo.setBounds( (Main.ANCHO_VENTANA-113)/2,
                WindowComponent.yPositivo(panelCampos, 1),
                113,
                160);

        // agrega el boton de confirmacion
        JButton botonEntrar = WindowComponent.setBoton("Entrar",
                                                            WindowComponent.xPositivo(panelCampos, 29),
                                                            panelCampos.getY(),
                                                            78,
                                                            50,
                                                            WindowComponent.FONDO_BOTON);
        WindowComponent.configurarTexto(botonEntrar,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        12);
        WindowComponent.eventoBoton(botonEntrar,
                                    () ->
                                    {
                                        // valida si la información del usuario es correcta
                                        if (Sistema.loginEstudiante(this,
                                                                    campoCodigo,
                                                                    campoClave))
                                        {
                                            // asigna el estudiante actual a la variable estática
                                            ESTUDIANTE_ACTUAL = Sistema.estudianteDAO.obtenerEstudiante(Integer.parseInt(campoCodigo.getText().trim()));
                                            // carga las materias del estudiante desde la base de datos
                                            Sistema.materiaDAO.cargarMaterias(this, ESTUDIANTE_ACTUAL.getCodigo());
                                            // entra al menu de las materias
                                            WindowComponent.cambiarPanel(this, MENU_MATERIA);
                                            // limpia los campos de texto
                                            limpiarTodo();
                                        }
                                        else
                                        {
                                            WindowComponent.cuadroMensaje(contenedor,
                                                                        "ID/Contraseña incorrecta.",
                                                                        "Error de autenticación",
                                                                        JOptionPane.ERROR_MESSAGE);
                                            limpiarTodo();
                                        }
                                    },
                                    WindowComponent.FONDO_BOTON,
                                    WindowComponent.FONDO_SOBRE_BOTON,
                                    WindowComponent.FONDO_PRESIONAR_BOTON);
        // agrega el boton de confirmacion
        JButton botonRecuperar = WindowComponent.setBoton("Recuperar",
                                                            botonEntrar.getX(),
                                                            WindowComponent.yNegativo(botonEntrar, 10),
                                                            78,
                                                            50,
                                                            WindowComponent.FONDO_BOTON);
        WindowComponent.configurarTexto(botonRecuperar,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        10);
        WindowComponent.eventoBoton(botonRecuperar,
                                    () ->
                                    {
                                        // genera el cuadro para ingresar el id del estudiante
                                        cuadroCodigo(this);
                                    },
                                    WindowComponent.FONDO_BOTON,
                                    WindowComponent.FONDO_SOBRE_BOTON,
                                    WindowComponent.FONDO_PRESIONAR_BOTON);

        // titulo del correo
        JLabel tituloCorreo = WindowComponent.setTexto("Código",
                                                        (panelCampos.getWidth()-260)/2,
                                                        20,
                                                        260,
                                                        22);
        WindowComponent.configurarTexto(tituloCorreo,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloCorreo));

        // campo de texto del correo
        campoCodigo = WindowComponent.setCampoTexto(tituloCorreo.getX(),
                                                    WindowComponent.yNegativo(tituloCorreo,5),
                                                    260,
                                                    30);

        WindowComponent.configurarTexto(campoCodigo,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // titulo de la clave
        JLabel tituloClave = WindowComponent.setTexto("Contraseña",
                                                    (panelCampos.getWidth()-260)/2,
                                                    WindowComponent.yNegativo(campoCodigo,10),
                                                    260,
                                                    22);
        WindowComponent.configurarTexto(tituloClave,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloClave));

        // campo de texto del correo
        campoClave = WindowComponent.setCampoClave(tituloClave.getX(),
                                                    WindowComponent.yNegativo(tituloClave,5),
                                                    260,
                                                    30);
        WindowComponent.configurarTexto(campoClave,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // agrega los componentes al contenedor
        add(panelCampos);
        add(logo);
        add(botonEntrar);
        add(botonRecuperar);

        // agrega los componentes al panel
        panelCampos.add(tituloCorreo);
        panelCampos.add(campoCodigo);
        panelCampos.add(tituloClave);
        panelCampos.add(campoClave);

        contenedor.add(this);
    }

    // metodo para limpiar los campos de texto
    public void limpiarTodo()
    {
        WindowComponent.limpiarCampo(campoCodigo);
        WindowComponent.limpiarCampo(campoClave);
    }

    // metodo para inicializar el cuadro interactivo para ingresar el codigo de la materia
    public void cuadroCodigo(Component contenedor)
    {
        Window ventana = SwingUtilities.getWindowAncestor(contenedor);
        JDialog dialogo = new JDialog(ventana, "Ingresar código", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(250, 100);
        dialogo.setLayout(new FlowLayout());
        dialogo.setLocationRelativeTo(contenedor);

        JTextField campoTexto = new JTextField(8);
        WindowComponent.configurarTexto(campoTexto, Color.decode("#3D3D3D"), 1, 14);
        JButton inscribir = new JButton("Vale");

        inscribir.addActionListener(e -> {
            try
            {
                int id = Integer.parseInt(campoTexto.getText().trim());
                if (Sistema.estudianteDAO.estudianteExiste(id, dialogo))
                {
                    // actualiza el id al del estudiante a recuperar
                    RecuperarClave.ID_ESTUDIANTE = id;
                    // envia el codigo OTP al correo electronico
                    OTP.enviarOTP(OTP.generarOTP(id, dialogo),
                                    Sistema.estudianteDAO.obtenerEstudiante(id).getCorreo(),
                                    dialogo);
                    WindowComponent.cuadroMensaje(dialogo,
                                                    "Código OTP enviado a tu correo.",
                                                    "OTP generado",
                                                    JOptionPane.INFORMATION_MESSAGE);
                    // cierra el cuadro de dialogo
                    dialogo.dispose();
                    // entra al menu de recuperar clave
                    WindowComponent.cambiarPanel(this, menuRecuperar);
                }
                else
                {
                    WindowComponent.cuadroMensaje(dialogo,
                                                "El estudiante no existe.",
                                                "Error de entrada",
                                                JOptionPane.ERROR_MESSAGE);
                    campoTexto.setText("");
                }
            }
            catch (NumberFormatException nfe)
            {
                nfe.getStackTrace();
                WindowComponent.cuadroMensaje(dialogo,
                                            "Solo puedes ingresar números enteros.",
                                            "Error de entrada",
                                            JOptionPane.ERROR_MESSAGE);
                campoTexto.setText("");
            }
        });

        dialogo.add(new JLabel("Código: "));
        dialogo.add(campoTexto);
        dialogo.add(inscribir);
        dialogo.setVisible(true);
    }
}