package org.newdawn.slick;

import java.awt.Canvas;
import javax.swing.SwingUtilities;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.util.Log;












public class CanvasGameContainer
  extends Canvas
{
  protected Container container;
  protected Game game;
  
  public CanvasGameContainer(Game game)
    throws SlickException
  {
    this(game, false);
  }
  








  public CanvasGameContainer(Game game, boolean shared)
    throws SlickException
  {
    this.game = game;
    setIgnoreRepaint(true);
    requestFocus();
    setSize(500, 500);
    
    container = new Container(game, shared);
    container.setForceExit(false);
  }
  



  public void start()
    throws SlickException
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run() {
        try {
          
          try {
            Display.setParent(CanvasGameContainer.this);
          } catch (LWJGLException e) {
            throw new SlickException("Failed to setParent of canvas", e);
          }
          
          container.setup();
          CanvasGameContainer.this.scheduleUpdate();
        } catch (SlickException e) {
          e.printStackTrace();
          System.exit(0);
        }
      }
    });
  }
  


  private void scheduleUpdate()
  {
    if (!isVisible()) {
      return;
    }
    
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          container.gameLoop();
        } catch (SlickException e) {
          e.printStackTrace();
        }
        container.checkDimensions();
        CanvasGameContainer.this.scheduleUpdate();
      }
    });
  }
  




  public void dispose() {}
  



  public GameContainer getContainer()
  {
    return container;
  }
  










  private class Container
    extends AppGameContainer
  {
    public Container(Game game, boolean shared)
      throws SlickException
    {
      super(CanvasGameContainer.this.getWidth(), CanvasGameContainer.this.getHeight(), false);
      
      width = CanvasGameContainer.this.getWidth();
      height = CanvasGameContainer.this.getHeight();
      
      if (shared) {
        enableSharedContext();
      }
    }
    


    protected void updateFPS()
    {
      super.updateFPS();
    }
    


    protected boolean running()
    {
      return (super.running()) && (isDisplayable());
    }
    


    public int getHeight()
    {
      return CanvasGameContainer.this.getHeight();
    }
    


    public int getWidth()
    {
      return CanvasGameContainer.this.getWidth();
    }
    


    public void checkDimensions()
    {
      if ((width != CanvasGameContainer.this.getWidth()) || 
        (height != CanvasGameContainer.this.getHeight())) {
        try
        {
          setDisplayMode(CanvasGameContainer.this.getWidth(), 
            CanvasGameContainer.this.getHeight(), false);
        } catch (SlickException e) {
          Log.error(e);
        }
      }
    }
  }
}
