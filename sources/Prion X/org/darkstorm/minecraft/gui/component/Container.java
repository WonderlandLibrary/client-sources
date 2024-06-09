package org.darkstorm.minecraft.gui.component;

import org.darkstorm.minecraft.gui.layout.Constraint;
import org.darkstorm.minecraft.gui.layout.LayoutManager;

public abstract interface Container
  extends Component
{
  public abstract LayoutManager getLayoutManager();
  
  public abstract void setLayoutManager(LayoutManager paramLayoutManager);
  
  public abstract Component[] getChildren();
  
  public abstract void add(Component paramComponent, Constraint... paramVarArgs);
  
  public abstract Constraint[] getConstraints(Component paramComponent);
  
  public abstract Component getChildAt(int paramInt1, int paramInt2);
  
  public abstract boolean hasChild(Component paramComponent);
  
  public abstract boolean remove(Component paramComponent);
  
  public abstract void layoutChildren();
}
