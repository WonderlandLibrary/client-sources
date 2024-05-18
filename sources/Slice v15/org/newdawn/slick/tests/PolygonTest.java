package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;








public class PolygonTest
  extends BasicGame
{
  private Polygon poly;
  private boolean in;
  private float y;
  
  public PolygonTest()
  {
    super("Polygon Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    poly = new Polygon();
    poly.addPoint(300.0F, 100.0F);
    poly.addPoint(320.0F, 200.0F);
    poly.addPoint(350.0F, 210.0F);
    poly.addPoint(280.0F, 250.0F);
    poly.addPoint(300.0F, 200.0F);
    poly.addPoint(240.0F, 150.0F);
  }
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {
    in = poly.contains(container.getInput().getMouseX(), container.getInput().getMouseY());
    
    poly.setCenterY(0.0F);
  }
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    if (in) {
      g.setColor(Color.red);
      g.fill(poly);
    }
    g.setColor(Color.yellow);
    g.fillOval(poly.getCenterX() - 3.0F, poly.getCenterY() - 3.0F, 6.0F, 6.0F);
    g.setColor(Color.white);
    g.draw(poly);
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new PolygonTest(), 640, 480, false);
      container.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
