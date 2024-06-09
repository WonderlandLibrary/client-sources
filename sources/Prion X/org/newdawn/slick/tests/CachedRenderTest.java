package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.CachedRender;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;










public class CachedRenderTest
  extends BasicGame
{
  private Runnable operations;
  private CachedRender cached;
  private boolean drawCached;
  
  public CachedRenderTest()
  {
    super("Cached Render Test");
  }
  

  public void init(final GameContainer container)
    throws SlickException
  {
    operations = new Runnable() {
      public void run() {
        for (int i = 0; i < 100; i++) {
          int c = i + 100;
          container.getGraphics().setColor(new Color(c, c, c, c));
          container.getGraphics().drawOval(i * 5 + 50, i * 3 + 50, 100.0F, 100.0F);
        }
        
      }
    };
    cached = new CachedRender(operations);
  }
  

  public void update(GameContainer container, int delta)
    throws SlickException
  {
    if (container.getInput().isKeyPressed(57)) {
      drawCached = (!drawCached);
    }
  }
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.setColor(Color.white);
    g.drawString("Press space to toggle caching", 10.0F, 130.0F);
    if (drawCached) {
      g.drawString("Drawing from cache", 10.0F, 100.0F);
      cached.render();
    } else {
      g.drawString("Drawing direct", 10.0F, 100.0F);
      operations.run();
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new CachedRenderTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
