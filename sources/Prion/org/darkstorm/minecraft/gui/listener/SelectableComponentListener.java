package org.darkstorm.minecraft.gui.listener;

import org.darkstorm.minecraft.gui.component.SelectableComponent;

public abstract interface SelectableComponentListener
  extends ComponentListener
{
  public abstract void onSelectedStateChanged(SelectableComponent paramSelectableComponent);
}
