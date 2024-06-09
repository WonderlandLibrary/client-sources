package org.newdawn.slick.gui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.InputAdapter;







public abstract class AbstractComponent
  extends InputAdapter
{
  private static AbstractComponent currentFocus = null;
  

  protected GUIContext container;
  

  protected Set listeners;
  

  private boolean focus = false;
  



  protected Input input;
  



  public AbstractComponent(GUIContext container)
  {
    this.container = container;
    
    listeners = new HashSet();
    
    input = container.getInput();
    input.addPrimaryListener(this);
    
    setLocation(0, 0);
  }
  







  public void addListener(ComponentListener listener)
  {
    listeners.add(listener);
  }
  







  public void removeListener(ComponentListener listener)
  {
    listeners.remove(listener);
  }
  


  protected void notifyListeners()
  {
    Iterator it = listeners.iterator();
    while (it.hasNext()) {
      ((ComponentListener)it.next()).componentActivated(this);
    }
  }
  







  public abstract void render(GUIContext paramGUIContext, Graphics paramGraphics)
    throws SlickException;
  






  public abstract void setLocation(int paramInt1, int paramInt2);
  






  public abstract int getX();
  






  public abstract int getY();
  






  public abstract int getWidth();
  






  public abstract int getHeight();
  






  public void setFocus(boolean focus)
  {
    if (focus) {
      if (currentFocus != null) {
        currentFocus.setFocus(false);
      }
      currentFocus = this;
    }
    else if (currentFocus == this) {
      currentFocus = null;
    }
    
    this.focus = focus;
  }
  




  public boolean hasFocus()
  {
    return focus;
  }
  


  protected void consumeEvent()
  {
    input.consumeEvent();
  }
  




  public void mouseReleased(int button, int x, int y)
  {
    setFocus(Rectangle.contains(x, y, getX(), getY(), getWidth(), 
      getHeight()));
  }
}
