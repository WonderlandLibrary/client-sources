package org.darkstorm.minecraft.gui.component;

import me.connorm.Nodus.module.core.NodusModule;
import org.darkstorm.minecraft.gui.listener.ButtonListener;

public abstract interface Button
  extends Component, TextComponent
{
  public abstract void press();
  
  public abstract void addButtonListener(ButtonListener paramButtonListener);
  
  public abstract void removeButtonListener(ButtonListener paramButtonListener);
  
  public abstract ButtonGroup getGroup();
  
  public abstract void setGroup(ButtonGroup paramButtonGroup);
  
  public abstract NodusModule getModule();
  
  public abstract String getCommand();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.Button
 * JD-Core Version:    0.7.0.1
 */