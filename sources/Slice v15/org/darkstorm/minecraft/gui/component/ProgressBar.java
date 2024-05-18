package org.darkstorm.minecraft.gui.component;

public abstract interface ProgressBar
  extends Component, BoundedRangeComponent
{
  public abstract boolean isIndeterminate();
  
  public abstract void setIndeterminate(boolean paramBoolean);
}
