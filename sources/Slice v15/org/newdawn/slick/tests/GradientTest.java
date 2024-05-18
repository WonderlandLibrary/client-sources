package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.opengl.renderer.Renderer;

















public class GradientTest
  extends BasicGame
{
  private GameContainer container;
  private GradientFill gradient;
  private GradientFill gradient2;
  private GradientFill gradient4;
  private Rectangle rect;
  private Rectangle center;
  private RoundedRectangle round;
  private RoundedRectangle round2;
  private Polygon poly;
  private float ang;
  
  public GradientTest()
  {
    super("Gradient Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    this.container = container;
    
    rect = new Rectangle(400.0F, 100.0F, 200.0F, 150.0F);
    round = new RoundedRectangle(150.0F, 100.0F, 200.0F, 150.0F, 50.0F);
    round2 = new RoundedRectangle(150.0F, 300.0F, 200.0F, 150.0F, 50.0F);
    center = new Rectangle(350.0F, 250.0F, 100.0F, 100.0F);
    
    poly = new Polygon();
    poly.addPoint(400.0F, 350.0F);
    poly.addPoint(550.0F, 320.0F);
    poly.addPoint(600.0F, 380.0F);
    poly.addPoint(620.0F, 450.0F);
    poly.addPoint(500.0F, 450.0F);
    
    gradient = new GradientFill(0.0F, -75.0F, Color.red, 0.0F, 75.0F, Color.yellow, true);
    gradient2 = new GradientFill(0.0F, -75.0F, Color.blue, 0.0F, 75.0F, Color.white, true);
    gradient4 = new GradientFill(-50.0F, -40.0F, Color.green, 50.0F, 40.0F, Color.cyan, true);
  }
  



  public void render(GameContainer container, Graphics g)
  {
    g.rotate(400.0F, 300.0F, ang);
    g.fill(rect, gradient);
    g.fill(round, gradient);
    g.fill(poly, gradient2);
    g.fill(center, gradient4);
    
    g.setAntiAlias(true);
    g.setLineWidth(10.0F);
    g.draw(round2, gradient2);
    g.setLineWidth(2.0F);
    g.draw(poly, gradient);
    g.setAntiAlias(false);
    
    g.fill(center, gradient4);
    g.setAntiAlias(true);
    g.setColor(Color.black);
    g.draw(center);
    g.setAntiAlias(false);
  }
  


  public void update(GameContainer container, int delta)
  {
    ang += delta * 0.01F;
  }
  



  public static void main(String[] argv)
  {
    try
    {
      Renderer.setRenderer(2);
      
      AppGameContainer container = new AppGameContainer(new GradientTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  


  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      container.exit();
    }
  }
}
