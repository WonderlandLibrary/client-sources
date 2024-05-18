package org.darkstorm.minecraft.gui.component;

import org.darkstorm.minecraft.gui.listener.ButtonListener;

public abstract interface Button
  extends Component, TextComponent
{
  public abstract void press();
  
  public abstract void addButtonListener(ButtonListener paramButtonListener);
  
  public abstract void removeButtonListener(ButtonListener paramButtonListener);
  
  public abstract ButtonGroup getGroup();
  
  public abstract void setGroup(ButtonGroup paramButtonGroup);
}
