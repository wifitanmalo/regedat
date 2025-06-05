package interfaz;

import java.awt.Image;

import java.awt.Color;
import java.awt.Container;
import javax.swing.*;

import logica.Inscripcion;

public class MenuPrincipal extends JPanel
{
    // objetos de los menu
    private final MenuInicio inicio;

    // objeto del contenedor
    private final Container contenedor;

    // objeto del reporte
    public static Inscripcion reporte;

    // constructor
    public MenuPrincipal()
    {
        this.inicio = new MenuInicio();
        this.reporte = new Inscripcion();

        inicio.setVisible(false);

        this.contenedor = WindowComponent.getContenedor();
        contenedor.add(inicio);

        iniciarPanel();
    }

    // metodo para inicializar el panel
    private void iniciarPanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FONDO_VENTANA);
        setBounds(0, 0, Main.ANCHO_VENTANA, Main.ALTURA_VENTANA);

        ImageIcon icon = new ImageIcon("logoUnivalle.jpg");
        Image imagen = icon.getImage().getScaledInstance(113, 160, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(imagen));
        logo.setBounds( (Main.ANCHO_VENTANA-113)/2,
                        (Main.ALTURA_VENTANA-160)/3,
                        113,
                        160);

        // agrega el boton para iniciar sesion
        JButton botonIniciar = WindowComponent.setBoton("Entrar",
                                                    Main.ANCHO_VENTANA/3,
                                                    WindowComponent.yNegativo(logo, 20),
                                                    98,
                                                    50,
                                                    WindowComponent.FONDO_GRIS);
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
                                    WindowComponent.FONDO_GRIS,
                                    Color.decode("#AAAAAA"),
                                    Color.decode("#C7C8CA"));

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

        // agrega los componentes al contenedor
        add(botonIniciar);
        add(botonInformacion);
        add(logo);
        contenedor.add(this);
    }
}