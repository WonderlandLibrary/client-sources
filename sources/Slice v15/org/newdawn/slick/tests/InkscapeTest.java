package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.svg.InkscapeLoader;
import org.newdawn.slick.svg.SimpleDiagramRenderer;





public class InkscapeTest
  extends BasicGame
{
  private SimpleDiagramRenderer[] renderer = new SimpleDiagramRenderer[5];
  
  private float zoom = 1.0F;
  

  private float x;
  
  private float y;
  

  public InkscapeTest()
  {
    super("Inkscape Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    container.getGraphics().setBackground(Color.white);
    
    InkscapeLoader.RADIAL_TRIANGULATION_LEVEL = 2;
    



    renderer[3] = new SimpleDiagramRenderer(InkscapeLoader.load("testdata/svg/clonetest.svg"));
    

    container.getGraphics().setBackground(new Color(0.5F, 0.7F, 1.0F));
  }
  

  public void update(GameContainer container, int delta)
    throws SlickException
  {
    if (container.getInput().isKeyDown(16)) {
      zoom += delta * 0.01F;
      if (zoom > 10.0F) {
        zoom = 10.0F;
      }
    }
    if (container.getInput().isKeyDown(30)) {
      zoom -= delta * 0.01F;
      if (zoom < 0.1F) {
        zoom = 0.1F;
      }
    }
    if (container.getInput().isKeyDown(205)) {
      x += delta * 0.1F;
    }
    if (container.getInput().isKeyDown(203)) {
      x -= delta * 0.1F;
    }
    if (container.getInput().isKeyDown(208)) {
      y += delta * 0.1F;
    }
    if (container.getInput().isKeyDown(200)) {
      y -= delta * 0.1F;
    }
  }
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.scale(zoom, zoom);
    g.translate(x, y);
    g.scale(0.3F, 0.3F);
    
    g.scale(3.3333333F, 3.3333333F);
    g.translate(400.0F, 0.0F);
    
    g.translate(100.0F, 300.0F);
    g.scale(0.7F, 0.7F);
    
    g.scale(1.4285715F, 1.4285715F);
    
    g.scale(0.5F, 0.5F);
    g.translate(-1100.0F, -380.0F);
    renderer[3].render(g);
    g.scale(2.0F, 2.0F);
    




    g.resetTransform();
  }
  



  public static void main(String[] argv)
  {
    try
    {
      Renderer.setRenderer(2);
      Renderer.setLineStripRenderer(4);
      
      AppGameContainer container = new AppGameContainer(new InkscapeTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
