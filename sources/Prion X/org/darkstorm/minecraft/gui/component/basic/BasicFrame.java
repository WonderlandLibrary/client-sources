package org.darkstorm.minecraft.gui.component.basic;

import java.awt.Point;
import org.darkstorm.minecraft.gui.component.AbstractContainer;
import org.darkstorm.minecraft.gui.theme.ComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;

public class BasicFrame extends AbstractContainer implements org.darkstorm.minecraft.gui.component.Frame
{
  private String title;
  private Point dragOffset;
  private boolean pinned;
  private boolean pinnable = true;
  private boolean minimized; private boolean minimizable = true;
  private boolean closable = true;
  
  public void render()
  {
    if (isDragging())
      if (Mouse.isButtonDown(0)) {
        Point mouseLocation = RenderUtil.calculateMouseLocation();
        setX(x - dragOffset.x);
        setY(y - dragOffset.y);
      } else {
        setDragging(false);
      }
    if (minimized) {
      if (ui != null)
        ui.render(this);
    } else
      super.render();
  }
  
  public BasicFrame() {
    this("");
  }
  
  public BasicFrame(String title) {
    setVisible(false);
    this.title = title;
  }
  
  public String getTitle()
  {
    return title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public boolean isDragging()
  {
    return dragOffset != null;
  }
  
  public void setDragging(boolean dragging)
  {
    if (dragging) {
      Point mouseLocation = RenderUtil.calculateMouseLocation();
      dragOffset = new Point(x - getX(), y - 
        getY());
    } else {
      dragOffset = null;
    }
  }
  
  public boolean isPinned() {
    return pinned;
  }
  
  public void setPinned(boolean pinned)
  {
    if (!pinnable)
      pinned = false;
    this.pinned = pinned;
  }
  
  public boolean isPinnable()
  {
    return pinnable;
  }
  
  public void setPinnable(boolean pinnable)
  {
    if (!pinnable)
      pinned = false;
    this.pinnable = pinnable;
  }
  
  public boolean isMinimized()
  {
    return minimized;
  }
  
  public void setMinimized(boolean minimized)
  {
    if (!minimizable)
      minimized = false;
    this.minimized = minimized;
  }
  
  public boolean isMinimizable()
  {
    return minimizable;
  }
  
  public void setMinimizable(boolean minimizable)
  {
    if (!minimizable)
      minimized = false;
    this.minimizable = minimizable;
  }
  
  public void close()
  {
    if (closable) {
      setVisible(false);
    }
  }
  
  public boolean isClosable() {
    return closable;
  }
  
  public void setClosable(boolean closable)
  {
    this.closable = closable;
  }
}
