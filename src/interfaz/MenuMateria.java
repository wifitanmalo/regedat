package interfaz;

import java.awt.*;
import javax.swing.*;

public class MenuMateria extends JPanel
{
    // objeto del contenedor
    private final Container container;

    // panel de las materias
    public static final JPanel panelMaterias = new JPanel();

    // objeto del menu de crear materias
    private final CrearMateria crearMateria;
    private final MenuNotas notas;

    // constructor
    public MenuMateria()
    {
        this.crearMateria = new CrearMateria();
        this.notas = new MenuNotas();

        crearMateria.setVisible(false);
        notas.setVisible(false);

        this.container = WindowComponent.getContenedor();
        inicializarPanel();
    }

    // metodo para inicializar el menu de las materias
    public void inicializarPanel()
    {
        // crea el panel
        setLayout(null);
        setBackground(WindowComponent.FONDO_VENTANA);
        setBounds(0, 0, Main.ANCHO_VENTANA, Main.ALTURA_VENTANA);

        // agrega una barra de desplazamiento vertical al listado de materias
        JScrollPane barraScroll = WindowComponent.setScrollbar(panelMaterias,
                                                                104,
                                                                60,
                                                                380,
                                                                270);

        // titulo de las materias
        JLabel tituloMaterias = WindowComponent.setTexto("Materias",
                                                        barraScroll.getX(),
                                                        barraScroll.getY()-32,
                                                        260,
                                                        26);
        WindowComponent.configurarTexto(tituloMaterias,
                                        WindowComponent.FONDO_PRESIONAR_BOTON,
                                        3,
                                        WindowComponent.getAltura(tituloMaterias));

        // agrega el boton para crear materias
        JButton botonAgregar = WindowComponent.setBoton("+",
                                                        16,
                                                        barraScroll.getY() + ((barraScroll.getHeight()-50)/2),
                                                        50,
                                                        50,
                                                        WindowComponent.FONDO_BOTON);
        WindowComponent.configurarTexto(botonAgregar,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        18);
        WindowComponent.eventoBoton(botonAgregar,
                                    () ->
                                    {
                                        System.out.println("agregar materia");
                                        WindowComponent.cambiarPanel(this, crearMateria);
                                    },
                                    WindowComponent.FONDO_BOTON,
                                    WindowComponent.FONDO_SOBRE_BOTON,
                                    WindowComponent.FONDO_PRESIONAR_BOTON);

        // agrega el boton para cerrar sesion
        JButton botonCerrar = WindowComponent.setBoton("Cerrar",
                                                        botonAgregar.getX(),
                                                        WindowComponent.yNegativo(botonAgregar, 20),
                                                        78,
                                                        50,
                                                        Color.decode("#8A9597"));
        WindowComponent.configurarTexto(botonCerrar,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        12);
        WindowComponent.eventoBoton(botonCerrar,
                                    () ->
                                    {
                                        WindowComponent.cambiarPanel(this, Main.principal);
                                        System.out.println("cerrar sesion");
                                    },
                                    Color.decode("#8A9597"),
                                    Color.decode("#AAAAAA"),
                                    Color.decode("#C7C8CA"));

        // agrega el boton para cerrar sesion
        JButton botonReporte = WindowComponent.setBoton("Reporte",
                                                        Main.ANCHO_VENTANA-104,
                                                        botonAgregar.getY(),
                                                        78,
                                                        50,
                                                        Color.decode("#8A9597"));
        WindowComponent.configurarTexto(botonReporte,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        10);
        WindowComponent.eventoBoton(botonReporte,
                                    () ->
                                    {
                                        System.out.println("mostrar reporte de materias");
                                    },
                                    Color.decode("#8A9597"),
                                    Color.decode("#91BAD6"),
                                    Color.decode("#528AAE"));

        // agrega los componentes al panel
        add(botonAgregar);
        add(botonCerrar);
        add(botonReporte);
        add(tituloMaterias);
        add(barraScroll);

        // agrega los componentes al contenedor
        container.add(this);
        container.add(crearMateria);
    }
}
