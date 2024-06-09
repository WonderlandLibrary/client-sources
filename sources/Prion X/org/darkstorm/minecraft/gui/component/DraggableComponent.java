package org.darkstorm.minecraft.gui.component;

public abstract interface DraggableComponent
  extends Component
{
  public abstract boolean isDragging();
  
  public abstract void setDragging(boolean paramBoolean);
}
