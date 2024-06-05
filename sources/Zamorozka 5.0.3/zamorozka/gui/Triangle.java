package zamorozka.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Triangle extends Component {

@Override
public void paint(Graphics g) {
    Random rn = new Random();
    Color c = new Color(rn.nextInt(256), rn.nextInt(256), rn.nextInt(256));
    Graphics2D g2d = (Graphics2D) g; // холст для рисования

    int nPoints = 3;
    int[] xPoints = new int[nPoints + 1];
    int[] yPoints = new int[nPoints + 1];
    for (int i = 0; i < nPoints; i++) {
        double angle = 2 * Math.PI * i / nPoints;
        xPoints[i] = (int) (150 + 175 * Math.sin(angle));
        yPoints[i] = (int) (100 - 100 * Math.cos(angle));
    }
    g2d.setColor(c);        
    g2d.fillPolygon(xPoints, yPoints, nPoints);
    //repaint(); //если раскомментировать - треугольник неистово мигает в основном окне.
}
}