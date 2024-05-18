package org.newdawn.slick;

import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;










public class ScalableGame
  implements Game
{
  private static SGL GL = ;
  

  private float normalWidth;
  

  private float normalHeight;
  

  private Game held;
  

  private boolean maintainAspect;
  

  private int targetWidth;
  

  private int targetHeight;
  
  private GameContainer container;
  

  public ScalableGame(Game held, int normalWidth, int normalHeight)
  {
    this(held, normalWidth, normalHeight, false);
  }
  







  public ScalableGame(Game held, int normalWidth, int normalHeight, boolean maintainAspect)
  {
    this.held = held;
    this.normalWidth = normalWidth;
    this.normalHeight = normalHeight;
    this.maintainAspect = maintainAspect;
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    this.container = container;
    
    recalculateScale();
    held.init(container);
  }
  



  public void recalculateScale()
    throws SlickException
  {
    targetWidth = container.getWidth();
    targetHeight = container.getHeight();
    
    if (maintainAspect) {
      boolean normalIsWide = normalWidth / normalHeight > 1.6D;
      boolean containerIsWide = targetWidth / targetHeight > 1.6D;
      float wScale = targetWidth / normalWidth;
      float hScale = targetHeight / normalHeight;
      
      if ((normalIsWide & containerIsWide)) {
        float scale = wScale < hScale ? wScale : hScale;
        targetWidth = ((int)(normalWidth * scale));
        targetHeight = ((int)(normalHeight * scale));
      } else if ((normalIsWide & !containerIsWide)) {
        targetWidth = ((int)(normalWidth * wScale));
        targetHeight = ((int)(normalHeight * wScale));
      } else if ((!normalIsWide & containerIsWide)) {
        targetWidth = ((int)(normalWidth * hScale));
        targetHeight = ((int)(normalHeight * hScale));
      } else {
        float scale = wScale < hScale ? wScale : hScale;
        targetWidth = ((int)(normalWidth * scale));
        targetHeight = ((int)(normalHeight * scale));
      }
    }
    

    if ((held instanceof InputListener)) {
      container.getInput().addListener((InputListener)held);
    }
    container.getInput().setScale(normalWidth / targetWidth, 
      normalHeight / targetHeight);
    

    int yoffset = 0;
    int xoffset = 0;
    
    if (targetHeight < container.getHeight()) {
      yoffset = (container.getHeight() - targetHeight) / 2;
    }
    if (targetWidth < container.getWidth()) {
      xoffset = (container.getWidth() - targetWidth) / 2;
    }
    container.getInput().setOffset(-xoffset / (targetWidth / normalWidth), 
      -yoffset / (targetHeight / normalHeight));
  }
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {
    if ((targetHeight != container.getHeight()) || 
      (targetWidth != container.getWidth())) {
      recalculateScale();
    }
    
    held.update(container, delta);
  }
  


  public final void render(GameContainer container, Graphics g)
    throws SlickException
  {
    int yoffset = 0;
    int xoffset = 0;
    
    if (targetHeight < container.getHeight()) {
      yoffset = (container.getHeight() - targetHeight) / 2;
    }
    if (targetWidth < container.getWidth()) {
      xoffset = (container.getWidth() - targetWidth) / 2;
    }
    
    SlickCallable.enterSafeBlock();
    g.setClip(xoffset, yoffset, targetWidth, targetHeight);
    GL.glTranslatef(xoffset, yoffset, 0.0F);
    g.scale(targetWidth / normalWidth, targetHeight / normalHeight);
    GL.glPushMatrix();
    held.render(container, g);
    GL.glPopMatrix();
    g.clearClip();
    SlickCallable.leaveSafeBlock();
    
    renderOverlay(container, g);
  }
  





  protected void renderOverlay(GameContainer container, Graphics g) {}
  




  public boolean closeRequested()
  {
    return held.closeRequested();
  }
  


  public String getTitle()
  {
    return held.getTitle();
  }
}
