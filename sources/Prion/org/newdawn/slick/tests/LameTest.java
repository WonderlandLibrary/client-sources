package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;





public class LameTest
  extends BasicGame
{
  private Polygon poly = new Polygon();
  

  private Image image;
  

  public LameTest()
  {
    super("Lame Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    poly.addPoint(100.0F, 100.0F);
    poly.addPoint(120.0F, 100.0F);
    poly.addPoint(120.0F, 120.0F);
    poly.addPoint(100.0F, 120.0F);
    
    image = new Image("testdata/rocks.png");
  }
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {}
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.setColor(Color.white);
    g.texture(poly, image);
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new LameTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
