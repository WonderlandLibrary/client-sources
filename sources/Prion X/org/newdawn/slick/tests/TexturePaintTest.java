package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.TexCoordGenerator;
import org.newdawn.slick.geom.Vector2f;





public class TexturePaintTest
  extends BasicGame
{
  private Polygon poly = new Polygon();
  

  private Image image;
  
  private Rectangle texRect = new Rectangle(50.0F, 50.0F, 100.0F, 100.0F);
  

  private TexCoordGenerator texPaint;
  

  public TexturePaintTest()
  {
    super("Texture Paint Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    poly.addPoint(120.0F, 120.0F);
    poly.addPoint(420.0F, 100.0F);
    poly.addPoint(620.0F, 420.0F);
    poly.addPoint(300.0F, 320.0F);
    
    image = new Image("testdata/rocks.png");
    
    texPaint = new TexCoordGenerator() {
      public Vector2f getCoordFor(float x, float y) {
        float tx = (texRect.getX() - x) / texRect.getWidth();
        float ty = (texRect.getY() - y) / texRect.getHeight();
        
        return new Vector2f(tx, ty);
      }
    };
  }
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {}
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.setColor(Color.white);
    g.texture(poly, image);
    
    ShapeRenderer.texture(poly, image, texPaint);
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new TexturePaintTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
