package interfaz;

// awt imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// swing imports
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// package imports
import logica.Nota;
import logica.Materia;

public class PanelNota extends JPanel
{
    // panel where the grades are added
    private final JPanel gradeBox;

    // subject and grade objects
    private final Nota nota;

    // text boxes
    private JTextField campoPuntaje, campoPorcentaje;

    // constructor
    public PanelNota(int idInscripcion, Nota nota, JPanel panelNotas)
    {
        this.nota = nota;
        this.gradeBox = panelNotas;
        panelNota();
    }

    // method to create a grade
    public void panelNota()
    {
        setPreferredSize(new Dimension(400, 80));
        setBackground(WindowComponent.FONDO_GRIS);
        setLayout(null);

        // text field where you enter the score
        campoPuntaje = WindowComponent.setCampoTexto(80,
                36,
                80,
                30);
        WindowComponent.configurarTexto(campoPuntaje,
                WindowComponent.FONDO_BOTON,
                1,
                18);
        campoPuntaje.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                try
                {
                    double nuevoPuntaje = Double.parseDouble(campoPuntaje.getText().trim());
                    nota.setPuntaje(nuevoPuntaje);
                }
                catch (NumberFormatException ex)
                {
                    nota.setPuntaje(0);
                }
                super.keyReleased(e);
            }
        });

        // nombre de la calificacion
        JLabel nombreNota = WindowComponent.setTexto(nota.getNombre(),
                campoPuntaje.getX(),
                campoPuntaje.getY() - 30,
                176,
                30);
        WindowComponent.configurarTexto(nombreNota,
                WindowComponent.COLOR_FUENTE,
                1,
                12);

        // text field where you enter the percentage
        campoPorcentaje = WindowComponent.setCampoTexto(WindowComponent.xPositivo(campoPuntaje, 16),
                campoPuntaje.getY(),
                campoPuntaje.getWidth(),
                campoPuntaje.getHeight());
        WindowComponent.configurarTexto(campoPorcentaje,
                WindowComponent.FONDO_BOTON,
                1,
                18);
        campoPorcentaje.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                // Update grade object
                try
                {
                    double nuevoPorcentaje = Double.parseDouble(campoPorcentaje.getText().trim());
                    nota.setPorcentaje(nuevoPorcentaje);
                }
                catch (NumberFormatException ex) {
                    nota.setPorcentaje(0);
                }
                super.keyReleased(e);
            }
        });

        // percentage symbol
        JLabel simboloPorcentaje = WindowComponent.setTexto("%",
                WindowComponent.xPositivo(campoPorcentaje, 6),
                campoPorcentaje.getY(),
                50,
                30);
        WindowComponent.configurarTexto(simboloPorcentaje,
                WindowComponent.COLOR_FUENTE,
                1,
                18);

        // button to delete a grade
        JButton botonEliminar = WindowComponent.setBoton("x",
                300,
                15,
                50,
                50,
                WindowComponent.FONDO_BOTON);
        WindowComponent.configurarTexto(botonEliminar,
                WindowComponent.COLOR_FUENTE,
                1,
                18);
        WindowComponent.eventoBoton(botonEliminar,
                () ->
                {
                    System.out.println("eliminar nota");
                },
                botonEliminar.getBackground(),
                Color.decode("#FF4F4B"),
                Color.decode("#FF1D18"));

        // add the components on the panel
        add(nombreNota);
        add(campoPuntaje);
        add(campoPorcentaje);
        add(simboloPorcentaje);
        add(botonEliminar);

        // add the panel to the grade box
        gradeBox.add(this);

        // reload the panel to show the changes
        WindowComponent.recargar(gradeBox);
    }

    // setters and getters

    public void setTextoPuntaje(String score)
    {
        campoPuntaje.setText(score);
    }
    public String getTextoPuntaje()
    {
        return campoPuntaje.getText().trim();
    }

    public void setTextoPorcentaje(String percentage)
    {
        campoPorcentaje.setText(percentage);
    }
    public String getTextoPorcentaje()
    {
        return campoPorcentaje.getText().trim();
    }
}
