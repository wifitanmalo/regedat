package interfaz;

// importaciones de awt
import java.awt.*;

// importaciones de swing
import javax.swing.*;

// importaciones de los paquetes
import logica.Estudiante;
import logica.Reporte;

public class MenuInicio extends JPanel
{
    // objeto del estudiante actual
    public static Estudiante ESTUDIANTE_ACTUAL;

    // campos de texto
    private JTextField campoCodigo;
    private JPasswordField campoClave;

    // objeto del contenedor
    private final Container contenedor;

    // objeto del menu de las materias
    public static MenuMateria menuMateria;

    // objeto del reporte
    public static Reporte reporte;

    // constructor
    public MenuInicio()
    {
        this.reporte = new Reporte();
        this.menuMateria = new MenuMateria();
        menuMateria.setVisible(false);
        this.contenedor = WindowComponent.getContenedor();
        contenedor.add(menuMateria);
        iniciarPanel();
    }

    // metodo para inicializar el panel
    private void iniciarPanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FONDO_VENTANA);
        setBounds(0, 0, Main.ANCHO_VENTANA, Main.ALTURA_VENTANA);

        // agrega el boton para mostrar la informacion del sistema
        JButton botonInformacion = WindowComponent.setBoton("i",
                Main.ANCHO_VENTANA -80,
                20,
                50,
                50,
                WindowComponent.FONDO_GRIS);
        WindowComponent.configurarTexto(botonInformacion,
                WindowComponent.COLOR_FUENTE,
                1,
                20);
        WindowComponent.eventoBoton(botonInformacion,
                () ->
                {
                    System.out.println("mostrar informacion");
                },
                WindowComponent.FONDO_GRIS,
                Color.decode("#91BAD6"),
                Color.decode("#528AAE"));

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
                                        if (Reporte.loginEstudiante(this,
                                                                    campoCodigo,
                                                                    campoClave))
                                        {
                                            // asigna el estudiante actual a la variable estática
                                            ESTUDIANTE_ACTUAL = Reporte.estudianteDAO.obtenerEstudiante(Integer.parseInt(campoCodigo.getText().trim()));
                                            // carga las materias del estudiante desde la base de datos
                                            Reporte.materiaDAO.cargarMaterias(this, ESTUDIANTE_ACTUAL.getCodigo());
                                            // entra al menu de las materias
                                            WindowComponent.cambiarPanel(this, menuMateria);
                                            // limpia los campos de texto
                                            limpiarTodo();
                                        }
                                        else
                                        {
                                            WindowComponent.cuadroMensaje(contenedor,
                                                                        "ID/Contraseña incorrecta.",
                                                                        "Error de autenticación",
                                                                        JOptionPane.ERROR_MESSAGE);
                                        }
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
        add(botonInformacion);
        add(panelCampos);
        add(logo);
        add(botonEntrar);

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
}
