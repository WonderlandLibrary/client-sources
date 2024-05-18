package org.darkstorm.minecraft.gui.layout;

import java.awt.Rectangle;

public class BasicLayoutManager implements LayoutManager {
  public BasicLayoutManager() {}
  
  public void reposition(Rectangle area, Rectangle[] componentAreas, Constraint[][] constraints) { int offset = 0;
    for (Rectangle componentArea : componentAreas) {
      if (componentArea == null)
        throw new NullPointerException();
      x = x;
      y += offset;
      offset += height;
    }
  }
  

  public java.awt.Dimension getOptimalPositionedSize(Rectangle[] componentAreas, Constraint[][] constraints)
  {
    int width = 0;int height = 0;
    for (Rectangle component : componentAreas) {
      if (component == null)
        throw new NullPointerException();
      height += height;
      width = Math.max(width, width);
    }
    return new java.awt.Dimension(width, height);
  }
}
