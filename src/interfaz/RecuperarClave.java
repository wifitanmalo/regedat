package interfaz;

import logica.Sistema;

import java.awt.*;
import javax.swing.*;

public class RecuperarClave extends JPanel
{
    // text boxes
    private JTextField  campoID;
    private JPasswordField  campoClave;
    private JPasswordField  campoConfirmacion;

    // constructor
    public RecuperarClave()
    {
        inicializarPanel();
    }

    // method to initialize
    private void inicializarPanel()
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
                                                        Color.decode("#8A9597"));
        WindowComponent.configurarTexto(botonVolver,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        14);
        WindowComponent.eventoBoton(botonVolver,
                                    () ->
                                    {
                                        WindowComponent.cambiarPanel(this, Main.MENU_PRINCIPAL);
                                        limpiarTodo();
                                    },
                                    Color.decode("#8A9597"),
                                    Color.decode("#AAAAAA"),
                                    Color.decode("#C7C8CA"));

        // agrega el boton de cambiar la clave
        JButton botonCambiar = WindowComponent.setBoton("Cambiar",
                                                        WindowComponent.xPositivo(campoPanel, 20),
                                                        botonVolver.getY(),
                                                        100,
                                                        50,
                                                        WindowComponent.FONDO_BOTON);
        WindowComponent.configurarTexto(botonCambiar,
                                            WindowComponent.COLOR_FUENTE,
                                            1,
                                            14);
        WindowComponent.eventoBoton(botonCambiar,
                                    () -> {
                                        try
                                        {
                                            int id = Integer.parseInt(campoID.getText());
                                            String clave = campoClave.getText();
                                            String confirmar = campoConfirmacion.getText();
                                            String hash = Sistema.hashSHA256(clave);
                                            if (!Sistema.estudianteDAO.estudianteExiste(id, campoPanel))
                                            {
                                                WindowComponent.cuadroMensaje(campoPanel,
                                                                            "El estudiante no existe.",
                                                                            "Error de autenticación",
                                                                            JOptionPane.ERROR_MESSAGE);
                                            }
                                            else if (!Sistema.compararClaves(clave, confirmar) )
                                            {
                                                WindowComponent.cuadroMensaje(campoPanel,
                                                                            "Las claves deben ser iguales.",
                                                                            "Error de autenticación",
                                                                            JOptionPane.ERROR_MESSAGE);
                                            }
                                            else if (!Sistema.validarClave(clave))
                                            {
                                                WindowComponent.cuadroMensaje(campoPanel,
                                                                            "La clave debe tener una mayúscula, una minúscula, un número y un simbolo.",
                                                                            "Clave poco segura",
                                                                            JOptionPane.ERROR_MESSAGE);
                                            }
                                            else
                                            {
                                                Sistema.estudianteDAO.actualizarClave(id, hash, campoPanel);
                                                WindowComponent.cuadroMensaje(campoPanel,
                                                                            "Clave actualizada con éxito.",
                                                                            "Recuperacion exitosa",
                                                                            JOptionPane.INFORMATION_MESSAGE);
                                                WindowComponent.cambiarPanel(this, Main.MENU_PRINCIPAL);
                                            }
                                        }
                                        catch (NumberFormatException nfw)
                                        {
                                            nfw.printStackTrace();
                                            WindowComponent.cuadroMensaje(campoPanel,
                                                                        "Código de usuario inválido.",
                                                                        "Error de validación",
                                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }
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

        // titulo de la clave
        JLabel tituloClave = WindowComponent.setTexto("Nueva clave",
                                                    tituloID.getX(),
                                                    WindowComponent.yNegativo(campoID,20),
                                                    250,
                                                    22);
        WindowComponent.configurarTexto(tituloClave,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloClave));

        // campo de texto del nombre
        campoClave = WindowComponent.setCampoClave(tituloID.getX(),
                                                    WindowComponent.yNegativo(tituloClave,5),
                                                    250,
                                                    30);
        WindowComponent.configurarTexto(campoClave,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // titulo de confirmar la clave
        JLabel tituloConfirmar = WindowComponent.setTexto("Confirmar clave",
                                                tituloClave.getX(),
                                                WindowComponent.yNegativo(campoClave,20),
                                                250,
                                                22);
        WindowComponent.configurarTexto(tituloConfirmar,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        WindowComponent.getAltura(tituloConfirmar));

        // campo de texto para confirmar la clave
        campoConfirmacion = WindowComponent.setCampoClave(tituloConfirmar.getX(),
                                                        WindowComponent.yNegativo(tituloConfirmar,5),
                                                        250,
                                                        30);
        WindowComponent.configurarTexto(campoConfirmacion,
                                        Color.decode("#3D3D3D"),
                                        1,
                                        18);

        // agrega los componentes al contenedor
        add(campoPanel);
        add(botonVolver);
        add(botonCambiar);

        // agrega los componentes a este panel
        campoPanel.add(tituloID);
        campoPanel.add(campoID);
        campoPanel.add(tituloClave);
        campoPanel.add(campoClave);
        campoPanel.add(tituloConfirmar);
        campoPanel.add(campoConfirmacion);
    }

    // metodo para limpiar las cajas de texto
    public void limpiarTodo()
    {
        WindowComponent.limpiarCampo(campoID);
        WindowComponent.limpiarCampo(campoClave);
        WindowComponent.limpiarCampo(campoConfirmacion);
    }
}
