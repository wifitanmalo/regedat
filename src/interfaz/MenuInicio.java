package interfaz;

// importaciones de awt
import java.awt.Color;
import java.awt.Container;

// importaciones de swing
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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

    // constructor
    public MenuInicio()
    {
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

        // agrega un panel con los campos de texto
        JPanel panelCampos = WindowComponent.setPanel(WindowComponent.FONDO_BOTON,
                                                    (this.getWidth()/2) - (300/2),
                                                    (this.getHeight()-210)/2,
                                                    300,
                                                    170);

        // agrega el boton de regresar
        JButton botonVolver = WindowComponent.setBoton("Volver",
                                                        panelCampos.getX()/4,
                                                        161,
                                                        78,
                                                        50,
                                                        WindowComponent.FONDO_GRIS);
        WindowComponent.configurarTexto(botonVolver,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        12);
        WindowComponent.eventoBoton(botonVolver,
                                    () ->
                                    {
                                        // cambia el panel al del menu principal
                                        WindowComponent.cambiarPanel(this, Main.principal);
                                        // limpia todos los campos de texto
                                        limpiarTodo();
                                    },
                                    WindowComponent.FONDO_GRIS,
                                    Color.decode("#AAAAAA"),
                                    Color.decode("#C7C8CA"));

        // agrega el boton de confirmacion
        JButton botonEntrar = WindowComponent.setBoton("Entrar",
                                                            WindowComponent.xPositivo(panelCampos, 29),
                                                            botonVolver.getY(),
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
        add(panelCampos);
        add(botonVolver);
        add(botonEntrar);

        // agrega los componentes al panel
        panelCampos.add(tituloCorreo);
        panelCampos.add(campoCodigo);
        panelCampos.add(tituloClave);
        panelCampos.add(campoClave);
    }

    // metodo para limpiar los campos de texto
    public void limpiarTodo()
    {
        WindowComponent.limpiarCampo(campoCodigo);
        WindowComponent.limpiarCampo(campoClave);
    }
}
