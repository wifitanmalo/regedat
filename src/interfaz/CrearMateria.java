package interfaz;

import java.awt.*;
import javax.swing.*;

public class CrearMateria extends JPanel
{
    // text boxes
    private JTextField campoID;
    private JTextField campoNombre;
    private JTextField campoCreditos;

    // constructor
    public CrearMateria()
    {
        initializePanel();
    }

    // method to initialize
    private void initializePanel()
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
                                                        WindowComponent.FONDO_GRIS);
        WindowComponent.configurarTexto(botonVolver,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        14);
        WindowComponent.eventoBoton(botonVolver,
                                    () ->
                                    {
                                        MenuInicio.materia.refrescarMaterias();
                                        WindowComponent.cambiarPanel(this, MenuInicio.materia);
                                        limpiarTodo();
                                    },
                                    WindowComponent.FONDO_GRIS,
                                    Color.decode("#AAAAAA"),
                                    Color.decode("#C7C8CA"));

        // agrega el boton de crear
        JButton botonCrear = WindowComponent.setBoton("Crear",
                                                        WindowComponent.xPositivo(campoPanel, 29),
                                                        botonVolver.getY(),
                                                        88,
                                                        50,
                                                        WindowComponent.FONDO_BOTON);
        WindowComponent.configurarTexto(botonCrear,
                                            WindowComponent.COLOR_FUENTE,
                                            1,
                                            14);
        WindowComponent.eventoBoton(botonCrear,
                                    () ->
                                    {
                                        MenuPrincipal.reporte.validarMateria(this,
                                                                                campoID,
                                                                                campoNombre,
                                                                                campoCreditos);
                                        MenuInicio.materia.refrescarMaterias();
                                        limpiarTodo();
                                    },
                                    WindowComponent.FONDO_BOTON,
                                    WindowComponent.FONDO_SOBRE_BOTON,
                                    WindowComponent.FONDO_PRESIONAR_BOTON);

        // titulo del codigo de la materia
        JLabel tituloID = WindowComponent.setTexto("ID",
                                                    (campoPanel.getWidth()-250)/2,
                                                    20,
                                                    250,
                                                    22);
        WindowComponent.configurarTexto(tituloID,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloID));

        // campo de texto del codigo de la materia
        campoID = WindowComponent.setCampoTexto(tituloID.getX(),
                                                    WindowComponent.yNegativo(tituloID,5),
                                                    250,
                                                    30);
        WindowComponent.configurarTexto(campoID,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // titulo del nombre
        JLabel tituloNombre = WindowComponent.setTexto("Nombre",
                                                    tituloID.getX(),
                                                    WindowComponent.yNegativo(campoID,20),
                                                    250,
                                                    22);
        WindowComponent.configurarTexto(tituloNombre,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloNombre));

        // campo de texto del nombre
        campoNombre = WindowComponent.setCampoTexto(tituloID.getX(),
                                                    WindowComponent.yNegativo(tituloNombre,5),
                                                    250,
                                                    30);
        WindowComponent.configurarTexto(campoNombre,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // titulo de los creditos
        JLabel tituloCreditos = WindowComponent.setTexto("Creditos",
                                                tituloNombre.getX(),
                                                WindowComponent.yNegativo(campoNombre,20),
                                                250,
                                                22);
        WindowComponent.configurarTexto(tituloCreditos,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloCreditos));

        // campo de texto de los creditos
        campoCreditos = WindowComponent.setCampoTexto(tituloCreditos.getX(),
                                                        WindowComponent.yNegativo(tituloCreditos,5),
                                                        250,
                                                        30);
        WindowComponent.configurarTexto(campoCreditos,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // agrega los componentes al contenedor
        add(campoPanel);
        add(botonVolver);
        add(botonCrear);

        // agrega los componentes a este panel
        campoPanel.add(tituloID);
        campoPanel.add(campoID);
        campoPanel.add(tituloNombre);
        campoPanel.add(campoNombre);
        campoPanel.add(tituloCreditos);
        campoPanel.add(campoCreditos);
    }

    // metodo para limpiar las cajas de texto
    public void limpiarTodo()
    {
        WindowComponent.limpiarCampo(campoID);
        WindowComponent.limpiarCampo(campoNombre);
        WindowComponent.limpiarCampo(campoCreditos);
    }
}
