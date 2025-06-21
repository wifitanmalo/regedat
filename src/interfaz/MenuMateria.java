package interfaz;

// importaciones de awt
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;

// importaciones de swing
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// improtaciones de los paquetes
import datos.DatosMateria;
import logica.Sistema;

public class MenuMateria extends JPanel
{
    // objeto del contenedor
    private final Container container;

    // panel donde seran mostradas las materias
    public static final JPanel PANEL_MATERIAS = new JPanel();

    // objeto del menu del listado de materias
    private MenuListado menuListado;

    // constructor
    public MenuMateria()
    {
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

        // genera el menu de listado de materias
        menuListado = new MenuListado();
        menuListado.setVisible(false);
        container.add(menuListado);

        // agrega una barra de desplazamiento vertical al listado de materias
        JScrollPane barraScroll = WindowComponent.setScrollbar(PANEL_MATERIAS,
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
                                        // abre el cuadro de dialogo
                                        cuadroCodigo(PANEL_MATERIAS);
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
                                                        WindowComponent.FONDO_GRIS);
        WindowComponent.configurarTexto(botonCerrar,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        12);
        WindowComponent.eventoBoton(botonCerrar,
                                    () ->
                                    {
                                        int choice = JOptionPane.showConfirmDialog(PANEL_MATERIAS,
                                                                                "¿Quieres cerrar la sesión?",
                                                                                "Cerrar sesión",
                                                                                JOptionPane.YES_NO_OPTION);
                                        if(choice == JOptionPane.YES_OPTION)
                                        {
                                            // cierra la sesion y regresa al menu principal
                                            WindowComponent.cambiarPanel(this, Main.MENU_PRINCIPAL);
                                        }
                                    },
                                    WindowComponent.FONDO_GRIS,
                                    Color.decode("#AAAAAA"),
                                    Color.decode("#C7C8CA"));

        // agrega el boton para cerrar sesion
        JButton botonListado = WindowComponent.setBoton("Listado",
                                                        Main.ANCHO_VENTANA-104,
                                                        botonAgregar.getY(),
                                                        78,
                                                        50,
                                                        WindowComponent.FONDO_GRIS);
        WindowComponent.configurarTexto(botonListado,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        10);
        WindowComponent.eventoBoton(botonListado,
                                    () ->
                                    {
                                        // cierra la sesion y regresa al menu principal
                                        WindowComponent.cambiarPanel(this, menuListado);
                                    },
                                    WindowComponent.FONDO_GRIS,
                                    Color.decode("#91BAD6"),
                                    Color.decode("#528AAE"));

        // agrega los componentes al panel
        add(botonAgregar);
        add(botonCerrar);
        add(botonListado);
        add(tituloMaterias);
        add(barraScroll);

        // agrega los componentes al contenedor
        container.add(this);
    }

    // metodo para inicializar el cuadro interactivo para ingresar el codigo de la materia
    public void cuadroCodigo(Component contenedor)
    {
        Window ventana = SwingUtilities.getWindowAncestor(contenedor);
        JDialog dialogo = new JDialog(ventana, "Ingresar código", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(250, 100);
        dialogo.setLayout(new FlowLayout());
        dialogo.setLocationRelativeTo(contenedor);

        JTextField campoTexto = new JTextField(8);
        WindowComponent.configurarTexto(campoTexto, Color.decode("#3D3D3D"), 1, 14);
        JButton inscribir = new JButton("Vale");

        inscribir.addActionListener(e -> {
            try
            {
                int codigoMateria = Integer.parseInt(campoTexto.getText().trim());
                if (DatosMateria.materiaExiste(codigoMateria, this))
                {
                    // inserta la inscripción en la base de datos
                    Sistema.materiaDAO.inscribirMateria(codigoMateria,this);
                    // recarga las materias para mostrar los cambios
                    Sistema.materiaDAO.cargarMaterias(this, MenuInicio.ESTUDIANTE_ACTUAL.getCodigo());
                    // cierra el cuadro de dialogo
                    dialogo.dispose();
                }
                else
                {
                    WindowComponent.cuadroMensaje(dialogo,
                                                "La materia no existe.",
                                                "Error de entrada",
                                                JOptionPane.ERROR_MESSAGE);
                    campoTexto.setText("");
                }
            }
            catch (NumberFormatException nfe)
            {
                nfe.getStackTrace();
                WindowComponent.cuadroMensaje(dialogo,
                                            "Solo puedes ingresar números enteros.",
                                            "Error de entrada",
                                            JOptionPane.ERROR_MESSAGE);
                campoTexto.setText("");
            }
        });

        dialogo.add(new JLabel("Código: "));
        dialogo.add(campoTexto);
        dialogo.add(inscribir);
        dialogo.setVisible(true);
    }
}