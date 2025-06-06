package interfaz;

// importaciones de awt
import java.awt.Color;
import java.awt.Dimension;

// importaciones de swing
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// importaciones de los paquetes
import logica.Materia;
import logica.Reporte;

public class PanelMateria extends JPanel
{
    // objeto de la materia
    private final Materia materia;

    // panel de las materias y de las notas
    private final JPanel panelMaterias;
    private MenuNotas menuNotas;

    // paneles de rendimiento
    private JLabel nombreMateria, puntajeTotal, totalEvaluado;

    // constructor
    public PanelMateria(Materia materia)
    {
        this.materia = materia;
        this.panelMaterias = MenuMateria.panelMaterias;
        inicializarPanel();
    }

    // metodo para inicializar el panel
    public void inicializarPanel()
    {
        setPreferredSize(new Dimension(380, 80));
        setBackground(WindowComponent.FONDO_GRIS);
        setLayout(null);

        menuNotas = new MenuNotas(materia, this);
        menuNotas.setVisible(false);
        WindowComponent.getContenedor().add(menuNotas);

        // nombre de la materia
        nombreMateria = WindowComponent.setTexto(materia.getId() + " " + materia.getNombre(), 10, 10, 220, 18);
        WindowComponent.configurarTexto(nombreMateria, WindowComponent.COLOR_FUENTE, 1, 10);

        // texto donde se muestra el puntaje total obtenido
        puntajeTotal = WindowComponent.setTexto(("Puntaje total: 0.0"),
                                                10,
                                                WindowComponent.yNegativo(nombreMateria, 2),
                                                350,
                                                16);
        WindowComponent.configurarTexto(puntajeTotal,
                                        WindowComponent.COLOR_FUENTE,
                                        3,
                                        WindowComponent.getAltura(puntajeTotal));

        // texto donde se muestra el porcentaje total evaluado
        totalEvaluado = WindowComponent.setTexto(("Total evaluado: 0.0%"),
                                                10,
                                                WindowComponent.yNegativo(puntajeTotal, 2),
                                                350,
                                                16);
        WindowComponent.configurarTexto(totalEvaluado,
                                        WindowComponent.COLOR_FUENTE,
                                        3,
                                        WindowComponent.getAltura(totalEvaluado));

        // boton para eliminar una materia
        JButton botonEliminar = WindowComponent.setBoton("x",
                                                        300,
                                                        15,
                                                        50,
                                                        50,
                                                        Color.decode("#4D5156"));
        WindowComponent.configurarTexto(botonEliminar,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        18);
        WindowComponent.eventoBoton(botonEliminar,
                                    () ->
                                    {
                                        int choice = JOptionPane.showConfirmDialog(panelMaterias,
                                                                                "¿Quieres eliminar esta materia?",
                                                                                "Eliminar materia",
                                                                                JOptionPane.YES_NO_OPTION);
                                        if(choice == JOptionPane.YES_OPTION)
                                        {
                                            // elimina la inscripcion de la materia en la base de datos
                                            Reporte.materiaDAO.eliminarMateria(this.materia.getId(), panelMaterias);
                                            // recarga el panel para mostrar los cambios
                                            Reporte.materiaDAO.cargarMaterias(panelMaterias, MenuInicio.ESTUDIANTE_ACTUAL.getCodigo());
                                        }
                                    },
                                    botonEliminar.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

        // boton para entrar al menu de las notas
        JButton botonNota = WindowComponent.setBoton("+",
                                                    (botonEliminar.getX()-60),
                                                    botonEliminar.getY(),
                                                    50,
                                                    50,
                                                    Color.decode("#4D5156"));
        WindowComponent.configurarTexto(botonNota,
                                        WindowComponent.COLOR_FUENTE,
                                        1,
                                        18);
        WindowComponent.eventoBoton(botonNota,
                                    () ->
                                    {
                                        // carga las notas de la materia
                                        Reporte.notaDAO.cargarNotas(materia, menuNotas.getPanelNotas());
                                        menuNotas.setTextoPuntaje(this.materia.getPuntajeTotal());
                                        WindowComponent.cambiarPanel(MenuInicio.menuMateria, menuNotas);
                                    },
                                    botonNota.getBackground(),
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // agrega los componentes al panel
        add(nombreMateria);
        add(puntajeTotal);
        add(totalEvaluado);
        add(botonEliminar);
        add(botonNota);

        // agrega la materia al panel de las materias
        panelMaterias.add(this);

        // recarga el panel para mostrar los cambios
        WindowComponent.recargar(panelMaterias);
    }

    // metodo para cambiar el color de los textos del panel
    public void setTextoColor(Color color)
    {
        nombreMateria.setForeground(color);
        puntajeTotal.setForeground(color);
        totalEvaluado.setForeground(color);
    }

    // setters and getters
    public void set_score_label(double score)
    {
        score = (int)(score * 100) / 100.0;
        puntajeTotal.setText("Puntaje total: " + score);
    }
    public JLabel get_score_label()
    {
        return puntajeTotal;
    }

    public void set_evaluated_label(double percentage)
    {
        totalEvaluado.setText("Porcentaje evaluado: " + (int)(percentage * 100) / 100.0 + "%");
    }
    public JLabel get_evaluated_label()
    {
        return totalEvaluado;
    }
}