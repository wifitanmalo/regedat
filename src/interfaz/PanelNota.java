package interfaz;

// importaciones de awt
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// importaciones de swing
import javax.swing.*;

// importaciones de los paquetes
import logica.Materia;
import logica.Nota;
import logica.Sistema;

public class PanelNota extends JPanel
{
    // panel donde se mostraran las notas agregadas
    private final JPanel panelNotas;

    // materia de la nota
    private final Materia materia;

    // objeto de la nota actual
    private final Nota nota;

    // texto del valor que suma la nota
    private JLabel valorNota;

    // campos de los valores de la nota
    private JTextField campoPuntaje, campoPorcentaje;

    // constructor
    public PanelNota(Materia materia, Nota nota, JPanel panelNotas)
    {
        this.materia = materia;
        this.nota = nota;
        this.panelNotas = panelNotas;
        panelNota();
    }

    // metodo para inicializar el panel
    public void panelNota()
    {
        setPreferredSize(new Dimension(340, 80));
        setBackground(WindowComponent.FONDO_GRIS);
        setLayout(null);

        // campo de texto donde se ingresa el puntaje
        campoPuntaje = WindowComponent.setCampoTexto(70,
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
                    double porcentaje = Double.parseDouble(campoPorcentaje.getText().trim());
                    if (nuevoPuntaje < 0)
                    {
                        WindowComponent.cuadroMensaje(panelNotas,
                                                    "El puntaje no puede ser negativo.",
                                                    "Error de entrada",
                                                    JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- numero negativo -----");
                    }
                    else if (nuevoPuntaje > Materia.MAXIMO_PUNTAJE)
                    {
                        WindowComponent.cuadroMensaje(panelNotas,
                                                    "El puntaje no puede ser mayor que " + Materia.MAXIMO_PUNTAJE + ".",
                                                    "Error de entrada",
                                                    JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- error de limite -----");
                    }
                    else
                    {
                        // actualiza el puntaje de la nota en la base de datos
                        Sistema.notaDAO.actualizarPuntaje(nota, nuevoPuntaje, panelNotas);
                    }
                }
                catch (NumberFormatException ex)
                {
                    // cambia el puntaje fallido al valor predeterminado
                    Sistema.notaDAO.actualizarPuntaje(nota, 0.0, panelNotas);
                    // recarga el panel para mostrar los cambios
                    Sistema.notaDAO.cargarNotas(materia, panelNotas);
                }
                // actualiza el valor de la nota en el panel
                setValorNota(String.format("%.2f", Sistema.notaDAO.obtenerValor(nota.getId(), nota.getIdInscripcion(), panelNotas)));
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

        // campo de texto donde se ingresa el porcentaje
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
                try
                {
                    double puntaje = Double.parseDouble(campoPuntaje.getText().trim());
                    double nuevoPorcentaje = Double.parseDouble(campoPorcentaje.getText().trim());
                    if (nuevoPorcentaje < 0)
                    {
                        WindowComponent.cuadroMensaje(panelNotas,
                                                    "El porcentaje no puede ser negativo.",
                                                    "Error de entrada",
                                                    JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- numero negativo -----");
                    }
                    else if (nuevoPorcentaje > 100)
                    {
                        WindowComponent.cuadroMensaje(panelNotas,
                                                    "El puntaje no puede ser mayor que 100%.",
                                                    "Error de entrada",
                                                    JOptionPane.ERROR_MESSAGE);
                        throw new NumberFormatException("----- error de limite -----");
                    }
                    else
                    {
                        // actualiza el porcentaje de la nota en la base de datos
                        Sistema.notaDAO.actualizarPorcentaje(nota, nuevoPorcentaje, panelNotas);
                    }
                }
                catch (NumberFormatException ex)
                {
                    // cambia el porcentaje fallido al valor predeterminado
                    Sistema.notaDAO.actualizarPorcentaje(nota, 0.0, panelNotas);
                    // recarga el panel para mostrar los cambios
                    Sistema.notaDAO.cargarNotas(materia, panelNotas);
                }
                // actualiza el valor de la nota en el panel
                setValorNota(String.format("%.2f", Sistema.notaDAO.obtenerValor(nota.getId(), nota.getIdInscripcion(), panelNotas)));
                super.keyReleased(e);
            }
        });

        // texto donde se muestra el simbolo del porcentaje
        valorNota = WindowComponent.setTexto("0.0",
                12,
                campoPuntaje.getY(),
                50,
                30);
        WindowComponent.configurarTexto(valorNota,
                Color.LIGHT_GRAY,
                1,
                18);

        // boton para eliminar una nota
        JButton botonEliminar = WindowComponent.setBoton("x",
                260,
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
                    // elimina la nota de la base de datos
                    Sistema.notaDAO.eliminarNota(nota, panelNotas);
                    // recarga las notas para mostrar los cambios
                    Sistema.notaDAO.cargarNotas(materia, panelNotas);
                },
                Color.decode("#4D5156"),
                Color.decode("#FF4F4B"),
                Color.decode("#FF1D18"));

        // agrega los componentes en el panel
        add(nombreNota);
        add(campoPuntaje);
        add(campoPorcentaje);
        add(valorNota);
        add(botonEliminar);

        // agrega la nota en el panel de las notas
        panelNotas.add(this);

        // recarga el panel para mostrar los cambios
        WindowComponent.recargar(panelNotas);
    }

    // setters and getters
    public void setValorNota(String puntaje)
    {
        valorNota.setText(String.valueOf(puntaje));
    }
    public JLabel getValorNota()
    {
        return valorNota;
    }

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