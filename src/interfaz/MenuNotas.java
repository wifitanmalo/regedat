package interfaz;

import javax.swing.*;
import java.awt.*;

public class MenuNotas extends JPanel
{
    // panel de las notas
    private static final JPanel listaNotas = new JPanel();

    // constructor
    public MenuNotas()
    {
        initialize_panel();
    }

    // method to initialize the main panel
    public void initialize_panel()
    {
        // create the main panel
        setLayout(null);
        setBackground(WindowComponent.FONDO_VENTANA);
        setBounds(0, 0, Main.ANCHO_VENTANA, Main.ALTURA_VENTANA);

        // agrega una barra de desplazamiento vertical al listado de materias
        JScrollPane barraScroll = WindowComponent.setScrollbar(listaNotas,
                                                                104,
                                                                60,
                                                                380,
                                                                270);

        // titulo de las calificaciones
        JLabel tituloNotas = WindowComponent.setTexto("Calificaciones",
                                                        barraScroll.getX(),
                                                        barraScroll.getY()-32,
                                                        260,
                                                        26);
        WindowComponent.configurarTexto(tituloNotas,
                                        WindowComponent.FONDO_PRESIONAR_BOTON,
                                        3,
                                        WindowComponent.getAltura(tituloNotas));

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
                                        System.out.println("agregar nota");
                                    },
                                    WindowComponent.FONDO_BOTON,
                                    WindowComponent.FONDO_SOBRE_BOTON,
                                    WindowComponent.FONDO_PRESIONAR_BOTON);

        // agrega el boton para volver al menu de las materias
        JButton botonVolver = WindowComponent.setBoton("Volver",
                                                        botonAgregar.getX(),
                                                        WindowComponent.yNegativo(botonAgregar, 20),
                                                        78,
                                                        50,
                                                        Color.decode("#8A9597"));
        WindowComponent.configurarTexto(botonVolver,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        12);
        WindowComponent.eventoBoton(botonVolver,
                                    () ->
                                    {
                                        WindowComponent.cambiarPanel(this, MenuInicio.materia);
                                        System.out.println("volver al menu de las materias");
                                    },
                                    Color.decode("#8A9597"),
                                    Color.decode("#AAAAAA"),
                                    Color.decode("#C7C8CA"));

        // agrega el boton para calcular el puntaje total de la materia
        JButton botonTotal = WindowComponent.setBoton("Total",
                                                        Main.ANCHO_VENTANA-104,
                                                        botonAgregar.getY(),
                                                        78,
                                                        50,
                                                        Color.decode("#8A9597"));
        WindowComponent.configurarTexto(botonTotal,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        10);
        WindowComponent.eventoBoton(botonTotal,
                                    () ->
                                    {
                                        System.out.println("puntaje total de la materia");
                                    },
                                    Color.decode("#8A9597"),
                                    Color.decode("#91BAD6"),
                                    Color.decode("#528AAE"));

        // agrega los componentes al panel
        add(botonAgregar);
        add(botonVolver);
        add(botonTotal);
        add(tituloNotas);
        add(barraScroll);
    }
}