package org.darkstorm.minecraft.gui.component;

public abstract interface ProgressBar
  extends Component, BoundedRangeComponent
{
  public abstract boolean isIndeterminate();
  
  public abstract void setIndeterminate(boolean paramBoolean);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.ProgressBar
 * JD-Core Version:    0.7.0.1
 */