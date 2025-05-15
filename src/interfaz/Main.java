package interfaz;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame
{
    // medidas de la ventana
    public static final int ANCHO_VENTANA=600, ALTURA_VENTANA=400;

    // objeto del menu principal
    public static MenuPrincipal principal;

    // constructor
    public Main()
    {
        iniciarVentana();
        WindowComponent.setContenedor(this.getContentPane());
        principal = new MenuPrincipal();
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
