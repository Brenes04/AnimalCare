//Description: Custom panel used to display a background image in the graphical interface.
import javax.swing.*;
import java.awt.*;

//inherits from JPanel
public class PanelConFondo extends JPanel {
    private Image imagenFondo;
    public PanelConFondo() {
        ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/logo.png"));
        imagenFondo = icono.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //we use int to produce a decimal value
        int ancho = (int) (getWidth() * 0.70);
        int alto = (int) (getHeight() * 0.80);
        int x = getWidth() - ancho - 20;
        int y = (getHeight() - alto) / 2;

        //Draws imagenFondo at the X/Y position using the width and height we calculated.
        g.drawImage(imagenFondo, x, y, ancho, alto, this);
    }
}
