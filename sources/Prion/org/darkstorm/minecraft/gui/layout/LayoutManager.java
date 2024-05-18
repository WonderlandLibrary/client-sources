package org.darkstorm.minecraft.gui.layout;

import java.awt.Dimension;
import java.awt.Rectangle;

public abstract interface LayoutManager
{
  public abstract void reposition(Rectangle paramRectangle, Rectangle[] paramArrayOfRectangle, Constraint[][] paramArrayOfConstraint);
  
  public abstract Dimension getOptimalPositionedSize(Rectangle[] paramArrayOfRectangle, Constraint[][] paramArrayOfConstraint);
}
