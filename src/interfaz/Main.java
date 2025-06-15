package interfaz;

// importaciones de swing
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame
{
    // dimensiones de la ventana
    public static final int ANCHO_VENTANA=600, ALTURA_VENTANA=400;

    // objeto estático del menu principal
    public static MenuInicio MENU_PRINCIPAL;

    // constructor
    public Main()
    {
        iniciarVentana();
        WindowComponent.setContenedor(this.getContentPane());
        MENU_PRINCIPAL = new MenuInicio();
    }

    // metodo para inicializar la ventana principal
    public void iniciarVentana()
    {
        setTitle("regedat");
        setBounds(0, 0, ANCHO_VENTANA, ALTURA_VENTANA);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // aqui es donde comienza la magia
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(Main::new);
    }
}