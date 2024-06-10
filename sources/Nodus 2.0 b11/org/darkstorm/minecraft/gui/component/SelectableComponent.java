package org.darkstorm.minecraft.gui.component;

import org.darkstorm.minecraft.gui.listener.SelectableComponentListener;

public abstract interface SelectableComponent
  extends Component
{
  public abstract boolean isSelected();
  
  public abstract void setSelected(boolean paramBoolean);
  
  public abstract void addSelectableComponentListener(SelectableComponentListener paramSelectableComponentListener);
  
  public abstract void removeSelectableComponentListener(SelectableComponentListener paramSelectableComponentListener);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.SelectableComponent
 * JD-Core Version:    0.7.0.1
 */