package interfaz;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class MenuInicio extends JPanel
{
    // campos de texto
    private JTextField campoCorreo;
    private JTextField campoClave;

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
                                        System.out.println("volver a inicio");
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
                                    () -> {
                                            WindowComponent.cambiarPanel(this, materia);
                                            System.out.println("usuario ingresado al sistema");
                                            limpiarTodo();
                                    },
                                    WindowComponent.FONDO_BOTON,
                                    WindowComponent.FONDO_SOBRE_BOTON,
                                    WindowComponent.FONDO_PRESIONAR_BOTON);

        // titulo del correo
        JLabel tituloCorreo = WindowComponent.setTexto("Correo",
                                                        (panelCampos.getWidth()-260)/2,
                                                        20,
                                                        260,
                                                        22);
        WindowComponent.configurarTexto(tituloCorreo,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloCorreo));

        // campo de texto del correo
        campoCorreo = WindowComponent.setCampoTexto(tituloCorreo.getX(),
                                                    WindowComponent.yNegativo(tituloCorreo,5),
                                                    260,
                                                    30);
        WindowComponent.configurarTexto(campoCorreo,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // titulo de la clave
        JLabel tituloClave = WindowComponent.setTexto("Contraseña",
                                                    (panelCampos.getWidth()-260)/2,
                                                    WindowComponent.yNegativo(campoCorreo,10),
                                                    260,
                                                    22);
        WindowComponent.configurarTexto(tituloClave,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloClave));

        // campo de texto del correo
        campoClave = WindowComponent.setCampoTexto(tituloClave.getX(),
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
        panelCampos.add(campoCorreo);
        panelCampos.add(tituloClave);
        panelCampos.add(campoClave);
    }

    // metodo para limpiar las cajas de texto
    public void limpiarTodo()
    {
        WindowComponent.limpiarCampo(campoCorreo);
        WindowComponent.limpiarCampo(campoClave);
    }
}
