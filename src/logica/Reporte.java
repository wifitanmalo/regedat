package logica;

import interfaz.MenuInicio;
import interfaz.MenuMateria;
import interfaz.PanelMateria;
import interfaz.WindowComponent;
import datos.DatosMateria;

import javax.swing.*;
import java.awt.*;

public class Reporte
{
    // objeto para manejar los datos de las materias
    public static DatosMateria materiaDAO;

    // constructor
    public Reporte()
    {
        this.materiaDAO = new DatosMateria();
    }

    // metodo para verificar si la materia es valida
    public void validarMateria(Container contenedor,
                               JTextField idTextBox,
                               JTextField nameTextBox,
                               JTextField creditsTextBox)
    {
        try
        {
            // obtiene los datos del panel de la materia
            int id = Integer.parseInt(idTextBox.getText().trim());
            String nombre = nameTextBox.getText().trim();
            int creditos = Integer.parseInt(creditsTextBox.getText().trim());

            if(materiaExiste(id))
            {
                WindowComponent.cuadroMensaje(contenedor,
                                                "El ID ya existe.",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
            }
            else if ( (id < 0) || (creditos <= 0) )
            {
                WindowComponent.cuadroMensaje(contenedor,
                        "ID/Créditos deben ser mayores que 0.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(nombre.isEmpty())
            {
                WindowComponent.cuadroMensaje(contenedor,
                        "El nombre no puede estar vacio.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(nameTextBox.getText().length() > 50)
            {
                WindowComponent.cuadroMensaje(contenedor,
                        "El nombre no puede ser mayor a 50 caracteres.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(DatosMateria.CREDITOS_ACTUALES + creditos > DatosMateria.MAXIMO_CREDITOS)
            {
                WindowComponent.cuadroMensaje(contenedor,
                        "Los créditos no pueden ser mayores que " + DatosMateria.MAXIMO_CREDITOS + ".",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                // crea una nueva materia en la lista
                Materia nuevaMateria = new Materia(id, nombre, creditos);
                materiaDAO.crearMateria(nuevaMateria);

                // refresca el panel de las materias
                refrescarMaterias();

                // recarga el panel para mostrar la nueva materia
                WindowComponent.recargar(MenuInicio.materia);

                // regresa al menu principal
                WindowComponent.cambiarPanel(contenedor, MenuInicio.materia);
            }
        }
        catch (NumberFormatException e)
        {
            WindowComponent.cuadroMensaje(contenedor,
                                        "ID/Créditos no son válidos.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para verificar si una materia existe
    public boolean materiaExiste(int id)
    {
        for (Materia materia : materiaDAO.getListaMaterias())
        {
            if (id == materia.getId())
            {
                return true;
            }
        }
        return false;
    }

    // metodo para refrescar las materias en la lista de materias
    public void refrescarMaterias()
    {
        MenuMateria.panelMaterias.removeAll();
        for(logica.Materia materia : materiaDAO.getListaMaterias())
        {
            // create the panel in the subject box
            PanelMateria current_panel = new PanelMateria(materia);
            current_panel.set_score_label(materia.getPuntajeTotal());
        }
        // reload the panel to show the changes
        WindowComponent.recargar(MenuMateria.panelMaterias);
    }
}
