package org.darkstorm.minecraft.gui.component;

public abstract interface ButtonGroup
{
  public abstract void addButton(Button paramButton);
  
  public abstract void removeButton(Button paramButton);
  
  public abstract Button[] getButtons();
}
