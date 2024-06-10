package org.darkstorm.minecraft.gui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import org.darkstorm.minecraft.gui.theme.Theme;

public abstract interface Component
{
  public abstract Theme getTheme();
  
  public abstract void setTheme(Theme paramTheme);
  
  public abstract void render();
  
  public abstract void update();
  
  public abstract int getX();
  
  public abstract int getY();
  
  public abstract int getWidth();
  
  public abstract int getHeight();
  
  public abstract void setX(int paramInt);
  
  public abstract void setY(int paramInt);
  
  public abstract void setWidth(int paramInt);
  
  public abstract void setHeight(int paramInt);
  
  public abstract Point getLocation();
  
  public abstract Dimension getSize();
  
  public abstract Rectangle getArea();
  
  public abstract Container getParent();
  
  public abstract Color getBackgroundColor();
  
  public abstract Color getForegroundColor();
  
  public abstract void setBackgroundColor(Color paramColor);
  
  public abstract void setForegroundColor(Color paramColor);
  
  public abstract void setParent(Container paramContainer);
  
  public abstract void onMousePress(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void onMouseRelease(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void resize();
  
  public abstract boolean isVisible();
  
  public abstract void setVisible(boolean paramBoolean);
  
  public abstract boolean isEnabled();
  
  public abstract void setEnabled(boolean paramBoolean);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.Component
 * JD-Core Version:    0.7.0.1
 */