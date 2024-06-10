package org.darkstorm.minecraft.gui.listener;

import org.darkstorm.minecraft.gui.component.SelectableComponent;

public abstract interface SelectableComponentListener
  extends ComponentListener
{
  public abstract void onSelectedStateChanged(SelectableComponent paramSelectableComponent);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.listener.SelectableComponentListener
 * JD-Core Version:    0.7.0.1
 */