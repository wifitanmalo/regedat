package interfaz;

import java.awt.*;
import javax.swing.*;

import logica.Estudiante;
import logica.Inscripcion;

public class MenuInicio extends JPanel
{
    // objeto del estudiante actual
    public static Estudiante estudiante;

    // campos de texto
    private JTextField campoCodigo;
    private JPasswordField campoClave;

    // objeto del contenedor
    private final Container contenedor;

    // objeto del menu de las materias
    public static MenuMateria materia;

    // constructor
    public MenuInicio()
    {
        this.materia = new MenuMateria();

        materia.setVisible(false);

        this.contenedor = WindowComponent.getContenedor();
        contenedor.add(materia);

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
                                        WindowComponent.cambiarPanel(this, Main.principal);
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
                                        if (Inscripcion.loginEstudiante(this,
                                                                    campoCodigo,
                                                                    campoClave))
                                        {
                                            this.estudiante = Inscripcion.estudianteDAO.obtenerEstudiante(Integer.parseInt(campoCodigo.getText().trim()));
                                            Inscripcion.materiaDAO.cargarMaterias(this,
                                                                                estudiante.getCodigo());
                                            WindowComponent.cambiarPanel(this, materia);
                                            limpiarTodo();
                                        }
                                        else
                                        {
                                            WindowComponent.cuadroMensaje(contenedor,
                                                                        "ID/Contraseña incorrecta.",
                                                                        "Error de autenticacion",
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

    // metodo para limpiar las cajas de texto
    public void limpiarTodo()
    {
        WindowComponent.limpiarCampo(campoCodigo);
        WindowComponent.limpiarCampo(campoClave);
    }
}
