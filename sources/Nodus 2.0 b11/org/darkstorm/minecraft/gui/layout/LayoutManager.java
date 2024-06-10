package org.darkstorm.minecraft.gui.layout;

import java.awt.Dimension;
import java.awt.Rectangle;

public abstract interface LayoutManager
{
  public abstract void reposition(Rectangle paramRectangle, Rectangle[] paramArrayOfRectangle, Constraint[][] paramArrayOfConstraint);
  
  public abstract Dimension getOptimalPositionedSize(Rectangle[] paramArrayOfRectangle, Constraint[][] paramArrayOfConstraint);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.layout.LayoutManager
 * JD-Core Version:    0.7.0.1
 */