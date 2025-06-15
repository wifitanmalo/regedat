package interfaz;

// importaciones de awt
import java.awt.*;

// importaciones de SQL

// importaciones de swing
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// importaciones de los paquetes
import logica.Carrera;
import logica.Sistema;

public class MenuListado extends JPanel
{
    private JComboBox<Carrera> listadoCarreras;
    private JTable tablaMaterias;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollMaterias;

    private static final Carrera OPCION_TODAS = new Carrera(-1, "Todas las carreras");

    public MenuListado()
    {
        inicializarPanel();
    }

    public void inicializarPanel()
    {
        setLayout(null);
        setBackground(WindowComponent.FONDO_VENTANA);
        setBounds(0, 0, Main.ANCHO_VENTANA, Main.ALTURA_VENTANA);

        // ---------- JComboBox de carreras ----------
        listadoCarreras = new JComboBox<>();
        listadoCarreras.setBounds(124, 20, 340, 30);
        Sistema.materiaDAO.cargarCarreras(listadoCarreras, OPCION_TODAS, this);

        listadoCarreras.addActionListener(e ->
        {
            Carrera seleccionado = (Carrera) listadoCarreras.getSelectedItem();
            if (seleccionado != null) {
                Sistema.materiaDAO.listarMaterias(seleccionado.getId(), modeloTabla, this);
            }
        });
        WindowComponent.configurarTexto(listadoCarreras, Color.decode("#363636"), 1, 14);

        // tabla en la que seran mostradas las materias
        modeloTabla = new DefaultTableModel(new String[]{"Código", "Nombre"}, 0)
        {
            @Override
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };

        tablaMaterias = new JTable(modeloTabla);
        tablaMaterias.setFillsViewportHeight(true);
        WindowComponent.configurarTexto(tablaMaterias, Color.decode("#363636"), 1, 14);

        scrollMaterias = WindowComponent.setScrollbar(tablaMaterias, 124, 60, 340, 270);
        tablaMaterias.setBackground(Color.LIGHT_GRAY);

        // titulo del listado de las materias
        JLabel tituloListado = WindowComponent.setTexto(
                "Listado de materias",
                scrollMaterias.getX(),
                scrollMaterias.getY() - 32,
                260,
                26
        );
        WindowComponent.configurarTexto(tituloListado,
                WindowComponent.FONDO_PRESIONAR_BOTON,
                3,
                WindowComponent.getAltura(tituloListado)
        );

        // ---------- Botón Volver ----------
        JButton botonVolver = WindowComponent.setBoton(
                "Volver",
                20,
                scrollMaterias.getY() + ((scrollMaterias.getHeight() - 50) / 2),
                78,
                50,
                WindowComponent.FONDO_GRIS
        );
        WindowComponent.configurarTexto(botonVolver,
                WindowComponent.COLOR_FUENTE,
                1,
                12
        );
        WindowComponent.eventoBoton(botonVolver,
                () ->
                {
                    Sistema.materiaDAO.cargarMaterias(this, MenuInicio.ESTUDIANTE_ACTUAL.getCodigo());
                    WindowComponent.cambiarPanel(this, MenuInicio.MENU_MATERIA);
                },
                WindowComponent.FONDO_GRIS,
                Color.decode("#AAAAAA"),
                Color.decode("#C7C8CA")
        );

        // agrega los componentes al panel
        add(listadoCarreras);
        add(tituloListado);
        add(scrollMaterias);
        add(botonVolver);

        // carga por defecto todas las materias registradas
        listadoCarreras.setSelectedItem(OPCION_TODAS);
    }
}