package org.newdawn.slick.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;











/**
 * @deprecated
 */
public abstract class BasicComponent
  extends AbstractComponent
{
  protected int x;
  protected int y;
  protected int width;
  protected int height;
  
  public BasicComponent(GUIContext container)
  {
    super(container);
  }
  


  public int getHeight()
  {
    return height;
  }
  


  public int getWidth()
  {
    return width;
  }
  


  public int getX()
  {
    return x;
  }
  


  public int getY()
  {
    return y;
  }
  




  public abstract void renderImpl(GUIContext paramGUIContext, Graphics paramGraphics);
  



  public void render(GUIContext container, Graphics g)
    throws SlickException
  {
    renderImpl(container, g);
  }
  


  public void setLocation(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
}
