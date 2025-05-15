package interfaz;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JButton;

public class menuPrincipal extends JPanel
{
    // objetos de los menu
    private final menuInicio inicio;
    private final menuRegistro registro;

    // objeto del contenedor
    private final Container contenedor;

    // constructor
    public menuPrincipal()
    {
        this.registro = new menuRegistro();
        this.inicio = new menuInicio();

        inicio.setVisible(false);
        registro.setVisible(false);

        this.contenedor = WindowComponent.getContenedor();
        contenedor.add(inicio);
        contenedor.add(registro);

        iniciarPanel();
    }

    // metodo para inicializar el panel
    private void iniciarPanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FONDO_VENTANA);
        setBounds(0, 0, Main.ANCHO_VENTANA, Main.ALTURA_VENTANA);

        // agrega el boton para iniciar sesion
        JButton botonIniciar = WindowComponent.setBoton("Entrar",
                                                    Main.ANCHO_VENTANA /3,
                                                    161,
                                                    98,
                                                    50,
                                                    Color.decode("#8A9597"));
        WindowComponent.configurarTexto(botonIniciar,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        12);
        WindowComponent.eventoBoton(botonIniciar,
                                    () ->
                                    {
                                        WindowComponent.cambiarPanel(this, inicio);
                                        System.out.println("iniciar sesion");
                                    },
                                    Color.decode("#8A9597"),
                                    Color.decode("#AAAAAA"),
                                    Color.decode("#C7C8CA"));

        // agrega el boton para iniciar sesion
        JButton botonRegistro = WindowComponent.setBoton("Registrar",
                                                        WindowComponent.xPositivo(botonIniciar, 10),
                                                        botonIniciar.getY(),
                                                        98,
                                                        50,
                                                        WindowComponent.FONDO_BOTON);
        WindowComponent.configurarTexto(botonRegistro,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        12);
        WindowComponent.eventoBoton(botonRegistro,
                                    () ->
                                    {
                                        WindowComponent.cambiarPanel(this, registro);
                                        System.out.println("registrar usuario");
                                    },
                                    WindowComponent.FONDO_BOTON,
                                    WindowComponent.FONDO_SOBRE_BOTON,
                                    WindowComponent.FONDO_PRESIONAR_BOTON);

        // agrega el boton para mostrar la informacion del sistema
        JButton botonInformacion = WindowComponent.setBoton("i",
                                                        Main.ANCHO_VENTANA -80,
                                                        20,
                                                        50,
                                                        50,
                                                        Color.decode("#8A9597"));
        WindowComponent.configurarTexto(botonInformacion,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        20);
        WindowComponent.eventoBoton(botonInformacion,
                                    () ->
                                    {
                                        System.out.println("mostrar informacion");
                                    },
                                    Color.decode("#8A9597"),
                                    Color.decode("#91BAD6"),
                                    Color.decode("#528AAE"));

        add(botonIniciar);
        add(botonRegistro);
        add(botonInformacion);

        contenedor.add(this);
    }
}
