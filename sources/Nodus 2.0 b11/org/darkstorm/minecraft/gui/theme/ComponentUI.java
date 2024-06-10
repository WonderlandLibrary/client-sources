package org.darkstorm.minecraft.gui.theme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Container;

public abstract interface ComponentUI
{
  public abstract void render(Component paramComponent);
  
  public abstract Rectangle getChildRenderArea(Container paramContainer);
  
  public abstract Dimension getDefaultSize(Component paramComponent);
  
  public abstract Color getDefaultBackgroundColor(Component paramComponent);
  
  public abstract Color getDefaultForegroundColor(Component paramComponent);
  
  public abstract Rectangle[] getInteractableRegions(Component paramComponent);
  
  public abstract void handleInteraction(Component paramComponent, Point paramPoint, int paramInt);
  
  public abstract void handleUpdate(Component paramComponent);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.theme.ComponentUI
 * JD-Core Version:    0.7.0.1
 */