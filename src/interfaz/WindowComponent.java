package interfaz;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class WindowComponent
{
    // fuente de letra predeterminada del sistema
    public static final String FUENTE_PRINCIPAL = "Verdana";

    // colores predeterminados
    public static final Color COLOR_FUENTE = Color.decode("#FFFFFF");
    public static final Color FONDO_BOTON = Color.decode("#FF5252");
    public static final Color FONDO_SOBRE_BOTON = Color.decode("#FF8A80");
    public static final Color FONDO_PRESIONAR_BOTON = Color.decode("#F44336");
    public static final Color FONDO_VENTANA = Color.decode("#F4F3F2");
    public static final Color FONDO_GRIS = Color.decode("#757575");

    // objeto del contenedor
    public static Container contenedorActual;

    // metodo para añadir un panel
    public static JPanel setPanel(Color color,
                                  int x,
                                  int y,
                                  int ancho,
                                  int largo)
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(color);
        panel.setBounds(x, y, ancho, largo);
        return panel;
    }

    // metodo para cambiar de paneles
    public static void cambiarPanel(Container panelActual,
                                    Container panelSiguiente)
    {
        panelActual.setVisible(false);
        panelSiguiente.setVisible(true);
    }

    // metodo para añadir una barra de desplazamiento
    public static JScrollPane setScrollbar(Container panel,
                                             int x,
                                             int y,
                                             int ancho,
                                             int largo)
    {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(FONDO_GRIS);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBounds(x, y, ancho, largo);
        return scrollPanel;
    }

    // method to add a label
    public static JLabel setTexto(String text,
                                  int x,
                                  int y,
                                  int ancho,
                                  int largo)
    {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, ancho, largo);
        return label;
    }

    // metodo para configurar un texto
    public static void configurarTexto(Container container,
                                       Color color,
                                       int estilo,
                                       int tamano)
    {
        container.setForeground(color);
        container.setFont(new Font(WindowComponent.FUENTE_PRINCIPAL, estilo, tamano));
    }

    // metodo para añadir un campo de texto
    public static JTextField setCampoTexto(int x,
                                           int y,
                                           int ancho,
                                           int largo)
    {
        JTextField campoTexto = new JTextField();
        campoTexto.setBounds(x, y, ancho, largo);
        return campoTexto;
    }

    // metodo para añadir un campo de las claves
    public static JPasswordField setCampoClave(int x,
                                               int y,
                                               int ancho,
                                               int largo)
    {
        JPasswordField campoClave = new JPasswordField();
        campoClave.setBounds(x, y, ancho, largo);
        campoClave.setEchoChar('•');
        return campoClave;
    }

    // metodo para limpiar un campo de texto
    public static void limpiarCampo(JTextField text_box)
    {
        text_box.setText("");
    }

    // method to add a button
    public static JButton setBoton(String text,
                                   int x,
                                   int y,
                                   int ancho,
                                   int largo,
                                   Color color)
    {
        JButton boton = new JButton(text);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setBackground(color);
        boton.setBounds(x, y, ancho, largo);
        return boton;
    }

    // metodo para definir el evento de un boton
    public static void eventoBoton(JButton boton,
                                   Runnable funcion,
                                   Color botonInactivo,
                                   Color sobreBoton,
                                   Color botonPresionado)
    {
        boton.addActionListener(e -> funcion.run());

        boton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                boton.setBackground(sobreBoton);
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                boton.setBackground(botonInactivo);
                boton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                boton.setBackground(botonPresionado);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                boton.setBackground(botonInactivo);
            }
        });
    }

    // metodo para mostrar un cuadro de mensaje
    public static void cuadroMensaje(Container contenedor,
                                     String mensaje,
                                     String titulo,
                                     int opcion)
    {
        JOptionPane.showMessageDialog(contenedor,
                                        mensaje,
                                        titulo,
                                        opcion);
    }

    // metodo para recargar los elementos de una ventana
    public static void recargar(Container contenedor)
    {
        contenedor.revalidate();
        contenedor.repaint();
    }

    // metodos para mover un contenedor en x
    public static int xPositivo(Container container, int distance)
    {
        return (container.getX()+container.getWidth()) + distance;
    }
    public static int xNegativo(Container container, int distance)
    {
        return (container.getX()-container.getWidth()) - distance;
    }

    // metodos para mover un contenedor en y
    public static int yPositivo(Container container, int distance)
    {
        return (container.getY()-container.getHeight()) - distance;
    }
    public static int yNegativo(Container container, int distance)
    {
        return (container.getY()+container.getHeight()) + distance;
    }

    // setters and getters
    public static void setContenedor(Container contenedor)
    {
        contenedorActual = contenedor;
    }
    public static Container getContenedor()
    {
        return contenedorActual;
    }

    // metodo para obtener el largo/altura de un contenedor
    public static int getAltura(Container contenedor)
    {
        return contenedor.getHeight()-6;
    }
}
