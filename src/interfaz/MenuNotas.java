package interfaz;

// importaciones de awt
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;

// importaciones de swing
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// importaciones de los paquetes
import logica.Materia;
import logica.Sistema;

public class MenuNotas extends JPanel
{
    // materia actual del menu
    private final Materia materia;

    // texto donde se mostrara el puntaje total de la materia
    private JLabel textoPuntaje;

    // panel donde se mostraran las notas
    private final JPanel panelNotas = new JPanel();

    private final PanelMateria panelMateria;

    // constructor
    public MenuNotas(Materia materia, PanelMateria panelMateria)
    {
        this.materia = materia;
        this.panelMateria = panelMateria;
        inicializarPanel();
    }

    // metodo para inicializar el panel
    public void inicializarPanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FONDO_VENTANA);
        setBounds(0, 0, Main.ANCHO_VENTANA, Main.ALTURA_VENTANA);

        // agrega una barra de desplazamiento vertical al listado de materias
        JScrollPane barraScroll = WindowComponent.setScrollbar(panelNotas,
                124,
                60,
                340,
                270);

        // titulo de las calificaciones
        JLabel tituloNotas = WindowComponent.setTexto("Notas",
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
                                                        20,
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
                                        cuadroNombre(this, this.materia);
                                        WindowComponent.recargar(panelNotas);
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
                                                        WindowComponent.FONDO_GRIS);
        WindowComponent.configurarTexto(botonVolver,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        12);
        WindowComponent.eventoBoton(botonVolver,
                                    () ->
                                    {
                                        // carga las materias desde la base de datos
                                        Sistema.materiaDAO.cargarMaterias(this, MenuInicio.ESTUDIANTE_ACTUAL.getCodigo());
                                        // cambia el panel al menu de las materias
                                        WindowComponent.cambiarPanel(this, MenuInicio.MENU_MATERIA);
                                    },
                                    WindowComponent.FONDO_GRIS,
                                    Color.decode("#AAAAAA"),
                                    Color.decode("#C7C8CA"));

        // texto donde el puntaje de la materia es mostrado
        textoPuntaje = WindowComponent.setTexto(String.valueOf(materia.getPuntajeTotal()),
                                                botonVolver.getX(),
                                                WindowComponent.yPositivo(botonAgregar, 14),
                                                80,
                                                20);
        WindowComponent.configurarTexto(textoPuntaje,
                                        WindowComponent.FONDO_GRIS,
                                        3,
                                        WindowComponent.getAltura(tituloNotas));

        // agrega el boton para calcular el puntaje total de la materia
        JButton botonTotal = WindowComponent.setBoton("Total",
                                                        Main.ANCHO_VENTANA-114,
                                                        botonAgregar.getY(),
                                                        78,
                                                        50,
                                                        WindowComponent.FONDO_GRIS);
        WindowComponent.configurarTexto(botonTotal,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        10);
        WindowComponent.eventoBoton(botonTotal,
                                    () ->
                                    {
                                        // realiza los calculos para actualizar los valores de la materia
                                        Sistema.materiaDAO.actualizarMateria(materia.getIdInscripcion(), panelNotas);
                                        // actualiza el texto del puntaje total
                                        setTextoPuntaje(Sistema.materiaDAO.getPuntajeTotal(materia.getIdInscripcion(), panelNotas));
                                        // avisa que tal va el rendimiento de la mataeria
                                        Sistema.evaluarRiesgo(materia.getNombre(),
                                                                Sistema.materiaDAO.getPuntajeTotal(materia.getIdInscripcion(),
                                                                panelNotas),
                                                                Sistema.materiaDAO.getPorcentajeTotal(materia.getIdInscripcion(), panelNotas),
                                                                panelMateria,
                                                                true);
                                    },
                                    WindowComponent.FONDO_GRIS,
                                    Color.decode("#91BAD6"),
                                    Color.decode("#528AAE"));

        // agrega los componentes al panel
        add(botonAgregar);
        add(botonVolver);
        add(botonTotal);
        add(tituloNotas);
        add(barraScroll);
        add(textoPuntaje);
    }

    // metodo para mostrar un cuadro interactivo para ingresar el nombre de la nota
    public void cuadroNombre(Component contenedor, Materia materiaActual)
    {
        Window ventana = SwingUtilities.getWindowAncestor(contenedor);
        JDialog dialogo = new JDialog(ventana, "Ingresar nombre", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(250, 100);
        dialogo.setLayout(new FlowLayout());
        dialogo.setLocationRelativeTo(contenedor);

        JTextField campoTexto = new JTextField(15);
        JButton botonCrear = new JButton("Vale");

        botonCrear.addActionListener(e -> {
            String nombreNota = campoTexto.getText();
            // crea la nota en la base de datos
            Sistema.notaDAO.crearNota(Sistema.materiaDAO.obtenerIdInscripcion(materiaActual.getId()), nombreNota, panelNotas);
            // carga las notas nuevamente para mostrar los cambios
            Sistema.notaDAO.cargarNotas(materiaActual, panelNotas);
            // cierra el cuadro de dialogo
            dialogo.dispose();
        });

        dialogo.add(new JLabel("Nombre: "));
        dialogo.add(campoTexto);
        dialogo.add(botonCrear);
        dialogo.setVisible(true);
    }

    // metodo para actualizar el número y color del texto del puntaje
    public void setTextoPuntaje(double puntaje)
    {
        puntaje = (int)(puntaje * 100) / 100.0;
        textoPuntaje.setText(String.valueOf(puntaje));
        if(puntaje >= Materia.MINIMO_PUNTAJE)
        {
            textoPuntaje.setForeground(Color.decode("#64820A"));
        }
        else
        {
            textoPuntaje.setForeground(Color.decode("#FF6865"));
        }
        WindowComponent.recargar(textoPuntaje);
    }

    // metodo para obtener el panel de notas
    public JPanel getPanelNotas()
    {
        return panelNotas;
    }
}