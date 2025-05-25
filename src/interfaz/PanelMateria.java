package interfaz;

import java.awt.*;
import javax.swing.*;

import logica.Materia;
import logica.Reporte;

public class PanelMateria extends JPanel
{
    // objeto de la materia
    private final Materia materia;

    // subject panels
    private final JPanel panelMaterias;

    // performance labels
    private JLabel puntajeTotal;
    private JLabel totalEvaluado;

    // constructor
    public PanelMateria(Materia materia)
    {
        this.materia = materia;
        this.panelMaterias = MenuMateria.panelMaterias;
        inicializarPanel();
    }

    // method to add a subject
    public void inicializarPanel()
    {
        setPreferredSize(new Dimension(380, 80));
        setBackground(Color.decode("#8A9597"));
        setLayout(null);

        // nombre de la materia
        JLabel nombreMateria = WindowComponent.setTexto(materia.getId() + " " + materia.getNombre(), 10, 10, 220, 18);
        WindowComponent.configurarTexto(nombreMateria, WindowComponent.COLOR_FUENTE, 1, 8);

        // text of the total score
        puntajeTotal = WindowComponent.setTexto(("Puntaje total: 0.0"),
                                                10,
                                                WindowComponent.yNegativo(nombreMateria, 2),
                                                350,
                                                16);
        WindowComponent.configurarTexto(puntajeTotal,
                                        Color.lightGray,
                                        3,
                                        WindowComponent.getAltura(puntajeTotal));

        // text of the total percentage evaluated
        totalEvaluado = WindowComponent.setTexto(("Total evaluado: 0.0%"),
                                                10,
                                                WindowComponent.yNegativo(puntajeTotal, 2),
                                                350,
                                                16);
        WindowComponent.configurarTexto(totalEvaluado,
                                        Color.lightGray,
                                        3,
                                        WindowComponent.getAltura(totalEvaluado));

        // button to delete a subject
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
                                                "You want to delete this subject?",
                                                "Delete subject",
                                                JOptionPane.YES_NO_OPTION);
                                        if(choice == JOptionPane.YES_OPTION && Reporte.materiaDAO.getListaMaterias().contains(materia))
                                        {
                                            // elimina la materia del archivo/panel
                                            panelMaterias.remove(this);
                                            Reporte.materiaDAO.eliminarMateria(materia);

                                            // recarga el panel para mostrar los cambios
                                            WindowComponent.recargar(panelMaterias);
                                        }
                                    },
                                    botonEliminar.getBackground(),
                                    Color.decode("#FF4F4B"),
                                    Color.decode("#FF1D18"));

        // button to enter on the grades menu
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
                                        System.out.println("menu de calificaciones");
                                    },
                                    botonNota.getBackground(),
                                    Color.decode("#C5EF48"),
                                    Color.decode("#9DD100"));

        // add the components on the panel
        add(nombreMateria);
        add(puntajeTotal);
        add(totalEvaluado);
        add(botonEliminar);
        add(botonNota);

        // add the subject on the subject box
        panelMaterias.add(this);

        // reload the panel to show the changes
        WindowComponent.recargar(panelMaterias);
    }

    // setters and getters
    public void set_score_label(double score)
    {
        score = (int)(score * 100) / 100.0;
        puntajeTotal.setText("Total score: " + score);
    }
    public JLabel get_score_label()
    {
        return puntajeTotal;
    }

    public void set_evaluated_label(double percentage)
    {
        totalEvaluado.setText("Evaluated percentage: " + (int)(percentage * 100) / 100.0 + "%");
    }
    public JLabel get_evaluated_label()
    {
        return totalEvaluado;
    }
}