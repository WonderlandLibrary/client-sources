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
